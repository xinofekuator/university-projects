#include <stdio.h>
#include <string.h>
#include "auxiliar.h"
#include <stdlib.h>

#define MAXBUFF 2048

int main(int argc, char *argv[])
{
  argv0 = "bocabajo"; //de auxiliar.c
  FILE *fichero;
  char linea[MAXBUFF];
  char ** array;
  int size;
  int contador;
  int i;
  int j;
  
  if(argc < 1)
    {
      Error(EX_NOINPUT,"Error en el n�mero de par�metros del main (debe ser mayor o igual que 1)");
    }
    
  if (argc == 1) //caso stdin
    {
      contador = 0;
      size = 1;
      array = (char**)malloc(sizeof(char*));
      while(fgets(linea,MAXBUFF,stdin) != NULL)
	{
	  if(contador >= size)
	    {
	      if((array = (char**)realloc(array, sizeof(char**)*(size+1))) != NULL)
		{
		  size = size+1;;
		}
	      else
		{
		  Error(EX_OSERR,"No se pudo ubicar la memoria din�mica necesaria.");
		}
	    }
	  
	  array[contador] = strdup(linea);
	  contador++;
	}
      for(j=0; j<contador; j++)
	{
	  fprintf(stdout,"%s",array[contador-j-1]);
	  free(array[contador-j-1]);
	}
      free(array);
      
      return EX_OK;
    }
  
  if (strcmp(argv[1],"-h") == 0 || strcmp(argv[1],"--help") == 0)
    {
      fprintf(stdout,"bocabajo: Uso: bocabajo [ fichero... ]\n");
      fprintf(stdout,"bocabajo: Invierte el orden de las lineas de los ficheros (o de la entrada).\n");
      return EX_OK;
    }
  
  for(i=0; i<argc; i++)
    {
      fichero = fopen(argv[i],"r");
      if (fichero == NULL)
	{
	  Error(EX_NOINPUT,"El fichero \"%s\" no puede ser le�do",argv[i]);
	}
    }
  
  for(i = argc-1; i>0; i--)
    {
      contador = 0;
      fichero = fopen(argv[i],"r");
      size = 1;
      array = (char**)malloc(sizeof(char*));
      while(fgets(linea,MAXBUFF,fichero) != NULL)
	{
	  if(contador >= size)
	    {
	      if((array = (char**)realloc(array, sizeof(char**)*(size+1))) != NULL)
		{
		  size = 1+ size;
		}
	      else
		{
		  Error(EX_OSERR,"No se pudo ubicar la memoria din�mica necesaria.");
		}
	    }
	  
	  array[contador] = strdup(linea);
	  contador++;
	}
      for(j=0; j<contador; j++)
	{
	  fprintf(stdout,"%s",array[contador-j-1]);
	  free(array[contador-j-1]);
	}
      free(array);
      
      if(fclose(fichero) != 0)
	{
	  Error(EX_NOINPUT,"El fichero \"%s\" no puede ser cerrado",argv[i]);
	  continue;
	}

    }//Fin del for que recorre ficheros
  return EX_OK;
}
