#include <sys/types.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <time.h>
#include <unistd.h>
#include <dirent.h>
#include <sys/stat.h>
#include <errno.h>
#include <dlfcn.h>
#include "filtrar.h"


/* ---------------- PROTOTIPOS ----------------- */ 
/* Esta funcion monta el filtro indicado y busca el simbolo "tratar"
   que debe contener, y aplica dicha funcion "tratar()" para filtrar
   toda la informacion que le llega por su entrada estandar antes
   de enviarla hacia su salida estandar. */
extern void filtrar_con_filtro(char* nombre_filtro);

/* Esta funcion lanza todos los procesos necesarios para ejecutar los filtros.
   Dichos procesos tendran que tener redirigida su entrada y su salida. */
void preparar_filtros(void);

/* Esta funcion recorrera el directorio pasado como argumento y por cada entrada
   que no sea un directorio o cuyo nombre comience por un punto '.' la lee y 
   la escribe por la salida estandar (que seria redirigida al primero de los 
   filtros, si existe). */
void recorrer_directorio(char* nombre_dir);

/* Esta funcion recorre los procesos arrancados para ejecutar los filtros, 
   esperando a su terminacion y recogiendo su estado de terminacion. */ 
void esperar_terminacion(void);

/* Desarrolle una funcion que permita controlar la temporizacion de la ejecucion
   de los filtros. */ 
extern void preparar_alarma(void);

/* Funcion que realiza el tratamiento correspondiente de la alarma (añadida por mi)*/
void tratar_alarma(int);

/* ---------------- IMPLEMENTACIONES ----------------- */ 
char** filtros;   /* Lista de nombres de los filtros a aplicar */
int    n_filtros; /* Tama~no de dicha lista */
pid_t* pids;      /* Lista de los PIDs de los procesos que ejecutan los filtros */



/* Funcion principal */
int main(int argc, char* argv[])
{
  /* Chequeo de argumentos */
  if(argc<2) 
    {
      /* Invocacion sin argumentos  o con un numero de argumentos insuficiente */
      fprintf(stderr,"Uso: %s directorio [filtro...]\n",argv[0]);	
      exit(1);
    }
  
  
  filtros = &(argv[2]);                             /* Lista de filtros a aplicar */
  n_filtros = argc-2;                               /* Numero de filtros a usar */
  pids = (pid_t*)malloc(sizeof(pid_t)*n_filtros);   /* Lista de pids */
  
  preparar_alarma();
  
  preparar_filtros();
  
  recorrer_directorio(argv[1]);
  
  esperar_terminacion();
  
  return 0;
}


void recorrer_directorio(char* nombre_dir)
{
  
  DIR* dir;
  struct dirent* ent;
  char fich[1024];
  char buff[4096];
  int fd;
  struct stat buffer_estado;
  int estado;
  
  /* Abrir el directorio */
  dir = opendir(nombre_dir);
  /* Tratamiento del error. */
  if(dir == NULL)
    {
      fprintf(stderr,"Error al abrir el directorio '%s'\n",nombre_dir);
      exit(1);
    }
  
  /* Recorremos las entradas del directorio */
  while((ent=readdir(dir))!=NULL)
    {
      /* Nos saltamos las que comienzan por un punto "." */
      if(ent->d_name[0]=='.')
	continue;
      
      /* fich debe contener la ruta completa al fichero */
      sprintf(fich,"%s/%s",nombre_dir,ent->d_name);
      
      /* Nos saltamos las rutas que sean directorios. */
      estado=stat(fich,&buffer_estado);
      if(estado!=0)
	{
	  fprintf(stderr,"AVISO: No se puede stat el fichero '%s'!\n",fich);
	  continue; /*Para que no de error en el Loop pero no siga ejecutando*/
	}
      if(S_ISDIR(buffer_estado.st_mode))
	continue;
      
      /* Abrir el archivo. */
      fd = open(fich,O_RDONLY);
      /* Tratamiento del error*/
      if(fd<0)
	{
	  fprintf(stderr,"AVISO: No se puede abrir el fichero '%s'!\n",fich);
	  continue;
	}
      
      /* Cuidado con escribir en un pipe sin lectores! */
      signal(SIGPIPE,SIG_IGN);

      /* Emitimos el contenido del archivo por la salida estandar. */
      int aux;
      int leidos;
      while( (leidos = read(fd,buff,4096)) > 0)
	{
	  aux =  write(1,buff,leidos);
	  if (aux < 0 )
	    {
	      fprintf(stderr,"Error al emitir el fichero '%s'\n",fich);
	      break;
	    }
	}

      /* Cerrar. */
      close(fd);
      
    }
  if (errno == EBADF)
    {
      fprintf(stderr,"Error al leer el directorio '%s'\n",nombre_dir);
      exit(1);
    }
  
  /* Cerrar. */
  closedir(dir);
  close(1);
  /* IMPORTANTE:
   * Para que los lectores del pipe puedan terminar
   * no deben quedar escritores al otro extremo. */
  // IMPORTANTE
}


void preparar_filtros(void)
{
  int p[2];
  int i;
  
  for(i=n_filtros-1;i>=0;i--)
    {
      /* Tuberia hacia el hijo (que es el proceso que filtra). */
      if(pipe(p)<0)
	{
	  fprintf(stderr,"Error al crear el pipe\n");
	  exit(1);
	}
      pids[i] = fork();
      /* Lanzar nuevo proceso */
      switch(pids[i])
	{
	case -1:
	  fprintf(stderr,"Error al crear proceso %d\n",getpid());
	  exit(1);
	  
	case  0:
	  /* Hijo: Redireccion y Ejecuta el filtro. */
	  dup2(p[0],0);
	  close(p[0]);
	  close(p[1]);
	  /* El nombre termina en ".so" ? */
	  char *cadena;
	  if ( ((cadena = strstr(filtros[i], ".so")) != NULL) && (strcmp(cadena,".so") == 0) )
	    {	/* SI. Montar biblioteca y utilizar filtro. */ 
	      filtrar_con_filtro(filtros[i]);
	      exit(0);
	    }
	  else
	    {	/* NO. Ejecutar como mandato estandar. */
	      execlp(filtros[i],filtros[i],NULL);
	      fprintf(stderr,"Error al ejecutar el mandato '%s'\n",filtros[i]);
	      exit(1);
	    }
       	  //
	default:
	  /* Padre: Redireccion */
	  dup2(p[1],1);
	  close(p[1]);
	  close(p[0]);
	  break;
	}
    }
}


void imprimir_estado(char* filtro, int status)
{
  /* Imprimimos el nombre del filtro y su estado de terminacion */
  if(WIFEXITED(status))
    fprintf(stderr,"%s: %d\n",filtro,WEXITSTATUS(status));
  else
    fprintf(stderr,"%s: senyal %d\n",filtro,WTERMSIG(status));
}


void esperar_terminacion(void)
{
  int i;
  int status;
   for(i=0;i<=n_filtros-1;i++)
     {
       /* Espera al proceso pids[p] */
       if(waitpid(pids[i],&status,0) < 0)
	 {
	   fprintf(stderr,"Error al esperar proceso %d\n",pids[i]);
	   exit(1);
	 }
       /* Muestra su estado. */
       imprimir_estado(filtros[i],status);
     }
}

void filtrar_con_filtro(char* nombre_filtro)
{
  tratar_t *tratar;
  void *lib;
   
  if ((lib =  dlopen(nombre_filtro,RTLD_LAZY)) == NULL) 
    {
      fprintf(stderr, "Error al abrir la biblioteca '%s'\n",nombre_filtro);
      exit(1);
    }
  if ((tratar=dlsym(lib, "tratar")) == NULL) 
    {
      fprintf(stderr, "Error al buscar el simbolo '%s' en '%s'\n","tratar",nombre_filtro);
      exit(1);
    }

  char buff_in[4096]; 
  char buff_out[4096];
  int n;

  while((n = read(0,buff_in,4096)) > 0)
    {
      int error;
      int tam = (*tratar)(buff_in,buff_out,n);
      error = write(1,buff_out,tam);
      //ERROR AL EJECUTAR EL FILTRO
      if (error < 0)
	{
	  fprintf(stderr,"Error al ejecutar el filtro '%s'\n",nombre_filtro);
	}
    }
}

void preparar_alarma(void)
{
  struct sigaction act;
  int time;
  char *timestr;
  char *fin;
  char var[]  = "FILTRAR_TIMEOUT";
  //errores no tratados si el numero es muy grande
  timestr = getenv(var); 
  if(timestr != NULL)
    {
      time = strtol(timestr,&fin,10); //usar mejor strtol
      if(time <= 0 || fin == NULL)
	{
	  fprintf(stderr,"Error FILTRAR_TIMEOUT no es entero positivo: '%s'\n",timestr);
	  exit(1);
	}
      else
	{
	  act.sa_handler = tratar_alarma;
	  act.sa_flags = SA_RESTART;
	  sigaction(SIGALRM, &act, NULL);
	  alarm(time);
	  fprintf(stderr,"AVISO: La alarma vencera tras %d segundos!\n",time);
	}
    }
}

void tratar_alarma(int signo)
{
  int i;
  fprintf(stderr,"AVISO: La alarma ha saltado!\n");
  for(i=0;i<=n_filtros-1;i++)
    {
      if(kill(pids[i],0)==0)
	{ 
	  if(kill(pids[i],SIGKILL) != 0)
	    {
	      fprintf(stderr,"Error al intentar matar proceso %d\n",pids[i]);
	      exit(1);
	    }
	} 
    }
}
