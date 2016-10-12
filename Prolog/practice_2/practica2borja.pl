%Autores: Ignacio Amaya - 11m021
%         Adrian Camara - 11m004
%         Borja Mas     - 11m001

%PARTE1 - HIPERCUBO
%Suma de polinomios
suma([],[],[]).
suma([],Y,Y).
suma(X,[],X).
suma([X|Xs],[Y|Ys],[Z|Zs]):-
        suma(Xs,Ys,Zs),Z is X+Y.
%Multiplica un escalar por un polinomio
mult(0,_,[]).
mult(_,[],[]).
mult(X,[Y|Ys],[Z|Zs]):-
        Z is X*Y,
        mult(X,Ys,Zs).
%% Predicado auxiliar usado en rellena0
repeat(_,0,[]).
repeat(Elem,1,[Elem]).
repeat(Elem,N,[Elem|Xs]):-
        N>0,
        N1 is N-1,
        repeat(Elem,N1,Xs).
%Añade 0 por la izquierda en la lista para multiplicar
rellena0(X,0,X).
rellena0(X,N,Z):-
        N>0,
        repeat(0,N,S),
        append(S,X,Z).
%Multiplica un polinomio por una variable
mult(_,_,[],[]).
mult(_,0,_,[]).
mult(G,Coef,X,Z):-
        mult(Coef,X,S1),
        rellena0(S1,G,Z).
%Multiplica polinomios
multPol(_,[],[]).
multPol([],_,[]).
multPol(X,Y,Z):-
        multPol(0,X,Y,Z).
multPol(_,[],_,[]).
multPol(_,_,[],[]).
multPol(N,[X|Xs],Y,Z):-
        N1 is N+1,
        multPol(N1,Xs,Y,S1),
        mult(N,X,Y,S2),
        suma(S1,S2,Z).

potencia(_,0,[1]).
potencia([],_,[]).
potencia(X,N,Xn):-
	N>0,
	mult(1,X,X1),
	N1 is N-1,
	potencia(X1,N1,Xn2),
	multPol(X1,Xn2,Xn).

hipercubo(N,L):-
	potencia([1,2],N,L), !.

%?- hipercubo(12,X).
%

%PARTE2 - DETECTANDO CICLOS EN GRAFOS

grafo(nociclo, [l(a,b), l(b,c), l(c,d)]).
grafo(seta, [l(a,b), l(b,c), l(c,b),l(c,d), l(d,a)]).
grafo(vainas,[l(a,b),l(b,c),l(c,d),l(d,e),l(d,b),l(b,a)]).


cicloX(G,X,Z):-
	cicloX(G,X,[X],Z).

cicloX(G,X,Zs,Z):-
	grafo(G,A),
	member(l(X,W),A),
	member(W,Zs),
	append(Zs,[W],Z).

cicloX(G,X,Zs,Z):-
	grafo(G,A),
	member(l(X,W),A),
	not(member(W,Zs)),
	append(Zs,[W],Z1),
	cicloX(G,W,Z1,Z).

hay_Ciclo(G,R):-
	cicloX(G,_,R).

%PARTE3 - DECODIFICANDO LOS GENES


aminoacido(X,Z):-
	arg(1,a4("ucag"),W),
	member(Y,W),
	X==[103,99,Y],
	append([ala],[],Z).

aminoacido(X,Z):-
	arg(1,a2("ag"),W),
	member(Y,W),
	X==[103,97,Y],
	append([glu],[],Z).

aminoacido(X,Z):-
	X==[97,117,103],
	append([met],[],Z).

aminoacido(X,Z):-
	arg(1,a4("ucag"),W),
	member(Y,W),
	X==[97,99,Y],
	append([thr],[],Z).

aminoacido(X,Z):-
	arg(1,a1("uc"),W),
	member(Y,W),
	X==[103,97,Y],
	append([asp],[],Z).

aminoacido(X,Z):-
	arg(1,a4("ucag"),W),
	member(Y,W),
	X==[99,117,Y],
	append([leu],[],Z).

aminoacido(X,Z):-
	arg(1,a2("ag"),W),
	member(Y,W),
	X==[117,97,Y],
	append([stop],[],Z).

aminoacido(X,Z):-
	arg(1,a2("ag"),W),
	member(Y,W),
	X==[97,103,Y],
	append([arg],[],Z).

aminoacido(X,Z):-
	arg(1,a4("ucag"),W),
	member(Y,W),
	X==[103,103,Y],
	append([gly],[],Z).

aminoacido(X,Z):-
	arg(1,a1("uc"),W),
	member(Y,W),
	X==[117,117,Y],
	append([phe],[],Z).

aminoacido(X,Z):-
	X==[117,103,103],
	append([trp],[],Z).

aminoacido(X,Z):-
	arg(1,a2("ag"),W),
	member(Y,W),
	X==[99,97,Y],
	append([gln],[],Z).

aminoacido(X,Z):-
	arg(1,a2("ag"),W),
	member(Y,W),
	X==[97,97,Y],
	append([lys],[],Z).

aminoacido(X,Z):-
	X==[117,103,97],
	append([stop],[],Z).

aminoacido(X,Z):-
	arg(1,a4("ucag"),W),
	member(Y,W),
	X==[99,103,Y],
	append([arg],[],Z).

aminoacido(X,Z):-
	arg(1,a1("uc"),W),
	member(Y,W),
	X==[99,97,Y],
	append([his],[],Z).

aminoacido(X,Z):-
	arg(1,a4("ucag"),W),
	member(Y,W),
	X==[99,99,Y],
	append([pro],[],Z).

aminoacido(X,Z):-
	arg(1,a1("uc"),W),
	member(Y,W),
	X==[117,97,Y],
	append([tyr],[],Z).

aminoacido(X,Z):-
	arg(1,a4("ucag"),W),
	member(Y,W),
	X==[117,99,Y],
	append([ser],[],Z).

aminoacido(X,Z):-
	arg(1,a1("uc"),W),
	member(Y,W),
	X==[117,103,Y],
	append([cys],[],Z).

aminoacido(X,Z):-
	arg(1,a2("ag"),W),
	member(Y,W),
	X==[117,117,Y],
	append([leu],[],Z).

aminoacido(X,Z):-
	arg(1,a1("uc"),W),
	member(Y,W),
	X==[97,97,Y],
	append([asn],[],Z).

aminoacido(X,Z):-
	arg(1,a3("uca"),W),
	member(Y,W),
	X==[97,117,Y],
	append([ile],[],Z).

aminoacido(X,Z):-
	arg(1,a1("uc"),W),
	member(Y,W),
	X==[97,103,Y],
	append([ser],[],Z).

aminoacido(X,Z):-
	arg(1,a4("ucag"),W),
	member(Y,W),
	X==[103,117,Y],
	append([val],[],Z).

nPrimeros(1,[X|_],[],[X]).

nPrimeros(N,[X|Xs],Ys,Y):-
	N>1,
	N1 is N-1,
	nPrimeros(N1,Xs,_,Y2),
	append([X],Y2,Y),
        append(Y2,Ys,Xs).

comienza([X|Xs],Y):-
	nPrimeros(3,[X|Xs],Ys,Y2),
	aminoacido(Y2,[met]),
	append(Ys,[],Y).
comienza([X|Xs],Y):-
	nPrimeros(3,[X|Xs],_,Y2),
	Y2\=="aug",
	comienza(Xs,Y).

comienza([X|Xs],Y):-
	nPrimeros(3,[X|Xs],_,Y2),
	aminoacido(Y2,[met]),
	comienza(Xs,Y).

aminoacidos2(X,Y,Z):-
	nPrimeros(3,X,Y,Y2),
	aminoacido(Y2,[stop]),
	append([],[],Z).

aminoacidos2([_|Xs],Y,Z):-
	nPrimeros(3,Xs,Y,Y2),
	aminoacido(Y2,[stop]),
	append([],[],Z).

aminoacidos2([_,_|Xs],Y,Z):-
	nPrimeros(3,Xs,Y,Y2),
	aminoacido(Y2,[stop]),
	append([],[],Z).

aminoacidos2(X,Y,Z):-
	nPrimeros(3,X,Ys,Y2),
	aminoacido(Y2,Zs),
	not(member(stop,Zs)),
	aminoacidos2(Ys,Ws,Z2),
	append(Ws,[],Y),
	append(Zs,Z2,Z).

aminoacidos2([_|Xs],Y,Z):-
	nPrimeros(3,Xs,Ys,Y2),
	aminoacido(Y2,Zs),
	not(member(stop,Zs)),
	aminoacidos2(Ys,Ws,Z2),
	append(Ws,[],Y),
	append(Zs,Z2,Z).

aminoacidos2([_,_|Xs],Y,Z):-
	nPrimeros(3,Xs,Ys,Y2),
	aminoacido(Y2,Zs),
	not(member(stop,Zs)),
	aminoacidos2(Ys,Ws,Z2),
	append(Ws,[],Y),
	append(Zs,Z2,Z).

unificaListas([X|[]],X).

unificaListas([X,Y|[]],Z):-
	Z=..['-',Y,X].

unificaListas([X|Xs],Y):-
	length(Xs,N),
	N>1,
	unificaListas(Xs,Z),
	Y=..['-',Z,X].

aminoacidos(X,Z):-
	comienza(X,Y),
	aminoacidos2(Y,W,Z2),
	reverse(Z2,Z3),
	unificaListas(Z3,Z4),
	append([Z4],[],Z5),
	aminoacidos(W,Z6),
	append(Z5,Z6,Z).

aminoacidos(X,Z):-
	comienza(X,Y),
	aminoacidos2(Y,_,Z2),
	reverse(Z2,Z3),
	unificaListas(Z3,Z4),
	append([Z4],[],Z).













