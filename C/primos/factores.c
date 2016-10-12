#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "auxiliar.h"

int main(int argc, char*argv[])
{
  
  int esPrimo(long numero,int size,long*array); 
  
  char *end;
  char cadena[2048];
  char aux[80];
  long num;
  long*primos;
  long*puntero;
  int size;
  long i;
  int j;
  int k;
  int terminado;
  
  argv0 = "factores";

  if (argc == 2 && (strcmp(argv[1],"-h") == 0 || strcmp(argv[1],"--help") == 0))
    {
      fprintf(stdout,"factores: Uso: factores\n");
      fprintf(stdout,"factores: Factoriza los números presentes en su entrada estándar.\n");
      return EX_OK;
    }
  
  if (argc != 1)
    {
      Error(EX_USAGE,"El número de argumentos no es correcto");
    }
  
  fgets(cadena,2048,stdin);
  terminado = 1;
  size = 1;
  j = 0;
  int  pivote;
  pivote = 0;
  while(terminado == 1)
    {
      if((cadena[j] == '\n')== 1 || (cadena[j] == '\0') == 1)
	{
	  // printf("entra");
	  terminado = 0;
	  return EX_OK;
	}
      //vale 1 cuando se cumple, es decir cuando hay espacio
      while( (cadena[j] == ' ') == 1 && (cadena[j] == '\n') != 1 && (cadena[j] == '\0') != 1)
	{
	  j++;
	}
      k = 0;
      while( ((cadena[j] == ' ') != 1) && (cadena[j] == '\n') != 1 && (cadena[j] == '\0') != 1)
	{
	  aux[k] = cadena[j];
	  k++;
	  j++;
	}
      aux[k]='\0';
      num = strtol(aux,&end,10);
      // fprintf(stdout,"%ld\n",num);
      
      if (*end != '\0' || num < 0)
	{
	  fprintf(stderr,"factores: \"%s\" no es un entero no negativo.\n",aux);
	  continue;
	}
      if ((num == 0) || (num == 1))
	{
	  printf(" %ld\n",num);
	  continue;
	}

      if (num > pivote)
	{
	  size = 1;
	  pivote = num;
	  if( (primos = (long*)malloc(sizeof(long)*num)) == NULL)
	    {
	      Error(EX_OSERR,"No se pudo ubicar la memoria dinámica necesaria.");
	    }
	  *primos = 2;
	  puntero = primos;
	  for(i=2;i<=num;i++)
	    {
	      if(esPrimo(i,size,primos)==0)
		{
		  if(size>1)
		    {
		      size = size + 1;
		      puntero++;
		      *puntero = i;
		    }
		  if(size==1)
		    {
		      size++;
		    }
		}
	    }
	}
      int cociente;
      cociente = num;
      int repetir;
      while (cociente != 1)
	{
	  repetir = 1;
	  for(i=0;i<=size-2 && repetir != 0;i++)
	    {
	      if((cociente%primos[i]) == 0)
		{
		  cociente = cociente / primos[i];
		  if(primos[i] == num)
		    {
		      printf(" %ld",num); 
		      repetir = 0;
		    }
		  else
		    {
		      fprintf(stdout,"\t%ld",primos[i]);
		      repetir = 0;
		    }
		}
	    }
	}
      fprintf(stdout,"\n");
    }
  return(EX_OK);
}

//le pasamos el número que queremos ver si es primo y el tamaño del array de primos
int esPrimo(long numero,int size,long*array)
{
  int resultado;
  int i;
  long*puntero;
  puntero = array;
  resultado = 0;
  for(i=0;i<=size-2;i++)
    {
      if((numero % (*puntero)) == 0)
	{
	  resultado = 1;
	}
      puntero++;
    }
  return resultado;
}
