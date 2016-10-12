function [m]=m_res_rel(M)
    n=size(M,1);
    m=zeros(n);
    [a,b,c]=Potencia_ultimo(M);
    W=matriz_ratios(b);
    for i=1:n;
        for j=1:n;
            m(i,j)=abs((M(i,j)-W(i,j)))/M(i,j);
        end;
    end;
            