function [M]= media_aritmetica(W)
    n=size(W,1);
    M=ones(n,1);
    suma=0;
    for i=1:n;
        for j=1:n;
            suma=W(i,j)+suma;
        end;
        M(i)=suma/n;
        suma=0;
    end;
    return