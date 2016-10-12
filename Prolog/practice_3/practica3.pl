%Autores: Ignacio Amaya - 11m021
%	  Adrián Cámara - 11m004
%	  Borja Mas - 11m001

%PARTE 1 - Encuestas

:-include('encuesta').

numeroVeces(_,[],0).
numeroVeces(X,[Z|Zs],N):-
	X==Z,
	numeroVeces(X,Zs,N1),
	N is N1+1.
numeroVeces(X,[Z|Zs],N):-
	X\==Z,
	numeroVeces(X,Zs,N).

masRepetido(L1,L2):-
	setof(X,((member(X,L1),numeroVeces(X,L1,N1),not((member(Y,L1),numeroVeces(Y,L1,N2),N1<N2)))),L2).

equipos_preferidos(L):-
	findall(X,equipo(X),L1),
	masRepetido(L1,L).


menosRepetido(L1,L2):-
	setof(X,((member(X,L1),numeroVeces(X,L1,N1),not((member(Y,L1),numeroVeces(Y,L1,N2),N1>N2)))),L2).

jugadores_no_populares(L):-
	findall(X,jugador(X),L1),
	menosRepetido(L1,L).


%PARTE 2 - La bandera de Italia

%nombreEstructura(dimensiones(lado,etc..),color).

bandera_italia(L1,L2):-
	findall(X,(member(X,L1),arg(2,X,verde)),V),
	findall(Y,(member(Y,L1),arg(2,Y,blanco)),B),
	findall(Z,(member(Z,L1),arg(2,Z,rojo)),R),
	append(V,B,L3),
	append(L3,R,L2).

bandera_italia_adhoc(L1,L2):-
	write('Escoja su figura favorita:'),
	tab(1),
	read(X),
	write('Elija sus dimensiones:'),
	tab(1),
	read(D),
	findall(Y,(member(Y,L1),functor(Y,X,_)),L3),
	findall(Z,(member(Z,L3),arg(1,Z,Zs),Zs@=<D),L4),
	bandera_italia(L4,L2).

%PARTE 3 - Ordenacion por criterio libre

%Orden creciente de los elementos de la lista
ordenCreciente(X,Y):-
	X<Y.

%Orden decreciente de los elementos de la lista
ordenDecreciente(X,Y):-
	X>Y.

%Orden ascendente alfabético-lexicográfico del tercer argumento de los elementos de la lista.
orden3Lex(X,Y):-
	arg(3,X,Xs),
	arg(3,Y,Ys),
	Xs@=<Ys.

qsort([],_,[]).
qsort([X|L],Order,SL) :-
	partition(L,X,Order,Left,Right),
	qsort(Left,Order,SLeft),
	qsort(Right,Order,SRight),
	append(SLeft,[X|SRight],SL).

partition([],_B,_,[],[]).
partition([E|R],C,Order,[E|Left1],Right):-
	P=..[Order,E,C],
	call(P),
	partition(R,C,Order,Left1,Right).
partition([E|R],C,Order,Left,[E|Right1]):-
	P=..[Order,E,C],
	not(P),
	%E >= C,
	partition(R,C,Order,Left,Right1).





