function [M] = crear_consistente(v) %pasar vector en columnas
n=size(v,1);
M=zeros(n);
for i=1:n
    for j=1:n
        M(i,j)=v(i)/v(j);
    end;
end;