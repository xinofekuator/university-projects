function [M,n]= matriz_ratios(W)
    n=length(W);
    M=ones(n);
    for i=1:n;
        for j=1:n;
            M(i,j)=W(i)/W(j);
        end;
    end;
    return