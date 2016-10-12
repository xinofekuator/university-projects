package secuenciasIterables;

/**
 * @author Clara Benac Earle
 */

/**
 * Definición de un TAD Secuencia Iterable e
 * Implementación de los atributos necesarios para implementar dicho TAD 
 * como una cadena doblemente enlazada
 *
 */
abstract public class SecuenciaIterableAbstracta<Informacion> {//SecuenciaIterableAbstracta



	//-------------------------------------------------------------------
	//--  DOMINIO
	//--    TIPO Sec_Iterable = (Coleccion : Secuencia (TipoElemento) x
	//--                     Actual : |N)
	//--    INVARIANTE: (A) i EN Sec_Iterable. Es_Estructura (i) /\
	//--                     (i.Actual <= longitud(i.Coleccion)) /\
	//--                     (i.Coleccion /= <> <-> i.Actual > 0)
	//--
	//--  NOTA: Es_Estructura es un predicado que pretende informar
	//--  al usuario de ciertas propiedades de cualquier TAD
	//--  implementado con punteros (ver el TAD Lista).

	// estos atributos permiten acceder a la cadena doblemente enlazada
	private NodoDoble<Informacion> actual, primero, ultimo;

	// Métodos getter y setter
	protected NodoDoble<Informacion> getActual() {
		return actual;
	}

	protected NodoDoble<Informacion> getPrimero() {
		return primero;
	}

	protected NodoDoble<Informacion> getUltimo() {
		return ultimo;
	}

	protected void setActual(NodoDoble<Informacion> actual) {
		this.actual = actual;
	}

	protected void setPrimero(NodoDoble<Informacion> primero) {
		this.primero = primero;
	}

	protected void setUltimo(NodoDoble<Informacion> ultimo) {
		this.ultimo = ultimo;
	}



	//-------------------------------------------------------------------
	//--  PRE: Cierto
	//--  POST: I.Coleccion = <>
	//--  COMPLEJIDAD: O(1)
	/**
	 * Método esVacia
	 * Indica si la secuencia iterable está vacía
	 * <br><B>PRE:</B> Cierto
	 * <br><B>POST:</B> devuelve cierto si no quedan elementos en la secuencia iterable, y 
	 * falso en caso contrario
	 * <br><B>COMPLEJIDAD:</B> O(1)<br>
	 * 
	 */
	public abstract boolean esVacia ();


	//-------------------------------------------------------------------
	//--  PRE: NO Es_Vacio(I)
	//--  POST: resultado = I.Coleccion (I.Actual)
	//--  COMPLEJIDAD: O (1)

	/**
	 * Devuelve el elemento en el que se encuentra el cursor
	 * <br><B>PRE:</B> La secuencia iterable no está vacía
	 * <br><B>POST:</B> Devuelve el elemento en el que se encuentra el cursor
	 * <br><B>COMPLEJIDAD:</B> O(1)<br>
	 * @return  una referencia al mismo dato referenciado en la secuencia, 
	 * <B> y NO una referencia a una copia del dato</B>
	 * @throws ExcepcionSecuenciaIterableVacia excepción que se genera si se viola la precondición
	 */
	public abstract Informacion actual() throws ExcepcionSecuenciaIterableVacia;


	//-------------------------------------------------------------------
	//--  PRE: cierto
	//--  POST: I^ent.Coleccion = I^sal.Coleccion /\ I^sal.Actual = 1
	//--  COMPLEJIDAD: O (1)
	/**
	 * Coloca el cursor en el primer elemento de la secuencia iterable
	 * <br><B>PRE:</B> Cierto
	 * <br><B>POST:</B> deja el cursor apuntando a primer elemento de la secuencia iterable(this)
	 * <br><B>COMPLEJIDAD:</B> O(1)<br>
	 */
	public abstract void alPrincipio ();



	//-------------------------------------------------------------------
	//--  PRE: cierto
	//--  POST: I^ent.Coleccion = I^sal.Coleccion /\
	//--        I^sal.Actual = longitud (I^ent.Coleccion)
	//--  COMPLEJIDAD: O (1)
	/**
	 * Coloca el cursor en el último elemento de la secuencia iterable
	 * <br><B>PRE:</B> Cierto
	 * <br><B>POST:</B> deja el cursor apuntando a último elemento de la secuencia iterable(this)
	 * <br><B>COMPLEJIDAD:</B> O(1)<br>
	 */
	public abstract void alFinal ();


	//-------------------------------------------------------------------
	//--  PRE: Cierto
	//--  POST: 
	//--		  (esVacia(I) -> I^sal.Coleccion = <Dato> /\ I^sal.Actual = 1) /\ 
	//--		  (NO esVacia(I) /\ 
	//--         Insertar_Antes -> I^sal.Coleccion = I^ent.Coleccion (1..I^ent.Actual-1) + <Dato> +
	//--                                             I^ent.Coleccion (I^ent.Actual..N) /\
	//--                                             I^sal.Actual = I^ent.Actual) /\
	//--
	//--       (e.o.c.          -> I^sal.Coleccion = I^ent.Coleccion (1..I^ent.Actual) + <Dato> +
	//--                                             I^ent.Coleccion (I^ent.Actual+1..N) /\
	//--                                             I^sal.Actual = I^ent.Actual + 1)
	//--
	//--  DONDE: N = longitud(I^ent.Coleccion)
	//--  COMPLEJIDAD: O(1)
	/**
	 * Inserta un elemento antes o después del cursor
	 * <br><B>PRE:</B> Cierto
	 * <br><B>POST:</B> Si la secuencia está vacía, entonces se devuelve una secuencia con informacion como
	 * único dato; si la secuencia no está vacía, entonces distinguimos dos casos:
	 * <ul>
	 * <li>Si se desea insertar antes, el dato se inserta justo antes del elemento en el que está el cursor;</li>
	 * <li>Si se desea insertar después, el dato se inserta justo después del elemento en el que está el cursor;</li>
	 * </ul>
	 * En cualquier caso, el cursor se queda en el dato que se acaba de insertar.
	 * <br><B>COMPLEJIDAD:</B> O(1)<br>
	 * @param  informacion Referencia al objeto dato que se va a guardar en la secuencia. 
	 * No se guarda una referencia a una copia del dato
	 */
	public abstract void insertar (Informacion informacion, boolean  insertarAntes);


	//-------------------------------------------------------------------
	//--  PRE: cierto
	//--  POST: resultado = (I.Actual > 1)
	//--  COMPLEJIDAD: O (1)
	/**
	 * Comprueba si el cursor está en el primer elemento de la secuencia iterable
	 * <br><B>PRE:</B> Cierto
	 * <br><B>POST:</B> devuelve falso si el cursor está en el primer elemento de
	 * la secuencia iterable(this), y cierto en caso contrario
	 * <br><B>COMPLEJIDAD:</B> O(1)<br>
	 * NOTA: Si la secuencia está vacía, se supone que no hay siguiente ni anterior
	 */
	public abstract boolean hayAnterior ();



	//-------------------------------------------------------------------
	//--  PRE: Hay_Anterior (I)
	//--  POST: I^sal.Coleccion = I^ent.Coleccion /\
	//--        I^sal.Actual = I^ent.Actual - 1
	//--  COMPLEJIDAD: O (1)
	/**
	 * Desplaza el cursor al anterior elemento de la secuencia iterable
	 * <br><B>PRE:</B> Hay un anterior en la secuencia iterable
	 * <br><B>POST:</B> deja el cursor en el anterior elemento de la secuencia iterable
	 * <br><B>COMPLEJIDAD:</B> O(1)<br>
	 * @throws ExcepcionPrincipioSecuencia excepción que se genera si se viola la precondición
	 */
	public abstract void anterior () throws ExcepcionPrincipioSecuencia;


	//-------------------------------------------------------------------
	//--  PRE: cierto
	//--  POST: resultado = (I.Actual < longitud (I.Coleccion))
	//--  COMPLEJIDAD: O (1)
	/**
	 * Comprueba si el cursor está en el último elemento de la secuencia iterable
	 * <br><B>PRE:</B> Cierto
	 * <br><B>POST:</B> devuelve falso si el cursor está en el último elemento de
	 * la secuencia iterable(this), y cierto en caso contrario
	 * <br><B>COMPLEJIDAD:</B> O(1)<br>
	 * NOTA: Si la secuencia está vacía, se supone que no hay siguiente ni anterior
	 */
	public abstract boolean haySiguiente();


	//-------------------------------------------------------------------
	//--  PRE: Hay_Siguiente (I)
	//--  POST: I^sal.Coleccion = I^ent.Coleccion /\
	//--        I^sal.Actual = I^ent.Actual + 1
	//--  COMPLEJIDAD: O (1)
	/**
	 * Desplaza el cursor al siguiente elemento de la secuencia iterable
	 * <br><B>PRE:</B> Hay un siguiente en la secuencia iterable
	 * <br><B>POST:</B> deja el cursor en el siguiente elemento de la secuencia iterable
	 * <br><B>COMPLEJIDAD:</B> O(1)<br>
	 * @throws ExcepcionFinSecuencia excepción que se genera si se viola la precondición
	 */
	public abstract void siguiente () throws ExcepcionFinSecuencia;


	//-------------------------------------------------------------------
	//--  PRE: Es_Vacio(I1)
	//--  POST: I1^sal.Coleccion = I2.Coleccion /\ NO Hay_Anterior(I1^sal)
	//--  COMPLEJIDAD: O (n) donde n = longitud(I2.Coleccion)
	/**
	 * Indica si dos secuencias iterables son iguales
	 * <br><B>PRE:</B> Cierto
	 * <br><B>POST:</B> devuelve cierto si las dos secuencias son iguales, y falso en caso contrario
	 * <br><B>COMPLEJIDAD:</B> O(N) donde N = Min(longitud(this), longitud(obj))<br>
	 */
	@Override
	public abstract boolean equals (Object o);


	//-------------------------------------------------------------------
	//--  PRE: NO Es_Vacio (I)
	//--  POST: (I^sal = I^ent.Coleccion (1..I^ent.Actual-1) + I^ent.Coleccion (I^ent.Actual+1..N))/\
	//--       (I^sal.Actual = Nuevo_Actual(I^ent.Actual, longitud(I^ent.Coleccion)))
	//--
	//--  DONDE:
	//--         N = longitud(I^ent.Coleccion)
	//--         Nuevo_Actual(v, max) = v,    si v/=max
	//--         Nuevo_Actual(v, max) = v-1,  si v=max
	//--  COMPLEJIDAD: O(1)
	/**
	 * Borra el elemento de la secuencia iterable en el que se encuentra el cursor
	 * <br><B>PRE:</B> No es vacía la secuencia
	 * <br><B>POST:</B> El resultado es el this de entrada sin el elemento en el que se 
	 * encontraba el cursor, y:
	 * <ul>
	 * <li>Si el elemento borrado era el último, el cursor se queda en el nuevo último elemento;</li>
	 * <li>Si el elemento borrado no era el último, el cursor se queda en el siguiente al elemento borrado;</li>
	 * </ul>
	 * <br><B>COMPLEJIDAD:</B> O(1)<br>
	 * @throws ExcepcionSecuenciaIterableVacia 
	 */
	public abstract void borrar () throws ExcepcionSecuenciaIterableVacia;


	//-------------------------------------------------------------------
	//--  PRE: NO Es_Vacio (I) /\ 1 <= (I.Actual + posicion) <= longitud(I^ent.Coleccion)
	//--  POST: 
	//--		 (I^sal = I^ent.Coleccion (1..I^ent.Actual+posicion-1) + I^ent.Coleccion (I^ent.Actual+posicion+1..N)) /\
	//--		 (I^sal.Actual = Nuevo_Actual(I^ent.Actual, N))
	//--  DONDE:
	//--         N = longitud(I^ent.Coleccion)
	//--         Nuevo_Actual(v, max) = v,    si v/=max
	//--         Nuevo_Actual(v, max) = v-1,  si v=max
	//--  COMPLEJIDAD: O(posicion)
	/**
	 * <br><B>PRE</B>: secuencia no vacía y 1 <= (posicion del actual + posicion) <= n 
	 * <br> donde n es el número total de elementos en la secuencia
	 * <br><B>POST:</B> borra el elemento que se encuentra en (posicion del actual + posicion) y:
	 * <ul>
	 * <li>Si el elemento borrado era el último, el cursor se queda en el nuevo último elemento;</li>
	 * <li>Si el elemento borrado no era el último, el cursor se queda en el siguiente al elemento borrado;</li>
	 * </ul>
	 * <br><B>COMPLEJIDAD:</B> O(n) donde n es el valor de posición<br>
	 * @param posicion posición del elemento que se desea borrar
	 * @throws ExcepcionSecuenciaIterableVacia
	 * @throws ExcepcionPosicionFueraDeRango
	 */
	public abstract void borrarEn(int posicion) throws ExcepcionSecuenciaIterableVacia,ExcepcionPosicionFueraDeRango;



	/**
	 * Método que retorna el número de elementos que hay en la secuencia.
	 * <br><B>PRE:</B> Cierto
	 * <br><B>POST:</B> retorna el número de elementos en la secuencia
	 * <br><B>COMPLEJIDAD:</B> O(1)<br>
	 * @return retorna el número de elementos en la secuencia
	 */
	public abstract int numeroElementos();




}//SecuenciaIterableAbstracta
