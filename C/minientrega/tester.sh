#!/bin/bash

for i in $@
do
#Comprobamos que la variable MINIENTREGA_CONF este creada y sea un directorio                           
echo "---------------------------------------------------------"
echo "PRUEBA $i"
echo "---------------------------------------------------------"
test $MINIENTREGA_CONF || echo "No esta creado MINIENTREGA_CONF" 

ID_PRACTICA=${MINIENTREGA_CONF}/$i
test -r $ID_PRACTICA || echo "No existe la practica $i"

source ${ID_PRACTICA}
echo "Contenido de $ID_PRACTICA:"
echo "Ficheros a entregar: $MINIENTREGA_FICHEROS"
echo "Fecha limite: $MINIENTREGA_FECHALIMITE"
echo "Fichero destino: $MINIENTREGA_DESTINO"

./minientrega.sh $i

ret=$?
echo -e  "\nExit status: $ret"

directorio_destino=$MINIENTREGA_DESTINO/${USER}
echo -e "\nContenido de $directorio_destino:"
for n in $MINIENTREGA_FICHEROS
do
test $ret -eq 0 && test -e $directorio_destino/$n && echo "$n creado correctamente" || echo "$n no se ha creado" 
done
test $ret -eq 0  && echo -e "\nENTREGA REALIZADA CORRECTAMENTE" || echo -e "\nENTREGA NO REALIZADA"
done