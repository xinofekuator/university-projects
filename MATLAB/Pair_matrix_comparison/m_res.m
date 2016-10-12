function m=m_res(M)
    n=size(M,1);
    m=0;
    [a,b,c]=Potencia_ultimo(M);
    W=matriz_ratios(b);
    for i=1:n;
        for j=1:n;
            m=(((M(i,j))-W(i,j)))^2+m;
        end;
    end;
    m=(1/n^2)*m^(1/2);
    return