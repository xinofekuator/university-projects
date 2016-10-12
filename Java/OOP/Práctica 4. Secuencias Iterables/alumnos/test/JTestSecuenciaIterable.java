package test;

import secuenciasIterables.*;

import org.junit.Test;
import static org.junit.Assert.*;


public class JTestSecuenciaIterable {


	/**
	 * Comprueba que al llamar al constructor de SecuenciaIterable hay cero elementos
	 */
	@Test (timeout=50)
	public void testConstructorOk() {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		assertTrue("No es vacio",secuencia.esVacia());
		assertEquals("Numero de elementos incorrecto",0,secuencia.numeroElementos());
	}


	/**
	 * Comprueba que si se llama al método actual con una secuencia recién creada 
	 * el método genera una excepción 
	 * @throws ExcepcionSecuenciaIterableVacia
	 */
	@Test(expected=ExcepcionSecuenciaIterableVacia.class, timeout=50)
	public void testActualVacia() throws ExcepcionSecuenciaIterableVacia {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.actual();
	}	


	/**
	 * Comprueba que si se llama al método anterior con una secuencia recién creada 
	 * el método genera una excepción 
	 * @throws ExcepcionPrincipioSecuencia 	 
	 */
	@Test(expected=ExcepcionPrincipioSecuencia.class, timeout=50)
	public void testAlPrincipioVacia() throws ExcepcionPrincipioSecuencia  {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.anterior();		
	}


	/**
	 * Comprueba que si se llama al método siguiente con una secuencia recién creada el 
	 * método genera una excepción 
	 * @throws ExcepcionFinSecuencia 	
	 */
	@Test(expected=ExcepcionFinSecuencia.class, timeout=50)
	public void testAlFinalVacia() throws ExcepcionFinSecuencia {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.siguiente();
	}


	/**
	 * Comprueba que si se llama al método anterior con una secuencia recién creada el método genera una excepción
	 * Comprueba también que el método hayAnterior devuelve false 
	 * @throws ExcepcionPrincipioSecuencia
	 */
	@Test(expected=ExcepcionPrincipioSecuencia.class, timeout=50)
	public void testAnteriorVacia() throws ExcepcionPrincipioSecuencia{
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		assertFalse("hayAnterior incorrecto",secuencia.hayAnterior());
		secuencia.anterior();

	}	

	/**
	 * Comprueba los métodos hayAnterior y anterior	
	 */
	@Test (timeout=50)
	public void testAnteriorOk()  {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(5, true);
		secuencia.insertar(6, false);
		assertTrue("hayAnterior incorrecto",secuencia.hayAnterior());
		try {
			secuencia.anterior();
			assertEquals("Anterior incorrecto",5,secuencia.actual().intValue());
		} catch (ExcepcionPrincipioSecuencia e) {
			// TODO Auto-generated catch block
			fail("Deberia haber un elemento anterior");
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("No deberia estar vacia");
		}

	}

	/**
	 * Comprueba que si se llama al método siguiente con una secuencia recién creada el método 
	 * genera una excepción 
	 * @throws ExcepcionFinSecuencia
	 */
	@Test(expected=ExcepcionFinSecuencia.class, timeout=50)
	public void testSiguienteVacia() throws ExcepcionFinSecuencia{
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable <Integer>();
		assertFalse("haySiguiente incorrecto",secuencia.haySiguiente());
		secuencia.siguiente();
	}	

	/**
	 * Comprueba los métodos haySiguiente y siguiente	
	 */
	@Test (timeout=50)
	public void testSiguienteOk() {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(5, true);
		secuencia.insertar(6, true);
		assertTrue("haySiguiente incorrecto", secuencia.haySiguiente()); 
		try {
			secuencia.siguiente();
			assertEquals("Siguiente incorrecto",5,secuencia.actual().intValue());
		} catch (ExcepcionFinSecuencia e) {
			// TODO Auto-generated catch block
			fail("Deberia haber un elemento siguiente");
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("No deberia estar vacia");
		}
	}	

	
	
	/**
	 * Insertar un elemento en una secuencia vacía. 
	 * Comprueba los métodos insertar, actual, alPrincipio y alFinal
		 */

	@Test (timeout=50)
	public void testInsertarVaciaOk()  {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(5, true);
		assertFalse("Es vacio", secuencia.esVacia());
		assertEquals("No hay 1 elemento", 1, secuencia.numeroElementos());
		try {
			assertEquals("Actual incorrecto",5,secuencia.actual().intValue());
			assertFalse("Hay anterior",secuencia.hayAnterior());
			assertFalse("Hay siguiente",secuencia.haySiguiente());
			secuencia.alPrincipio();
			assertEquals("No hay 1 elemento", 1, secuencia.numeroElementos());
			assertEquals("Primero incorrecto",5,secuencia.actual().intValue());
			assertFalse("Hay anterior",secuencia.hayAnterior());
			assertFalse("Hay siguiente",secuencia.haySiguiente());
			secuencia.alFinal();
			assertEquals("No hay 1 elemento", 1, secuencia.numeroElementos());
			assertEquals("Ultimo incorrecto",5,secuencia.actual().intValue());
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("No deberia estar vacia");
		}
		assertFalse("Hay anterior",secuencia.hayAnterior());
		assertFalse("Hay siguiente",secuencia.haySiguiente());
	}


	/**
	 * Comprueba que al insertar un elemento antes de la posición del cursor el cursor queda 
	 * en el elemento que se ha insertado y se actualiza bien el primero
	 */
	@Test (timeout=50)
	public void testInsertarAntes() {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(5, true);
		secuencia.insertar(6, true);
		secuencia.insertar(7, true);
		try {
			assertEquals("Actual incorrecto",7,secuencia.actual().intValue());
			secuencia.alPrincipio();
			assertEquals("Primero incorrecto",7,secuencia.actual().intValue());
			secuencia.alFinal();
			assertEquals("Ultimo incorrecto",5,secuencia.actual().intValue());
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("No deberia estar vacia");
		}

		assertEquals("No hay 3 elementos", 3, secuencia.numeroElementos());
	}

	/**
	 * Comprueba que al insertar un elemento después de la posición del cursor queda 
	 * en el elemento que se ha insertado y se actualiza bien el ultimo	 
	 */
	@Test (timeout=50)
	public void testInsertarDespues() {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(5, false);
		secuencia.insertar(6, false);
		secuencia.insertar(7, false);
		try {
			assertEquals("Actual incorrecto",7,secuencia.actual().intValue());
			secuencia.alPrincipio();
			assertEquals("Primero incorrecto",5,secuencia.actual().intValue());
			secuencia.alFinal();
			assertEquals("Ultimo incorrecto",7,secuencia.actual().intValue());
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("No deberia estar vacia");
		}
		
		assertEquals("No hay 3 elementos", 3, secuencia.numeroElementos());
	}
	
	/**
	 * Comprueba que al Insertar en una secuencia antes de un elemento se actualizan bien los
	 * atributos siguiente y anterior	
	 */
	@Test (timeout=50)
	public void testInsertarAntesUnElemento()  {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(5, true);
		
		// no hay elem a la izquierda
		secuencia.insertar(7, true); // 7, 5
		
		try {					
			assertEquals("Actual incorrecto",7,secuencia.actual().intValue());
			secuencia.siguiente();
			assertEquals("Ultimo incorrecto",5,secuencia.actual().intValue());
			assertFalse("Deberia ser el ultimo", secuencia.haySiguiente());
			secuencia.anterior();
			assertEquals("Primero incorrecto",7,secuencia.actual().intValue());
			assertFalse("Deberia ser el primero", secuencia.hayAnterior());
		} catch (ExcepcionPrincipioSecuencia e) {
			// TODO Auto-generated catch block
			fail("Deberia haber un elemento anterior");
		} catch (ExcepcionFinSecuencia e) {
			// TODO Auto-generated catch block
			fail("Deberia haber un elemento siguiente");
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("No deberia estar vacia");
		}
		
		// hay elem a la izquierda		
		try {
			secuencia.siguiente();
			assertEquals("Actual incorrecto", 5, secuencia.actual().intValue());
			
			// insertamos el 6 a la izquierda del 5
			secuencia.insertar(6, true); // 7, 6, 5
			assertEquals("Actual incorrecto",6,secuencia.actual().intValue());
			secuencia.anterior();
			assertEquals("Primero incorrecto",7,secuencia.actual().intValue());
			assertFalse("Deberia ser el primero", secuencia.hayAnterior());
			secuencia.siguiente();
			secuencia.siguiente();
			assertEquals("Ultimo incorrecto",5,secuencia.actual().intValue());
		} catch (ExcepcionPrincipioSecuencia e) {
			// TODO Auto-generated catch block
			fail("Deberia haber un elemento anterior");
		} catch (ExcepcionFinSecuencia e) {
			// TODO Auto-generated catch block
			fail("Deberia haber un elemento siguiente");
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("No deberia estar vacia");
		}		
		assertFalse("Deberia ser el ultimo", secuencia.haySiguiente());
		
	}

	/**
	 * Comprueba que al Insertar en una secuencia despues de un elemento se actualizan bien los
	 * atributos siguiente y anterior	
	 */
	@Test (timeout=50)
	public void testInsertarDespuesUnElemento()  {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(5, false);
		
		// no hay elem a la derecha
		
		secuencia.insertar(7, false); // 5, 7
		try {
			assertEquals("Actual incorrecto",7,secuencia.actual().intValue());		
			try {
				secuencia.anterior();
				
				assertEquals("Primero incorrecto",5,secuencia.actual().intValue());
				assertFalse("Deberia ser el primero", secuencia.hayAnterior());
				
				secuencia.siguiente();
			} catch (ExcepcionPrincipioSecuencia e) {
				// TODO Auto-generated catch block
				fail("Deberia haber un elemento anterior");
			} catch (ExcepcionFinSecuencia e) {
				// TODO Auto-generated catch block
				fail("Deberia haber un elemento siguiente");
			}
				assertEquals("Ultimo incorrecto",7,secuencia.actual().intValue());		
				assertFalse("Deberia ser el ultimo", secuencia.haySiguiente());
				
				// hay elem a la derecha
				
				// insertamos el 6 a la derecha del 5
				secuencia.alPrincipio();
				assertEquals("Primero incorrecto",5,secuencia.actual().intValue());
				assertFalse("Deberia ser el primero", secuencia.hayAnterior());
				secuencia.insertar(6, false); // 5, 6, 7
				assertEquals("Actual incorrecto",6,secuencia.actual().intValue());		
			try {
				secuencia.siguiente();
				assertEquals("Ultimo incorrecto",7,secuencia.actual().intValue());
				assertFalse("Deberia ser el ultimo", secuencia.haySiguiente());
				secuencia.anterior();
				secuencia.anterior();
			} catch (ExcepcionPrincipioSecuencia e) {
				// TODO Auto-generated catch block
				fail("Deberia haber un elemento anterior");
			} catch (ExcepcionFinSecuencia e) {
				// TODO Auto-generated catch block
				fail("Deberia haber un elemento siguiente");
			}
			assertEquals("Primero incorrecto",5,secuencia.actual().intValue());
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("La secuencia no debería estar vacía");
		}		
		assertFalse("Deberia ser el primero", secuencia.hayAnterior());
		
	}


	/**
	 * Comprueba que dos secuencias de diferente tipo con 1 elemento son distintas
	 */
	@Test (timeout=50)
	public void testEqualsTipoNoSecuencia(){
		Integer secuencia1 = 5;
		SecuenciaIterableAbstracta<Integer> secuencia2 = new SecuenciaIterable<Integer>();		
			
		assertFalse("Equals incorrecto cuando compara una secuencia " +
				"con un objeto que no es una secuencia",secuencia1.equals(secuencia2));
		assertFalse("Equals incorrecto cuando compara una secuencia de un tipo " +
				"con un objeto que no es una secuencia",secuencia2.equals(secuencia1));	
		
	}
	
	/**
	 * Comprueba que dos secuencias de diferente tipo con 1 elemento son distintas
	 */
	@Test (timeout=50)
	public void testEqualsSecuenciasDifTipo(){
		SecuenciaIterable<Character> secuencia1 = new SecuenciaIterable<Character>();
		SecuenciaIterableAbstracta<Integer> secuencia2 = new SecuenciaIterable<Integer>();		
		
		secuencia1.insertar('a', true);
		secuencia2.insertar(2, true);
	
		assertFalse("Equals incorrecto cuando compara una secuencia de un tipo " +
				"con otra de otro tipo",secuencia1.equals(secuencia2));
		assertFalse("Equals incorrecto cuando compara una secuencia de un tipo " +
				"con otra de otro tipo",secuencia2.equals(secuencia1));	
		
	}
	
	/**
	 * Comprueba que dos secuencias vacías son iguales y que una secuencia que es vacía 
	 * no es igual que otra secuencia que no es vacía
	 */
	@Test (timeout=50)
	public void testEqualsVacia(){
		SecuenciaIterableAbstracta<Integer> secuencia1 = new SecuenciaIterable<Integer>();
		SecuenciaIterableAbstracta<Integer> secuencia2 = new SecuenciaIterable<Integer>();
		assertTrue("Equals incorrecto porque dice que dos secuencias vacías no son iguales",
				secuencia1.equals(secuencia2));
		assertTrue("Equals incorrecto porque dice que dos secuencias vacías no son iguales",
				secuencia2.equals(secuencia1));
		assertTrue("Equals incorrecto porque dice que dos secuencias vacías no son iguales",
				secuencia1.equals(secuencia1));
		
		
		// se compara una vacía con otra que no lo es
		secuencia1.insertar(5, true);
		assertFalse("Equals incorrecto cuando compara una secuencia vacía " +
				"con otra que no es vacía",secuencia1.equals(secuencia2));
		assertFalse("Equals incorrecto cuando compara una secuencia vacía " +
				"con otra que no es vacía",secuencia2.equals(secuencia1));
	}

	@Test (timeout=50)
	public void testEqualsHabitual(){
		SecuenciaIterableAbstracta<Integer> secuencia1 = new SecuenciaIterable<Integer>();
		SecuenciaIterableAbstracta<Integer> secuencia2 = new SecuenciaIterable<Integer>();
		assertTrue("Equals incorrecto porque dice que dos secuencias vacías no son iguales",
				secuencia1.equals(secuencia2));
		assertTrue("Equals incorrecto porque dice que dos secuencias vacías no son iguales",
				secuencia2.equals(secuencia1));
		assertTrue("Equals incorrecto porque dice que dos secuencias vacías no son iguales",
				secuencia1.equals(secuencia1));
				
		// se compara una vacía con otra que no lo es
		secuencia1.insertar(5, true);
		assertFalse("Equals incorrecto cuando compara una secuencia vacía " +
				"con otra que no es vacía",secuencia1.equals(secuencia2));
		assertFalse("Equals incorrecto cuando compara una secuencia vacía " +
				"con otra que no es vacía",secuencia2.equals(secuencia1));
		
		// se compara una con 1 elemento con otra con 2.
		secuencia1.insertar(6, false);
		secuencia2.insertar(5, true);
		assertFalse("Equals incorrecto cuando compara una secuencia " +
				"con 2 eltos con otra con 1 elto.",secuencia1.equals(secuencia2));
		assertFalse("Equals incorrecto cuando compara una secuencia " +
				"con 1 elto con otra con 2 eltos.",secuencia2.equals(secuencia1));
		
	}


	/**
	 * Comprueba si dos secuencia son iguales para el caso en que lo son y 
	 * para el caso en que no lo son
	 */
	@Test (timeout=50)
	public void testEqualsOk(){
		SecuenciaIterableAbstracta<Integer> secuencia1 = new SecuenciaIterable<Integer>();
		SecuenciaIterableAbstracta<Integer> secuencia2 = new SecuenciaIterable<Integer>();
		secuencia1.insertar(5, true);
		secuencia1.insertar(6, true);
		secuencia1.insertar(7, true);
		
		secuencia2.insertar(5, true);
		secuencia2.insertar(6, true);
		secuencia2.insertar(7, true);
		
		// secuencias iguales
		
		assertTrue("Equals incorrecto cuando compara dos secuencias iguales", secuencia1.equals(secuencia2));
		assertTrue("Equals incorrecto cuando compara dos secuencias iguales", secuencia2.equals(secuencia1));
		
		// secuencias iguales cuando actual esta en posiciones distintas
		try {
			secuencia1.alPrincipio();
			secuencia2.alPrincipio();
			secuencia1.siguiente();
			int actual = secuencia1.actual();
			assertTrue("Equals incorrecto cuando compara dos secuencias iguales pero actual esta en posiciones distintas", secuencia1.equals(secuencia2));
			assertTrue("Equals incorrecto cuando compara dos secuencias iguales pero actual esta en posiciones distintas", secuencia2.equals(secuencia1));
			assertTrue ("Equals modifica la posicion de actual", actual==secuencia1.actual());
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("La secuencia no debería estar vacía");
		} catch (ExcepcionFinSecuencia e) {
			// TODO Auto-generated catch block
			fail("No debería ser el ultimo");
		}	
		
		// secuencias distintas
		
		secuencia2.alFinal();
		secuencia2.insertar(8, false); // con un elem más
		assertFalse("Equals incorrecto cuando compara dos secuencias distintas", secuencia1.equals(secuencia2));
		assertFalse("Equals incorrecto cuando compara dos secuencias distintas", secuencia2.equals(secuencia1));
		
		secuencia1.alFinal();
		secuencia1.insertar(9, false); // el mismo no. de elems pero el ultimo es distinto
		assertFalse("Equals incorrecto cuando compara dos secuencias distintas", secuencia1.equals(secuencia2));
		assertFalse("Equals incorrecto cuando compara dos secuencias distintas", secuencia2.equals(secuencia1));
		
		secuencia1 = new SecuenciaIterable<Integer>();
		secuencia2 = new SecuenciaIterable<Integer>();
		secuencia1.insertar(5, true);
		secuencia1.insertar(6, true);
		secuencia1.insertar(8, true);
		
		secuencia2.insertar(5, true);
		secuencia2.insertar(6, true);
		secuencia2.insertar(7, true);
		
		// el mismo no. de elems pero el primero es distinto
		assertFalse("Equals incorrecto cuando compara dos secuencias distintas", secuencia1.equals(secuencia2));
		assertFalse("Equals incorrecto cuando compara dos secuencias distintas", secuencia2.equals(secuencia1));		
		
	}


	/**
	 * Comprueba que el método borrar genera una excepción cuando se le llama con 
	 * una secuencia vacía
	 * @throws ExcepcionSecuenciaIterableVacia
	 */
	@Test(expected=ExcepcionSecuenciaIterableVacia.class, timeout=50)
	public void testBorrarSecuenciaVacia() throws ExcepcionSecuenciaIterableVacia{
		SecuenciaIterableAbstracta<Integer> secuencia1 = new SecuenciaIterable<Integer>();
		secuencia1.borrar();	
	}	

	/**
	 * Comprueba el método borrar cuando sólo hay un elemento	
	 */
	@Test (timeout=50)
	public void testBorrarUnElemento() {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(5, true);
		try {
			secuencia.borrar();
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("La secuencia no debería estar vacía");
		}
		assertTrue("No es vacio", secuencia.esVacia());
		assertEquals("No hay cero elementos",0,secuencia.numeroElementos());
	}	

	/**
	 * Comprueba método borrar en una secuencia cuando se borra el primero	
	 */
	@Test (timeout=50)
	public void testBorrarPrimeroSecuencia() {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(4, true);
		secuencia.insertar(5, true);
		secuencia.insertar(6, true); // actual
		
		// borra el primero (6)
		try {
			secuencia.borrar();
		} catch (ExcepcionSecuenciaIterableVacia e1) {
			// TODO Auto-generated catch block
			fail("La secuencia no debería estar vacía");
		}
		assertFalse("Es vacio", secuencia.esVacia());
		assertEquals("No hay dos elementos",2,secuencia.numeroElementos());
		try {
			assertEquals(5,secuencia.actual().intValue());
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("La secuencia no debería estar vacía");
		}
		
		assertFalse("El actual debería ser el primero", secuencia.hayAnterior());
	}

	/**
	 * Comprueba método borrar en una secuencia cuando se borra el ultimo	
	 */
	@Test (timeout=50)
	public void testBorrarUltimoSecuencia() {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(4, false);
		secuencia.insertar(5, false);
		secuencia.insertar(6, false); // actual
		
		// borra el ultimo (6)
		try {
			secuencia.borrar();
		} catch (ExcepcionSecuenciaIterableVacia e1) {
			// TODO Auto-generated catch block
			fail("La secuencia no debería estar vacía");
		}
		assertFalse("Es vacio", secuencia.esVacia());
		assertEquals("No hay dos elementos",2,secuencia.numeroElementos());
		try {
			assertEquals(5,secuencia.actual().intValue());
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("La secuencia no debería estar vacía");
		}
		
		assertFalse("El actual debería ser el ultimo", secuencia.haySiguiente());
	}

	/**
	 * Comprueba método borrar en una secuencia cuando se borra un elem intermedio	
	 */
	@Test (timeout=50)
	public void testBorrarIntermedioSecuencia() {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(4, false);
		secuencia.insertar(5, false);
		secuencia.insertar(6, false); // actual
		
		try {
			secuencia.anterior();
		} catch (ExcepcionPrincipioSecuencia e2) {
			// TODO Auto-generated catch block
			fail("No debería ser el primero");
		}
		
		// borra el intermedio (5)
		try {
			secuencia.borrar();
		} catch (ExcepcionSecuenciaIterableVacia e1) {
			// TODO Auto-generated catch block
			fail("La secuencia no debería estar vacía");
		}
		assertFalse("Es vacio", secuencia.esVacia());
		assertEquals("No hay dos elementos",2,secuencia.numeroElementos());
		try {
			assertEquals(6,secuencia.actual().intValue());
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("La secuencia no debería estar vacía");
		}
		
		assertFalse("El actual debería ser el ultimo", secuencia.haySiguiente());
		
		// recorremos la secuencia hacia delante
		
		secuencia.alPrincipio();
		
		try {
			assertEquals(4,secuencia.actual().intValue());		
			secuencia.siguiente();		
			assertEquals(6,secuencia.actual().intValue());
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("La secuencia no debería estar vacía");
		} catch (ExcepcionFinSecuencia e) {
			// TODO Auto-generated catch block
			fail("No debería ser el ultimo");
		}
		
		// recorremos la secuencia hacia atrás
		
		secuencia.alFinal();		
		
		try {
			secuencia.anterior();
			assertEquals(4,secuencia.actual().intValue());
		} catch (ExcepcionPrincipioSecuencia e) {
			// TODO Auto-generated catch block
			fail("No debería ser el primero");
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("La secuencia no debería estar vacía");
		}
		
	}	
	

	/**
	 * Comprueba que el método borrarEn genera una excepción si se 
	 * le llama con una secuencia vacía
	 * @throws ExcepcionSecuenciaIterableVacia	
	 */
	@Test(expected=ExcepcionSecuenciaIterableVacia.class, timeout=50)
	public void testBorrarEnSecuenciaVacia() throws ExcepcionSecuenciaIterableVacia {
		SecuenciaIterableAbstracta<Integer> secuencia1 = new SecuenciaIterable<Integer>();
		try {
			secuencia1.borrarEn(1);
		} catch (ExcepcionPosicionFueraDeRango e) {
			// TODO Auto-generated catch block
			fail("No debería saltar esta excepción si la secuencia está vacía");
		}

	}
	
	/**
	 * Comprueba que el método borrarEn genera una excepción si se 
	 * la posición negativa está fuera de rango
	 * @throws ExcepcionPosicionFueraDeRango	
	 */
	@Test(expected=ExcepcionPosicionFueraDeRango.class, timeout=50)
	public void testBorrarPosNegativaFueraRango() throws ExcepcionPosicionFueraDeRango {
		SecuenciaIterableAbstracta<Integer> secuencia1 = new SecuenciaIterable<Integer>();
		secuencia1.insertar(1, false);
		secuencia1.insertar(2, false);
		try {
			secuencia1.borrarEn(-2); // el actual es el 2
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("No debería saltar esta excepción si la secuencia no está vacía");
		}
	}
	
	/**
	 * Comprueba que el método borrarEn genera una excepción si se 
	 * la posición positiva está fuera de rango
	 * @throws ExcepcionPosicionFueraDeRango	
	 */
	@Test(expected=ExcepcionPosicionFueraDeRango.class, timeout=50)
	public void testBorrarPosPositivaFueraRango() throws ExcepcionPosicionFueraDeRango {
		SecuenciaIterableAbstracta<Integer> secuencia1 = new SecuenciaIterable<Integer>();
		secuencia1.insertar(1, true);
		secuencia1.insertar(2, true);
		try {
			secuencia1.borrarEn(2); // el actual es el 2
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("No debería saltar esta excepción si la secuencia no está vacía");
		}
	}

	/**
	 * Comprueba el método borrarEn cuando sólo hay un elemento en la secuencia
	 */
	@Test (timeout=50)
	public void testBorrarEnUnElemento() {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(5, true);
		try {
			secuencia.borrarEn(0);
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("No debería estar vacía");
		} catch (ExcepcionPosicionFueraDeRango e) {
			// TODO Auto-generated catch block
			fail("No debería salirse del rango");
		}
		assertTrue("No es vacio", secuencia.esVacia());
		assertEquals("No hay zero elementos",0,secuencia.numeroElementos());
	}	

	/**
	 * Comprueba el método borrarEn cuando se borran los extremos de la secuencia	 
	 */
	@Test (timeout=50)
	public void testBorrarEnExtremos() {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(5, true);
		secuencia.insertar(6, true);
		secuencia.insertar(7, true);
		secuencia.insertar(8, true); // actual
		try {
			secuencia.borrarEn(3); // borra el ultimo (5)
			assertFalse("El actual debería ser el ultimo", secuencia.haySiguiente());
			assertEquals("Actual incorrecto",6,secuencia.actual().intValue());
			secuencia.borrarEn(-2); // borra el primero (8)
			assertEquals("Actual incorrecto",7,secuencia.actual().intValue());
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("No debería estar vacía");
		} catch (ExcepcionPosicionFueraDeRango e) {
			// TODO Auto-generated catch block
			fail("No debería salirse del rango");
		}
		assertFalse("El actual debería ser el primero", secuencia.hayAnterior());		
	}

	/**
	 * Comprueba el método borrarEn cuando se borran los intermedios de la secuencia	
	 */
	@Test (timeout=50)
	public void testBorrarEnIntermedios() {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(5, true);
		secuencia.insertar(6, true);
		secuencia.insertar(7, true);
		secuencia.insertar(8, true); // actual
		try {
			secuencia.siguiente();
			secuencia.borrarEn(1); // borra el 6 -> 8,7,5(A)			
			assertEquals("Actual incorrecto", 5, secuencia.actual().intValue());
			secuencia.anterior();
			assertEquals("Actual incorrecto", 7, secuencia.actual().intValue());
			secuencia.siguiente();
			assertEquals("Actual incorrecto", 5, secuencia.actual().intValue());			
			secuencia.borrarEn(-1); // borra el 7 -> 8,5(A)
			assertEquals("Actual incorrecto", 5, secuencia.actual().intValue());
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("No debería estar vacía");
		} catch (ExcepcionPosicionFueraDeRango e) {
			// TODO Auto-generated catch block
			fail("No debería salirse del rango");
		} catch (ExcepcionFinSecuencia e) {
			// TODO Auto-generated catch block
			fail("No debería ser el ultimo");
		} catch (ExcepcionPrincipioSecuencia e) {
			// TODO Auto-generated catch block
			fail("No debería ser el primero");
		}		
	}


	/**
	 * Comprueba el contenido final de una secuencia después de insertar y borrar elementos	
	 */
	@Test (timeout=50)
	public void numeroElementosInsertarBorrar() {
		SecuenciaIterableAbstracta<Integer> secuencia = new SecuenciaIterable<Integer>();
		secuencia.insertar(4, true);
		secuencia.insertar(5, true);
		secuencia.insertar(7, true);		
		secuencia.insertar(8, true); // actual		
		try {
			secuencia.borrarEn(1); // borra 7 -> 8,5(A),4
			secuencia.insertar(9, true); // 8,9(A),5,4		
			secuencia.insertar(3, false); // 8,9,3(A),5,4		
			secuencia.borrarEn(-2); // borra 8 -> 9(A),3,5,4
			secuencia.borrarEn(1); // borra 3 -> 9,5(A),4
			
			secuencia.alPrincipio();
			assertEquals("Actual incorrecto", 9, secuencia.actual().intValue());
			secuencia.siguiente();
			assertEquals("Actual incorrecto", 5, secuencia.actual().intValue());
			secuencia.siguiente();
			assertEquals("Actual incorrecto", 4, secuencia.actual().intValue());
			secuencia.anterior();
			assertEquals("Actual incorrecto", 5, secuencia.actual().intValue());
			secuencia.anterior();
			assertEquals("Actual incorrecto", 9, secuencia.actual().intValue());			
			
		} catch (ExcepcionSecuenciaIterableVacia e) {
			// TODO Auto-generated catch block
			fail("No debería estar vacía");
		} catch (ExcepcionPosicionFueraDeRango e) {
			// TODO Auto-generated catch block
			fail("No debería salirse del rango");
		} catch (ExcepcionFinSecuencia e) {
			// TODO Auto-generated catch block
			fail("No debería ser el ultimo");
		} catch (ExcepcionPrincipioSecuencia e) {
			// TODO Auto-generated catch block
			fail("No debería ser el primero");
		}		
	}

}
