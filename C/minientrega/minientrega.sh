#!/bin/bash

#Error en el numero de parametros (ningun parametro o mas de uno)                   
if (test $# -ne 1)
then
echo "minientrega.sh: Error(EX_USAGE), uso incorrecto del mandato." '"Success"' 1>&2
echo "minientrega.sh: No se ha introducido un identificador de la practica" 1>&2
exit 64
fi

#Opcion de ayuda (unica soportada por nuestro script)
if (test $1 = --help || test $1 = -h)
then 
echo "minientrega.sh: Uso: minientrega.sh ID_PRACTICA"
echo "minientrega.sh: Realiza la entrega de la practica indicada "
exit 0
fi

#Comprobamos que la variable MINIENTREGA_CONF este creada y sea un directorio
test $MINIENTREGA_CONF && test -d $MINIENTREGA_CONF || exit 64

#Se comprueba que el identificador de la practica es valido
ID_PRACTICA=${MINIENTREGA_CONF}/$1
test -r $ID_PRACTICA || exit 66

#Vamos a ejecutar el codigo de ID_PRACTICA para poder leer las variables que
#estan definidas dentro de este
source ${ID_PRACTICA}

#Comprobamos si MINIENTREGA_FECHALIMITE tiene un formato adecuado(dddd-dd-dd)
test $(echo $MINIENTREGA_FECHALIMITE | egrep -o '\b([0-9]{4})-([0-9]{2})-([0-9]{2})\b') || exit 65

#Comprobamos si MINIENTREGA_FECHALIMITE contiene una fecha valida
date -d $MINIENTREGA_FECHALIMITE >/dev/null 2>&1
ret=$?
test $ret -eq 0 || exit 65
#Vemos si no esta fuera de plazo
fecha_entrada=$(date +%s )
fecha_limite=$(date -d $MINIENTREGA_FECHALIMITE +%s)
test $fecha_entrada -le $fecha_limite || exit 65

#Verificamos que los ficheros especificados en MINIENTREGA_FICHEROS estan en el 
#directorio de trabajo desde el que ejecutamos nuestro script y son legibles
for i in $MINIENTREGA_FICHEROS
do
test -r $i || exit 66
done

#Comprobamos que el directorio MINIENTREGA_DESTINO existe
test -d $MINIENTREGA_DESTINO || exit 73

#Verificamos que podemos crear el directorio de entrega
test -r $MINIENTREGA_DESTINO && test -w $MINIENTREGA_DESTINO || exit 73

#Comprobamos que no este ya creado el directorio de entrega
directorio_entrega=$MINIENTREGA_DESTINO/${USER}
test -d $directorio_entrega && exit 73

#Creamos el directorio de entrega
mkdir $directorio_entrega 2>/dev/null|| exit 73

#Copiamos los ficheros a entregar en el directorio de entrega
for i in $MINIENTREGA_FICHEROS
do
cp $i $directorio_entrega || exit 73
done