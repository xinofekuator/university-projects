#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "auxiliar.h"

int main(int argc, char*argv[])
{
  
  double hasta;
  double desde;
  double paso;
  char *end;
  char *valEnv;
  long max;
  long num;
  long i;
  
  argv0 = "secuencia";
  
  if (argc < 1 || argc > 4)
    {
      Error(EX_USAGE,"El número de argumentos no es correcto");
    }
  
  hasta = 10;
  desde = 1;
  paso = 1;
  
  if (argc == 2 && (strcmp(argv[1],"-h") == 0 || strcmp(argv[1],"--help") == 0))
    {
      fprintf(stdout,"secuencia: Uso: secuencia [ hasta [ desde [ paso ]]]\n");
      fprintf(stdout,"secuencia: Genera la secuencia de números en el intervalo y paso indicados.\n");
      return EX_OK;
    }
  
  if(argc >= 2)
    {
      hasta = strtod(argv[1],&end);
      if (*end != '\0')
	{
	  Error(EX_USAGE,"El parámetro \"hasta\" no es un número real válido");
	}
      
      if(argc >= 3)
	{
	  desde = strtod(argv[2],&end);
	  if (*end != '\0')
	    {
	      Error(EX_USAGE,"El parámetro \"desde\" no es un número real válido");
	    }
	  
	  if(argc == 4)
	    {
	      paso = strtod(argv[3],&end);
	      if (*end != '\0')
		{
		  Error(EX_USAGE,"El parámetro \"paso\" no es un número real válido");
		}
	      else if (paso == 0)
		{
		  Error(EX_USAGE,"El parámetro \"paso\" no puede valer 0.");
		}
	      else
		{ 
		  if((hasta-desde)*paso < 0)
		    {
		      Error(EX_USAGE,"El signo de \"paso\" no permite recorrer el intervalo en el sentido \"desde\" a \"hasta\".");
		    }
		}
	    }
	}
    } /* FIN DE INICIALIZACION DE LAS VARIABLES*/
  
  if( ((valEnv =  getenv("MAX_OUTPUT")) == NULL) || strlen(valEnv)==0 || (max = strtol(valEnv,&end,10)) < 0 || (*end != '\0') )
    {
      max = 100;
    }
  
  //le simamos uno porque tambien hay que contar el numero desde el que se empieza.  
  num = (long) floor(((hasta-desde)/paso))+1;
  for(i=0;i<=num-1;i++)
    //le restamos 1 a num ya que num contiene el número de resultados y empezamos en el 0
    {
      if(i+1 <= max)
	{
	  //aux =  desde + (i*paso);
	  fprintf(stdout,"\t%g\n", desde+(i*paso) );
	}
    else
      {
	Error(EX_NOPERM,"Se intentó superar el límite de salida establecido por MAX_OUTPUT.");
      }
    }
  return(EX_OK);
}
