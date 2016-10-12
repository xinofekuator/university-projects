import numpy
import numpy.linalg

#Intercambio la fila i por la fila j en la matriz M

def cambioFila(M, i, j):
    
   aux = numpy.copy(M[i, :])
   M[i, :] = M[j, :]
   M[j, :] = aux
   return M

#Intercambio la columna i por la columna j en la matriz M

def cambioColumna(M, i, j):
    
   aux = numpy.copy(M[:, i])
   M[:, i] = M[:, j]
   M[:, j] = aux
   return M

#Multiplica una fila i de una matriz por A por un valor c

def multiplicarFila(M, i, c):
    
   M[i, :]  = M[i, :] * c * numpy.ones(M.shape[1])
   return M

#Multiplica una columna j de una matriz por A por un valor c

def multiplicarColumna(M, j, c):
    
   M[:, j] = M[:, j]* c*numpy.ones(M.shape[0])
   return M

#Se suman la columna i y la columna j multiplicada por un escalar
#El resultado se guarda en la columna i

def sumarColumnas(M, i, j, escalar):
    
   M[:, i] =  M[:, i] + (escalar * M[:, j])
   return M

#Se suma la fila i por la fila j multiplicada por un escalar
#El resultado se guarda en la columna i

def sumarFilas(M, i, j, escalar):
    
   M[i, :] = M[i, :] + (escalar * M[j, :])
   return M
   
#Reduzco la matriz

def reducirColumnas(M):

   numFilas, numColumnas = M.shape

   i,j = 0,0
   terminado=False
   while not(terminado):
      #Cuando la recorro entera termino
      if i >= numFilas-1 or j >= numColumnas-1:
         terminado=True
      #Compruebo si el elemento es cero  y si lo es avanzo las columnas 
      #hasta encontrar un elemento que no sea cero
      if M[i,j] == 0:
         columnaNoNula = j
         while columnaNoNula < numColumnas and M[i,columnaNoNula] == 0:
            columnaNoNula += 1
        
         #Si no se ha encontrado ningun elemento distinto de cero avanzo las filas
         #y reinicio el bucle
         if columnaNoNula == numColumnas:
            i += 1
            continue
         #Intercambio las columnas del nuevo elemento encontrado con las de
         #la posición anterior
         else:
            cambioColumna(M, j, columnaNoNula)

      
      #Pongo a 1 el pivote para después poder usar el método de suma fácilmente
      pivote = M[i,j]
      multiplicarColumna(M, j, 1.0 / pivote)
      
      #Hago ceros en las columnas con el pivote
      for otraColumna in range(0, numColumnas):
         if (M[i, otraColumna] != 0) and (otraColumna != j):
            escalar = -M[i, otraColumna]
            sumarColumnas(M, otraColumna, j, escalar)

      i += 1; j+= 1

   return M


#Análoga que la anterior pero con las filas

def reducirFilas(M):
   numFilas, numColumnas = M.shape

   i,j = 0,0
   terminado=False
   #Cuando la recorro entera termino
   while not(terminado):
      if i >= numFilas-1 or j >= numColumnas-1:
         terminado=True
        
      #Compruebo si el elemento es cero  y si lo es avanzo las filas 
      #hasta encontrar un elemento que no sea cero
      if M[i, j] == 0:
         filaNoNula = i
         while filaNoNula < numFilas and M[filaNoNula, j] == 0:
            filaNoNula += 1
            
         #Si no se ha encontrado ningun elemento distinto de cero avanzo las columnas
         #y reinicio el bucle
         if filaNoNula == numFilas:
            j += 1
            continue
         #Intercambio las filas del nuevo elemento encontrado con las de
         #la posición anterior
         else:
            cambioFila(M, i, filaNoNula)

      #Pongo a 1 el pivote para después poder usar el método de suma fácilmente
      pivote = M[i, j]
      multiplicarFila(M, i, 1.0 / pivote)
      
      #Hago ceros en las filas con el pivote
      for otraFila in range(0, numFilas):
         if otraFila == i:
            continue
         if M[otraFila, j] != 0:
            escalar = -M[otraFila, j]
            sumarFilas(M, otraFila, j, escalar)

      i += 1; j+= 1

   return M

#Comprueba el número de pivotes que tengo en las columnas
#Esto es lo mismo que comprobar el número de filas que no son todo ceros
#numpy.all devuelve el número total de booleanos que cumplen la expresión

def numPivoteColumnas(A):
   z = numpy.zeros(A.shape[0])
   return [numpy.all(A[:, j] == z) for j in range(A.shape[1])].count(False)

#Comprueba el número de pivotes que tengo en las columnas
#Esto es lo mismo que comprobar el número de columnas que no son todo ceros
#numpy.all devuelve el número total de booleanos que cumplen la expresión

def numPivoteFilas(A):
   z = numpy.zeros(A.shape[1])
   return [numpy.all(A[i, :] == z) for i in range(A.shape[0])].count(False)


#Calcula el número K de betti 

def numBetti(bordeK, bordeKMas1):
   A, B = numpy.copy(bordeK), numpy.copy(bordeKMas1)

   reducirColumnas(A)
   reducirFilas(A)
   reducirColumnas(B)
   reducirFilas(B)
   #Salen todos por la fórmula de dim(C_k)=dim(ker d_k)+dim(Im d_k)
   #Hay que tener en cuenta que dim(Ker d_k)=Z_k es el número de ciclos
   #y que dim(Im d_k)=B_(k-1) que es el número de bordes de dim inferior
   dimensionCadenas = A.shape[1]
   print("Dimensión de las k-cadenas: "+ str(dimensionCadenas))   
   dimensionNucleo = dimensionCadenas - numPivoteColumnas(A)
   print("Dimensión del núcleo del borde k: "+str(dimensionNucleo))
   dimensionImagen = numPivoteFilas(B)
   print("Dimensión de la imagen del borde k: "+str(dimensionImagen))
   
   return dimensionNucleo - dimensionImagen
   
#Le paso una lista de elementos maximales y de ahí obtengo todos los elementos

def sublistas(lista):
   
   resultado=[]
   tam=len(lista)
   for i in range(tam):
      listaAux=[]
      for j in range(tam-1):
         listaAux.append(lista[(i+j)%tam])
      resultado.append(listaAux)
   #En caso de estar vacía termina porque no entra en el caso recursivo   
   for i in resultado:
      resultado= resultado+sublistas(i)
   return resultado
   
def dividirEnListas(lista):
   resultado=[]
   tam=len(lista)
   for i in range(tam):
      listaAux=[]
      for j in range(tam-1):
         listaAux.append(lista[(i+j)%tam])
      resultado.append(listaAux)
   #En caso de estar vacía termina porque no entra en el caso recursivo   
   return  list(sorted(i) for i in resultado)

def quitarRepetidos(lista):
   for i in lista:
      elemento=i
      encontrado=False
      for j in lista:
         if (j==i):
            if(not(encontrado)):
               encontrado=True
            else:
               lista.remove(j)
   return lista
   
#Elimina los elementos repetidos y elimina el [] si lo hubiese y te devuelve la
#lista ordenada de mayor a menor longitud(y cada simplice de menor a mayor)
def ordenarSinRepetidos(lista):
   resultado=list(sorted(i) for i in lista)
   resultado=list(quitarRepetidos(sorted(resultado, reverse=True,key=len)))
      
   return resultado 
    
def elementosSimplice(listaMaximales):
   simplices=listaMaximales
   for i in listaMaximales:
      simplices=simplices+sublistas(i)
   simplices=ordenarSinRepetidos(simplices)
   return ordenarSinRepetidos(simplices)

def crearMatrizSimplices(listaSimplices):
   resultado=[] #array de numpy.array
   #Tamaño del mayor elemento(ordenada de mayor a menor)
   #La matriz de borde k-1 se hará con el tamaño k
   tam=len(listaSimplices[0])
   #para que haya un elemento de dimensión cero
   #listaSimplices.append([])

   for k in range(tam):
      #Haciendo el borde k
      columnas=0
      filas=0
      elementosFila=[]
      elementosColumna=[]
      #contamos las columnas
      for i in listaSimplices:
         if(len(i)==k):
            elementosFila.append(i) #llevar la cuenta del valor del índice que tendrán las columnas en la siguiente iteración
         if(len(i)==k+1):
            elementosColumna.append(i)
      
      elementosFila=list(sorted(i for i in quitarRepetidos(elementosFila)))
      elementosColumna=list(sorted(i for i in quitarRepetidos(elementosColumna)))
      filas=len(elementosFila)
      columnas=len(elementosColumna)
      print("********************")
      print("Cadenas de dimensión "+ str(k)) 
      print("********************")
      print(elementosColumna)
      print("********************")
      
      if(k==0):
         matrizAux=numpy.zeros((1,columnas))
      else:
         matrizAux=numpy.zeros((filas,columnas))
         
      contador=0
      
      #CASO BASE: vértices
      if(k==0):
         for i in range(columnas):
            matrizAux[0,contador]=0
            contador=contador+1
         resultado.append(matrizAux)

         print("Matriz del borde "+ str(k))
         print("********************")
         print(matrizAux)
         print("********************")
      else:
         
         for elemento in elementosColumna:
            filasElem=dividirEnListas(elemento)
            filasElem=list(sorted(i for i in filasElem))
            
            #El primer signo inicial se obtiene elevando -1 a k mod 2
            #Cuando k es par empezará en negativo, por lo que el signo
            #inicial será positivo
            signo=pow(-1,((k-1)%2))
            
            for e in filasElem:                  
                  indiceFila=elementosFila.index(e)
                  #El valor va cambiando de signo
                  matrizAux[indiceFila,contador]=signo
                  signo=signo*(-1)
                  
            contador=contador+1
         resultado.append(matrizAux)

         print("Matriz del borde "+ str(k))
         print("********************")
         print(matrizAux)
         print("********************")
   #caso final en el que el borde k+1 es todo ceros
   matrizAux=numpy.zeros((columnas,1))
   resultado.append(matrizAux)
   print("Matriz del borde "+ str(tam))
   print("********************")
   print(matrizAux)
   print("********************")

   return resultado

def simplicesBetti(simplicesMax):
   simplicesTodos=elementosSimplice(simplicesMax)
   bordes=crearMatrizSimplices(simplicesTodos)
   resultado=[]
   for k in range (len(bordes)-1):
      print("***************")
      print("k = "+str(k))
      bettiK=numBetti(bordes[k],bordes[k+1])
      resultado.append(bettiK)
   return resultado
      
### EJEMPLO DE UN TETRAEDRO HUECO

ej1=simplicesBetti([[0,1,2],[1,2,3],[2,3,0],[3,0,1]])
print("_______________________________")
print("                               ")
print("Números de Betti: " + str(ej1))

### EJEMPLO DE UN TETRAEDRO MACIZO

ej2=simplicesBetti([[0,1,2,3]])
print("_______________________________")
print("                               ")
print("Números de Betti: " + str(ej2))

### EJEMPLO DE UN CUADRADO TRIANGULADO (con un triángulo hueco)

ej3=simplicesBetti([[0,1,2],[1,3],[2,3]])
print("_______________________________")
print("                               ")
print("Números de Betti: " + str(ej3))

## EJEMPLO DE LA ESFERA DE BARNETTE

ej4=simplicesBetti([[1,2,3,4],[3,4,5,6],[1,2,5,6],[1,2,4,7],[1,3,4,7],[3,4,6,7],[3,5,6,7],[1,2,5,7],[2,5,6,7],[2,4,6,7],[1,2,3,8],[2,3,4,8],[3,4,5,8],[4,5,6,8],[1,2,6,8],[1,5,6,8],[1,3,5,8],[2,4,6,8],[1,3,5,7]])
print("_______________________________")
print("                               ")
print("Números de Betti: " + str(ej4))

## 2-ESFERA

esfera=simplicesBetti([[1,3,4],[3,4,2],[1,3,2],[1,4,2]])
print("_______________________________")
print("                               ")
print("Números de Betti: " + str(esfera))

## TORO

ej6=simplicesBetti([[1,2,6],[2,3,7],[3,1,7],[1,4,6],[2,6,7],[1,7,4],[4,6,9],[6,7,8],[7,4,5],[4,5,9],[6,9,8],[8,7,5],[5,9,1],[9,8,3],[8,5,1],[1,2,9],[3,2,9],[8,3,1]])
print("_______________________________")
print("                               ")
print("Números de Betti: " + str(ej6))

##TORO (de SAGE)

toro=simplicesBetti([[0, 1, 2], [2, 3, 6], [0, 1, 5], [1, 3, 6], [1, 3, 4], [1, 2, 4], [2, 3, 5], [0, 3, 5], [0, 3, 4], [0, 2, 6], [0, 4, 6], [2, 4, 5], [4, 5, 6], [1, 5, 6]])
print("_______________________________")
print("                               ")
print("Números de Betti: " + str(toro))

## BOTELLA DE KLEIN

ej7=simplicesBetti([[1,2,6],[2,3,7],[3,1,7],[1,4,6],[2,6,7],[1,7,4],[4,6,9],[6,7,8],[7,4,5],[4,5,9],[6,9,8],[8,7,5],[5,9,1],[9,8,2],[8,5,1],[1,9,3],[9,3,2],[8,2,1]])
print("_______________________________")
print("                               ")
print("Números de Betti: " + str(ej7))

##BOTELLA DE KLEIN (de SAGE)

klein=simplicesBetti([[3, 4, 6], [2, 5, 7], [2, 3, 7], [3, 4, 7], [1, 4, 7], [0, 2, 5], [2, 4, 6], [1, 2, 6], [0, 1, 6], [1, 3, 5], [1, 2, 3], [0, 2, 4], [1, 5, 7], [0, 1, 4], [0, 5, 6], [3, 5, 6]])
print("_______________________________")
print("                               ")
print("Números de Betti: " + str(klein))

## PLANO PROYECTIVO

ej8=simplicesBetti([[1,3,4],[1,2,4],[2,3,6],[3,6,1],[1,5,6],[2,5,1],[2,3,5],[3,4,5],[4,6,2],[5,4,6]])
print("_______________________________")
print("                               ")
print("Números de Betti: " + str(ej8))

## PLANO PROYECTIVO (de Sage)

proyectivo=simplicesBetti([[0, 2, 3], [0, 3, 4], [0, 1, 5], [0, 4, 5], [2, 3, 5], [1, 2, 4], [0, 1, 2], [1, 3, 4], [1, 3, 5], [2, 4, 5]])
print("_______________________________")
print("                               ")
print("Números de Betti: " + str(proyectivo))

## K3 complejo (de Sage) ---> Falla en betta-3 que según SAGE debiera valer 0 y a mí me sale 1

k3=simplicesBetti([[2, 10, 13, 15, 16], [3, 5, 10, 13, 15], [2, 5, 7, 8, 10], [1, 9, 11, 13, 14], [1, 2, 8, 10, 12], [1, 3, 5, 6, 11], [1, 5, 6, 9, 12], [1, 4, 5, 6, 14], [1, 4, 10, 13, 14], [1, 9, 10, 14, 15], [2, 4, 7, 8, 12], [3, 4, 6, 10, 12], [1, 6, 7, 8, 9], [3, 4, 5, 7, 15], [1, 7, 12, 15, 16], [4, 5, 7, 13, 16], [5, 8, 11, 12, 15], [2, 4, 7, 12, 14], [1, 4, 5, 14, 16], [2, 5, 6, 10, 11], [1, 6, 8, 12, 14], [5, 8, 9, 14, 16], [5, 10, 11, 12, 13], [2, 4, 8, 9, 12], [7, 9, 12, 15, 16], [1, 2, 6, 9, 15], [1, 5, 14, 15, 16], [2, 3, 4, 5, 9], [6, 8, 10, 11, 15], [1, 5, 8, 10, 12], [1, 3, 7, 9, 10], [6, 7, 8, 9, 13], [1, 2, 9, 11, 15], [2, 8, 11, 14, 16], [2, 4, 5, 13, 16], [1, 4, 8, 13, 15], [4, 7, 8, 10, 11], [2, 4, 8, 9, 10], [2, 3, 4, 9, 13], [2, 8, 10, 12, 13], [1, 2, 4, 11, 15], [2, 3, 9, 11, 15], [2, 8, 11, 15, 16], [3, 4, 5, 9, 11], [6, 10, 13, 15, 16], [8, 10, 11, 15, 16], [6, 7, 11, 13, 15], [1, 5, 7, 15, 16], [4, 5, 7, 9, 15], [3, 4, 6, 7, 16], [2, 3, 11, 14, 16], [3, 4, 9, 11, 13], [1, 2, 5, 14, 15], [2, 3, 9, 13, 14], [1, 7, 12, 13, 16], [1, 2, 5, 13, 16], [2, 3, 7, 8, 12], [2, 9, 11, 12, 14], [1, 9, 11, 15, 16], [2, 4, 7, 8, 10], [1, 4, 9, 13, 14], [1, 2, 3, 12, 16], [8, 11, 12, 14, 15], [1, 2, 6, 13, 16], [1, 4, 10, 12, 13], [3, 6, 8, 10, 16], [2, 7, 8, 14, 16], [1, 6, 8, 9, 12], [6, 9, 10, 14, 16], [5, 8, 11, 12, 16], [5, 9, 10, 14, 15], [3, 9, 12, 15, 16], [4, 6, 10, 11, 15], [2, 4, 9, 10, 16], [5, 8, 9, 13, 15], [2, 3, 6, 9, 15], [6, 11, 12, 14, 16], [2, 3, 10, 13, 15], [3, 5, 9, 11, 16], [3, 4, 8, 11, 13], [3, 4, 5, 7, 13], [5, 7, 8, 10, 14], [4, 12, 13, 14, 15], [6, 7, 10, 14, 16], [5, 10, 11, 13, 14], [3, 4, 7, 13, 16], [6, 8, 9, 12, 13], [1, 3, 4, 10, 14], [2, 4, 6, 11, 12], [1, 7, 9, 10, 14], [4, 6, 8, 13, 14], [4, 9, 10, 11, 16], [3, 7, 8, 10, 16], [5, 7, 9, 15, 16], [1, 7, 9, 11, 14], [6, 8, 10, 15, 16], [4, 6, 9, 14, 16], [5, 8, 9, 10, 14], [7, 8, 10, 14, 16], [2, 6, 7, 9, 11], [7, 9, 10, 13, 15], [3, 6, 7, 10, 12], [2, 4, 6, 10, 11], [4, 5, 8, 9, 11], [1, 2, 3, 8, 16], [1, 3, 8, 9, 12], [1, 2, 6, 8, 14], [3, 5, 6, 13, 15], [1, 5, 6, 12, 14], [2, 5, 7, 14, 15], [1, 5, 10, 11, 12], [3, 7, 8, 10, 11], [1, 2, 6, 14, 15], [1, 2, 6, 8, 16], [7, 9, 10, 12, 15], [3, 4, 6, 8, 14], [3, 7, 13, 14, 16], [2, 5, 7, 8, 14], [6, 7, 9, 10, 14], [2, 3, 7, 12, 14], [4, 10, 12, 13, 14], [2, 5, 6, 11, 13], [4, 5, 6, 7, 16], [1, 3, 12, 13, 16], [1, 4, 11, 15, 16], [1, 3, 4, 6, 10], [1, 10, 11, 12, 13], [3, 9, 11, 15, 16], [3, 5, 10, 14, 15], [5, 8, 9, 10, 13], [1, 2, 5, 7, 15], [2, 4, 11, 12, 14], [3, 11, 13, 14, 16], [1, 2, 5, 7, 13], [4, 7, 8, 9, 15], [1, 5, 6, 10, 11], [6, 7, 10, 13, 15], [3, 4, 7, 14, 15], [3, 4, 10, 12, 14], [1, 2, 6, 7, 13], [2, 3, 4, 5, 13], [5, 8, 12, 13, 15], [4, 6, 9, 13, 14], [2, 4, 5, 6, 12], [2, 9, 10, 13, 16], [8, 11, 12, 14, 16], [1, 7, 12, 13, 15], [8, 12, 13, 14, 15], [2, 8, 9, 12, 13], [4, 6, 10, 12, 15], [2, 8, 11, 14, 15], [2, 6, 9, 11, 12], [8, 9, 10, 11, 16], [2, 3, 6, 13, 15], [2, 3, 12, 15, 16], [1, 3, 5, 9, 12], [2, 5, 6, 9, 12], [5, 6, 8, 11, 15], [2, 6, 13, 15, 16], [2, 3, 11, 15, 16], [3, 5, 6, 8, 15], [2, 4, 5, 9, 12], [2, 10, 12, 13, 14], [6, 8, 12, 13, 14], [1, 2, 3, 8, 12], [1, 4, 7, 8, 11], [5, 6, 7, 12, 16], [3, 5, 7, 13, 14], [3, 4, 5, 8, 11], [6, 7, 11, 12, 15], [3, 4, 6, 7, 12], [1, 2, 4, 7, 11], [3, 9, 10, 12, 15], [4, 10, 12, 15, 16], [3, 5, 7, 14, 15], [3, 9, 11, 13, 14], [5, 9, 14, 15, 16], [4, 5, 6, 7, 12], [1, 3, 6, 10, 11], [1, 3, 9, 10, 15], [4, 7, 8, 9, 12], [5, 9, 10, 13, 15], [1, 3, 8, 13, 16], [2, 9, 12, 13, 14], [6, 7, 10, 12, 15], [2, 6, 8, 14, 15], [3, 5, 6, 8, 11], [3, 4, 7, 12, 14], [1, 3, 10, 14, 15], [7, 11, 12, 13, 16], [3, 11, 12, 13, 16], [3, 4, 5, 8, 15], [7, 11, 13, 14, 16], [2, 4, 7, 14, 15], [1, 2, 10, 12, 16], [1, 6, 8, 13, 16], [1, 7, 8, 13, 15], [6, 9, 11, 12, 14], [3, 6, 8, 14, 15], [2, 4, 11, 14, 15], [3, 7, 9, 10, 12], [1, 3, 6, 14, 15], [2, 4, 5, 6, 10], [1, 4, 9, 14, 16], [1, 3, 7, 8, 9], [5, 7, 9, 12, 16], [1, 3, 7, 10, 11], [7, 8, 9, 13, 15], [1, 4, 7, 8, 15], [1, 4, 10, 12, 16], [1, 7, 10, 11, 14], [1, 2, 6, 7, 9], [1, 3, 11, 12, 13], [1, 5, 7, 13, 16], [5, 7, 10, 11, 14], [2, 10, 12, 15, 16], [3, 6, 7, 10, 16], [1, 2, 5, 8, 10], [4, 10, 11, 15, 16], [5, 8, 10, 12, 13], [3, 6, 8, 10, 11], [4, 5, 7, 9, 12], [6, 7, 11, 12, 16], [2, 8, 9, 10, 13], [8, 9, 10, 14, 16], [3, 4, 6, 8, 16], [1, 10, 11, 13, 14], [1, 2, 5, 8, 14], [2, 4, 5, 10, 16], [1, 2, 7, 9, 11], [1, 3, 5, 6, 9], [5, 7, 11, 13, 14], [3, 5, 10, 13, 14], [2, 3, 9, 11, 14], [4, 11, 12, 14, 15], [2, 3, 7, 14, 16], [3, 4, 8, 13, 16], [6, 7, 9, 11, 14], [5, 6, 11, 13, 15], [4, 5, 6, 14, 16], [3, 4, 8, 14, 15], [4, 5, 8, 9, 15], [1, 4, 8, 11, 13], [5, 6, 12, 14, 16], [2, 3, 10, 12, 14], [1, 2, 5, 10, 16], [2, 5, 7, 10, 11], [2, 6, 7, 11, 13], [1, 4, 5, 10, 16], [2, 6, 8, 15, 16], [2, 3, 10, 12, 15], [7, 11, 12, 13, 15], [1, 3, 8, 11, 13], [4, 8, 9, 10, 11], [1, 9, 14, 15, 16], [1, 3, 6, 9, 15], [6, 9, 12, 13, 14], [2, 3, 10, 13, 14], [2, 5, 7, 11, 13], [2, 3, 5, 6, 13], [4, 6, 8, 13, 16], [6, 7, 9, 10, 13], [5, 8, 12, 14, 16], [4, 6, 9, 13, 16], [5, 8, 9, 11, 16], [2, 3, 5, 6, 9], [1, 3, 5, 11, 12], [3, 7, 8, 9, 12], [4, 6, 11, 12, 15], [3, 5, 9, 12, 16], [5, 11, 12, 13, 15], [1, 3, 4, 6, 14], [3, 5, 11, 12, 16], [1, 5, 8, 12, 14], [4, 8, 13, 14, 15], [1, 3, 7, 8, 11], [6, 9, 10, 13, 16], [2, 4, 9, 13, 16], [1, 6, 7, 8, 13], [1, 4, 12, 13, 15], [2, 4, 7, 10, 11], [1, 4, 9, 11, 13], [6, 7, 11, 14, 16], [1, 4, 9, 11, 16], [1, 4, 12, 15, 16], [1, 2, 4, 7, 15], [2, 3, 7, 8, 16], [1, 4, 5, 6, 10]])
print("_______________________________")
print("                               ")
print("Números de Betti: " + str(k3))