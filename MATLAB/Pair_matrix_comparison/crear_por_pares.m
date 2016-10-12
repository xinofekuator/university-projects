%Para una matriz 4x4 se necesitan 3+2+1 datos
%Para una matriz de nxn se necesitan n*(n-1)/2 datos
%Le pasamos la dimension de la matriz y los datos en un vector
%El vector se pasa en una fila
function [M] = crear_por_pares(n,v)
s=size(v,2);
if(n*(n-1)/2 ~= s)
    error('Wrong number of data or wrong matrix dimension');
end;
M=zeros();
contador=1;
for i=1:n
    for j=1:n
        if i==j
            M(i,i)=1;
        end;
        if i<j
            M(i,j)= v(contador);
            M(j,i)=1/v(contador);
            contador = contador+1;
        end;
    end;
end;