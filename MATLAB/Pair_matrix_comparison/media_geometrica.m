function [M]= media_geometica(W)
    n=size(W,1);
    M=ones(n,1);
    mult=1;
    for i=1:n;
        for j=1:n;
            mult=W(i,j)*mult;
        end;
        M(i)=(mult)^(1/n);
        mult=1;
    end;
    return