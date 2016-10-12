function [M,w]=min_sq_weighted(B)
n=size(B,1); %B es cuadrada
filas=(n*(n-1))/2;
columnas=n; 
M=zeros(filas,columnas);
contador=1;
bloque=1;
for i=1:n-1
    for j=bloque+1:n
        M(contador,bloque)=-1;
        M(contador,j)=B(i,j);
        contador=contador+1;
    end;
    bloque=bloque+1;
end;
for k=1:n
    M(contador,k)=1;
end;
z=zeros(filas+1,1);
z(filas+1,1)=1;
w=M\z;
w=w/norm(w,1);
display(w);