function [lambda, x, iter]= Potencia (S, x0, tol, nmax)
    n=size(S,1);
    
    if nargin==1,
        x0=ones(n,1);
        tol=1e-15;
        nmax=12000;
    end
    if nargin==2,
        tol=1e-15;
        nmax=12000;
    end
    if nargin==3,
        nmax=12000;
    end
    
    x0=x0/norm(x0);
    x1=S*x0;
    lambda=x0'*x1;
    err=tol*abs(lambda)+1;
    iter=0;
    while err>tol*abs(lambda) & abs(lambda)~=0 & iter<=nmax
        x=x1;
        x=x/norm(x);
        x1=S*x;
        lambda2=x'*x1;
        err=abs(lambda2-lambda);
        lambda=lambda2;
        iter=iter+1;
    end
        x=x1/norm(x1,1);
    return
    
        