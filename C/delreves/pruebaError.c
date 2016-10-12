/* Programa de ejemplo de llamada a la función Error */

#include <stdio.h> 
#include "auxiliar.h"

int main(int argc, char *argv[]) { 
  
  argv0 = "miprog"; /* de auxiliar.c */
  Error(EX_OK, "%s", "Hola, esto es una prueba."); 
  
  /* lo siguiente ya no se ejecuta, Error contiene un exit() */
  printf("Esto ya no no sale en pantalla...");
  return 0; 
} 

/* La llamada a gcc incluye a auxiliar.c: 
   gcc -g -Wall -ansi -pedantic -o ejemplo_llamada_Error1 ejemplo_llamada_Error1.c auxiliar.c
   ./ejemplo_llamada_Error1
*/ 
/* Nota: se precisa auxiliar.c y auxiliar.h */
