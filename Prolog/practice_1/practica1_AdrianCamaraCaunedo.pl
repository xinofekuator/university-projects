/*Autores:
  -Ignacio Amaya de la Peña - 11M021
  -Adrian Camara Caunedo - 11M004
  -Borja Mas Garcia - 11M001
*/

%PARTE 1

%Ejercicio 1

en_suelo(a).
en_suelo(d).
en_suelo(f).
en_suelo(j).
contiguo(a,d).
contiguo(d,f).
contiguo(f,j).
sobre(c,d).
sobre(b,c).
sobre(e,f).
sobre(i,j).
sobre(h,i).
sobre(g,h).

%Forma estable(Para el ejercicio 6)

/*en_suelo(a).
en_suelo(c).
en_suelo(f).
en_suelo(j).
contiguo(a,c).
contiguo(c,f).
contiguo(f,j).
sobre(b,c).
sobre(d,b).
sobre(e,f).
sobre(i,j).
sobre(g,i).
sobre(h,g).
*/

%Ejercicio 2

base(B1,B2):-
	B1=B2,
	en_suelo(B1).

base(B1,B2):-
	sobre(B1,X),
	base(X,B2).

base_a_la_derecha(B1,B2):-
	contiguo(B1,B2).

base_a_la_derecha(B1,B2):-
	contiguo(B1,X),
	base_a_la_derecha(X,B2).

objeto_a_la_derecha(B1,B2):-
	base(B1,X),
	base(B2,Y),
	base_a_la_derecha(X,Y).

%Ejercicio 3

%objeto_a_la_derecha(X,e).
%base(b,X),base(Y,X).


%PARTE 2

%Ejercicio 4

forma(a,piramide).
forma(b,toro).
forma(c,cubo).
forma(d,esfera).
forma(e,cubo).
forma(f,toro).
forma(g,toro).
forma(h,esfera).
forma(i,toro).
forma(j,piramide).

%Ejercicio 5

inestable(O):-
	forma(O,X),
	X\=toro,
	forma(Y,piramide),
	sobre(O,Y).
inestable(O):-
	forma(O,X),
	X\=toro,
	forma(Y,piramide),
	forma(Z,toro),
	sobre(Z,Y),
	sobre(O,Z).
inestable(O):-
	forma(O,esfera),
	en_suelo(O).
inestable(O):-
	forma(O,esfera),
	sobre(O,X),
	forma(X,Y),
	Y\=toro.
inestable(O):-
	forma(O,X),
	X\=toro,
	sobre(O,Y),
	forma(Y,esfera).























