function [M,w]=min_sq_log(B)
n=size(B,1); %B es cuadrada
filas=(n*(n-1))/2;
columnas=n; 
M=zeros(filas,columnas);
z=zeros(filas+1,1); %vector resultado con los logaritmos
contador=1;
bloque=1;
for i=1:n-1
    for j=bloque+1:n
        M(contador,bloque)=1;
        M(contador,j)=-1;
        z(contador,1)=log(B(i,j));
        contador=contador+1;
    end;
    bloque=bloque+1;
end;
for k=1:n
    M(contador,k)=1;
end;
z(filas+1,1)=0;
v=M\z; %resultado con logaritmos
w=exp(v);
w=w/norm(w,1);
display(w);

