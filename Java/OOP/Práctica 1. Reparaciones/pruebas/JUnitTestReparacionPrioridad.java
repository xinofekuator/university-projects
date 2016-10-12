/**
 * Esta clase es un Caso de Prueba JUnit de la clase 
 * ReparacionPrioridad
 * @author susana
 */
package pruebas;

import static org.junit.Assert.*;

import org.junit.Test;

import excepciones.ErrorNumeroCiclosIncorrecto;
import excepciones.ErrorPrioridadIncorrecta;
import excepciones.ErrorReparacionYaConcluida;

import reparacion.ReparacionPrioridad;

public class JUnitTestReparacionPrioridad {
	
	/**
	 * Test method for {@link reparacion.ReparacionPrioritaria#ReparacionPrioritaria(int, int, int)}.
	 * Comprueba que salta la excepción ErrorNumeroCiclosIncorrecto cuando
	 * en número de ciclos de una reparación es 0
	 */
	@Test (expected = ErrorNumeroCiclosIncorrecto.class)
	public final void testReparacionPrioridadCiclos() throws ErrorNumeroCiclosIncorrecto, ErrorPrioridadIncorrecta{
		@SuppressWarnings("unused")
		ReparacionPrioridad reparacion = new ReparacionPrioridad(10,1,0);
		} // de testReparacionPrioridad

	/**
	 * Test method for {@link reparacion.ReparacionPrioritaria#ReparacionPrioritaria(int, int, int)}.
	 * Comprueba que salta la excepción ErrorPrioridadIncorrecta cuando
	 * la prioridad es 0 o negativa
	 */
	@Test (expected = ErrorPrioridadIncorrecta.class)
	public final void testReparacionPrioridadPrioridad() throws ErrorNumeroCiclosIncorrecto, ErrorPrioridadIncorrecta{
		@SuppressWarnings("unused")
		ReparacionPrioridad reparacion = new ReparacionPrioridad(10,-3,3);
		} // de testReparacionPrioridad

	/**
	 * Test method for {@link reparacion.ReparacionPrioridad#calcularCoste()}.
	 * Comprueba que el coste se ha calculado correctamente para 4 ciclos
	 */
	@Test
	public final void testCalcularCoste() throws ErrorNumeroCiclosIncorrecto, ErrorPrioridadIncorrecta {
		int prioridad = 3;
		int ciclos= 4;
		ReparacionPrioridad reparacion = new ReparacionPrioridad(11,prioridad,ciclos);
		assertTrue("El coste calculado de 1 ciclo no es correcto",
				30.0*ciclos*prioridad/2+25.0*ciclos/2+prioridad*10 == reparacion.calcularCoste());
	} // de testCalcularCoste
	
	/**
	 * Test method for {@link reparacion.Reparacion#realizar()}.
	 * Comprueba que al llamar a realizar de una reparación concluida
	 * salta una excepción
 */
	@Test (expected = ErrorReparacionYaConcluida.class)
	public final void testRealizar() throws ErrorNumeroCiclosIncorrecto, ErrorReparacionYaConcluida, ErrorPrioridadIncorrecta{
		ReparacionPrioridad reparacion = new ReparacionPrioridad(10,1,2);
		try{
		reparacion.realizar(); //primer ciclo
		reparacion.realizar(); //segundo ciclo 
		} catch (ErrorReparacionYaConcluida e)
		{
			fail ("Se ha lanzado la excepción antes de tiempo");
		}
		reparacion.realizar(); //salta excepción
	} // de testRealizar
  
	
	/**
	 * Test method for {@link reparacion.Reparacion#realizar()}.
	 * Comprueba que al llamar a realizar se decrementan los ciclos
	 */
	@Test 
	public final void testRealizar1() throws ErrorNumeroCiclosIncorrecto, ErrorReparacionYaConcluida, ErrorPrioridadIncorrecta{
		int ciclos = 2;
		int prioridad = 3;
		ReparacionPrioridad reparacion = new ReparacionPrioridad(10,prioridad,ciclos);
		reparacion.realizar(); //primer ciclo
		assertTrue("Realizar no decrementa los ciclos de la reparación",
			    ciclos-1 == reparacion.getCiclosPendientes());
	} // de testRealizar1()
	
	/**
	 * Test method for {@link reparacion.Reparacion#realizar()}.
	 * Comprueba que devuelve cierto en un caso en que quedan ciclos en la reparación por realizar
 */
	@Test 
	public final void testRealizar2() throws ErrorNumeroCiclosIncorrecto, ErrorReparacionYaConcluida, ErrorPrioridadIncorrecta{
		ReparacionPrioridad reparacion = new ReparacionPrioridad(10,1,3);
		reparacion.realizar(); //primer ciclo
		assertTrue("Quedan ciclos y devuelve false",reparacion.realizar());
	} // de testRealizar2()
  
	/**
	 * Test method for {@link reparacion.Reparacion#realizar()}.
	 * Comprueba que devuelve falso en un caso en que no quedan ciclos en la reparación por realizar
 */
	@Test 
	public final void testRealizar3() throws ErrorNumeroCiclosIncorrecto, ErrorReparacionYaConcluida, ErrorPrioridadIncorrecta{
		ReparacionPrioridad reparacion = new ReparacionPrioridad(10,1,1);
		assertTrue("No quedan ciclos y devuelve true",reparacion.realizar()==false);
	} // de testRealizar3()
 	
	/**
	 * Test method for {@link reparacion.Reparacion#realizar()}.
	 * Comprueba que el coste no cambia durante la realización de los ciclos
	 */
	@Test 
	public final void testCalcularCoste1() throws ErrorNumeroCiclosIncorrecto, ErrorReparacionYaConcluida, ErrorPrioridadIncorrecta {
		int ciclos = 2;
		int prioridad = 3;
		ReparacionPrioridad reparacion = new ReparacionPrioridad(10,prioridad,ciclos);
		double coste = reparacion.calcularCoste();
		reparacion.realizar(); //primer ciclo
		assertTrue("Cambia el coste durante la realización de los ciclos",
			    coste == reparacion.calcularCoste());
	} // de testCalcularCoste1()

} // de JUnitTestReparacionPrioridad
