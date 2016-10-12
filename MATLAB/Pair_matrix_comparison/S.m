function [M]= S (M)
    n=size(M,1);
    
    %Rellenar las columnas de ceros con 1/n. Undefeated season.
    Undefeated=true;
    for i=1:n,
        for j=1:n,
            if M(j,i)==0 && Undefeated==true,
              Undefeated=true;
            else
               Undefeated=false; 
            end;
            
         end;
         if Undefeated==true,
              
                for z=1:n,
                  M(z,i)=1/n;
                end
              
        end;
    end
    %%%Normalizar todas las columnas con la norma uno.
    for k=1:n,
        x=M(:,k);
        x=x/norm(x,1);
        M(:,k)=x;
    end
    
    return 