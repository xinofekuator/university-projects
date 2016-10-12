# -*- coding: utf-8 -*-
"""
Created on Wed Jun 25 00:33:42 2014

@author: Ignacio
"""

import os,sys

inputfile = ''

def main(argv):
   global inputfile 
   contador=0
   for i in argv:
      if(contador==0):
          inputfile=i
          contador=contador+1
      else:
          exit('Introduce solo un parametro con el nombre del fichero Javascript')
    
   if(contador==0):
       exit('Introduce como parametro el nombre de un fichero Javascript')        
   
   aux=len(inputfile)-1
   if(not(inputfile[aux]=='s' and inputfile[aux-1]=='j' and inputfile[aux-2]=='.')):
       exit('Introduce un fichero javascript (extension .js)')
   #print 'Input file is "', inputfile
   

if __name__ == "__main__":
   main(sys.argv[1:])

########################################
#Pegar el codigo a partir de aqui
########################################


class FileReader:
	def __init__(self, file_name):
		self._file=open(file_name,'r')
		self.filename=os.path.basename(file_name)		
		self.cur_line=self._file.readline()
		self._next=self.cur_line[0]
		self._on_done=None
		self.eof = False
		self.line=1
		self.col=1
	def get_char(self):
		if self.eof:
				return ''
		return self._next
	def next(self):
		if self.eof is not True:
			if self.col == len(self.cur_line):
				self.col = 1
				self.line=self.line+1
				self.cur_line = self._file.readline()
				if self.cur_line == '':
					self.eof =True
					if self._on_done is not None:
						self._on_done(self)
				else:
					self._next=self.cur_line[0]
			else:
				self.col = self.col+1
				self._next=self.cur_line[self.col-1]
		
	def set_eos_handler(self,handler):
		self._on_done=handler
	def has_data(self):
		return not self.eof
  
##############################################
  
errores=open('OUT-errores.txt','w')
tablasDeSimbolos=open('OUT-TablasDeSimbolos.txt','w')
p = open('OUT-parse.txt','w')
t = open('OUT-tokens.txt','w')

def cerrarFicheros():
    errores.close()    
    tablasDeSimbolos.close()
    t.close()
    p.close()
    

##############################################

def isAbreCorchete(c):
	return c in '['

def isCierraCorchete(c):
	return c in ']' 
 
def isCierraLlave(c):
	return c in '}' 
 
def isAbreLLave(c):
	return c in '{'

def isAbreParentesis(c):
	return c in '(' 


def isCierraParentesis(c):
	return c in ')'
 
def isEqual(c):
	return c in '='
    

def isAnd(c):
	return c == '&'

def isNumero(c):
	return c.isdigit();

def isLetra(c):
	return c.isalpha();

def isSeparador(c):
	return c==" " or c=="\t" or c=="\n"

def isNuevaLinea(c):
	return c=="\n"

def isAlfaNumerico(c):
	return c.isalnum()

def isSlash(c):
	return c == '/'

def isAsterisco(c):
	return c == '*'

def isComilla(c):
	return c=='"'

def isMenos(c):
	return c=='-'

def isBarrabaja(c):
	return c == '_'
 
def isMas(c):
    return c == '+'

def isPunto(c):
    return c == '.'
 
def isNotEqual(c):
    return not(isEqual(c))
    
def isNotId(c):
    return not(isNumero(c) or isLetra(c)  or isBarrabaja(c) or isPunto(c))
    
def isNotLetra(c):
    return not(isLetra(c))

def isNotComilla(c):
    return not(isComilla(c))

def isNotMas(c):
    return not(isMas(c))

def isNotNumero(c):
    return not(isNumero(c))
    
def isNotAsterisco(c):
    return not(isAsterisco(c))

def isNotAsteriscoNiSlash(c):
    return not(isAsterisco(c) or isSlash(c))

def isletraDigitoBarrabaja(c):
    return isLetra(c) or isNumero(c) or isBarrabaja(c)

###########################################

def isPalabraClave(String):
    return String in ['var','new', 'Array','prompt','if','do','while','function','document.write','return']
    
###########################################

lexema=''
valor=0
tokens=[]
leer=True
negativo=False

class Nodo:
    def __init__(self,name,salidas,accionesSemanticas):
        self.name = name
        self.salidas = salidas #salidas es un array de duplas (primer elemento el nombre y el segundo las condiciones)
        self.accionesSemanticas = accionesSemanticas    
    
    def salto(self,c):
        for i in self.salidas:
            if (i[1](c)):
                return i[0]      
        errores.write('ERROR EN EL LEXICO: carácter no válido: ' + c)
        cerrarFicheros()
        exit('ERROR EN EL LEXICO: carácter no válido: ' + c)
    
    def ejecutarSemanticas(self,c,nombre):
            self.accionesSemanticas(c,nombre)




class Token:
    def __init__(self,nombre,valor):
        self.valor=valor
        self.nombre=nombre
    def display(self):
        print('<'+str(self.nombre)+','+str(self.valor)+'>')
    def imprimir(self,f):
        #f es el fichero donde vamos a imprimir
        f.write('<'+str(self.nombre)+','+str(self.valor)+'>')   


                
def semantico0(c,nombre):
   global lexema
   global tokens
   global valor
   global negativo
   global leer
   leer=True
   if(nombre==1):
       lexema=c        
   elif(nombre==26):
        negativo=True
   elif(nombre==3):
       valor=int(c)
   elif(nombre==15):
       tokens.append(Token('(',None))
   elif(nombre==16):
       tokens.append(Token(')',None))
   elif(nombre==17):
       tokens.append(Token('{',None))
   elif(nombre==18):
       tokens.append(Token('}',None))
   elif(nombre==19):
       tokens.append(Token('[',None))
   elif(nombre==20):
       tokens.append(Token(']',None))
    
        
def semantico1(c,nombre):
   global lexema
   global leer
   if(nombre==1):
       lexema=lexema+c
   elif(nombre==2):
       global tokens
       if(isPalabraClave(lexema)):
          tokens.append(Token('palabraClave',lexema))
          lexema=''
          leer=False
       else:
          tokens.append(Token('id',lexema))
          lexema=''
          leer=False
   elif(nombre==24):
       lexema=lexema+c


def semantico24(c,nombre):
   global lexema
   if(nombre==24):
       lexema=lexema +c
   elif(nombre== 25):
       global leer
       if(isPalabraClave(lexema)):
           tokens.append(Token('palabraClave',lexema))
           lexema=''
           leer=False
       else:
           errores.write('ERROR EN EL LEXICO:El caracter . no esta permitido en identificadores.')
           cerrarFicheros()
           exit('ERROR EN EL LEXICO:El caracter . no esta permitido en identificadores. ')
           
def semantico3(c,nombre):
    global valor
    global negativo
    global leer
    if (nombre==3):
        valor=valor*10+int(c)
    elif(nombre==4):
        if(valor<=32767):
            if(negativo==True):
                tokens.append(Token('entero',-valor))
                valor=0
                negativo=False
                leer=False
            else:
                tokens.append(Token('entero',valor))
                valor=0
                negativo=False
                leer=False
        else:
            errores.write('ERROR EN EL LEXICO:entero overflow: ' +str(valor))
            cerrarFicheros()
            exit('ERROR EN EL LEXICO:entero overflow: ' +str(valor))
            
            
def semantico5(c,nombre):
    global lexema
    if(nombre==5):
        lexema=lexema+c
    elif(nombre==6):
        tokens.append(Token('cadena',lexema))
        lexema=''

def semanticoNulo(c,nombre):
    return
	
def semantico26(c,nombre):
    global valor
    if(nombre==3):
        valor=valor*10+int(c)
		
def semantico12(c,nombre):
	global tokens
	global leer
	if(nombre==13):
		tokens.append(Token('==',None))
	elif(nombre==14):
		tokens.append(Token('=',None))
		leer=False
def semantico9(c,nombre):
	global tokens
	global valor
	global leer
	if(nombre==10):
		tokens.append(Token('+',None))	
		leer=False	
	elif(nombre==11):
		tokens.append(Token('++',None))
	elif(nombre==3):
		valor=valor*10+int(c)
def semantico7(c,nombre):
	global tokens
	if(nombre==8):
		tokens.append(Token('&&',None))




n0=Nodo(0,[(1,isLetra),(3,isNumero),(5,isComilla),(7,isAnd),(9,isMas),(12,isEqual),(15,isAbreParentesis),(16,isCierraParentesis),(17,isAbreLLave),(18,isCierraLlave),(19,isAbreCorchete),(20,isCierraCorchete),(26,isMenos),(21,isSlash),(0,isSeparador)],semantico0)
n1=Nodo(1,[(1,isletraDigitoBarrabaja),(2,isNotId),(24,isPunto)],semantico1)
n2=Nodo(2,[],None)
n3=Nodo(3,[(3,isNumero),(4,isNotNumero)],semantico3)
n4=Nodo(4,[],None)
n5=Nodo(5,[(5,isNotComilla),(6,isComilla)],semantico5)
n6=Nodo(6,[],None)
n7=Nodo(7,[(8,isAnd)],semantico7)
n8=Nodo(8,[],None)
n9=Nodo(9,[(3,isNumero),(10,isNotMas),(11,isMas)],semantico9)
n10=Nodo(10,[],None)
n11=Nodo(11,[],None)
n12=Nodo(12,[(13,isEqual),(14,isNotEqual)],semantico12)
n13=Nodo(13,[],None)
n14=Nodo(14,[],None)
n15=Nodo(15,[],None)
n16=Nodo(16,[],None)
n17=Nodo(17,[],None)
n18=Nodo(18,[],None)
n19=Nodo(19,[],None)
n20=Nodo(20,[],None)
n21=Nodo(21,[(22,isAsterisco)],semanticoNulo)
n22=Nodo(22,[(22,isNotAsterisco),(23,isAsterisco)],semanticoNulo)
n23=Nodo(23,[(0,isSlash),(22,isNotAsteriscoNiSlash),(23,isAsterisco)],semanticoNulo)
n24=Nodo(24,[(24,isLetra),(25,isNotLetra)],semantico24)
n25=Nodo(25,[],None)
n26=Nodo(26,[(3,isNumero)],semantico26)

nodos=[n0,n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15,n16,n17,n18,n19,n20,n21,n22,n23,n24,n25,n26]
terminales=[n2,n4,n6,n8,n10,n11,n13,n14,n15,n16,n17,n18,n19,n20,n25]

##########
#MAIN DEL LEXICO
##########
lector=FileReader(inputfile)
estado=nodos[0]
while(lector.has_data()):
    caracter=lector.get_char()
    salto=estado.salto(caracter)
    estado.ejecutarSemanticas(caracter,salto)
    estado=nodos[salto]
    for i in terminales:
        if i.name==estado.name:
            estado=nodos[0]
            break
        
    if(leer):
        lector.next()
        
if(not(estado.name==0)):
    salto=estado.salto(' ')
    estado.ejecutarSemanticas(' ',salto)
    estado=nodos[salto]
    if(not(estado in terminales)):
        errores.write('ERROR EN EL LEXICO: Hay un error lexico en el codigo')
        cerrarFicheros()
        exit('ERROR EN EL LEXICO: Hay un error lexico en el codigo')

#Ahora imprimimos el fichero de los tokens en el formato de la practica

t.write('//Listado de tokens \n')
t.write('\n')
for i in tokens:
    i.imprimir(t)
    t.write('\n')

##########################

def lexicoASintactico(c):
    resultado=[]
    for i in c:
        if(i.nombre=='palabraClave'):
            resultado.append(i.valor)
        else:
            resultado.append(i.nombre)
    resultado.append('$')
    return resultado

# -*- coding: utf-8 -*-
"""
Created on Fri Jun 20 13:30:53 2014

@author: Ignacio
"""

action=[]
goto=[]
aux1=[]
aux2=[]

#primer indice de action y goto se refiere a los estados

for i in range(540): #recorre los estados
    action.append(['vacio','vacio','vacio','vacio','vacio','vacio','vacio',
                   'vacio','vacio','vacio','vacio','vacio','vacio','vacio',
                   'vacio','vacio','vacio','vacio','vacio','vacio','vacio',
                   'vacio','vacio','vacio','vacio'])

for i in range(540): #recorre los estados
    goto.append([-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]) #16 simbolos no terminales

###########
#Producciones

producciones=[]
for i in range(57):
    producciones.append(('izda',['1dcha','2cha']))

producciones[1] = ('S',['H'])
producciones[2] = ('H',['H','A'])
producciones[3] = ('H',['H' , 'D'])
producciones[4] = ('H',['H' , 'W'])
producciones[5] = ('H',['H' , 'F'])
producciones[6] = ('H',['H' , 'P'])
producciones[7] = ('H',['H' , 'M'])
producciones[8] = ('H',[])
producciones[9] = ('H1',['H1','A'])
producciones[10] = ('H1',['H1' ,'D1'])
producciones[11] = ('H1',['H1' , 'W'])
producciones[12] = ('H1',['H1' , 'F'])
producciones[13] = ('H1',['H1' , 'P'])
producciones[14] = ('H1',['H1' , 'M'])
producciones[15] = ('H1',[])
producciones[16] = ('A',['I' , '=' , 'E'])
producciones[17] = ('A',['I' ,'=', '++','I'])
producciones[18] = ('I',['id'])
producciones[19] = ('I',['id','[','N',']'])
producciones[20] = ('D',['var' , 'id'])
producciones[21] = ('D',['var', 'id', '=', 'new','Array','(','entero',')'])
producciones[22] = ('D',['id' , '(' , 'N1',')'])
producciones[23] = ('D',['++' , 'I'])
producciones[24] = ('N1',['N'])
producciones[25] = ('N1',[])
producciones[26] = ('D',['function' , 'id' , '(' , ')' , '{' , 'H1' , 'return' , '}'])
producciones[27] = ('D',['function' , 'id' , '(' , 'id' , ')' , '{' , 'H1' ,'return' , '}'])
producciones[28] = ('D',['function' , 'id' , '(' , ')' , '{' , 'H1' , 'return' , 'E' , '}'])
producciones[29] = ('D',['function' , 'id' , '(' , 'id' , ')' , '{' , 'H1' ,'return','E', '}'])
producciones[30] = ('D1',['var' , 'id'])
producciones[31] = ('D1',['var', 'id', '=', 'new','Array','(','entero',')'])
producciones[32] = ('D1',['id' , '(' , 'N1',')'])
producciones[33] = ('D1',['++' , 'I'])
producciones[34] = ('W',['do','{','H1','}','while','(','E',')'])
producciones[35] = ('F',['if','(','E',')','R'])
producciones[36] = ('R',['A'])
producciones[37] = ('R',['D'])
producciones[38] = ('R',['W'])
producciones[39] = ('R',['F'])
producciones[40] = ('R',['P'])
producciones[41] = ('R',['M'])
producciones[42] = ('P',['prompt','(','id',')'])
producciones[43] = ('M',['document.write', '(', 'N',')'])
producciones[44] = ('M',['document.write', '(', 'cadena',')'])
producciones[45] = ('E',['E', '&&','E1'])
producciones[46] = ('E',['E1'])
producciones[47] = ('E1',['N','==','N'])
producciones[48] = ('E1',['N'])
producciones[49] = ('N',['N', '+','entero'])
producciones[50] = ('N',['N','+','id'])
producciones[51] = ('N',['N','+','id','[','N',']'])
producciones[52] = ('N',['N','+','id','(','N1',')'])
producciones[53] = ('N',['entero'])
producciones[54] = ('N',['id'])
producciones[55] = ('N',['id','[','N',']'])
producciones[56] = ('N',['id','(','N1',')'])

##########################
#TABLA DE ACCIONES Y GOTO
##########################

action[0][0] = 'r(8)';action[0][1] = 'r(8)';action[0][11] = 'r(8)';action[0][13] = 'r(8)';
action[0][14] = 'r(8)';action[0][15] = 'r(8)';action[0][19] = 'r(8)';action[0][21] = 'r(8)';
goto[0][0] = 1;goto[0][1] = 2;action[0][24] = 'r(8)';action[1][24] = 'Aceptar';
action[2][0] = 's(13)';action[2][1] = 's(10)';action[2][11] = 's(12)';action[2][13] = 's(16)';
action[2][14] = 's(11)';action[2][15] = 's(17)';action[2][19] = 's(15)';action[2][21] = 's(14)';
goto[2][2] = 3;goto[2][3] = 4;goto[2][4] = 5;goto[2][5] = 6;
goto[2][6] = 7;goto[2][7] = 8;goto[2][10] = 9;action[2][24] = 'r(1)';
action[3][0] = 'r(2)';action[3][1] = 'r(2)';action[3][11] = 'r(2)';action[3][13] = 'r(2)';
action[3][14] = 'r(2)';action[3][15] = 'r(2)';action[3][19] = 'r(2)';action[3][21] = 'r(2)';
action[3][24] = 'r(2)';action[4][0] = 'r(3)';action[4][1] = 'r(3)';action[4][11] = 'r(3)';
action[4][13] = 'r(3)';action[4][14] = 'r(3)';action[4][15] = 'r(3)';action[4][19] = 'r(3)';
action[4][21] = 'r(3)';action[4][24] = 'r(3)';action[5][0] = 'r(4)';action[5][1] = 'r(4)';
action[5][11] = 'r(4)';action[5][13] = 'r(4)';action[5][14] = 'r(4)';action[5][15] = 'r(4)';
action[5][19] = 'r(4)';action[5][21] = 'r(4)';action[5][24] = 'r(4)';action[6][0] = 'r(5)';
action[6][1] = 'r(5)';action[6][11] = 'r(5)';action[6][13] = 'r(5)';action[6][14] = 'r(5)';
action[6][15] = 'r(5)';action[6][19] = 'r(5)';action[6][21] = 'r(5)';action[6][24] = 'r(5)';
action[7][0] = 'r(6)';action[7][1] = 'r(6)';action[7][11] = 'r(6)';action[7][13] = 'r(6)';
action[7][14] = 'r(6)';action[7][15] = 'r(6)';action[7][19] = 'r(6)';action[7][21] = 'r(6)';
action[7][24] = 'r(6)';action[8][0] = 'r(7)';action[8][1] = 'r(7)';action[8][11] = 'r(7)';
action[8][13] = 'r(7)';action[8][14] = 'r(7)';action[8][15] = 'r(7)';action[8][19] = 'r(7)';
action[8][21] = 'r(7)';action[8][24] = 'r(7)';action[9][8] = 's(18)';action[10][2] = 's(20)';
action[10][6] = 's(19)';action[10][8] = 'r(18)';action[11][1] = 's(21)';action[12][1] = 's(23)';
goto[12][10] = 22;action[13][1] = 's(24)';action[14][4] = 's(25)';action[15][2] = 's(26)';
action[16][2] = 's(27)';action[17][2] = 's(28)';action[18][1] = 's(34)';action[18][11] = 's(30)';
action[18][23] = 's(33)';goto[18][11] = 29;goto[18][12] = 32;goto[18][15] = 31;
action[19][1] = 's(37)';action[19][23] = 's(36)';goto[19][12] = 35;action[20][1] = 's(41)';
action[20][3] = 'r(25)';action[20][23] = 's(40)';goto[20][12] = 39;goto[20][13] = 38;
action[21][0] = 'r(20)';action[21][1] = 'r(20)';action[21][8] = 's(42)';action[21][11] = 'r(20)';
action[21][13] = 'r(20)';action[21][14] = 'r(20)';action[21][15] = 'r(20)';action[21][19] = 'r(20)';
action[21][21] = 'r(20)';action[21][24] = 'r(20)';action[22][0] = 'r(23)';action[22][1] = 'r(23)';
action[22][11] = 'r(23)';action[22][13] = 'r(23)';action[22][14] = 'r(23)';action[22][15] = 'r(23)';
action[22][19] = 'r(23)';action[22][21] = 'r(23)';action[22][24] = 'r(23)';action[23][0] = 'r(18)';
action[23][1] = 'r(18)';action[23][6] = 's(43)';action[23][11] = 'r(18)';action[23][13] = 'r(18)';
action[23][14] = 'r(18)';action[23][15] = 'r(18)';action[23][19] = 'r(18)';action[23][21] = 'r(18)';
action[23][24] = 'r(18)';action[24][2] = 's(44)';action[25][1] = 'r(15)';action[25][5] = 'r(15)';
action[25][11] = 'r(15)';action[25][13] = 'r(15)';action[25][14] = 'r(15)';action[25][15] = 'r(15)';
action[25][19] = 'r(15)';action[25][21] = 'r(15)';goto[25][8] = 45;action[26][1] = 's(34)';
action[26][23] = 's(33)';goto[26][11] = 46;goto[26][12] = 32;goto[26][15] = 47;
action[27][1] = 's(48)';action[28][1] = 's(41)';action[28][22] = 's(50)';action[28][23] = 's(40)';
goto[28][12] = 49;action[29][0] = 'r(16)';action[29][1] = 'r(16)';action[29][11] = 'r(16)';
action[29][13] = 'r(16)';action[29][14] = 'r(16)';action[29][15] = 'r(16)';action[29][18] = 's(51)';
action[29][19] = 'r(16)';action[29][21] = 'r(16)';action[29][24] = 'r(16)';action[30][1] = 's(23)';
goto[30][10] = 52;action[31][0] = 'r(46)';action[31][1] = 'r(46)';action[31][11] = 'r(46)';
action[31][13] = 'r(46)';action[31][14] = 'r(46)';action[31][15] = 'r(46)';action[31][18] = 'r(46)';
action[31][19] = 'r(46)';action[31][21] = 'r(46)';action[31][24] = 'r(46)';action[32][0] = 'r(48)';
action[32][1] = 'r(48)';action[32][3] = 'r(48)';action[32][9] = 's(53)';action[32][10] = 's(54)';
action[32][11] = 'r(48)';action[32][13] = 'r(48)';action[32][14] = 'r(48)';action[32][15] = 'r(48)';
action[32][18] = 'r(48)';action[32][19] = 'r(48)';action[32][21] = 'r(48)';action[32][24] = 'r(48)';
action[33][0] = 'r(53)';action[33][1] = 'r(53)';action[33][3] = 'r(53)';action[33][9] = 'r(53)';
action[33][10] = 'r(53)';action[33][11] = 'r(53)';action[33][13] = 'r(53)';action[33][14] = 'r(53)';
action[33][15] = 'r(53)';action[33][18] = 'r(53)';action[33][19] = 'r(53)';action[33][21] = 'r(53)';
action[33][24] = 'r(53)';action[34][0] = 'r(54)';action[34][1] = 'r(54)';action[34][2] = 's(56)';
action[34][3] = 'r(54)';action[34][6] = 's(55)';action[34][9] = 'r(54)';action[34][10] = 'r(54)';
action[34][11] = 'r(54)';action[34][13] = 'r(54)';action[34][14] = 'r(54)';action[34][15] = 'r(54)';
action[34][18] = 'r(54)';action[34][19] = 'r(54)';action[34][21] = 'r(54)';action[34][24] = 'r(54)';
action[35][7] = 's(57)';action[35][10] = 's(58)';action[36][7] = 'r(53)';action[36][10] = 'r(53)';
action[37][2] = 's(60)';action[37][6] = 's(59)';action[37][7] = 'r(54)';action[37][10] = 'r(54)';
action[38][3] = 's(61)';action[39][3] = 'r(24)';action[39][10] = 's(62)';action[40][3] = 'r(53)';
action[40][10] = 'r(53)';action[41][2] = 's(64)';action[41][3] = 'r(54)';action[41][6] = 's(63)';
action[41][10] = 'r(54)';action[42][16] = 's(65)';action[43][1] = 's(37)';action[43][23] = 's(36)';
goto[43][12] = 66;action[44][1] = 's(68)';action[44][3] = 's(67)';action[45][1] = 's(77)';
action[45][5] = 's(69)';action[45][11] = 's(79)';action[45][13] = 's(82)';action[45][14] = 's(78)';
action[45][15] = 's(83)';action[45][19] = 's(81)';action[45][21] = 's(80)';goto[45][2] = 70;
goto[45][4] = 72;goto[45][5] = 73;goto[45][6] = 74;goto[45][7] = 75;
goto[45][9] = 71;goto[45][10] = 76;action[46][3] = 's(84)';action[46][18] = 's(85)';
action[47][3] = 'r(46)';action[47][18] = 'r(46)';action[48][3] = 's(86)';action[49][3] = 's(87)';
action[49][10] = 's(62)';action[50][3] = 's(88)';action[51][1] = 's(92)';action[51][23] = 's(91)';
goto[51][12] = 90;goto[51][15] = 89;action[52][0] = 'r(17)';action[52][1] = 'r(17)';
action[52][11] = 'r(17)';action[52][13] = 'r(17)';action[52][14] = 'r(17)';action[52][15] = 'r(17)';
action[52][19] = 'r(17)';action[52][21] = 'r(17)';action[52][24] = 'r(17)';action[53][1] = 's(95)';
action[53][23] = 's(94)';goto[53][12] = 93;action[54][1] = 's(97)';action[54][23] = 's(96)';
action[55][1] = 's(37)';action[55][23] = 's(36)';goto[55][12] = 98;action[56][1] = 's(41)';
action[56][3] = 'r(25)';action[56][23] = 's(40)';goto[56][12] = 39;goto[56][13] = 99;
action[57][8] = 'r(19)';action[58][1] = 's(101)';action[58][23] = 's(100)';action[59][1] = 's(37)';
action[59][23] = 's(36)';goto[59][12] = 102;action[60][1] = 's(41)';action[60][3] = 'r(25)';
action[60][23] = 's(40)';goto[60][12] = 39;goto[60][13] = 103;action[61][0] = 'r(22)';
action[61][1] = 'r(22)';action[61][11] = 'r(22)';action[61][13] = 'r(22)';action[61][14] = 'r(22)';
action[61][15] = 'r(22)';action[61][19] = 'r(22)';action[61][21] = 'r(22)';action[61][24] = 'r(22)';
action[62][1] = 's(105)';action[62][23] = 's(104)';action[63][1] = 's(37)';action[63][23] = 's(36)';
goto[63][12] = 106;action[64][1] = 's(41)';action[64][3] = 'r(25)';action[64][23] = 's(40)';
goto[64][12] = 39;goto[64][13] = 107;action[65][17] = 's(108)';action[66][7] = 's(109)';
action[66][10] = 's(58)';action[67][4] = 's(110)';action[68][3] = 's(111)';action[69][20] = 's(112)';
action[70][1] = 'r(9)';action[70][5] = 'r(9)';action[70][11] = 'r(9)';action[70][13] = 'r(9)';
action[70][14] = 'r(9)';action[70][15] = 'r(9)';action[70][19] = 'r(9)';action[70][21] = 'r(9)';
action[71][1] = 'r(10)';action[71][5] = 'r(10)';action[71][11] = 'r(10)';action[71][13] = 'r(10)';
action[71][14] = 'r(10)';action[71][15] = 'r(10)';action[71][19] = 'r(10)';action[71][21] = 'r(10)';
action[72][1] = 'r(11)';action[72][5] = 'r(11)';action[72][11] = 'r(11)';action[72][13] = 'r(11)';
action[72][14] = 'r(11)';action[72][15] = 'r(11)';action[72][19] = 'r(11)';action[72][21] = 'r(11)';
action[73][1] = 'r(12)';action[73][5] = 'r(12)';action[73][11] = 'r(12)';action[73][13] = 'r(12)';
action[73][14] = 'r(12)';action[73][15] = 'r(12)';action[73][19] = 'r(12)';action[73][21] = 'r(12)';
action[74][1] = 'r(13)';action[74][5] = 'r(13)';action[74][11] = 'r(13)';action[74][13] = 'r(13)';
action[74][14] = 'r(13)';action[74][15] = 'r(13)';action[74][19] = 'r(13)';action[74][21] = 'r(13)';
action[75][1] = 'r(14)';action[75][5] = 'r(14)';action[75][11] = 'r(14)';action[75][13] = 'r(14)';
action[75][14] = 'r(14)';action[75][15] = 'r(14)';action[75][19] = 'r(14)';action[75][21] = 'r(14)';
action[76][8] = 's(113)';action[77][2] = 's(114)';action[77][6] = 's(19)';action[77][8] = 'r(18)';
action[78][1] = 's(115)';action[79][1] = 's(117)';goto[79][10] = 116;action[80][4] = 's(118)';
action[81][2] = 's(119)';action[82][2] = 's(120)';action[83][2] = 's(121)';action[84][0] = 's(13)';
action[84][1] = 's(10)';action[84][11] = 's(12)';action[84][13] = 's(16)';action[84][14] = 's(11)';
action[84][15] = 's(17)';action[84][19] = 's(15)';action[84][21] = 's(14)';goto[84][2] = 123;
goto[84][3] = 124;goto[84][4] = 125;goto[84][5] = 126;goto[84][6] = 127;
goto[84][7] = 128;goto[84][10] = 9;goto[84][14] = 122;action[85][1] = 's(132)';
action[85][23] = 's(131)';goto[85][12] = 130;goto[85][15] = 129;action[86][0] = 'r(42)';
action[86][1] = 'r(42)';action[86][11] = 'r(42)';action[86][13] = 'r(42)';action[86][14] = 'r(42)';
action[86][15] = 'r(42)';action[86][19] = 'r(42)';action[86][21] = 'r(42)';action[86][24] = 'r(42)';
action[87][0] = 'r(43)';action[87][1] = 'r(43)';action[87][11] = 'r(43)';action[87][13] = 'r(43)';
action[87][14] = 'r(43)';action[87][15] = 'r(43)';action[87][19] = 'r(43)';action[87][21] = 'r(43)';
action[87][24] = 'r(43)';action[88][0] = 'r(44)';action[88][1] = 'r(44)';action[88][11] = 'r(44)';
action[88][13] = 'r(44)';action[88][14] = 'r(44)';action[88][15] = 'r(44)';action[88][19] = 'r(44)';
action[88][21] = 'r(44)';action[88][24] = 'r(44)';action[89][0] = 'r(45)';action[89][1] = 'r(45)';
action[89][11] = 'r(45)';action[89][13] = 'r(45)';action[89][14] = 'r(45)';action[89][15] = 'r(45)';
action[89][18] = 'r(45)';action[89][19] = 'r(45)';action[89][21] = 'r(45)';action[89][24] = 'r(45)';
action[90][0] = 'r(48)';action[90][1] = 'r(48)';action[90][9] = 's(133)';action[90][10] = 's(134)';
action[90][11] = 'r(48)';action[90][13] = 'r(48)';action[90][14] = 'r(48)';action[90][15] = 'r(48)';
action[90][18] = 'r(48)';action[90][19] = 'r(48)';action[90][21] = 'r(48)';action[90][24] = 'r(48)';
action[91][0] = 'r(53)';action[91][1] = 'r(53)';action[91][9] = 'r(53)';action[91][10] = 'r(53)';
action[91][11] = 'r(53)';action[91][13] = 'r(53)';action[91][14] = 'r(53)';action[91][15] = 'r(53)';
action[91][18] = 'r(53)';action[91][19] = 'r(53)';action[91][21] = 'r(53)';action[91][24] = 'r(53)';
action[92][0] = 'r(54)';action[92][1] = 'r(54)';action[92][2] = 's(136)';action[92][6] = 's(135)';
action[92][9] = 'r(54)';action[92][10] = 'r(54)';action[92][11] = 'r(54)';action[92][13] = 'r(54)';
action[92][14] = 'r(54)';action[92][15] = 'r(54)';action[92][18] = 'r(54)';action[92][19] = 'r(54)';
action[92][21] = 'r(54)';action[92][24] = 'r(54)';action[93][0] = 'r(47)';action[93][1] = 'r(47)';
action[93][3] = 'r(47)';action[93][10] = 's(137)';action[93][11] = 'r(47)';action[93][13] = 'r(47)';
action[93][14] = 'r(47)';action[93][15] = 'r(47)';action[93][18] = 'r(47)';action[93][19] = 'r(47)';
action[93][21] = 'r(47)';action[93][24] = 'r(47)';action[94][0] = 'r(53)';action[94][1] = 'r(53)';
action[94][3] = 'r(53)';action[94][10] = 'r(53)';action[94][11] = 'r(53)';action[94][13] = 'r(53)';
action[94][14] = 'r(53)';action[94][15] = 'r(53)';action[94][18] = 'r(53)';action[94][19] = 'r(53)';
action[94][21] = 'r(53)';action[94][24] = 'r(53)';action[95][0] = 'r(54)';action[95][1] = 'r(54)';
action[95][2] = 's(139)';action[95][3] = 'r(54)';action[95][6] = 's(138)';action[95][10] = 'r(54)';
action[95][11] = 'r(54)';action[95][13] = 'r(54)';action[95][14] = 'r(54)';action[95][15] = 'r(54)';
action[95][18] = 'r(54)';action[95][19] = 'r(54)';action[95][21] = 'r(54)';action[95][24] = 'r(54)';
action[96][0] = 'r(49)';action[96][1] = 'r(49)';action[96][3] = 'r(49)';action[96][9] = 'r(49)';
action[96][10] = 'r(49)';action[96][11] = 'r(49)';action[96][13] = 'r(49)';action[96][14] = 'r(49)';
action[96][15] = 'r(49)';action[96][18] = 'r(49)';action[96][19] = 'r(49)';action[96][21] = 'r(49)';
action[96][24] = 'r(49)';action[97][0] = 'r(50)';action[97][1] = 'r(50)';action[97][2] = 's(141)';
action[97][3] = 'r(50)';action[97][6] = 's(140)';action[97][9] = 'r(50)';action[97][10] = 'r(50)';
action[97][11] = 'r(50)';action[97][13] = 'r(50)';action[97][14] = 'r(50)';action[97][15] = 'r(50)';
action[97][18] = 'r(50)';action[97][19] = 'r(50)';action[97][21] = 'r(50)';action[97][24] = 'r(50)';
action[98][7] = 's(142)';action[98][10] = 's(58)';action[99][3] = 's(143)';action[100][7] = 'r(49)';
action[100][10] = 'r(49)';action[101][2] = 's(145)';action[101][6] = 's(144)';action[101][7] = 'r(50)';
action[101][10] = 'r(50)';action[102][7] = 's(146)';action[102][10] = 's(58)';action[103][3] = 's(147)';
action[104][3] = 'r(49)';action[104][10] = 'r(49)';action[105][2] = 's(149)';action[105][3] = 'r(50)';
action[105][6] = 's(148)';action[105][10] = 'r(50)';action[106][7] = 's(150)';action[106][10] = 's(58)';
action[107][3] = 's(151)';action[108][2] = 's(152)';action[109][0] = 'r(19)';action[109][1] = 'r(19)';
action[109][11] = 'r(19)';action[109][13] = 'r(19)';action[109][14] = 'r(19)';action[109][15] = 'r(19)';
action[109][19] = 'r(19)';action[109][21] = 'r(19)';action[109][24] = 'r(19)';action[110][1] = 'r(15)';
action[110][11] = 'r(15)';action[110][12] = 'r(15)';action[110][13] = 'r(15)';action[110][14] = 'r(15)';
action[110][15] = 'r(15)';action[110][19] = 'r(15)';action[110][21] = 'r(15)';goto[110][8] = 153;
action[111][4] = 's(154)';action[112][2] = 's(155)';action[113][1] = 's(132)';action[113][11] = 's(157)';
action[113][23] = 's(131)';goto[113][11] = 156;goto[113][12] = 159;goto[113][15] = 158;
action[114][1] = 's(41)';action[114][3] = 'r(25)';action[114][23] = 's(40)';goto[114][12] = 39;
goto[114][13] = 160;action[115][1] = 'r(30)';action[115][5] = 'r(30)';action[115][8] = 's(161)';
action[115][11] = 'r(30)';action[115][13] = 'r(30)';action[115][14] = 'r(30)';action[115][15] = 'r(30)';
action[115][19] = 'r(30)';action[115][21] = 'r(30)';action[116][1] = 'r(33)';action[116][5] = 'r(33)';
action[116][11] = 'r(33)';action[116][13] = 'r(33)';action[116][14] = 'r(33)';action[116][15] = 'r(33)';
action[116][19] = 'r(33)';action[116][21] = 'r(33)';action[117][1] = 'r(18)';action[117][5] = 'r(18)';
action[117][6] = 's(162)';action[117][11] = 'r(18)';action[117][13] = 'r(18)';action[117][14] = 'r(18)';
action[117][15] = 'r(18)';action[117][19] = 'r(18)';action[117][21] = 'r(18)';action[118][1] = 'r(15)';
action[118][5] = 'r(15)';action[118][11] = 'r(15)';action[118][13] = 'r(15)';action[118][14] = 'r(15)';
action[118][15] = 'r(15)';action[118][19] = 'r(15)';action[118][21] = 'r(15)';goto[118][8] = 163;
action[119][1] = 's(132)';action[119][23] = 's(131)';goto[119][11] = 164;goto[119][12] = 159;
goto[119][15] = 47;action[120][1] = 's(165)';action[121][1] = 's(41)';action[121][22] = 's(167)';
action[121][23] = 's(40)';goto[121][12] = 166;action[122][0] = 'r(35)';action[122][1] = 'r(35)';
action[122][11] = 'r(35)';action[122][13] = 'r(35)';action[122][14] = 'r(35)';action[122][15] = 'r(35)';
action[122][19] = 'r(35)';action[122][21] = 'r(35)';action[122][24] = 'r(35)';action[123][0] = 'r(36)';
action[123][1] = 'r(36)';action[123][11] = 'r(36)';action[123][13] = 'r(36)';action[123][14] = 'r(36)';
action[123][15] = 'r(36)';action[123][19] = 'r(36)';action[123][21] = 'r(36)';action[123][24] = 'r(36)';
action[124][0] = 'r(37)';action[124][1] = 'r(37)';action[124][11] = 'r(37)';action[124][13] = 'r(37)';
action[124][14] = 'r(37)';action[124][15] = 'r(37)';action[124][19] = 'r(37)';action[124][21] = 'r(37)';
action[124][24] = 'r(37)';action[125][0] = 'r(38)';action[125][1] = 'r(38)';action[125][11] = 'r(38)';
action[125][13] = 'r(38)';action[125][14] = 'r(38)';action[125][15] = 'r(38)';action[125][19] = 'r(38)';
action[125][21] = 'r(38)';action[125][24] = 'r(38)';action[126][0] = 'r(39)';action[126][1] = 'r(39)';
action[126][11] = 'r(39)';action[126][13] = 'r(39)';action[126][14] = 'r(39)';action[126][15] = 'r(39)';
action[126][19] = 'r(39)';action[126][21] = 'r(39)';action[126][24] = 'r(39)';action[127][0] = 'r(40)';
action[127][1] = 'r(40)';action[127][11] = 'r(40)';action[127][13] = 'r(40)';action[127][14] = 'r(40)';
action[127][15] = 'r(40)';action[127][19] = 'r(40)';action[127][21] = 'r(40)';action[127][24] = 'r(40)';
action[128][0] = 'r(41)';action[128][1] = 'r(41)';action[128][11] = 'r(41)';action[128][13] = 'r(41)';
action[128][14] = 'r(41)';action[128][15] = 'r(41)';action[128][19] = 'r(41)';action[128][21] = 'r(41)';
action[128][24] = 'r(41)';action[129][3] = 'r(45)';action[129][18] = 'r(45)';action[130][3] = 'r(48)';
action[130][9] = 's(168)';action[130][10] = 's(169)';action[130][18] = 'r(48)';action[131][0] = 'r(53)';
action[131][1] = 'r(53)';action[131][3] = 'r(53)';action[131][5] = 'r(53)';action[131][9] = 'r(53)';
action[131][10] = 'r(53)';action[131][11] = 'r(53)';action[131][13] = 'r(53)';action[131][14] = 'r(53)';
action[131][15] = 'r(53)';action[131][18] = 'r(53)';action[131][19] = 'r(53)';action[131][21] = 'r(53)';
action[131][24] = 'r(53)';action[132][0] = 'r(54)';action[132][1] = 'r(54)';action[132][2] = 's(171)';
action[132][3] = 'r(54)';action[132][5] = 'r(54)';action[132][6] = 's(170)';action[132][9] = 'r(54)';
action[132][10] = 'r(54)';action[132][11] = 'r(54)';action[132][13] = 'r(54)';action[132][14] = 'r(54)';
action[132][15] = 'r(54)';action[132][18] = 'r(54)';action[132][19] = 'r(54)';action[132][21] = 'r(54)';
action[132][24] = 'r(54)';action[133][1] = 's(174)';action[133][23] = 's(173)';goto[133][12] = 172;
action[134][1] = 's(176)';action[134][23] = 's(175)';action[135][1] = 's(37)';action[135][23] = 's(36)';
goto[135][12] = 177;action[136][1] = 's(41)';action[136][3] = 'r(25)';action[136][23] = 's(40)';
goto[136][12] = 39;goto[136][13] = 178;action[137][1] = 's(180)';action[137][23] = 's(179)';
action[138][1] = 's(37)';action[138][23] = 's(36)';goto[138][12] = 181;action[139][1] = 's(41)';
action[139][3] = 'r(25)';action[139][23] = 's(40)';goto[139][12] = 39;goto[139][13] = 182;
action[140][1] = 's(37)';action[140][23] = 's(36)';goto[140][12] = 183;action[141][1] = 's(41)';
action[141][3] = 'r(25)';action[141][23] = 's(40)';goto[141][12] = 39;goto[141][13] = 184;
action[142][0] = 'r(55)';action[142][1] = 'r(55)';action[142][3] = 'r(55)';action[142][9] = 'r(55)';
action[142][10] = 'r(55)';action[142][11] = 'r(55)';action[142][13] = 'r(55)';action[142][14] = 'r(55)';
action[142][15] = 'r(55)';action[142][18] = 'r(55)';action[142][19] = 'r(55)';action[142][21] = 'r(55)';
action[142][24] = 'r(55)';action[143][0] = 'r(56)';action[143][1] = 'r(56)';action[143][3] = 'r(56)';
action[143][9] = 'r(56)';action[143][10] = 'r(56)';action[143][11] = 'r(56)';action[143][13] = 'r(56)';
action[143][14] = 'r(56)';action[143][15] = 'r(56)';action[143][18] = 'r(56)';action[143][19] = 'r(56)';
action[143][21] = 'r(56)';action[143][24] = 'r(56)';action[144][1] = 's(37)';action[144][23] = 's(36)';
goto[144][12] = 185;action[145][1] = 's(41)';action[145][3] = 'r(25)';action[145][23] = 's(40)';
goto[145][12] = 39;goto[145][13] = 186;action[146][7] = 'r(55)';action[146][10] = 'r(55)';
action[147][7] = 'r(56)';action[147][10] = 'r(56)';action[148][1] = 's(37)';action[148][23] = 's(36)';
goto[148][12] = 187;action[149][1] = 's(41)';action[149][3] = 'r(25)';action[149][23] = 's(40)';
goto[149][12] = 39;goto[149][13] = 188;action[150][3] = 'r(55)';action[150][10] = 'r(55)';
action[151][3] = 'r(56)';action[151][10] = 'r(56)';action[152][23] = 's(189)';action[153][1] = 's(198)';
action[153][11] = 's(200)';action[153][12] = 's(190)';action[153][13] = 's(203)';action[153][14] = 's(199)';
action[153][15] = 's(204)';action[153][19] = 's(202)';action[153][21] = 's(201)';goto[153][2] = 191;
goto[153][4] = 193;goto[153][5] = 194;goto[153][6] = 195;goto[153][7] = 196;
goto[153][9] = 192;goto[153][10] = 197;action[154][1] = 'r(15)';action[154][11] = 'r(15)';
action[154][12] = 'r(15)';action[154][13] = 'r(15)';action[154][14] = 'r(15)';action[154][15] = 'r(15)';
action[154][19] = 'r(15)';action[154][21] = 'r(15)';goto[154][8] = 205;action[155][1] = 's(132)';
action[155][23] = 's(131)';goto[155][11] = 206;goto[155][12] = 159;goto[155][15] = 47;
action[156][1] = 'r(16)';action[156][5] = 'r(16)';action[156][11] = 'r(16)';action[156][13] = 'r(16)';
action[156][14] = 'r(16)';action[156][15] = 'r(16)';action[156][18] = 's(207)';action[156][19] = 'r(16)';
action[156][21] = 'r(16)';action[157][1] = 's(117)';goto[157][10] = 208;action[158][1] = 'r(46)';
action[158][5] = 'r(46)';action[158][11] = 'r(46)';action[158][13] = 'r(46)';action[158][14] = 'r(46)';
action[158][15] = 'r(46)';action[158][18] = 'r(46)';action[158][19] = 'r(46)';action[158][21] = 'r(46)';
action[159][0] = 'r(48)';action[159][1] = 'r(48)';action[159][3] = 'r(48)';action[159][5] = 'r(48)';
action[159][9] = 's(209)';action[159][10] = 's(169)';action[159][11] = 'r(48)';action[159][13] = 'r(48)';
action[159][14] = 'r(48)';action[159][15] = 'r(48)';action[159][18] = 'r(48)';action[159][19] = 'r(48)';
action[159][21] = 'r(48)';action[159][24] = 'r(48)';action[160][3] = 's(210)';action[161][16] = 's(211)';
action[162][1] = 's(37)';action[162][23] = 's(36)';goto[162][12] = 212;action[163][1] = 's(77)';
action[163][5] = 's(213)';action[163][11] = 's(79)';action[163][13] = 's(82)';action[163][14] = 's(78)';
action[163][15] = 's(83)';action[163][19] = 's(81)';action[163][21] = 's(80)';goto[163][2] = 70;
goto[163][4] = 72;goto[163][5] = 73;goto[163][6] = 74;goto[163][7] = 75;
goto[163][9] = 71;goto[163][10] = 76;action[164][3] = 's(214)';action[164][18] = 's(85)';
action[165][3] = 's(215)';action[166][3] = 's(216)';action[166][10] = 's(62)';action[167][3] = 's(217)';
action[168][1] = 's(220)';action[168][23] = 's(219)';goto[168][12] = 218;action[169][1] = 's(222)';
action[169][23] = 's(221)';action[170][1] = 's(37)';action[170][23] = 's(36)';goto[170][12] = 223;
action[171][1] = 's(41)';action[171][3] = 'r(25)';action[171][23] = 's(40)';goto[171][12] = 39;
goto[171][13] = 224;action[172][0] = 'r(47)';action[172][1] = 'r(47)';action[172][10] = 's(225)';
action[172][11] = 'r(47)';action[172][13] = 'r(47)';action[172][14] = 'r(47)';action[172][15] = 'r(47)';
action[172][18] = 'r(47)';action[172][19] = 'r(47)';action[172][21] = 'r(47)';action[172][24] = 'r(47)';
action[173][0] = 'r(53)';action[173][1] = 'r(53)';action[173][10] = 'r(53)';action[173][11] = 'r(53)';
action[173][13] = 'r(53)';action[173][14] = 'r(53)';action[173][15] = 'r(53)';action[173][18] = 'r(53)';
action[173][19] = 'r(53)';action[173][21] = 'r(53)';action[173][24] = 'r(53)';action[174][0] = 'r(54)';
action[174][1] = 'r(54)';action[174][2] = 's(227)';action[174][6] = 's(226)';action[174][10] = 'r(54)';
action[174][11] = 'r(54)';action[174][13] = 'r(54)';action[174][14] = 'r(54)';action[174][15] = 'r(54)';
action[174][18] = 'r(54)';action[174][19] = 'r(54)';action[174][21] = 'r(54)';action[174][24] = 'r(54)';
action[175][0] = 'r(49)';action[175][1] = 'r(49)';action[175][9] = 'r(49)';action[175][10] = 'r(49)';
action[175][11] = 'r(49)';action[175][13] = 'r(49)';action[175][14] = 'r(49)';action[175][15] = 'r(49)';
action[175][18] = 'r(49)';action[175][19] = 'r(49)';action[175][21] = 'r(49)';action[175][24] = 'r(49)';
action[176][0] = 'r(50)';action[176][1] = 'r(50)';action[176][2] = 's(229)';action[176][6] = 's(228)';
action[176][9] = 'r(50)';action[176][10] = 'r(50)';action[176][11] = 'r(50)';action[176][13] = 'r(50)';
action[176][14] = 'r(50)';action[176][15] = 'r(50)';action[176][18] = 'r(50)';action[176][19] = 'r(50)';
action[176][21] = 'r(50)';action[176][24] = 'r(50)';action[177][7] = 's(230)';action[177][10] = 's(58)';
action[178][3] = 's(231)';action[179][0] = 'r(49)';action[179][1] = 'r(49)';action[179][3] = 'r(49)';
action[179][10] = 'r(49)';action[179][11] = 'r(49)';action[179][13] = 'r(49)';action[179][14] = 'r(49)';
action[179][15] = 'r(49)';action[179][18] = 'r(49)';action[179][19] = 'r(49)';action[179][21] = 'r(49)';
action[179][24] = 'r(49)';action[180][0] = 'r(50)';action[180][1] = 'r(50)';action[180][2] = 's(233)';
action[180][3] = 'r(50)';action[180][6] = 's(232)';action[180][10] = 'r(50)';action[180][11] = 'r(50)';
action[180][13] = 'r(50)';action[180][14] = 'r(50)';action[180][15] = 'r(50)';action[180][18] = 'r(50)';
action[180][19] = 'r(50)';action[180][21] = 'r(50)';action[180][24] = 'r(50)';action[181][7] = 's(234)';
action[181][10] = 's(58)';action[182][3] = 's(235)';action[183][7] = 's(236)';action[183][10] = 's(58)';
action[184][3] = 's(237)';action[185][7] = 's(238)';action[185][10] = 's(58)';action[186][3] = 's(239)';
action[187][7] = 's(240)';action[187][10] = 's(58)';action[188][3] = 's(241)';action[189][3] = 's(242)';
action[190][1] = 's(132)';action[190][5] = 's(243)';action[190][23] = 's(131)';goto[190][11] = 244;
goto[190][12] = 159;goto[190][15] = 245;action[191][1] = 'r(9)';action[191][11] = 'r(9)';
action[191][12] = 'r(9)';action[191][13] = 'r(9)';action[191][14] = 'r(9)';action[191][15] = 'r(9)';
action[191][19] = 'r(9)';action[191][21] = 'r(9)';action[192][1] = 'r(10)';action[192][11] = 'r(10)';
action[192][12] = 'r(10)';action[192][13] = 'r(10)';action[192][14] = 'r(10)';action[192][15] = 'r(10)';
action[192][19] = 'r(10)';action[192][21] = 'r(10)';action[193][1] = 'r(11)';action[193][11] = 'r(11)';
action[193][12] = 'r(11)';action[193][13] = 'r(11)';action[193][14] = 'r(11)';action[193][15] = 'r(11)';
action[193][19] = 'r(11)';action[193][21] = 'r(11)';action[194][1] = 'r(12)';action[194][11] = 'r(12)';
action[194][12] = 'r(12)';action[194][13] = 'r(12)';action[194][14] = 'r(12)';action[194][15] = 'r(12)';
action[194][19] = 'r(12)';action[194][21] = 'r(12)';action[195][1] = 'r(13)';action[195][11] = 'r(13)';
action[195][12] = 'r(13)';action[195][13] = 'r(13)';action[195][14] = 'r(13)';action[195][15] = 'r(13)';
action[195][19] = 'r(13)';action[195][21] = 'r(13)';action[196][1] = 'r(14)';action[196][11] = 'r(14)';
action[196][12] = 'r(14)';action[196][13] = 'r(14)';action[196][14] = 'r(14)';action[196][15] = 'r(14)';
action[196][19] = 'r(14)';action[196][21] = 'r(14)';action[197][8] = 's(246)';action[198][2] = 's(247)';
action[198][6] = 's(19)';action[198][8] = 'r(18)';action[199][1] = 's(248)';action[200][1] = 's(250)';
goto[200][10] = 249;action[201][4] = 's(251)';action[202][2] = 's(252)';action[203][2] = 's(253)';
action[204][2] = 's(254)';action[205][1] = 's(198)';action[205][11] = 's(200)';action[205][12] = 's(255)';
action[205][13] = 's(203)';action[205][14] = 's(199)';action[205][15] = 's(204)';action[205][19] = 's(202)';
action[205][21] = 's(201)';goto[205][2] = 191;goto[205][4] = 193;goto[205][5] = 194;
goto[205][6] = 195;goto[205][7] = 196;goto[205][9] = 192;goto[205][10] = 197;
action[206][3] = 's(256)';action[206][18] = 's(85)';action[207][1] = 's(132)';action[207][23] = 's(131)';
goto[207][12] = 258;goto[207][15] = 257;action[208][1] = 'r(17)';action[208][5] = 'r(17)';
action[208][11] = 'r(17)';action[208][13] = 'r(17)';action[208][14] = 'r(17)';action[208][15] = 'r(17)';
action[208][19] = 'r(17)';action[208][21] = 'r(17)';action[209][1] = 's(261)';action[209][23] = 's(260)';
goto[209][12] = 259;action[210][1] = 'r(32)';action[210][5] = 'r(32)';action[210][11] = 'r(32)';
action[210][13] = 'r(32)';action[210][14] = 'r(32)';action[210][15] = 'r(32)';action[210][19] = 'r(32)';
action[210][21] = 'r(32)';action[211][17] = 's(262)';action[212][7] = 's(263)';action[212][10] = 's(58)';
action[213][20] = 's(264)';action[214][0] = 's(275)';action[214][1] = 's(273)';action[214][11] = 's(274)';
action[214][13] = 's(82)';action[214][14] = 's(272)';action[214][15] = 's(83)';action[214][19] = 's(81)';
action[214][21] = 's(80)';goto[214][2] = 266;goto[214][3] = 267;goto[214][4] = 268;
goto[214][5] = 269;goto[214][6] = 270;goto[214][7] = 271;goto[214][10] = 76;
goto[214][14] = 265;action[215][1] = 'r(42)';action[215][5] = 'r(42)';action[215][11] = 'r(42)';
action[215][13] = 'r(42)';action[215][14] = 'r(42)';action[215][15] = 'r(42)';action[215][19] = 'r(42)';
action[215][21] = 'r(42)';action[216][1] = 'r(43)';action[216][5] = 'r(43)';action[216][11] = 'r(43)';
action[216][13] = 'r(43)';action[216][14] = 'r(43)';action[216][15] = 'r(43)';action[216][19] = 'r(43)';
action[216][21] = 'r(43)';action[217][1] = 'r(44)';action[217][5] = 'r(44)';action[217][11] = 'r(44)';
action[217][13] = 'r(44)';action[217][14] = 'r(44)';action[217][15] = 'r(44)';action[217][19] = 'r(44)';
action[217][21] = 'r(44)';action[218][3] = 'r(47)';action[218][10] = 's(276)';action[218][18] = 'r(47)';
action[219][3] = 'r(53)';action[219][10] = 'r(53)';action[219][18] = 'r(53)';action[220][2] = 's(278)';
action[220][3] = 'r(54)';action[220][6] = 's(277)';action[220][10] = 'r(54)';action[220][18] = 'r(54)';
action[221][0] = 'r(49)';action[221][1] = 'r(49)';action[221][3] = 'r(49)';action[221][5] = 'r(49)';
action[221][9] = 'r(49)';action[221][10] = 'r(49)';action[221][11] = 'r(49)';action[221][13] = 'r(49)';
action[221][14] = 'r(49)';action[221][15] = 'r(49)';action[221][18] = 'r(49)';action[221][19] = 'r(49)';
action[221][21] = 'r(49)';action[221][24] = 'r(49)';action[222][0] = 'r(50)';action[222][1] = 'r(50)';
action[222][2] = 's(280)';action[222][3] = 'r(50)';action[222][5] = 'r(50)';action[222][6] = 's(279)';
action[222][9] = 'r(50)';action[222][10] = 'r(50)';action[222][11] = 'r(50)';action[222][13] = 'r(50)';
action[222][14] = 'r(50)';action[222][15] = 'r(50)';action[222][18] = 'r(50)';action[222][19] = 'r(50)';
action[222][21] = 'r(50)';action[222][24] = 'r(50)';action[223][7] = 's(281)';action[223][10] = 's(58)';
action[224][3] = 's(282)';action[225][1] = 's(284)';action[225][23] = 's(283)';action[226][1] = 's(37)';
action[226][23] = 's(36)';goto[226][12] = 285;action[227][1] = 's(41)';action[227][3] = 'r(25)';
action[227][23] = 's(40)';goto[227][12] = 39;goto[227][13] = 286;action[228][1] = 's(37)';
action[228][23] = 's(36)';goto[228][12] = 287;action[229][1] = 's(41)';action[229][3] = 'r(25)';
action[229][23] = 's(40)';goto[229][12] = 39;goto[229][13] = 288;action[230][0] = 'r(55)';
action[230][1] = 'r(55)';action[230][9] = 'r(55)';action[230][10] = 'r(55)';action[230][11] = 'r(55)';
action[230][13] = 'r(55)';action[230][14] = 'r(55)';action[230][15] = 'r(55)';action[230][18] = 'r(55)';
action[230][19] = 'r(55)';action[230][21] = 'r(55)';action[230][24] = 'r(55)';action[231][0] = 'r(56)';
action[231][1] = 'r(56)';action[231][9] = 'r(56)';action[231][10] = 'r(56)';action[231][11] = 'r(56)';
action[231][13] = 'r(56)';action[231][14] = 'r(56)';action[231][15] = 'r(56)';action[231][18] = 'r(56)';
action[231][19] = 'r(56)';action[231][21] = 'r(56)';action[231][24] = 'r(56)';action[232][1] = 's(37)';
action[232][23] = 's(36)';goto[232][12] = 289;action[233][1] = 's(41)';action[233][3] = 'r(25)';
action[233][23] = 's(40)';goto[233][12] = 39;goto[233][13] = 290;action[234][0] = 'r(55)';
action[234][1] = 'r(55)';action[234][3] = 'r(55)';action[234][10] = 'r(55)';action[234][11] = 'r(55)';
action[234][13] = 'r(55)';action[234][14] = 'r(55)';action[234][15] = 'r(55)';action[234][18] = 'r(55)';
action[234][19] = 'r(55)';action[234][21] = 'r(55)';action[234][24] = 'r(55)';action[235][0] = 'r(56)';
action[235][1] = 'r(56)';action[235][3] = 'r(56)';action[235][10] = 'r(56)';action[235][11] = 'r(56)';
action[235][13] = 'r(56)';action[235][14] = 'r(56)';action[235][15] = 'r(56)';action[235][18] = 'r(56)';
action[235][19] = 'r(56)';action[235][21] = 'r(56)';action[235][24] = 'r(56)';action[236][0] = 'r(51)';
action[236][1] = 'r(51)';action[236][3] = 'r(51)';action[236][9] = 'r(51)';action[236][10] = 'r(51)';
action[236][11] = 'r(51)';action[236][13] = 'r(51)';action[236][14] = 'r(51)';action[236][15] = 'r(51)';
action[236][18] = 'r(51)';action[236][19] = 'r(51)';action[236][21] = 'r(51)';action[236][24] = 'r(51)';
action[237][0] = 'r(52)';action[237][1] = 'r(52)';action[237][3] = 'r(52)';action[237][9] = 'r(52)';
action[237][10] = 'r(52)';action[237][11] = 'r(52)';action[237][13] = 'r(52)';action[237][14] = 'r(52)';
action[237][15] = 'r(52)';action[237][18] = 'r(52)';action[237][19] = 'r(52)';action[237][21] = 'r(52)';
action[237][24] = 'r(52)';action[238][7] = 'r(51)';action[238][10] = 'r(51)';action[239][7] = 'r(52)';
action[239][10] = 'r(52)';action[240][3] = 'r(51)';action[240][10] = 'r(51)';action[241][3] = 'r(52)';
action[241][10] = 'r(52)';action[242][0] = 'r(21)';action[242][1] = 'r(21)';action[242][11] = 'r(21)';
action[242][13] = 'r(21)';action[242][14] = 'r(21)';action[242][15] = 'r(21)';action[242][19] = 'r(21)';
action[242][21] = 'r(21)';action[242][24] = 'r(21)';action[243][0] = 'r(26)';action[243][1] = 'r(26)';
action[243][11] = 'r(26)';action[243][13] = 'r(26)';action[243][14] = 'r(26)';action[243][15] = 'r(26)';
action[243][19] = 'r(26)';action[243][21] = 'r(26)';action[243][24] = 'r(26)';action[244][5] = 's(291)';
action[244][18] = 's(292)';action[245][5] = 'r(46)';action[245][18] = 'r(46)';action[246][1] = 's(298)';
action[246][11] = 's(294)';action[246][23] = 's(297)';goto[246][11] = 293;goto[246][12] = 296;
goto[246][15] = 295;action[247][1] = 's(41)';action[247][3] = 'r(25)';action[247][23] = 's(40)';
goto[247][12] = 39;goto[247][13] = 299;action[248][1] = 'r(30)';action[248][8] = 's(300)';
action[248][11] = 'r(30)';action[248][12] = 'r(30)';action[248][13] = 'r(30)';action[248][14] = 'r(30)';
action[248][15] = 'r(30)';action[248][19] = 'r(30)';action[248][21] = 'r(30)';action[249][1] = 'r(33)';
action[249][11] = 'r(33)';action[249][12] = 'r(33)';action[249][13] = 'r(33)';action[249][14] = 'r(33)';
action[249][15] = 'r(33)';action[249][19] = 'r(33)';action[249][21] = 'r(33)';action[250][1] = 'r(18)';
action[250][6] = 's(301)';action[250][11] = 'r(18)';action[250][12] = 'r(18)';action[250][13] = 'r(18)';
action[250][14] = 'r(18)';action[250][15] = 'r(18)';action[250][19] = 'r(18)';action[250][21] = 'r(18)';
action[251][1] = 'r(15)';action[251][5] = 'r(15)';action[251][11] = 'r(15)';action[251][13] = 'r(15)';
action[251][14] = 'r(15)';action[251][15] = 'r(15)';action[251][19] = 'r(15)';action[251][21] = 'r(15)';
goto[251][8] = 302;action[252][1] = 's(298)';action[252][23] = 's(297)';goto[252][11] = 303;
goto[252][12] = 296;goto[252][15] = 47;action[253][1] = 's(304)';action[254][1] = 's(41)';
action[254][22] = 's(306)';action[254][23] = 's(40)';goto[254][12] = 305;action[255][1] = 's(298)';
action[255][5] = 's(307)';action[255][23] = 's(297)';goto[255][11] = 308;goto[255][12] = 296;
goto[255][15] = 245;action[256][0] = 'r(34)';action[256][1] = 'r(34)';action[256][11] = 'r(34)';
action[256][13] = 'r(34)';action[256][14] = 'r(34)';action[256][15] = 'r(34)';action[256][19] = 'r(34)';
action[256][21] = 'r(34)';action[256][24] = 'r(34)';action[257][1] = 'r(45)';action[257][5] = 'r(45)';
action[257][11] = 'r(45)';action[257][13] = 'r(45)';action[257][14] = 'r(45)';action[257][15] = 'r(45)';
action[257][18] = 'r(45)';action[257][19] = 'r(45)';action[257][21] = 'r(45)';action[258][1] = 'r(48)';
action[258][5] = 'r(48)';action[258][9] = 's(309)';action[258][10] = 's(169)';action[258][11] = 'r(48)';
action[258][13] = 'r(48)';action[258][14] = 'r(48)';action[258][15] = 'r(48)';action[258][18] = 'r(48)';
action[258][19] = 'r(48)';action[258][21] = 'r(48)';action[259][0] = 'r(47)';action[259][1] = 'r(47)';
action[259][3] = 'r(47)';action[259][5] = 'r(47)';action[259][10] = 's(310)';action[259][11] = 'r(47)';
action[259][13] = 'r(47)';action[259][14] = 'r(47)';action[259][15] = 'r(47)';action[259][18] = 'r(47)';
action[259][19] = 'r(47)';action[259][21] = 'r(47)';action[259][24] = 'r(47)';action[260][0] = 'r(53)';
action[260][1] = 'r(53)';action[260][3] = 'r(53)';action[260][5] = 'r(53)';action[260][10] = 'r(53)';
action[260][11] = 'r(53)';action[260][13] = 'r(53)';action[260][14] = 'r(53)';action[260][15] = 'r(53)';
action[260][18] = 'r(53)';action[260][19] = 'r(53)';action[260][21] = 'r(53)';action[260][24] = 'r(53)';
action[261][0] = 'r(54)';action[261][1] = 'r(54)';action[261][2] = 's(312)';action[261][3] = 'r(54)';
action[261][5] = 'r(54)';action[261][6] = 's(311)';action[261][10] = 'r(54)';action[261][11] = 'r(54)';
action[261][13] = 'r(54)';action[261][14] = 'r(54)';action[261][15] = 'r(54)';action[261][18] = 'r(54)';
action[261][19] = 'r(54)';action[261][21] = 'r(54)';action[261][24] = 'r(54)';action[262][2] = 's(313)';
action[263][1] = 'r(19)';action[263][5] = 'r(19)';action[263][11] = 'r(19)';action[263][13] = 'r(19)';
action[263][14] = 'r(19)';action[263][15] = 'r(19)';action[263][19] = 'r(19)';action[263][21] = 'r(19)';
action[264][2] = 's(314)';action[265][1] = 'r(35)';action[265][5] = 'r(35)';action[265][11] = 'r(35)';
action[265][13] = 'r(35)';action[265][14] = 'r(35)';action[265][15] = 'r(35)';action[265][19] = 'r(35)';
action[265][21] = 'r(35)';action[266][1] = 'r(36)';action[266][5] = 'r(36)';action[266][11] = 'r(36)';
action[266][13] = 'r(36)';action[266][14] = 'r(36)';action[266][15] = 'r(36)';action[266][19] = 'r(36)';
action[266][21] = 'r(36)';action[267][1] = 'r(37)';action[267][5] = 'r(37)';action[267][11] = 'r(37)';
action[267][13] = 'r(37)';action[267][14] = 'r(37)';action[267][15] = 'r(37)';action[267][19] = 'r(37)';
action[267][21] = 'r(37)';action[268][1] = 'r(38)';action[268][5] = 'r(38)';action[268][11] = 'r(38)';
action[268][13] = 'r(38)';action[268][14] = 'r(38)';action[268][15] = 'r(38)';action[268][19] = 'r(38)';
action[268][21] = 'r(38)';action[269][1] = 'r(39)';action[269][5] = 'r(39)';action[269][11] = 'r(39)';
action[269][13] = 'r(39)';action[269][14] = 'r(39)';action[269][15] = 'r(39)';action[269][19] = 'r(39)';
action[269][21] = 'r(39)';action[270][1] = 'r(40)';action[270][5] = 'r(40)';action[270][11] = 'r(40)';
action[270][13] = 'r(40)';action[270][14] = 'r(40)';action[270][15] = 'r(40)';action[270][19] = 'r(40)';
action[270][21] = 'r(40)';action[271][1] = 'r(41)';action[271][5] = 'r(41)';action[271][11] = 'r(41)';
action[271][13] = 'r(41)';action[271][14] = 'r(41)';action[271][15] = 'r(41)';action[271][19] = 'r(41)';
action[271][21] = 'r(41)';action[272][1] = 's(315)';action[273][2] = 's(316)';action[273][6] = 's(19)';
action[273][8] = 'r(18)';action[274][1] = 's(117)';goto[274][10] = 317;action[275][1] = 's(318)';
action[276][1] = 's(320)';action[276][23] = 's(319)';action[277][1] = 's(37)';action[277][23] = 's(36)';
goto[277][12] = 321;action[278][1] = 's(41)';action[278][3] = 'r(25)';action[278][23] = 's(40)';
goto[278][12] = 39;goto[278][13] = 322;action[279][1] = 's(37)';action[279][23] = 's(36)';
goto[279][12] = 323;action[280][1] = 's(41)';action[280][3] = 'r(25)';action[280][23] = 's(40)';
goto[280][12] = 39;goto[280][13] = 324;action[281][0] = 'r(55)';action[281][1] = 'r(55)';
action[281][3] = 'r(55)';action[281][5] = 'r(55)';action[281][9] = 'r(55)';action[281][10] = 'r(55)';
action[281][11] = 'r(55)';action[281][13] = 'r(55)';action[281][14] = 'r(55)';action[281][15] = 'r(55)';
action[281][18] = 'r(55)';action[281][19] = 'r(55)';action[281][21] = 'r(55)';action[281][24] = 'r(55)';
action[282][0] = 'r(56)';action[282][1] = 'r(56)';action[282][3] = 'r(56)';action[282][5] = 'r(56)';
action[282][9] = 'r(56)';action[282][10] = 'r(56)';action[282][11] = 'r(56)';action[282][13] = 'r(56)';
action[282][14] = 'r(56)';action[282][15] = 'r(56)';action[282][18] = 'r(56)';action[282][19] = 'r(56)';
action[282][21] = 'r(56)';action[282][24] = 'r(56)';action[283][0] = 'r(49)';action[283][1] = 'r(49)';
action[283][10] = 'r(49)';action[283][11] = 'r(49)';action[283][13] = 'r(49)';action[283][14] = 'r(49)';
action[283][15] = 'r(49)';action[283][18] = 'r(49)';action[283][19] = 'r(49)';action[283][21] = 'r(49)';
action[283][24] = 'r(49)';action[284][0] = 'r(50)';action[284][1] = 'r(50)';action[284][2] = 's(326)';
action[284][6] = 's(325)';action[284][10] = 'r(50)';action[284][11] = 'r(50)';action[284][13] = 'r(50)';
action[284][14] = 'r(50)';action[284][15] = 'r(50)';action[284][18] = 'r(50)';action[284][19] = 'r(50)';
action[284][21] = 'r(50)';action[284][24] = 'r(50)';action[285][7] = 's(327)';action[285][10] = 's(58)';
action[286][3] = 's(328)';action[287][7] = 's(329)';action[287][10] = 's(58)';action[288][3] = 's(330)';
action[289][7] = 's(331)';action[289][10] = 's(58)';action[290][3] = 's(332)';action[291][0] = 'r(28)';
action[291][1] = 'r(28)';action[291][11] = 'r(28)';action[291][13] = 'r(28)';action[291][14] = 'r(28)';
action[291][15] = 'r(28)';action[291][19] = 'r(28)';action[291][21] = 'r(28)';action[291][24] = 'r(28)';
action[292][1] = 's(298)';action[292][23] = 's(297)';goto[292][12] = 334;goto[292][15] = 333;
action[293][1] = 'r(16)';action[293][11] = 'r(16)';action[293][12] = 'r(16)';action[293][13] = 'r(16)';
action[293][14] = 'r(16)';action[293][15] = 'r(16)';action[293][18] = 's(335)';action[293][19] = 'r(16)';
action[293][21] = 'r(16)';action[294][1] = 's(250)';goto[294][10] = 336;action[295][1] = 'r(46)';
action[295][11] = 'r(46)';action[295][12] = 'r(46)';action[295][13] = 'r(46)';action[295][14] = 'r(46)';
action[295][15] = 'r(46)';action[295][18] = 'r(46)';action[295][19] = 'r(46)';action[295][21] = 'r(46)';
action[296][0] = 'r(48)';action[296][1] = 'r(48)';action[296][3] = 'r(48)';action[296][5] = 'r(48)';
action[296][9] = 's(337)';action[296][10] = 's(338)';action[296][11] = 'r(48)';action[296][12] = 'r(48)';
action[296][13] = 'r(48)';action[296][14] = 'r(48)';action[296][15] = 'r(48)';action[296][18] = 'r(48)';
action[296][19] = 'r(48)';action[296][21] = 'r(48)';action[296][24] = 'r(48)';action[297][0] = 'r(53)';
action[297][1] = 'r(53)';action[297][3] = 'r(53)';action[297][5] = 'r(53)';action[297][9] = 'r(53)';
action[297][10] = 'r(53)';action[297][11] = 'r(53)';action[297][12] = 'r(53)';action[297][13] = 'r(53)';
action[297][14] = 'r(53)';action[297][15] = 'r(53)';action[297][18] = 'r(53)';action[297][19] = 'r(53)';
action[297][21] = 'r(53)';action[297][24] = 'r(53)';action[298][0] = 'r(54)';action[298][1] = 'r(54)';
action[298][2] = 's(340)';action[298][3] = 'r(54)';action[298][5] = 'r(54)';action[298][6] = 's(339)';
action[298][9] = 'r(54)';action[298][10] = 'r(54)';action[298][11] = 'r(54)';action[298][12] = 'r(54)';
action[298][13] = 'r(54)';action[298][14] = 'r(54)';action[298][15] = 'r(54)';action[298][18] = 'r(54)';
action[298][19] = 'r(54)';action[298][21] = 'r(54)';action[298][24] = 'r(54)';action[299][3] = 's(341)';
action[300][16] = 's(342)';action[301][1] = 's(37)';action[301][23] = 's(36)';goto[301][12] = 343;
action[302][1] = 's(77)';action[302][5] = 's(344)';action[302][11] = 's(79)';action[302][13] = 's(82)';
action[302][14] = 's(78)';action[302][15] = 's(83)';action[302][19] = 's(81)';action[302][21] = 's(80)';
goto[302][2] = 70;goto[302][4] = 72;goto[302][5] = 73;goto[302][6] = 74;
goto[302][7] = 75;goto[302][9] = 71;goto[302][10] = 76;action[303][3] = 's(345)';
action[303][18] = 's(85)';action[304][3] = 's(346)';action[305][3] = 's(347)';action[305][10] = 's(62)';
action[306][3] = 's(348)';action[307][0] = 'r(27)';action[307][1] = 'r(27)';action[307][11] = 'r(27)';
action[307][13] = 'r(27)';action[307][14] = 'r(27)';action[307][15] = 'r(27)';action[307][19] = 'r(27)';
action[307][21] = 'r(27)';action[307][24] = 'r(27)';action[308][5] = 's(349)';action[308][18] = 's(292)';
action[309][1] = 's(352)';action[309][23] = 's(351)';goto[309][12] = 350;action[310][1] = 's(354)';
action[310][23] = 's(353)';action[311][1] = 's(37)';action[311][23] = 's(36)';goto[311][12] = 355;
action[312][1] = 's(41)';action[312][3] = 'r(25)';action[312][23] = 's(40)';goto[312][12] = 39;
goto[312][13] = 356;action[313][23] = 's(357)';action[314][1] = 's(298)';action[314][23] = 's(297)';
goto[314][11] = 358;goto[314][12] = 296;goto[314][15] = 47;action[315][1] = 'r(20)';
action[315][5] = 'r(20)';action[315][8] = 's(359)';action[315][11] = 'r(20)';action[315][13] = 'r(20)';
action[315][14] = 'r(20)';action[315][15] = 'r(20)';action[315][19] = 'r(20)';action[315][21] = 'r(20)';
action[316][1] = 's(41)';action[316][3] = 'r(25)';action[316][23] = 's(40)';goto[316][12] = 39;
goto[316][13] = 360;action[317][1] = 'r(23)';action[317][5] = 'r(23)';action[317][11] = 'r(23)';
action[317][13] = 'r(23)';action[317][14] = 'r(23)';action[317][15] = 'r(23)';action[317][19] = 'r(23)';
action[317][21] = 'r(23)';action[318][2] = 's(361)';action[319][3] = 'r(49)';action[319][10] = 'r(49)';
action[319][18] = 'r(49)';action[320][2] = 's(363)';action[320][3] = 'r(50)';action[320][6] = 's(362)';
action[320][10] = 'r(50)';action[320][18] = 'r(50)';action[321][7] = 's(364)';action[321][10] = 's(58)';
action[322][3] = 's(365)';action[323][7] = 's(366)';action[323][10] = 's(58)';action[324][3] = 's(367)';
action[325][1] = 's(37)';action[325][23] = 's(36)';goto[325][12] = 368;action[326][1] = 's(41)';
action[326][3] = 'r(25)';action[326][23] = 's(40)';goto[326][12] = 39;goto[326][13] = 369;
action[327][0] = 'r(55)';action[327][1] = 'r(55)';action[327][10] = 'r(55)';action[327][11] = 'r(55)';
action[327][13] = 'r(55)';action[327][14] = 'r(55)';action[327][15] = 'r(55)';action[327][18] = 'r(55)';
action[327][19] = 'r(55)';action[327][21] = 'r(55)';action[327][24] = 'r(55)';action[328][0] = 'r(56)';
action[328][1] = 'r(56)';action[328][10] = 'r(56)';action[328][11] = 'r(56)';action[328][13] = 'r(56)';
action[328][14] = 'r(56)';action[328][15] = 'r(56)';action[328][18] = 'r(56)';action[328][19] = 'r(56)';
action[328][21] = 'r(56)';action[328][24] = 'r(56)';action[329][0] = 'r(51)';action[329][1] = 'r(51)';
action[329][9] = 'r(51)';action[329][10] = 'r(51)';action[329][11] = 'r(51)';action[329][13] = 'r(51)';
action[329][14] = 'r(51)';action[329][15] = 'r(51)';action[329][18] = 'r(51)';action[329][19] = 'r(51)';
action[329][21] = 'r(51)';action[329][24] = 'r(51)';action[330][0] = 'r(52)';action[330][1] = 'r(52)';
action[330][9] = 'r(52)';action[330][10] = 'r(52)';action[330][11] = 'r(52)';action[330][13] = 'r(52)';
action[330][14] = 'r(52)';action[330][15] = 'r(52)';action[330][18] = 'r(52)';action[330][19] = 'r(52)';
action[330][21] = 'r(52)';action[330][24] = 'r(52)';action[331][0] = 'r(51)';action[331][1] = 'r(51)';
action[331][3] = 'r(51)';action[331][10] = 'r(51)';action[331][11] = 'r(51)';action[331][13] = 'r(51)';
action[331][14] = 'r(51)';action[331][15] = 'r(51)';action[331][18] = 'r(51)';action[331][19] = 'r(51)';
action[331][21] = 'r(51)';action[331][24] = 'r(51)';action[332][0] = 'r(52)';action[332][1] = 'r(52)';
action[332][3] = 'r(52)';action[332][10] = 'r(52)';action[332][11] = 'r(52)';action[332][13] = 'r(52)';
action[332][14] = 'r(52)';action[332][15] = 'r(52)';action[332][18] = 'r(52)';action[332][19] = 'r(52)';
action[332][21] = 'r(52)';action[332][24] = 'r(52)';action[333][5] = 'r(45)';action[333][18] = 'r(45)';
action[334][5] = 'r(48)';action[334][9] = 's(370)';action[334][10] = 's(338)';action[334][18] = 'r(48)';
action[335][1] = 's(298)';action[335][23] = 's(297)';goto[335][12] = 372;goto[335][15] = 371;
action[336][1] = 'r(17)';action[336][11] = 'r(17)';action[336][12] = 'r(17)';action[336][13] = 'r(17)';
action[336][14] = 'r(17)';action[336][15] = 'r(17)';action[336][19] = 'r(17)';action[336][21] = 'r(17)';
action[337][1] = 's(375)';action[337][23] = 's(374)';goto[337][12] = 373;action[338][1] = 's(377)';
action[338][23] = 's(376)';action[339][1] = 's(37)';action[339][23] = 's(36)';goto[339][12] = 378;
action[340][1] = 's(41)';action[340][3] = 'r(25)';action[340][23] = 's(40)';goto[340][12] = 39;
goto[340][13] = 379;action[341][1] = 'r(32)';action[341][11] = 'r(32)';action[341][12] = 'r(32)';
action[341][13] = 'r(32)';action[341][14] = 'r(32)';action[341][15] = 'r(32)';action[341][19] = 'r(32)';
action[341][21] = 'r(32)';action[342][17] = 's(380)';action[343][7] = 's(381)';action[343][10] = 's(58)';
action[344][20] = 's(382)';action[345][0] = 's(393)';action[345][1] = 's(391)';action[345][11] = 's(392)';
action[345][13] = 's(203)';action[345][14] = 's(390)';action[345][15] = 's(204)';action[345][19] = 's(202)';
action[345][21] = 's(201)';goto[345][2] = 384;goto[345][3] = 385;goto[345][4] = 386;
goto[345][5] = 387;goto[345][6] = 388;goto[345][7] = 389;goto[345][10] = 197;
goto[345][14] = 383;action[346][1] = 'r(42)';action[346][11] = 'r(42)';action[346][12] = 'r(42)';
action[346][13] = 'r(42)';action[346][14] = 'r(42)';action[346][15] = 'r(42)';action[346][19] = 'r(42)';
action[346][21] = 'r(42)';action[347][1] = 'r(43)';action[347][11] = 'r(43)';action[347][12] = 'r(43)';
action[347][13] = 'r(43)';action[347][14] = 'r(43)';action[347][15] = 'r(43)';action[347][19] = 'r(43)';
action[347][21] = 'r(43)';action[348][1] = 'r(44)';action[348][11] = 'r(44)';action[348][12] = 'r(44)';
action[348][13] = 'r(44)';action[348][14] = 'r(44)';action[348][15] = 'r(44)';action[348][19] = 'r(44)';
action[348][21] = 'r(44)';action[349][0] = 'r(29)';action[349][1] = 'r(29)';action[349][11] = 'r(29)';
action[349][13] = 'r(29)';action[349][14] = 'r(29)';action[349][15] = 'r(29)';action[349][19] = 'r(29)';
action[349][21] = 'r(29)';action[349][24] = 'r(29)';action[350][1] = 'r(47)';action[350][5] = 'r(47)';
action[350][10] = 's(394)';action[350][11] = 'r(47)';action[350][13] = 'r(47)';action[350][14] = 'r(47)';
action[350][15] = 'r(47)';action[350][18] = 'r(47)';action[350][19] = 'r(47)';action[350][21] = 'r(47)';
action[351][1] = 'r(53)';action[351][5] = 'r(53)';action[351][10] = 'r(53)';action[351][11] = 'r(53)';
action[351][13] = 'r(53)';action[351][14] = 'r(53)';action[351][15] = 'r(53)';action[351][18] = 'r(53)';
action[351][19] = 'r(53)';action[351][21] = 'r(53)';action[352][1] = 'r(54)';action[352][2] = 's(396)';
action[352][5] = 'r(54)';action[352][6] = 's(395)';action[352][10] = 'r(54)';action[352][11] = 'r(54)';
action[352][13] = 'r(54)';action[352][14] = 'r(54)';action[352][15] = 'r(54)';action[352][18] = 'r(54)';
action[352][19] = 'r(54)';action[352][21] = 'r(54)';action[353][0] = 'r(49)';action[353][1] = 'r(49)';
action[353][3] = 'r(49)';action[353][5] = 'r(49)';action[353][10] = 'r(49)';action[353][11] = 'r(49)';
action[353][13] = 'r(49)';action[353][14] = 'r(49)';action[353][15] = 'r(49)';action[353][18] = 'r(49)';
action[353][19] = 'r(49)';action[353][21] = 'r(49)';action[353][24] = 'r(49)';action[354][0] = 'r(50)';
action[354][1] = 'r(50)';action[354][2] = 's(398)';action[354][3] = 'r(50)';action[354][5] = 'r(50)';
action[354][6] = 's(397)';action[354][10] = 'r(50)';action[354][11] = 'r(50)';action[354][13] = 'r(50)';
action[354][14] = 'r(50)';action[354][15] = 'r(50)';action[354][18] = 'r(50)';action[354][19] = 'r(50)';
action[354][21] = 'r(50)';action[354][24] = 'r(50)';action[355][7] = 's(399)';action[355][10] = 's(58)';
action[356][3] = 's(400)';action[357][3] = 's(401)';action[358][3] = 's(402)';action[358][18] = 's(85)';
action[359][16] = 's(403)';action[360][3] = 's(404)';action[361][1] = 's(406)';action[361][3] = 's(405)';
action[362][1] = 's(37)';action[362][23] = 's(36)';goto[362][12] = 407;action[363][1] = 's(41)';
action[363][3] = 'r(25)';action[363][23] = 's(40)';goto[363][12] = 39;goto[363][13] = 408;
action[364][3] = 'r(55)';action[364][10] = 'r(55)';action[364][18] = 'r(55)';action[365][3] = 'r(56)';
action[365][10] = 'r(56)';action[365][18] = 'r(56)';action[366][0] = 'r(51)';action[366][1] = 'r(51)';
action[366][3] = 'r(51)';action[366][5] = 'r(51)';action[366][9] = 'r(51)';action[366][10] = 'r(51)';
action[366][11] = 'r(51)';action[366][13] = 'r(51)';action[366][14] = 'r(51)';action[366][15] = 'r(51)';
action[366][18] = 'r(51)';action[366][19] = 'r(51)';action[366][21] = 'r(51)';action[366][24] = 'r(51)';
action[367][0] = 'r(52)';action[367][1] = 'r(52)';action[367][3] = 'r(52)';action[367][5] = 'r(52)';
action[367][9] = 'r(52)';action[367][10] = 'r(52)';action[367][11] = 'r(52)';action[367][13] = 'r(52)';
action[367][14] = 'r(52)';action[367][15] = 'r(52)';action[367][18] = 'r(52)';action[367][19] = 'r(52)';
action[367][21] = 'r(52)';action[367][24] = 'r(52)';action[368][7] = 's(409)';action[368][10] = 's(58)';
action[369][3] = 's(410)';action[370][1] = 's(413)';action[370][23] = 's(412)';goto[370][12] = 411;
action[371][1] = 'r(45)';action[371][11] = 'r(45)';action[371][12] = 'r(45)';action[371][13] = 'r(45)';
action[371][14] = 'r(45)';action[371][15] = 'r(45)';action[371][18] = 'r(45)';action[371][19] = 'r(45)';
action[371][21] = 'r(45)';action[372][1] = 'r(48)';action[372][9] = 's(414)';action[372][10] = 's(338)';
action[372][11] = 'r(48)';action[372][12] = 'r(48)';action[372][13] = 'r(48)';action[372][14] = 'r(48)';
action[372][15] = 'r(48)';action[372][18] = 'r(48)';action[372][19] = 'r(48)';action[372][21] = 'r(48)';
action[373][0] = 'r(47)';action[373][1] = 'r(47)';action[373][3] = 'r(47)';action[373][5] = 'r(47)';
action[373][10] = 's(415)';action[373][11] = 'r(47)';action[373][12] = 'r(47)';action[373][13] = 'r(47)';
action[373][14] = 'r(47)';action[373][15] = 'r(47)';action[373][18] = 'r(47)';action[373][19] = 'r(47)';
action[373][21] = 'r(47)';action[373][24] = 'r(47)';action[374][0] = 'r(53)';action[374][1] = 'r(53)';
action[374][3] = 'r(53)';action[374][5] = 'r(53)';action[374][10] = 'r(53)';action[374][11] = 'r(53)';
action[374][12] = 'r(53)';action[374][13] = 'r(53)';action[374][14] = 'r(53)';action[374][15] = 'r(53)';
action[374][18] = 'r(53)';action[374][19] = 'r(53)';action[374][21] = 'r(53)';action[374][24] = 'r(53)';
action[375][0] = 'r(54)';action[375][1] = 'r(54)';action[375][2] = 's(417)';action[375][3] = 'r(54)';
action[375][5] = 'r(54)';action[375][6] = 's(416)';action[375][10] = 'r(54)';action[375][11] = 'r(54)';
action[375][12] = 'r(54)';action[375][13] = 'r(54)';action[375][14] = 'r(54)';action[375][15] = 'r(54)';
action[375][18] = 'r(54)';action[375][19] = 'r(54)';action[375][21] = 'r(54)';action[375][24] = 'r(54)';
action[376][0] = 'r(49)';action[376][1] = 'r(49)';action[376][3] = 'r(49)';action[376][5] = 'r(49)';
action[376][9] = 'r(49)';action[376][10] = 'r(49)';action[376][11] = 'r(49)';action[376][12] = 'r(49)';
action[376][13] = 'r(49)';action[376][14] = 'r(49)';action[376][15] = 'r(49)';action[376][18] = 'r(49)';
action[376][19] = 'r(49)';action[376][21] = 'r(49)';action[376][24] = 'r(49)';action[377][0] = 'r(50)';
action[377][1] = 'r(50)';action[377][2] = 's(419)';action[377][3] = 'r(50)';action[377][5] = 'r(50)';
action[377][6] = 's(418)';action[377][9] = 'r(50)';action[377][10] = 'r(50)';action[377][11] = 'r(50)';
action[377][12] = 'r(50)';action[377][13] = 'r(50)';action[377][14] = 'r(50)';action[377][15] = 'r(50)';
action[377][18] = 'r(50)';action[377][19] = 'r(50)';action[377][21] = 'r(50)';action[377][24] = 'r(50)';
action[378][7] = 's(420)';action[378][10] = 's(58)';action[379][3] = 's(421)';action[380][2] = 's(422)';
action[381][1] = 'r(19)';action[381][11] = 'r(19)';action[381][12] = 'r(19)';action[381][13] = 'r(19)';
action[381][14] = 'r(19)';action[381][15] = 'r(19)';action[381][19] = 'r(19)';action[381][21] = 'r(19)';
action[382][2] = 's(423)';action[383][1] = 'r(35)';action[383][11] = 'r(35)';action[383][12] = 'r(35)';
action[383][13] = 'r(35)';action[383][14] = 'r(35)';action[383][15] = 'r(35)';action[383][19] = 'r(35)';
action[383][21] = 'r(35)';action[384][1] = 'r(36)';action[384][11] = 'r(36)';action[384][12] = 'r(36)';
action[384][13] = 'r(36)';action[384][14] = 'r(36)';action[384][15] = 'r(36)';action[384][19] = 'r(36)';
action[384][21] = 'r(36)';action[385][1] = 'r(37)';action[385][11] = 'r(37)';action[385][12] = 'r(37)';
action[385][13] = 'r(37)';action[385][14] = 'r(37)';action[385][15] = 'r(37)';action[385][19] = 'r(37)';
action[385][21] = 'r(37)';action[386][1] = 'r(38)';action[386][11] = 'r(38)';action[386][12] = 'r(38)';
action[386][13] = 'r(38)';action[386][14] = 'r(38)';action[386][15] = 'r(38)';action[386][19] = 'r(38)';
action[386][21] = 'r(38)';action[387][1] = 'r(39)';action[387][11] = 'r(39)';action[387][12] = 'r(39)';
action[387][13] = 'r(39)';action[387][14] = 'r(39)';action[387][15] = 'r(39)';action[387][19] = 'r(39)';
action[387][21] = 'r(39)';action[388][1] = 'r(40)';action[388][11] = 'r(40)';action[388][12] = 'r(40)';
action[388][13] = 'r(40)';action[388][14] = 'r(40)';action[388][15] = 'r(40)';action[388][19] = 'r(40)';
action[388][21] = 'r(40)';action[389][1] = 'r(41)';action[389][11] = 'r(41)';action[389][12] = 'r(41)';
action[389][13] = 'r(41)';action[389][14] = 'r(41)';action[389][15] = 'r(41)';action[389][19] = 'r(41)';
action[389][21] = 'r(41)';action[390][1] = 's(424)';action[391][2] = 's(425)';action[391][6] = 's(19)';
action[391][8] = 'r(18)';action[392][1] = 's(250)';goto[392][10] = 426;action[393][1] = 's(427)';
action[394][1] = 's(429)';action[394][23] = 's(428)';action[395][1] = 's(37)';action[395][23] = 's(36)';
goto[395][12] = 430;action[396][1] = 's(41)';action[396][3] = 'r(25)';action[396][23] = 's(40)';
goto[396][12] = 39;goto[396][13] = 431;action[397][1] = 's(37)';action[397][23] = 's(36)';
goto[397][12] = 432;action[398][1] = 's(41)';action[398][3] = 'r(25)';action[398][23] = 's(40)';
goto[398][12] = 39;goto[398][13] = 433;action[399][0] = 'r(55)';action[399][1] = 'r(55)';
action[399][3] = 'r(55)';action[399][5] = 'r(55)';action[399][10] = 'r(55)';action[399][11] = 'r(55)';
action[399][13] = 'r(55)';action[399][14] = 'r(55)';action[399][15] = 'r(55)';action[399][18] = 'r(55)';
action[399][19] = 'r(55)';action[399][21] = 'r(55)';action[399][24] = 'r(55)';action[400][0] = 'r(56)';
action[400][1] = 'r(56)';action[400][3] = 'r(56)';action[400][5] = 'r(56)';action[400][10] = 'r(56)';
action[400][11] = 'r(56)';action[400][13] = 'r(56)';action[400][14] = 'r(56)';action[400][15] = 'r(56)';
action[400][18] = 'r(56)';action[400][19] = 'r(56)';action[400][21] = 'r(56)';action[400][24] = 'r(56)';
action[401][1] = 'r(31)';action[401][5] = 'r(31)';action[401][11] = 'r(31)';action[401][13] = 'r(31)';
action[401][14] = 'r(31)';action[401][15] = 'r(31)';action[401][19] = 'r(31)';action[401][21] = 'r(31)';
action[402][1] = 'r(34)';action[402][5] = 'r(34)';action[402][11] = 'r(34)';action[402][13] = 'r(34)';
action[402][14] = 'r(34)';action[402][15] = 'r(34)';action[402][19] = 'r(34)';action[402][21] = 'r(34)';
action[403][17] = 's(434)';action[404][1] = 'r(22)';action[404][5] = 'r(22)';action[404][11] = 'r(22)';
action[404][13] = 'r(22)';action[404][14] = 'r(22)';action[404][15] = 'r(22)';action[404][19] = 'r(22)';
action[404][21] = 'r(22)';action[405][4] = 's(435)';action[406][3] = 's(436)';action[407][7] = 's(437)';
action[407][10] = 's(58)';action[408][3] = 's(438)';action[409][0] = 'r(51)';action[409][1] = 'r(51)';
action[409][10] = 'r(51)';action[409][11] = 'r(51)';action[409][13] = 'r(51)';action[409][14] = 'r(51)';
action[409][15] = 'r(51)';action[409][18] = 'r(51)';action[409][19] = 'r(51)';action[409][21] = 'r(51)';
action[409][24] = 'r(51)';action[410][0] = 'r(52)';action[410][1] = 'r(52)';action[410][10] = 'r(52)';
action[410][11] = 'r(52)';action[410][13] = 'r(52)';action[410][14] = 'r(52)';action[410][15] = 'r(52)';
action[410][18] = 'r(52)';action[410][19] = 'r(52)';action[410][21] = 'r(52)';action[410][24] = 'r(52)';
action[411][5] = 'r(47)';action[411][10] = 's(439)';action[411][18] = 'r(47)';action[412][5] = 'r(53)';
action[412][10] = 'r(53)';action[412][18] = 'r(53)';action[413][2] = 's(441)';action[413][5] = 'r(54)';
action[413][6] = 's(440)';action[413][10] = 'r(54)';action[413][18] = 'r(54)';action[414][1] = 's(444)';
action[414][23] = 's(443)';goto[414][12] = 442;action[415][1] = 's(446)';action[415][23] = 's(445)';
action[416][1] = 's(37)';action[416][23] = 's(36)';goto[416][12] = 447;action[417][1] = 's(41)';
action[417][3] = 'r(25)';action[417][23] = 's(40)';goto[417][12] = 39;goto[417][13] = 448;
action[418][1] = 's(37)';action[418][23] = 's(36)';goto[418][12] = 449;action[419][1] = 's(41)';
action[419][3] = 'r(25)';action[419][23] = 's(40)';goto[419][12] = 39;goto[419][13] = 450;
action[420][0] = 'r(55)';action[420][1] = 'r(55)';action[420][3] = 'r(55)';action[420][5] = 'r(55)';
action[420][9] = 'r(55)';action[420][10] = 'r(55)';action[420][11] = 'r(55)';action[420][12] = 'r(55)';
action[420][13] = 'r(55)';action[420][14] = 'r(55)';action[420][15] = 'r(55)';action[420][18] = 'r(55)';
action[420][19] = 'r(55)';action[420][21] = 'r(55)';action[420][24] = 'r(55)';action[421][0] = 'r(56)';
action[421][1] = 'r(56)';action[421][3] = 'r(56)';action[421][5] = 'r(56)';action[421][9] = 'r(56)';
action[421][10] = 'r(56)';action[421][11] = 'r(56)';action[421][12] = 'r(56)';action[421][13] = 'r(56)';
action[421][14] = 'r(56)';action[421][15] = 'r(56)';action[421][18] = 'r(56)';action[421][19] = 'r(56)';
action[421][21] = 'r(56)';action[421][24] = 'r(56)';action[422][23] = 's(451)';action[423][1] = 's(298)';
action[423][23] = 's(297)';goto[423][11] = 452;goto[423][12] = 296;goto[423][15] = 47;
action[424][1] = 'r(20)';action[424][8] = 's(453)';action[424][11] = 'r(20)';action[424][12] = 'r(20)';
action[424][13] = 'r(20)';action[424][14] = 'r(20)';action[424][15] = 'r(20)';action[424][19] = 'r(20)';
action[424][21] = 'r(20)';action[425][1] = 's(41)';action[425][3] = 'r(25)';action[425][23] = 's(40)';
goto[425][12] = 39;goto[425][13] = 454;action[426][1] = 'r(23)';action[426][11] = 'r(23)';
action[426][12] = 'r(23)';action[426][13] = 'r(23)';action[426][14] = 'r(23)';action[426][15] = 'r(23)';
action[426][19] = 'r(23)';action[426][21] = 'r(23)';action[427][2] = 's(455)';action[428][1] = 'r(49)';
action[428][5] = 'r(49)';action[428][10] = 'r(49)';action[428][11] = 'r(49)';action[428][13] = 'r(49)';
action[428][14] = 'r(49)';action[428][15] = 'r(49)';action[428][18] = 'r(49)';action[428][19] = 'r(49)';
action[428][21] = 'r(49)';action[429][1] = 'r(50)';action[429][2] = 's(457)';action[429][5] = 'r(50)';
action[429][6] = 's(456)';action[429][10] = 'r(50)';action[429][11] = 'r(50)';action[429][13] = 'r(50)';
action[429][14] = 'r(50)';action[429][15] = 'r(50)';action[429][18] = 'r(50)';action[429][19] = 'r(50)';
action[429][21] = 'r(50)';action[430][7] = 's(458)';action[430][10] = 's(58)';action[431][3] = 's(459)';
action[432][7] = 's(460)';action[432][10] = 's(58)';action[433][3] = 's(461)';action[434][2] = 's(462)';
action[435][1] = 'r(15)';action[435][11] = 'r(15)';action[435][12] = 'r(15)';action[435][13] = 'r(15)';
action[435][14] = 'r(15)';action[435][15] = 'r(15)';action[435][19] = 'r(15)';action[435][21] = 'r(15)';
goto[435][8] = 463;action[436][4] = 's(464)';action[437][3] = 'r(51)';action[437][10] = 'r(51)';
action[437][18] = 'r(51)';action[438][3] = 'r(52)';action[438][10] = 'r(52)';action[438][18] = 'r(52)';
action[439][1] = 's(466)';action[439][23] = 's(465)';action[440][1] = 's(37)';action[440][23] = 's(36)';
goto[440][12] = 467;action[441][1] = 's(41)';action[441][3] = 'r(25)';action[441][23] = 's(40)';
goto[441][12] = 39;goto[441][13] = 468;action[442][1] = 'r(47)';action[442][10] = 's(469)';
action[442][11] = 'r(47)';action[442][12] = 'r(47)';action[442][13] = 'r(47)';action[442][14] = 'r(47)';
action[442][15] = 'r(47)';action[442][18] = 'r(47)';action[442][19] = 'r(47)';action[442][21] = 'r(47)';
action[443][1] = 'r(53)';action[443][10] = 'r(53)';action[443][11] = 'r(53)';action[443][12] = 'r(53)';
action[443][13] = 'r(53)';action[443][14] = 'r(53)';action[443][15] = 'r(53)';action[443][18] = 'r(53)';
action[443][19] = 'r(53)';action[443][21] = 'r(53)';action[444][1] = 'r(54)';action[444][2] = 's(471)';
action[444][6] = 's(470)';action[444][10] = 'r(54)';action[444][11] = 'r(54)';action[444][12] = 'r(54)';
action[444][13] = 'r(54)';action[444][14] = 'r(54)';action[444][15] = 'r(54)';action[444][18] = 'r(54)';
action[444][19] = 'r(54)';action[444][21] = 'r(54)';action[445][0] = 'r(49)';action[445][1] = 'r(49)';
action[445][3] = 'r(49)';action[445][5] = 'r(49)';action[445][10] = 'r(49)';action[445][11] = 'r(49)';
action[445][12] = 'r(49)';action[445][13] = 'r(49)';action[445][14] = 'r(49)';action[445][15] = 'r(49)';
action[445][18] = 'r(49)';action[445][19] = 'r(49)';action[445][21] = 'r(49)';action[445][24] = 'r(49)';
action[446][0] = 'r(50)';action[446][1] = 'r(50)';action[446][2] = 's(473)';action[446][3] = 'r(50)';
action[446][5] = 'r(50)';action[446][6] = 's(472)';action[446][10] = 'r(50)';action[446][11] = 'r(50)';
action[446][12] = 'r(50)';action[446][13] = 'r(50)';action[446][14] = 'r(50)';action[446][15] = 'r(50)';
action[446][18] = 'r(50)';action[446][19] = 'r(50)';action[446][21] = 'r(50)';action[446][24] = 'r(50)';
action[447][7] = 's(474)';action[447][10] = 's(58)';action[448][3] = 's(475)';action[449][7] = 's(476)';
action[449][10] = 's(58)';action[450][3] = 's(477)';action[451][3] = 's(478)';action[452][3] = 's(479)';
action[452][18] = 's(85)';action[453][16] = 's(480)';action[454][3] = 's(481)';action[455][1] = 's(483)';
action[455][3] = 's(482)';action[456][1] = 's(37)';action[456][23] = 's(36)';goto[456][12] = 484;
action[457][1] = 's(41)';action[457][3] = 'r(25)';action[457][23] = 's(40)';goto[457][12] = 39;
goto[457][13] = 485;action[458][1] = 'r(55)';action[458][5] = 'r(55)';action[458][10] = 'r(55)';
action[458][11] = 'r(55)';action[458][13] = 'r(55)';action[458][14] = 'r(55)';action[458][15] = 'r(55)';
action[458][18] = 'r(55)';action[458][19] = 'r(55)';action[458][21] = 'r(55)';action[459][1] = 'r(56)';
action[459][5] = 'r(56)';action[459][10] = 'r(56)';action[459][11] = 'r(56)';action[459][13] = 'r(56)';
action[459][14] = 'r(56)';action[459][15] = 'r(56)';action[459][18] = 'r(56)';action[459][19] = 'r(56)';
action[459][21] = 'r(56)';action[460][0] = 'r(51)';action[460][1] = 'r(51)';action[460][3] = 'r(51)';
action[460][5] = 'r(51)';action[460][10] = 'r(51)';action[460][11] = 'r(51)';action[460][13] = 'r(51)';
action[460][14] = 'r(51)';action[460][15] = 'r(51)';action[460][18] = 'r(51)';action[460][19] = 'r(51)';
action[460][21] = 'r(51)';action[460][24] = 'r(51)';action[461][0] = 'r(52)';action[461][1] = 'r(52)';
action[461][3] = 'r(52)';action[461][5] = 'r(52)';action[461][10] = 'r(52)';action[461][11] = 'r(52)';
action[461][13] = 'r(52)';action[461][14] = 'r(52)';action[461][15] = 'r(52)';action[461][18] = 'r(52)';
action[461][19] = 'r(52)';action[461][21] = 'r(52)';action[461][24] = 'r(52)';action[462][23] = 's(486)';
action[463][1] = 's(198)';action[463][11] = 's(200)';action[463][12] = 's(487)';action[463][13] = 's(203)';
action[463][14] = 's(199)';action[463][15] = 's(204)';action[463][19] = 's(202)';action[463][21] = 's(201)';
goto[463][2] = 191;goto[463][4] = 193;goto[463][5] = 194;goto[463][6] = 195;
goto[463][7] = 196;goto[463][9] = 192;goto[463][10] = 197;action[464][1] = 'r(15)';
action[464][11] = 'r(15)';action[464][12] = 'r(15)';action[464][13] = 'r(15)';action[464][14] = 'r(15)';
action[464][15] = 'r(15)';action[464][19] = 'r(15)';action[464][21] = 'r(15)';goto[464][8] = 488;
action[465][5] = 'r(49)';action[465][10] = 'r(49)';action[465][18] = 'r(49)';action[466][2] = 's(490)';
action[466][5] = 'r(50)';action[466][6] = 's(489)';action[466][10] = 'r(50)';action[466][18] = 'r(50)';
action[467][7] = 's(491)';action[467][10] = 's(58)';action[468][3] = 's(492)';action[469][1] = 's(494)';
action[469][23] = 's(493)';action[470][1] = 's(37)';action[470][23] = 's(36)';goto[470][12] = 495;
action[471][1] = 's(41)';action[471][3] = 'r(25)';action[471][23] = 's(40)';goto[471][12] = 39;
goto[471][13] = 496;action[472][1] = 's(37)';action[472][23] = 's(36)';goto[472][12] = 497;
action[473][1] = 's(41)';action[473][3] = 'r(25)';action[473][23] = 's(40)';goto[473][12] = 39;
goto[473][13] = 498;action[474][0] = 'r(55)';action[474][1] = 'r(55)';action[474][3] = 'r(55)';
action[474][5] = 'r(55)';action[474][10] = 'r(55)';action[474][11] = 'r(55)';action[474][12] = 'r(55)';
action[474][13] = 'r(55)';action[474][14] = 'r(55)';action[474][15] = 'r(55)';action[474][18] = 'r(55)';
action[474][19] = 'r(55)';action[474][21] = 'r(55)';action[474][24] = 'r(55)';action[475][0] = 'r(56)';
action[475][1] = 'r(56)';action[475][3] = 'r(56)';action[475][5] = 'r(56)';action[475][10] = 'r(56)';
action[475][11] = 'r(56)';action[475][12] = 'r(56)';action[475][13] = 'r(56)';action[475][14] = 'r(56)';
action[475][15] = 'r(56)';action[475][18] = 'r(56)';action[475][19] = 'r(56)';action[475][21] = 'r(56)';
action[475][24] = 'r(56)';action[476][0] = 'r(51)';action[476][1] = 'r(51)';action[476][3] = 'r(51)';
action[476][5] = 'r(51)';action[476][9] = 'r(51)';action[476][10] = 'r(51)';action[476][11] = 'r(51)';
action[476][12] = 'r(51)';action[476][13] = 'r(51)';action[476][14] = 'r(51)';action[476][15] = 'r(51)';
action[476][18] = 'r(51)';action[476][19] = 'r(51)';action[476][21] = 'r(51)';action[476][24] = 'r(51)';
action[477][0] = 'r(52)';action[477][1] = 'r(52)';action[477][3] = 'r(52)';action[477][5] = 'r(52)';
action[477][9] = 'r(52)';action[477][10] = 'r(52)';action[477][11] = 'r(52)';action[477][12] = 'r(52)';
action[477][13] = 'r(52)';action[477][14] = 'r(52)';action[477][15] = 'r(52)';action[477][18] = 'r(52)';
action[477][19] = 'r(52)';action[477][21] = 'r(52)';action[477][24] = 'r(52)';action[478][1] = 'r(31)';
action[478][11] = 'r(31)';action[478][12] = 'r(31)';action[478][13] = 'r(31)';action[478][14] = 'r(31)';
action[478][15] = 'r(31)';action[478][19] = 'r(31)';action[478][21] = 'r(31)';action[479][1] = 'r(34)';
action[479][11] = 'r(34)';action[479][12] = 'r(34)';action[479][13] = 'r(34)';action[479][14] = 'r(34)';
action[479][15] = 'r(34)';action[479][19] = 'r(34)';action[479][21] = 'r(34)';action[480][17] = 's(499)';
action[481][1] = 'r(22)';action[481][11] = 'r(22)';action[481][12] = 'r(22)';action[481][13] = 'r(22)';
action[481][14] = 'r(22)';action[481][15] = 'r(22)';action[481][19] = 'r(22)';action[481][21] = 'r(22)';
action[482][4] = 's(500)';action[483][3] = 's(501)';action[484][7] = 's(502)';action[484][10] = 's(58)';
action[485][3] = 's(503)';action[486][3] = 's(504)';action[487][1] = 's(298)';action[487][5] = 's(505)';
action[487][23] = 's(297)';goto[487][11] = 506;goto[487][12] = 296;goto[487][15] = 245;
action[488][1] = 's(198)';action[488][11] = 's(200)';action[488][12] = 's(507)';action[488][13] = 's(203)';
action[488][14] = 's(199)';action[488][15] = 's(204)';action[488][19] = 's(202)';action[488][21] = 's(201)';
goto[488][2] = 191;goto[488][4] = 193;goto[488][5] = 194;goto[488][6] = 195;
goto[488][7] = 196;goto[488][9] = 192;goto[488][10] = 197;action[489][1] = 's(37)';
action[489][23] = 's(36)';goto[489][12] = 508;action[490][1] = 's(41)';action[490][3] = 'r(25)';
action[490][23] = 's(40)';goto[490][12] = 39;goto[490][13] = 509;action[491][5] = 'r(55)';
action[491][10] = 'r(55)';action[491][18] = 'r(55)';action[492][5] = 'r(56)';action[492][10] = 'r(56)';
action[492][18] = 'r(56)';action[493][1] = 'r(49)';action[493][10] = 'r(49)';action[493][11] = 'r(49)';
action[493][12] = 'r(49)';action[493][13] = 'r(49)';action[493][14] = 'r(49)';action[493][15] = 'r(49)';
action[493][18] = 'r(49)';action[493][19] = 'r(49)';action[493][21] = 'r(49)';action[494][1] = 'r(50)';
action[494][2] = 's(511)';action[494][6] = 's(510)';action[494][10] = 'r(50)';action[494][11] = 'r(50)';
action[494][12] = 'r(50)';action[494][13] = 'r(50)';action[494][14] = 'r(50)';action[494][15] = 'r(50)';
action[494][18] = 'r(50)';action[494][19] = 'r(50)';action[494][21] = 'r(50)';action[495][7] = 's(512)';
action[495][10] = 's(58)';action[496][3] = 's(513)';action[497][7] = 's(514)';action[497][10] = 's(58)';
action[498][3] = 's(515)';action[499][2] = 's(516)';action[500][1] = 'r(15)';action[500][11] = 'r(15)';
action[500][12] = 'r(15)';action[500][13] = 'r(15)';action[500][14] = 'r(15)';action[500][15] = 'r(15)';
action[500][19] = 'r(15)';action[500][21] = 'r(15)';goto[500][8] = 517;action[501][4] = 's(518)';
action[502][1] = 'r(51)';action[502][5] = 'r(51)';action[502][10] = 'r(51)';action[502][11] = 'r(51)';
action[502][13] = 'r(51)';action[502][14] = 'r(51)';action[502][15] = 'r(51)';action[502][18] = 'r(51)';
action[502][19] = 'r(51)';action[502][21] = 'r(51)';action[503][1] = 'r(52)';action[503][5] = 'r(52)';
action[503][10] = 'r(52)';action[503][11] = 'r(52)';action[503][13] = 'r(52)';action[503][14] = 'r(52)';
action[503][15] = 'r(52)';action[503][18] = 'r(52)';action[503][19] = 'r(52)';action[503][21] = 'r(52)';
action[504][1] = 'r(21)';action[504][5] = 'r(21)';action[504][11] = 'r(21)';action[504][13] = 'r(21)';
action[504][14] = 'r(21)';action[504][15] = 'r(21)';action[504][19] = 'r(21)';action[504][21] = 'r(21)';
action[505][1] = 'r(26)';action[505][5] = 'r(26)';action[505][11] = 'r(26)';action[505][13] = 'r(26)';
action[505][14] = 'r(26)';action[505][15] = 'r(26)';action[505][19] = 'r(26)';action[505][21] = 'r(26)';
action[506][5] = 's(519)';action[506][18] = 's(292)';action[507][1] = 's(298)';action[507][5] = 's(520)';
action[507][23] = 's(297)';goto[507][11] = 521;goto[507][12] = 296;goto[507][15] = 245;
action[508][7] = 's(522)';action[508][10] = 's(58)';action[509][3] = 's(523)';action[510][1] = 's(37)';
action[510][23] = 's(36)';goto[510][12] = 524;action[511][1] = 's(41)';action[511][3] = 'r(25)';
action[511][23] = 's(40)';goto[511][12] = 39;goto[511][13] = 525;action[512][1] = 'r(55)';
action[512][10] = 'r(55)';action[512][11] = 'r(55)';action[512][12] = 'r(55)';action[512][13] = 'r(55)';
action[512][14] = 'r(55)';action[512][15] = 'r(55)';action[512][18] = 'r(55)';action[512][19] = 'r(55)';
action[512][21] = 'r(55)';action[513][1] = 'r(56)';action[513][10] = 'r(56)';action[513][11] = 'r(56)';
action[513][12] = 'r(56)';action[513][13] = 'r(56)';action[513][14] = 'r(56)';action[513][15] = 'r(56)';
action[513][18] = 'r(56)';action[513][19] = 'r(56)';action[513][21] = 'r(56)';action[514][0] = 'r(51)';
action[514][1] = 'r(51)';action[514][3] = 'r(51)';action[514][5] = 'r(51)';action[514][10] = 'r(51)';
action[514][11] = 'r(51)';action[514][12] = 'r(51)';action[514][13] = 'r(51)';action[514][14] = 'r(51)';
action[514][15] = 'r(51)';action[514][18] = 'r(51)';action[514][19] = 'r(51)';action[514][21] = 'r(51)';
action[514][24] = 'r(51)';action[515][0] = 'r(52)';action[515][1] = 'r(52)';action[515][3] = 'r(52)';
action[515][5] = 'r(52)';action[515][10] = 'r(52)';action[515][11] = 'r(52)';action[515][12] = 'r(52)';
action[515][13] = 'r(52)';action[515][14] = 'r(52)';action[515][15] = 'r(52)';action[515][18] = 'r(52)';
action[515][19] = 'r(52)';action[515][21] = 'r(52)';action[515][24] = 'r(52)';action[516][23] = 's(526)';
action[517][1] = 's(198)';action[517][11] = 's(200)';action[517][12] = 's(527)';action[517][13] = 's(203)';
action[517][14] = 's(199)';action[517][15] = 's(204)';action[517][19] = 's(202)';action[517][21] = 's(201)';
goto[517][2] = 191;goto[517][4] = 193;goto[517][5] = 194;goto[517][6] = 195;
goto[517][7] = 196;goto[517][9] = 192;goto[517][10] = 197;action[518][1] = 'r(15)';
action[518][11] = 'r(15)';action[518][12] = 'r(15)';action[518][13] = 'r(15)';action[518][14] = 'r(15)';
action[518][15] = 'r(15)';action[518][19] = 'r(15)';action[518][21] = 'r(15)';goto[518][8] = 528;
action[519][1] = 'r(28)';action[519][5] = 'r(28)';action[519][11] = 'r(28)';action[519][13] = 'r(28)';
action[519][14] = 'r(28)';action[519][15] = 'r(28)';action[519][19] = 'r(28)';action[519][21] = 'r(28)';
action[520][1] = 'r(27)';action[520][5] = 'r(27)';action[520][11] = 'r(27)';action[520][13] = 'r(27)';
action[520][14] = 'r(27)';action[520][15] = 'r(27)';action[520][19] = 'r(27)';action[520][21] = 'r(27)';
action[521][5] = 's(529)';action[521][18] = 's(292)';action[522][5] = 'r(51)';action[522][10] = 'r(51)';
action[522][18] = 'r(51)';action[523][5] = 'r(52)';action[523][10] = 'r(52)';action[523][18] = 'r(52)';
action[524][7] = 's(530)';action[524][10] = 's(58)';action[525][3] = 's(531)';action[526][3] = 's(532)';
action[527][1] = 's(298)';action[527][5] = 's(533)';action[527][23] = 's(297)';goto[527][11] = 534;
goto[527][12] = 296;goto[527][15] = 245;action[528][1] = 's(198)';action[528][11] = 's(200)';
action[528][12] = 's(535)';action[528][13] = 's(203)';action[528][14] = 's(199)';action[528][15] = 's(204)';
action[528][19] = 's(202)';action[528][21] = 's(201)';goto[528][2] = 191;goto[528][4] = 193;
goto[528][5] = 194;goto[528][6] = 195;goto[528][7] = 196;goto[528][9] = 192;
goto[528][10] = 197;action[529][1] = 'r(29)';action[529][5] = 'r(29)';action[529][11] = 'r(29)';
action[529][13] = 'r(29)';action[529][14] = 'r(29)';action[529][15] = 'r(29)';action[529][19] = 'r(29)';
action[529][21] = 'r(29)';action[530][1] = 'r(51)';action[530][10] = 'r(51)';action[530][11] = 'r(51)';
action[530][12] = 'r(51)';action[530][13] = 'r(51)';action[530][14] = 'r(51)';action[530][15] = 'r(51)';
action[530][18] = 'r(51)';action[530][19] = 'r(51)';action[530][21] = 'r(51)';action[531][1] = 'r(52)';
action[531][10] = 'r(52)';action[531][11] = 'r(52)';action[531][12] = 'r(52)';action[531][13] = 'r(52)';
action[531][14] = 'r(52)';action[531][15] = 'r(52)';action[531][18] = 'r(52)';action[531][19] = 'r(52)';
action[531][21] = 'r(52)';action[532][1] = 'r(21)';action[532][11] = 'r(21)';action[532][12] = 'r(21)';
action[532][13] = 'r(21)';action[532][14] = 'r(21)';action[532][15] = 'r(21)';action[532][19] = 'r(21)';
action[532][21] = 'r(21)';action[533][1] = 'r(26)';action[533][11] = 'r(26)';action[533][12] = 'r(26)';
action[533][13] = 'r(26)';action[533][14] = 'r(26)';action[533][15] = 'r(26)';action[533][19] = 'r(26)';
action[533][21] = 'r(26)';action[534][5] = 's(536)';action[534][18] = 's(292)';action[535][1] = 's(298)';
action[535][5] = 's(537)';action[535][23] = 's(297)';goto[535][11] = 538;goto[535][12] = 296;
goto[535][15] = 245;action[536][1] = 'r(28)';action[536][11] = 'r(28)';action[536][12] = 'r(28)';
action[536][13] = 'r(28)';action[536][14] = 'r(28)';action[536][15] = 'r(28)';action[536][19] = 'r(28)';
action[536][21] = 'r(28)';action[537][1] = 'r(27)';action[537][11] = 'r(27)';action[537][12] = 'r(27)';
action[537][13] = 'r(27)';action[537][14] = 'r(27)';action[537][15] = 'r(27)';action[537][19] = 'r(27)';
action[537][21] = 'r(27)';action[538][5] = 's(539)';action[538][18] = 's(292)';action[539][1] = 'r(29)';
action[539][11] = 'r(29)';action[539][12] = 'r(29)';action[539][13] = 'r(29)';action[539][14] = 'r(29)';
action[539][15] = 'r(29)';action[539][19] = 'r(29)';action[539][21] = 'r(29)';

#Transformar los datos del action a nuestro formato
 
def obtenerAction(s):    
    for i in s:
        if(i.isalpha()):
            if(i == 'A'):
                letra='a'            
            elif(i == 'v'):
                letra='v'
            else:    
                letra=i
            break
    numero=0    
    for i in s:
        if(i.isdigit()):
            numero=numero*10 + int(i)
    if(letra=='a' or letra=='v'):
        numero=-1
    return (letra,numero)

contador1=0
for i in action:
    contador2=0
    for j in i:
        action[contador1][contador2]=obtenerAction(j)
        contador2=contador2+1
    contador1=contador1+1
############################
#Terminales y no terminales
############################

terminales = ['function','id','(',')','{','}','[',']','=','==','+','++','return','prompt','var','document.write',
              'new','Array','&&','if','while','do','cadena','entero','$']
noTerminales = ['S','H','A','D','W','F','P','M','H1','D1','I','E','N','N1','R','E1']

######################
#Funciones auxiliares
######################

def getNumTerminal(c,lista):
    contador=0
    for i in lista:
        if(c==i):
            return contador
        else:
            contador=contador+1
            
###############
#Funciones
###############

def desplazar(c):
    global cadenaTokens
    global pila
    pila.append(cadenaTokens[0])
    cadenaTokens.remove(cadenaTokens[0])
    pila.append(c)
    
def reducir(c):
    global parse
    global cadenaTokens
    global pila
    a=len(producciones[c][1])
    b=producciones[c][1]
    if(a>0):    
        for i in range(a):
           pila.pop()
           if(b[a-i-1]== pila[len(pila)-1]):
               pila.pop()
           else:
               errores.write('ERROR EN EL SINTACTICO: Error en la reduccion por la regla '+ str(c) + str(producciones[c]))
               cerrarFicheros()                
               exit('ERROR EN EL SINTACTICO: Error en la reduccion por la regla '+ str(c) + str(producciones[c]))
               return;
    estado=pila[len(pila)-1]           
    pila.append(producciones[c][0])
    num=getNumTerminal(producciones[c][0],noTerminales)
    pila.append(goto[estado][num])
    parse.append(c)

########################################
#MAIN DEL SINTACTICO
#######################################
#['if','(','entero','==','id','&&','id',')','prompt','(','id',')','document.write','(','cadena',')','do','{','var','id','id','=','entero','}','while','(','entero',')','$']
cadenaTokens = lexicoASintactico(tokens)
#print(str(cadenaTokens))
pila = []
parse = []
pila.append(0)

terminado=False
while (terminado==False):
    estado=pila[len(pila)-1]
    caracter= getNumTerminal(cadenaTokens[0],terminales)
    if (action[estado][caracter][0] == 'r'):
        reducir(action[estado][caracter][1])
    elif (action[estado][caracter][0] == 's'):
        desplazar(action[estado][caracter][1])
    elif ((action[estado][caracter][0] == 'a') and (len(pila)==3)): #Mirar si el segundo elemento es axioma        
        terminado=True
        #print(str(parse))
    else:
        errores.write('ERROR EN EL SINTACTICO: La gramatica no reconoce el texto porque en la tabla de accion la fila '+ 
        str(estado)+ ' y la columna ' +str(caracter) + ' estan vacios')
        cerrarFicheros()        
        exit('ERROR EN EL SINTACTICO: La gramatica no reconoce el texto porque en la tabla de accion la fila '+ 
        str(estado)+ ' y la columna ' +str(caracter) + ' estan vacios')
        break;

#Imprimir el parse en un fichero

p.write('Ascendente')
for i in parse:
    p.write(' ' + str(i))
#se cierra al final del fichero o en caso de error

#######################
# MAIN DEL SEMANTICO
#######################

#Todos los elementos de la tabla de simbolos son duplas de la forma (id,tipo)
#Si aun no tiene el tipo asignado entonces pondrá 'vacio'

class TablaSimbolos:
    
    def __init__ (self,id):
        self.id=id
        self.simbolos=[]
        
    def meter(self,e):
        self.simbolos.append(e)
        
    def buscar(self,elem):
        contador=0
        encontrado=False
        for i in self.simbolos:
            if(elem==i[0]):
                encontrado=True
                break;           
            else:
                contador=contador+1
        return (encontrado,contador)
        
    def setTipo(self,pos,tipo): #Le paso el elemento su posicion en el array d simbolos y el tipo que le quiero meter
        self.simbolos[pos][1]=tipo
    
    def setArgumento(self,pos,tipo): #Le paso el elemento su posicion en el array d simbolos y el tipo que le quiero meter
        self.simbolos[pos][2]=tipo
    
    def setReturn(self,pos,tipo): #Le paso el elemento su posicion en el array d simbolos y el tipo que le quiero meter
        self.simbolos[pos][3]=tipo
        
# Solo se pueden añadir tipos basicos dentro de un array
# Tipos de datos que puede haber en la tabla d simbolos
# ('nombre','entero') 
# ('nombre','array','longitud')  Puede haber cualquier tipo basico dentro
# ('nombre', 'function' , 'argumento' , 'return') Solo hay un argumento
####################################################
#Formato TS
tablasDeSimbolos.write('El formato de las entradas de la tabla de simbolos es el siguiente: \n')
tablasDeSimbolos.write( '| Nombre | Tipo | Args/long | Return | \n')
tablasDeSimbolos.write('\n')
tablasDeSimbolos.write('Nombre : Nombre del identificador \n')
tablasDeSimbolos.write('Tipo : Tipo del identificador \n')
tablasDeSimbolos.write('Args/long: \n')
tablasDeSimbolos.write('\t Id de tipo funcion: Tipo del argumento de la funcion \n')
tablasDeSimbolos.write('\t Id de tipo Array: Longitud del array \n')
tablasDeSimbolos.write('\t Otro caso: None \n')
tablasDeSimbolos.write('Return: \n')
tablasDeSimbolos.write('\t Id de tipo funcion: Tipo de lo que devuelve la funcion \n')
tablasDeSimbolos.write('\t Otro caso: None \n')
tablasDeSimbolos.write('\n')

def imprimirTS(t,nombre):
    tablasDeSimbolos.write('\n')    
    tablasDeSimbolos.write('####################################################### \n')    
    tablasDeSimbolos.write(nombre + '\n')
    tablasDeSimbolos.write('####################################################### \n') 
    for i in t.simbolos:
        tablasDeSimbolos.write('--------------------------------------------------- \n')
        tablasDeSimbolos.write(' | ' + str(i[0]) + ' | ' +str(i[1]) + ' | ' + str(i[2])+ ' | '  + str(i[3])+ ' | '  +'\n') 
    tablasDeSimbolos.write('------------------------------------------------------- \n')   
    tablasDeSimbolos.write('\n')         
        
####################################################
arrayTS = [TablaSimbolos(0),TablaSimbolos(1)]
contador=0
tablaActual=0

def imprimirErrorTS():
    tablasDeSimbolos.write('--------------------------------------------------- \n')
    tablasDeSimbolos.write('Tablas de simbolos cuando se ha producido el error: \n')
    tablasDeSimbolos.write('--------------------------------------------------- \n')
    if(len(arrayTS[1].simbolos)>0):
        imprimirTS(arrayTS[1],'Tabla de simbolos secundaria (no borrada): '+ str(arrayTS[0].simbolos[len(arrayTS[0].simbolos)-1][0]))  
    imprimirTS(arrayTS[0],'Tabla de simbolos principal')
def buscaParentesis(cursor):
    #devuelve el cierra parentesis de la funcion
    resultado=2
    contadorParentesis=0
    terminado=False    
    while(not(terminado)):
        if(tokens[cursor+resultado].nombre==')' and contadorParentesis==0):
            terminado=True
        elif(tokens[cursor+resultado].nombre=='('):
            contadorParentesis=contadorParentesis+1
            resultado=resultado+1
        elif((tokens[cursor+resultado].nombre)==')'):
            contadorParentesis=contadorParentesis-1
            resultado=resultado+1
        else:
            resultado=resultado+1
    return resultado
    
def auxiliarId(cursor):
    global arrayTS
    cursorPrincipio=cursor
    elemento=tokens[cursor].valor
    tablaUso= tablaActual
    encontrado=False
    if (tokens[cursor+1].nombre=='['):
        if(tablaActual==1 and arrayTS[tablaActual].buscar(elemento)[0] == True ):
            tablaUso=1
            encontrado=True
        elif(tablaActual==1 and arrayTS[tablaActual].buscar(elemento)[0] == False 
        and arrayTS[tablaActual-1].buscar(elemento)[0] == True):
            tablaUso=0
            encontrado=True
        elif(tablaActual==0 and arrayTS[tablaActual].buscar(elemento)[0] == True):
            tablaUso=0
            encontrado=True
        else:
            encontrado=False
            errores.write('ERROR EN EL SEMANTICO:El vector: ' + str(elemento) + ' no ha sido declarada')
            imprimirErrorTS()    
            cerrarFicheros()            
            exit('ERROR EN EL SEMANTICO:El vector: ' + str(elemento) + ' no ha sido declarada' )
            
        posicion=arrayTS[tablaUso].buscar(elemento)[1]
        if(encontrado and arrayTS[tablaUso].simbolos[posicion][1]=='array'):
            while(not(tokens[cursor+2].nombre ==']')):
                if(tokens[cursor+2].nombre =='id'):
                    desp=auxiliarId(cursor+2)
                    cursor=cursor+desp
                else:
                    cursor=cursor+1#OJOOOO tenemos que ver que hay entre corchetes
            cursor=cursor+3
        elif(encontrado):
            errores.write('ERROR EN EL SEMANTICO:La variable ' + str(elemento)+ 'no es un array')
            imprimirErrorTS()
            cerrarFicheros()            
            exit('ERROR EN EL SEMANTICO:La variable ' + str(elemento)+ 'no es un array')
    elif (tokens[cursor+1].nombre=='('): 
        posicion=arrayTS[0].buscar(elemento)[1]
        if(arrayTS[0].buscar(elemento)[0] == False):
            errores.write('ERROR EN EL SEMANTICO:La funcion: ' + str(elemento) + ' no ha sido declarada' )
            imprimirErrorTS()
            cerrarFicheros()
            exit('ERROR EN EL SEMANTICO:La funcion: ' + str(elemento) + ' no ha sido declarada' )
        elif(arrayTS[0].buscar(elemento)[0] == True and not(arrayTS[0].simbolos[posicion][1]=='function')):
            errores.write('ERROR EN EL SEMANTICO:La variable: ' + str(elemento) + ' no es una funcion' )
            imprimirErrorTS()
            cerrarFicheros()
            exit('ERROR EN EL SEMANTICO:La variable: ' + str(elemento) + ' no es una funcion' )
        elif(arrayTS[0].simbolos[posicion][1]=='function'):
            if (arrayTS[0].simbolos[posicion][3]==None):
                derecha=buscaParentesis(cursor)+1
                if((tokens[cursor-1].nombre == '&&') or (tokens[cursor-1].nombre == '+')
                or (tokens[cursor-1].nombre == '==') or (tokens[cursor-1].nombre == '=')): 
                #no petara por negativo index porque entraria por no declarada
                    errores.write('ERROR EN EL SEMANTICO:La funcion '  + str(elemento) + ' no devuelve nada')
                    imprimirErrorTS()
                    cerrarFicheros()
                    exit('ERROR EN EL SEMANTICO:La funcion '  + str(elemento) + ' no devuelve nada')
                elif((tokens[derecha].nombre == '&&') or (tokens[derecha].nombre == '+') 
                or (tokens[derecha].nombre == '==')):
                    errores.write('ERROR EN EL SEMANTICO:La funcion '  + str(elemento) + ' no devuelve nada')
                    imprimirErrorTS()
                    cerrarFicheros()
                    exit('ERROR EN EL SEMANTICO:La funcion '  + str(elemento) + ' no devuelve nada')    
                if(arrayTS[0].simbolos[posicion][2]==None):    
                    if(not(tokens[cursor+2].nombre == ')')):
                        errores.write('ERROR EN EL SEMANTICO:La funcion '  + str(elemento) + ' no debe recibir argumentos')
                        imprimirErrorTS()
                        cerrarFicheros()
                        exit('ERROR EN EL SEMANTICO:La funcion '  + str(elemento) + ' no debe recibir argumentos')
                    cursor=cursor+1
                elif(arrayTS[0].simbolos[posicion][2]=='entero'):
                    if(tokens[cursor+2].nombre ==')'):
                        errores.write('ERROR EN EL SEMANTICO: la funcion ' +str(elemento)+' debe recibir argumentos')
                        imprimirErrorTS()
                        cerrarFicheros()
                        exit('ERROR EN EL SEMANTICO: la funcion ' +str(elemento)+' debe recibir argumentos')
                    while(not(tokens[cursor+2].nombre ==')')):
                        if(tokens[cursor+2].nombre =='id'):
                            desp=auxiliarId(cursor+2)
                            cursor=cursor+desp
                        else:
                            cursor=cursor+1 #OJOOOO tenemos que ver que hay entre parentesis
                    cursor=cursor+3                        
            elif(arrayTS[0].simbolos[posicion][3]=='entero'):
                if(arrayTS[0].simbolos[posicion][2]==None):    
                    if(not(tokens[cursor+2].nombre == ')')):
                        errores.write('ERROR EN EL SEMANTICO:La funcion '  + str(elemento) + ' no debe recibir argumentos')
                        imprimirErrorTS()
                        cerrarFicheros()                        
                        exit('ERROR EN EL SEMANTICO:La funcion '  + str(elemento) + ' no debe recibir argumentos')
                    cursor=cursor+1
                elif(arrayTS[0].simbolos[posicion][2]=='entero'):
                    if(tokens[cursor+2].nombre ==')'):
                        errores.write('ERROR EN EL SEMANTICO: la funcion ' +str(elemento)+' debe recibir argumentos')
                        imprimirErrorTS()
                        cerrarFicheros()
                        exit('ERROR EN EL SEMANTICO: la funcion ' +str(elemento)+' debe recibir argumentos')
                    while(not(tokens[cursor+2].nombre ==')')):
                        if(tokens[cursor+2].nombre =='id'):
                            desp=auxiliarId(cursor+2)
                            cursor=cursor+desp
                        else:
                            cursor=cursor+1 #OJOOOO tenemos que ver que hay entre parentesis
                    cursor=cursor+3                        
            
    else: 
        posicion=arrayTS[tablaActual].buscar(elemento)[1]
        encontrado=False
        if(tablaActual==1 and arrayTS[tablaActual].buscar(elemento)[0] == True ):
            tablaUso=1
            encontrado=True
        elif(tablaActual==1 and arrayTS[tablaActual].buscar(elemento)[0] == False 
        and arrayTS[tablaActual-1].buscar(elemento)[0] == True):
            tablaUso=0
            encontrado=True
        elif(tablaActual==0 and arrayTS[tablaActual].buscar(elemento)[0] == True):
            tablaUso=0
            encontrado=True
        else:
            errores.write('ERROR EN EL SEMANTICO:La variable: ' + str(elemento) + ' no ha sido declarada')
            imprimirErrorTS()
            cerrarFicheros()
            exit('ERROR EN EL SEMANTICO:La variable: ' + str(elemento) + ' no ha sido declarada' )
            encontrado=False
        posicion=arrayTS[tablaUso].buscar(elemento)[1]
        if(encontrado and arrayTS[tablaUso].simbolos[posicion][1]=='array'):
            elemento2=tokens[cursor+2].valor
            if(tokens[cursor+1].nombre == '='):
                tablaUso2=tablaUso
                encontrado2=False
                if(tablaActual==1 and arrayTS[tablaActual].buscar(elemento)[0] == True ):
                    tablaUso2=1
                    encontrado2=True
                elif(tablaActual==1 and arrayTS[tablaActual].buscar(elemento)[0] == False 
                and arrayTS[tablaActual-1].buscar(elemento)[0] == True):
                    tablaUso2=0
                    encontrado2=True
                elif(tablaActual==0 and arrayTS[tablaActual].buscar(elemento)[0] == True):
                    tablaUso2=0
                    encontrado2=True
                else:
                    errores.write('ERROR EN EL SEMANTICO:La variable: ' + str(elemento2) + ' no ha sido declarada')
                    imprimirErrorTS()
                    cerrarFicheros()
                    exit('ERROR EN EL SEMANTICO:La variable: ' + str(elemento2) + ' no ha sido declarada' )
                    encontrado2=False
                posicion2=arrayTS[tablaUso2].buscar(elemento2)[1]                 
                if(encontrado2 and not(arrayTS[tablaUso2].simbolos[posicion2][1]=='array')):
                    errores.write('ERROR EN EL SEMANTICO: la asignacion de diferentes tipos entre '+ str(elemento)+" y "+str(elemento2)+'no esta permitida')
                    imprimirErrorTS()
                    cerrarFicheros()
                    exit('ERROR EN EL SEMANTICO: la asignacion de diferentes tipos entre '+ str(elemento)+" y "+str(elemento2)+'no esta permitida')
                elif(encontrado2 and arrayTS[tablaUso2].simbolos[posicion2][1]=='array' and 
                ((tokens[cursor+3].nombre == '==') or 
                (tokens[cursor+3].nombre == '&&') or 
                (tokens[cursor+3].nombre == '(') or 
                (tokens[cursor+3].nombre == '[') or (tokens[cursor+3].nombre == '+'))):
                    errores.write('ERROR EN EL SEMANTICO: Error en la asignación entre los arrays ' + str(elemento)+" y "+str(elemento2)+ ' es incorrecta' )
                    imprimirErrorTS()
                    cerrarFicheros()
                    exit('ERROR EN EL SEMANTICO: Error en la asignación entre los arrays ' + str(elemento)+" y "+str(elemento2)+ ' es incorrecta' )             
                elif(encontrado2):
                    cursor=cursor+3
            else:
                errores.write('ERROR EN EL SEMANTICO: Uso incorrecto de el vector ' + str(elemento))
                imprimirErrorTS()
                cerrarFicheros()
                exit('ERROR EN EL SEMANTICO: Uso incorrecto de el vector ' + str(elemento))
        elif(encontrado and not(arrayTS[tablaUso].simbolos[posicion][1]=='entero')):
           errores.write('ERROR EN EL SEMANTICO: La variable ' + str(elemento) + ' debe ser entera') 
           imprimirErrorTS()
           cerrarFicheros()
           exit('ERROR EN EL SEMANTICO: La variable ' + str(elemento) + ' debe ser entera')
        elif(encontrado):
            cursor=cursor+1
                        
    return cursor-cursorPrincipio
     
###########################################################
     
tokens.append(Token('Final',None))
tokens.append(Token('Final',None))
tokens.append(Token('Final',None))
tokens.append(Token('Final',None))
tokens.append(Token('Final',None))

while (contador < len(tokens)-1):
    #print(str(contador))
    if(tokens[contador].valor == 'function'):
        elemento=tokens[contador+1].valor
        if (arrayTS[tablaActual].buscar(elemento)[0] == True):
            errores.write('ERROR EN EL SEMANTICO: nombre de funcion ya declarada: ' + str(elemento))
            imprimirErrorTS()
            cerrarFicheros()
            exit('ERROR EN EL SEMANTICO: nombre de funcion ya declarada: ' + str(elemento))
        else:
            arrayTS[tablaActual].meter([elemento,'function',None,None])                
            posicion=arrayTS[tablaActual].buscar(elemento)[1]            
            if (tokens[contador+3].nombre == 'id'):    
                arrayTS[tablaActual].setArgumento(posicion,'entero')                
                tablaActual=1
                elemento = tokens[contador+3].valor
                arrayTS[tablaActual].meter([elemento,'entero',None,None])
                contador=contador+6
            else:
                tablaActual=1
                contador=contador+5
    elif(tokens[contador].valor == 'var'):
        elemento=tokens[contador+1].valor
        if (arrayTS[tablaActual].buscar(elemento)[0] == True):
            errores.write('ERROR EN EL SEMANTICO: nombre de variable ya declarada: ' + str(elemento))
            imprimirErrorTS()
            cerrarFicheros()
            exit('ERROR EN EL SEMANTICO: nombre de variable ya declarada: ' + str(elemento))
        else:
            arrayTS[tablaActual].meter([elemento,None,None,None])
            pos=arrayTS[tablaActual].buscar(elemento)[1]
            if(tokens[contador+2].nombre =='='):
                arrayTS[tablaActual].setTipo(pos,'array')
                longitud = tokens[contador + 6].valor
                arrayTS[tablaActual].setArgumento(pos,longitud)
                contador=contador+8
            else:
                arrayTS[tablaActual].setTipo(pos,'entero')
                contador=contador+2
    elif(tokens[contador].valor == 'return'): #comprobar en el parse si la expresion del return es valida
        if(not(tokens[contador+1].nombre =='}')):      
            pos=len(arrayTS[tablaActual-1].simbolos)-1
            arrayTS[tablaActual-1].setReturn(pos,'entero')   
            while(not(tokens[contador+1].nombre =='}')):
                if(tokens[contador+1].nombre =='id'):
                    desp=auxiliarId(contador+1)
                    contador=contador+desp
                else:
                    contador=contador+1
        contador=contador+2 #para saltarnos la llave
        pos=len(arrayTS[tablaActual-1].simbolos)-1
        #print('TS Secundaria borrada de la funcion: ' + str(arrayTS[tablaActual-1].simbolos[pos][0]) + '\n' + str(arrayTS[1].simbolos) + '\n')
        imprimirTS(arrayTS[1],'Tabla de simbolos secundaria (borrada): '+ str(arrayTS[tablaActual-1].simbolos[pos][0]))        
        arrayTS[tablaActual]=TablaSimbolos(1)
        tablaActual=0
    elif(tokens[contador].nombre == 'id'):
        desp=auxiliarId(contador)
        contador=contador+desp
    else:
        contador=contador+1
           



#print('TS Principal: ' + str(arrayTS[0].simbolos) + '\n')
imprimirTS(arrayTS[0],'Tabla de simbolos principal')
tablasDeSimbolos.close()
errores.close()
t.close() #se cierra OUT-tokens
p.close() #se cierra OUT-parse    