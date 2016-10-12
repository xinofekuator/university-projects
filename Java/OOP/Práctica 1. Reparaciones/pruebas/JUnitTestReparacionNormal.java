/**
 * Esta clase es un Caso de Prueba JUnit de la clase 
 * ReparacionNormal
 * @author susana
 */
package pruebas;

import static org.junit.Assert.*;

import org.junit.Test;

import excepciones.ErrorNumeroCiclosIncorrecto;
import excepciones.ErrorPrioridadIncorrecta;
import excepciones.ErrorReparacionYaConcluida;

import reparacion.ReparacionNormal;

public class JUnitTestReparacionNormal {

	/**
	 * Test method for {@link reparacion.ReparacionNormal#ReparacionNormal()}.
	 * Comprueba que salta la excepción ErrorNumeroCiclosIncorrecto cuando
	 * en número de ciclos de una reparación es 0
	 */
	@Test (expected = ErrorNumeroCiclosIncorrecto.class)
	public void testReparacionNormal() throws ErrorNumeroCiclosIncorrecto {
		@SuppressWarnings("unused")
		ReparacionNormal reparacion = new ReparacionNormal(10,0);
	} // de testReparacionNormal

	/**
	 * Test method for {@link reparacion.ReparacionNormal#calcularCoste()}.
	 * Comprueba que el coste se ha calculado correctamente para 4 ciclos
	 */
	@Test
	public final void testCalcularCoste() throws ErrorNumeroCiclosIncorrecto {
		int numciclos = 4;
		ReparacionNormal reparacion = new ReparacionNormal(11,numciclos);
		assertTrue("El coste calculado de " + numciclos + "ciclos no es correcto",
				    15.0*numciclos/3.0+25.0*2*numciclos/3.0 == reparacion.calcularCoste());
	} // de testCalcularCoste
	
	/**
	 * Test method for {@link reparacion.Reparacion#realizar()}.
	 * Comprueba que al llamar a realizar de una reparación concluida
	 * salta una excepción
	 */
	@Test (expected = ErrorReparacionYaConcluida.class)
	public final void testRealizar() throws ErrorNumeroCiclosIncorrecto, ErrorReparacionYaConcluida{
		ReparacionNormal reparacion = new ReparacionNormal(10,2);
		try{
		reparacion.realizar(); //primer ciclo
		reparacion.realizar(); //segundo ciclo 
		} catch (ErrorReparacionYaConcluida e)
		{
			fail ("Se ha lanzado la excepción antes de tiempo");
		}
		reparacion.realizar(); //salta la excepcion
	} // de testRealizar()
	
	/**
	 * Test method for {@link reparacion.Reparacion#realizar()}.
	 * Comprueba que al llamar a realizar se decrementan los ciclos
	 */
	@Test 
	public final void testRealizar1() throws ErrorNumeroCiclosIncorrecto, ErrorReparacionYaConcluida{
		int ciclos = 2;
		ReparacionNormal reparacion = new ReparacionNormal(10,ciclos);
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
		ReparacionNormal reparacion = new ReparacionNormal(10,3);
		reparacion.realizar(); //primer ciclo
		assertTrue("Quedan ciclos y devuelve false",reparacion.realizar());
	} // de testRealizar2()
  
		/**
	 * Test method for {@link reparacion.Reparacion#realizar()}.
	 * Comprueba que el coste no cambia durante la realización de los ciclos
	 */
	
	/**
	 * Test method for {@link reparacion.Reparacion#realizar()}.
	 * Comprueba que devuelve falso en un caso en que no quedan ciclos en la reparación por realizar
 */
	@Test 
	public final void testRealizar3() throws ErrorNumeroCiclosIncorrecto, ErrorReparacionYaConcluida, ErrorPrioridadIncorrecta{
		ReparacionNormal reparacion = new ReparacionNormal(10,1);
		assertTrue("No quedan ciclos y devuelve true",reparacion.realizar()==false);
	} // de testRealizar3()
  
		/**
	 * Test method for {@link reparacion.Reparacion#realizar()}.
	 * Comprueba que el coste no cambia durante la realización de los ciclos
	 */

	@Test 
	public final void testCalcularCoste1() throws ErrorNumeroCiclosIncorrecto, ErrorReparacionYaConcluida {
		int ciclos = 2;
		ReparacionNormal reparacion = new ReparacionNormal(10,ciclos);
		double coste = reparacion.calcularCoste();
		reparacion.realizar(); //primer ciclo
		assertTrue("Cambia el coste durante la realización de los ciclos",
			    coste == reparacion.calcularCoste());
	} // de testCalcularCoste1()

} // de JUnitRestReparacionNormal
