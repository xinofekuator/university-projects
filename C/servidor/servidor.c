#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "message.h"

void Escribir_Puerto (int puerto);

/* FUNCION MAIN DEL PROGRAMA SERVIDOR */
int main(int argc,char* argv[])
{

  int sdudp,sdtcp;
  int fichero;
  int cd;
  socklen_t s1,s2; //longitud
  struct sockaddr_in s_ain,s_ain1;
  struct sockaddr_in c_ain,c_ain1; 
  UDP_Msg msg;
  char buff[512];
  size_t  tam;

  bzero((char*)&msg,sizeof(UDP_Msg)); //rellenamos el mensaje con ceros

  /* El esqueleto de la funcion principal muestra, de forma esquematica la secuencia 
     de operaciones y la correspondiente traza que se sugiere */

  /* Creacion del socket UDP */
  sdudp = socket(AF_INET, SOCK_DGRAM,0); // IPPROTO_UDP);
  if(sdudp == -1)
    {
      fprintf(stderr,"SERVIDOR: Creacion del socket UDP: ERROR\n");
      exit(1);
    }
  fprintf(stderr,"SERVIDOR: Creacion del socket UDP: OK\n"); 
  

  /* Asignacion de la direccion local (del servidor) Puerto UDP*/
  bzero((char *)&s_ain, sizeof(s_ain));
  s_ain.sin_family = AF_INET;
  s_ain.sin_addr.s_addr = inet_addr(HOST_SERVIDOR);
  s_ain.sin_port = htons(0); /* bind() elige un puerto aleatoriamente si le asignamos el puerto 0*/

  s1 = sizeof(c_ain);

  if(bind(sdudp, (struct sockaddr *)&s_ain, sizeof(s_ain))==-1)
    {
      fprintf(stderr,"SERVIDOR: Asignacion del puerto servidor: ERROR\n");
      exit(1);
    }
  fprintf(stderr,"SERVIDOR: Asignacion del puerto servidor: OK\n");

  getsockname(sdudp,(struct sockaddr*)&c_ain,&s1);
  
  /* Escribimos el puerto de servicio */
  Escribir_Puerto(ntohs(c_ain.sin_port)); /* Numero de puerto asignado */
  
  /* Creacion del socket TCP de servicio */
  sdtcp = socket(AF_INET, SOCK_STREAM,0);
  if(sdtcp==-1)
    {
      fprintf(stderr,"SERVIDOR: Creacion del socket TCP: ERROR\n");
      exit(1);
    }
  fprintf(stderr,"SERVIDOR: Creacion del socket TCP: OK\n");
  
  /* Asignacion de la direccion local (del servidor) Puerto TCP*/
  
  bzero((char *)&s_ain1, sizeof(s_ain1));
  s_ain1.sin_family = AF_INET;
  s_ain1.sin_addr.s_addr = INADDR_ANY; /*Cualquier origen*/
  s_ain1.sin_port =htons(0); 

  s2 = sizeof(c_ain1);
  
  if( bind(sdtcp, (struct sockaddr *)&s_ain1, sizeof(s_ain1))==-1)
    {
      fprintf(stderr,"SERVIDOR: Asignacion del puerto servidor: ERROR\n");
      exit(1);
    }
  fprintf(stderr,"SERVIDOR: Asignacion del puerto servidor: OK\n");
  
  /* Aceptamos conexiones por el socket */
  if(listen(sdtcp,1)==-1)
    {
      fprintf(stderr,"SERVIDOR: Aceptacion de peticiones: ERROR\n");
      exit(1);
    }
  fprintf(stderr,"SERVIDOR: Aceptacion de peticiones: OK\n");

  /* Puerto TCP ya disponible */
  if(ntohs(c_ain1.sin_port)<0)
    { 
      fprintf(stderr,"SERVIDOR: Puerto TCP reservado: ERROR\n"); 
      exit(0);
    }
  fprintf(stderr,"SERVIDOR: Puerto TCP reservado: OK\n");

  while(1) /* Bucle de procesar peticiones */
    {
      fprintf(stderr,"SERVIDOR: Esperando mensaje.\n");

      /* Recibo mensaje */
      if(recvfrom(sdudp,(char*) &msg,sizeof(UDP_Msg),0,(struct sockaddr *)&c_ain, &s1)==-1)
	{
	  fprintf(stderr,"SERVIDOR: Mensaje del cliente: ERROR\n");
	  continue;
	}
      
      fprintf(stderr,"SERVIDOR: Mensaje del cliente: OK\n");
      
      if(ntohl(msg.op)==QUIT)
	{                             /* Mensaje QUIT*/
	  fprintf(stderr,"SERVIDOR: QUIT\n");
	  getsockname(sdtcp, (struct sockaddr*)&s_ain1, &s2);
	  msg.puerto = s_ain1.sin_port;
	  msg.op = htonl(OK);
	  fprintf(stderr,"SERVIDOR: Enviando del resultado [OK]: ");
	  if(sendto(sdudp,(char*) &msg, sizeof(UDP_Msg), 0, (struct sockaddr*) &c_ain, s1)<0)
	    { 
	      fprintf(stderr,"ERROR\n"); 
	      continue;
	    } 
	  fprintf(stderr,"OK\n");
	  break;
	}
      else
	{
	  fprintf(stderr,"SERVIDOR: REQUEST(%s,%s)\n",msg.local,msg.remoto);
	  /* Envio del resultado */
	  fichero=open(msg.remoto,O_RDONLY);
	  if(fichero<0)
	    {
	      msg.op = htonl(ERROR); 
	      sendto(sdudp,(char*) &msg, sizeof(UDP_Msg), 0, (struct sockaddr*) &c_ain, s1);
	      fprintf(stderr,"SERVIDOR: Enviando del resultado [ERROR]: ERROR\n");
	      continue;
	    }
	  msg.op = htonl(OK);
	  getsockname(sdtcp, (struct sockaddr*)&s_ain1, &s2);
	  msg.puerto = s_ain1.sin_port;
	  sendto(sdudp,(char*) &msg, sizeof(UDP_Msg), 0, (struct sockaddr*) &c_ain, s1);
	  fprintf(stderr,"SERVIDOR: Enviando del resultado [OK]: OK\n");
	  
	  /* Esperamos la llegada de una conexion */
	  cd = accept(sdtcp, (struct sockaddr *)&c_ain1, &s2);
	  if (cd==-1)
	    {
	      fprintf(stderr,"SERVIDOR: Llegada de un mensaje: ERROR\n");
	      continue;
	      close(cd);
	      close(fichero);
	    }
	  fprintf(stderr,"SERVIDOR: Llegada de un mensaje: OK\n");
	  while((tam=read(fichero,(void*)buff,512))>0)
	    {
	      write(cd,(void*)buff,tam);
	    }
	  close(fichero);
	  close(cd);
	  continue;
	}
      close(sdudp);
      close(sdtcp);
    }
  
  fprintf(stderr,"SERVIDOR: Finalizado\n");
  exit(0);
}

/* Funcion auxiliar que escribe un numero de puerto en el fichero */
void Escribir_Puerto (int puerto)
{
  int fd;
  if((fd=creat(FICHERO_PUERTO,0660))>=0)
    {
      write(fd,&puerto,sizeof(int));
      close(fd);
      fprintf(stderr,"SERVIDOR: Puerto guardado en fichero %s: OK\n",FICHERO_PUERTO);
    }
}



