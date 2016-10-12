#include <stdio.h>
#include <string.h>
#include "auxiliar.h"

#define MAXBUFF 2048

int main (int argc, char*argv[])
{
  argv0 = "delreves"; //de auxiliar.c
  int i;
  int j;
  int salto;
  FILE *fichero;
  char linea[MAXBUFF];

  if (argc < 1)
    {
      Error(EX_NOINPUT,"Erro en el número de paramétros del main (debe ser mayor o igual que 1)");
    }
 
  if(argc == 1)
    {
      salto = 1;
      while(fgets(linea,MAXBUFF,stdin) != NULL)
	{
	  int length = strlen(linea);
	  if(salto == 0)
	    {
	      fprintf(stdout,"\n");
	      salto = 1;
	    }		  
	  for(j = length-1; j>=0; j--)
	    {
	      if(linea[j] != '\n')
		{
		  fprintf(stdout,"%c",linea[j]);
		}
	      else 
		{
		  salto = 0;
		}
	    }//FIN DEL FOR
	  if (salto == 0)
	    {
	      fprintf(stdout,"\n");
	    }
	}//FIN DEL WHILE
      return EX_OK;
    }
  
  if (strcmp(argv[1],"-h") == 0 || strcmp(argv[1],"--help") == 0)
    {
      fprintf(stdout,"delreves: Uso: delreves [ fichero... ]\n");
      fprintf(stdout,"delreves: Invierte el contenido de las lineas de los ficheros (o de la entrada).\n");
      return EX_OK;
    }
  
  for (i = 1; i<argc; i++)
    {
      fichero = fopen(argv[i],"r");
      if (fichero == NULL)
	{
	  Error(EX_NOINPUT,"El fichero \"%s\" no puede ser leído",argv[i]);
	  continue;
	}
      salto = 1;
      while(fgets(linea,MAXBUFF,fichero) != NULL)
	{
	  int length = strlen(linea);
	  if(salto == 0)
	    {
	      fprintf(stdout,"\n");
	      salto = 1;
	    }
	  for(j = length-1; j>=0; j--)
	    {
	      if(linea[j] != '\n')
		{
		fprintf(stdout,"%c",linea[j]);
		}
	      else
		{ 
		  salto = 0;
		}
	    }//FIN DEL FOR
	}//FIN DEL WHILE
      if (salto==0)
	{
	  fprintf(stdout,"\n"); 
	}
      if (i!=argc-1)
	{
	  fprintf(stdout,"\n");
	}
      if (fclose(fichero) != 0)
	{
	  Error(EX_NOINPUT,"El fichero \"%s\" no puede ser cerrado",argv[i]);
	  continue;
	} 
    }//FIN DEL PRIMER FOR QUE RECORRE FICHEROS
  
  return EX_OK; 
}
