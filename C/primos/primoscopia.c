#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "auxiliar.h"

int main(int argc, char*argv[])
{

  int esPrimo(long num,int size,long*array); 
    
  long hasta;
  long desde;
  char *end;
  char *valEnv;
  long max;
  long *primos;
  long*puntero;
  int size;
  long i;
  int nimpresos;
   
  argv0 = "primos";
 
  
  if (argc < 1 || argc > 3)
    {
      Error(EX_USAGE,"El número de argumentos no es correcto");
    }
  
  hasta = 100;
  desde = 1;
   
  if (argc == 2 && (strcmp(argv[1],"-h") == 0 || strcmp(argv[1],"--help") == 0))
    {
      fprintf(stdout,"primos: Uso: primos [ hasta [ desde ]]\n");
      fprintf(stdout,"primos: Genera los números primos comprendidos en el intervalo indicado.\n");
      return EX_OK;
    }
  
   if(argc >= 2)
    {
      hasta = strtol(argv[1],&end,10);
      if (*end != '\0' || hasta<=0)
	{
	  Error(EX_USAGE,"El parámetro \"hasta\" no es un número entero positivo");
	}
      
      if(argc >= 3)
	{
	  desde = strtol(argv[2],&end,10);
	  if (*end != '\0' || desde<=0)
	    {
	      Error(EX_USAGE,"El parámetro \"desde\" no es un número entero positivo");
	    }
	  if(desde>hasta)
	    {
	      Error(EX_USAGE,"El parámetro \"desde\" ha de ser menor o igual que \"hasta\".");
	    }
	}
    } /* FIN DE INICIALIZACION DE LAS VARIABLES*/
  
  if( ((valEnv =  getenv("MAX_OUTPUT")) == NULL) || strlen(valEnv)==0 || (max = strtol(valEnv,&end,10)) < 0 || (*end != '\0') )
    {
      max = 100;
    }

  size = 1;
  nimpresos = 0;
  primos = (long*)malloc(sizeof(long));
  *primos = 2;
  puntero = primos;
  for(i=2;i<=hasta;i++)
    {
      if(esPrimo(i,size,primos)==0)
	{
	  if(size>1)
	    {
	      if((primos = (long*)realloc(primos,sizeof(long*)*(size+1))) != NULL)
		{
		  size = size + 1;
		  puntero++;
		  *puntero = i;
		}
	      else
		{
		  Error(EX_OSERR,"No se pudo ubicar la memoria dinámica necesaria.");
		}
	    }
	  if(size==1)
	    {
	      size++;
	    }
	  if(i>=desde)
	    {
	      if (nimpresos < max)
		{
		  fprintf(stdout,"\t%ld\n",i);
		  nimpresos++;
		}
	      else
		{
		  Error(EX_NOPERM,"Se intentó superar el límite de salida establecido por MAX_OUTPUT.");
		}
	    }
	}
    }
  return(EX_OK);
}

//le pasamos el número que queremos ver si es primo y el tamaño del array de primos
int esPrimo(long num,int size,long*array)
{
  int resultado;
  int i;
  long*puntero;
  puntero = array;
  resultado = 0;
  for(i=0;i<=size-2;i++)
    {
      if((num % (*puntero)) == 0)
	{
	  resultado = 1;
	}
      puntero++;
    }
  return resultado;
}
