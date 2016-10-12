import static org.junit.Assert.*;
import mecanico.Mecanico;
import mecanico.TEstado;
import mecanico.excepciones.ErrorSupervisorNulo;
import mecanico.excepciones.ErrorTecnicoNoDisponible;
import mecanico.excepciones.ErrorTecnicoOcioso;

import org.junit.Before;
import org.junit.Test;



import reparacion.ReparacionNormal;
import reparacion.ReparacionPrioridad;
import reparacion.excepciones.ErrorNumeroCiclosIncorrecto;
import reparacion.excepciones.ErrorPrioridadIncorrecta;
import reparacion.excepciones.ErrorReparacionYaConcluida;
import taller.SupervisorStub;


public class JTestMecanico {

	private SupervisorStub supervisor ;
	private Mecanico tester;
	
	@Before
	public void startUp(){		
		try {
			supervisor = new SupervisorStub();
			tester = new Mecanico(1, supervisor);
		} catch (ErrorSupervisorNulo e1) {
			// TODO Auto-generated catch block
			fail("Supervisor nulo");
		}
	}
	
	@Test(expected=ErrorSupervisorNulo.class)
	public  void testConstructorSupervisorNulo() throws ErrorSupervisorNulo  {
		new Mecanico(1, null);
	}
	
	@Test
	public  void testConstructor()  {			
		// el constructor se ejecuta en el metodo before (startup)
		assertTrue("El id del mecanico no coincide", 1 == tester.getIdMecanico());
		assertTrue("El estado del mecanico no coincide", tester.getEstado() == TEstado.LIBRE);
		
		try {
			Mecanico tester1 = new Mecanico(2, supervisor);
			
			// comprobamos que no siempre se asigna el mismo id al mecanico
			
			assertTrue("El id del mecanico no coincide", 2 == tester1.getIdMecanico());
			assertTrue("El estado del mecanico no coincide", tester1.getEstado() == TEstado.LIBRE);
		} catch (ErrorSupervisorNulo e) {
			// TODO Auto-generated catch block
			fail("Supervisor nulo");
		}
	}	
	
	// Probamos los casos que generan excepciones en los gets
	
	// get prioridad para mecanico libre
	
	@Test(expected=ErrorTecnicoOcioso.class)
	public void testgetPrioridadTecnicoLibre() throws ErrorTecnicoOcioso {
		
		
		tester.getPrioridadReparacionAsignada();
	}
			
	// get ciclos pendientes para mecanico libre
	
	@Test(expected=ErrorTecnicoOcioso.class)
	public void testgetCiclosPendientesTecnicoLibre() throws ErrorTecnicoOcioso {
		
		
		tester.getCiclosPendientesReparacionAsignada();
	}
	
	// get id reparacion asignada para mecanico libre
	
	@Test(expected=ErrorTecnicoOcioso.class)
	public void testgetIdReparacionAsignadaTecnicoLibre() throws ErrorTecnicoOcioso {
		
		
		tester.getIdReparacionAsignada();
	}
	
	
	// Pruebas de PUEDO HACER REPARACION Y ASIGNAR REPARACION
	
	
	// puedo Hacer Reparacion con mecanico libre
	
	@Test
	public void testPuedoHacerReparacionConMecanicoLibre(){
		
		
		
		// el mecanico esta libre
		try {
			assertTrue("Un mecanico libre no puede hacer una reparacion", tester.puedoHacerReparacion(new ReparacionNormal(1,1)));
		} catch (ErrorNumeroCiclosIncorrecto e) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		}		
				
	}
	
	// asignar reparacion a un mecanico libre
	
	@Test
	public void testAsignarReparacionMecanicoLibre() throws ErrorTecnicoNoDisponible, ErrorTecnicoOcioso{
		
		
		// el mecanico esta libre
		try {
			tester.asignarReparacion(new ReparacionNormal(1,10));
		} catch (ErrorNumeroCiclosIncorrecto e) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		}
		assertTrue("El mecanico deberia estar ocupado", TEstado.OCUPADO == tester.getEstado());
		assertTrue("El id de la reparacion asignada no coincide", 1 == tester.getIdReparacionAsignada());
		assertTrue("Los ciclos pendientes de la reparacion no coinciden", 10 == tester.getCiclosPendientesReparacionAsignada());
		
		// no hay nada que notificar
		assertFalse("Se ha notificado incorrectamente al supervisor que se ha pospuesto una reparacion", supervisor.isRealizadaNotificacionReparacionPospuesta());
		
	}
	
	// puedo hacer reparacion prioritaria con mecanico ocupado con una reparacion normal
	
	@Test
	public void testPuedoHacerReparacionPrioritariaOcupadoConNormal(){
		
	
		ReparacionNormal repNormal = null;
		try {
			repNormal = new ReparacionNormal(1,1);
		} catch (ErrorNumeroCiclosIncorrecto e1) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		}
		try {
			// asigno reparacion normal a un mecanico libre
			tester.asignarReparacion(repNormal);
		} catch (ErrorTecnicoNoDisponible e) {
			// TODO Auto-generated catch block
			fail("No se puede asignar una reparacion normal a un mecanico libre");
		}		
		try {
			assertTrue("Un mecanico ocupado con una reparacion normal no puede hacer una reparacion prioritaria", tester.puedoHacerReparacion(new ReparacionPrioridad(1,1,1)));
		} catch (ErrorNumeroCiclosIncorrecto e) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		} catch (ErrorPrioridadIncorrecta e) {
            // TODO Auto-generated catch block
		    fail("No debería ser incorrecta la prioridad");
        }		
		
		// no hay nada que notificar
		assertFalse("Se ha notificado incorrectamente al supervisor que se ha pospuesto una reparacion", supervisor.isRealizadaNotificacionReparacionPospuesta());
		
	}
	
	// asignar reparacion con prioridad a un mecanico ocupado con una reparacion normal
	
	@Test
	public void testAsignarReparacionPrioritariaOcupadoConNormal() throws ErrorTecnicoNoDisponible, ErrorTecnicoOcioso{
		
		
		// el mecanico esta libre
		try {
			tester.asignarReparacion(new ReparacionPrioridad(11,1,10));
		} catch (ErrorNumeroCiclosIncorrecto e) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		} catch (ErrorPrioridadIncorrecta e) {
            // TODO Auto-generated catch block
            fail("No debería ser incorrecta la prioridad");
        }
		assertTrue("El mecanico no esta ocupado", TEstado.OCUPADO == tester.getEstado());
		assertTrue("El id de la reparacion asignada no coincide", 11 == tester.getIdReparacionAsignada());
		assertTrue("La prioridad de la reparacion asignada no coincide", 1 == tester.getPrioridadReparacionAsignada());
		assertTrue("Los ciclos pendientes de la reparacion asignada no coinciden", 10 == tester.getCiclosPendientesReparacionAsignada());
	}
	
	// puedo hacer reparacion prioritaria con mecanico ocupado con una reparacion menos prioritaria
	
	@Test
	public void testPuedoHacerReparacionPrioritariaOcupadoConMenosPrioritaria(){
		
		
		// el mecanico esta libre
		ReparacionPrioridad repPrioridad = null;
		try {
			repPrioridad = new ReparacionPrioridad(11,1,10);
		} catch (ErrorNumeroCiclosIncorrecto e1) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		}catch (ErrorPrioridadIncorrecta e) {
            // TODO Auto-generated catch block
            fail("No debería ser incorrecta la prioridad");
        }
		try {
			// asigno reparacion con prioridad min a un mecanico libre
			tester.asignarReparacion(repPrioridad);
		} catch (ErrorTecnicoNoDisponible e) {
			// TODO Auto-generated catch block
			fail("No se puede asignar una reparacion con prioridad a un mecanico libre");
		}		
		try {
			assertTrue("Un mecanico ocupado con una reparacion con prioridad no puede hacer una reparacion mas prioritaria", 
			        tester.puedoHacerReparacion(new ReparacionPrioridad(12,2,5)));
		} catch (ErrorNumeroCiclosIncorrecto e) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		}	catch (ErrorPrioridadIncorrecta e) {
            // TODO Auto-generated catch block
            fail("No debería ser incorrecta la prioridad");
        }
		
		// no hay nada que notificar
		assertFalse("Se ha notificado incorrectamente al supervisor que se ha pospuesto una reparacion", supervisor.isRealizadaNotificacionReparacionPospuesta());
		
	}
	
	// asignar reparacion con prioridad a un mecanico ocupado con una reparacion menos prioritaria (se pospone)
	
	@Test
	public void testAsignarReparacionPrioritariaOcupadoConMenosPrioritaria() throws ErrorTecnicoNoDisponible, ErrorTecnicoOcioso{
		
		
		// el mecanico esta libre
		ReparacionPrioridad repPrioridad = null;
		try {
			repPrioridad = new ReparacionPrioridad(11,1,10);
		} catch (ErrorNumeroCiclosIncorrecto e1) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		} catch (ErrorPrioridadIncorrecta e) {
            // TODO Auto-generated catch block
            fail("No debería ser incorrecta la prioridad");
        }
		try {
			// asigno reparacion con prioridad min a un mecanico libre
			tester.asignarReparacion(repPrioridad);
		} catch (ErrorTecnicoNoDisponible e) {
			// TODO Auto-generated catch block
			fail("No se puede asignar una reparacion con prioridad a un mecanico libre");
		}	
		// el mecanico esta ocupado con una reparacion con prioridad 1
		try {
			tester.asignarReparacion(new ReparacionPrioridad(12, 2, 5));
		} catch (ErrorNumeroCiclosIncorrecto e) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		} catch (ErrorPrioridadIncorrecta e) {
            // TODO Auto-generated catch block
            fail("No debería ser incorrecta la prioridad");
        }
		
		assertTrue("El mecanico no esta ocupado", TEstado.OCUPADO == tester.getEstado());
		assertTrue("El id de la reparacion asignada no coincide", 12 == tester.getIdReparacionAsignada());
		assertTrue("La prioridad de la reparacion asignada no coincide", 2 == tester.getPrioridadReparacionAsignada());
		assertTrue("Los ciclos pendientes de la reparacion asignada no coinciden", 5 == tester.getCiclosPendientesReparacionAsignada());
		
		// el mecanico ha pospuesto la reparacion 11
		assertTrue("No se ha notificado al supervisor que se ha pospuesto una reparacion", supervisor.isRealizadaNotificacionReparacionPospuesta());
		assertSame("No se ha notificado al supervisor el id de la reparacion pospuesta", repPrioridad, supervisor.getRepPospuesta());		
		assertSame("No se ha notificado al supervisor la referencia al mecanico", tester, supervisor.getNotificador());
	}
	
	
	// NO puedo hacer reparacion normal con mecanico ocupado con otra normal
	
	@Test
	public void testNoPuedoHacerReparacionNormalOcupadoConNormal(){
		
		
		// el mecanico esta libre
		ReparacionNormal repNormal = null;
		try {
			repNormal = new ReparacionNormal(11,10);
		} catch (ErrorNumeroCiclosIncorrecto e1) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		}
		try {
			// asigno reparacion normal a un mecanico libre
			tester.asignarReparacion(repNormal);
		} catch (ErrorTecnicoNoDisponible e) {
			// TODO Auto-generated catch block
			fail("No se puede asignar una reparacion normal a un mecanico libre");
		}		
		try {
			assertFalse("Un mecanico ocupado con una reparacion normal puede hacer otra reparacion normal", tester.puedoHacerReparacion(new ReparacionNormal(12,2)));
		} catch (ErrorNumeroCiclosIncorrecto e) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		}		
		
		
	}
	
	// NO puedo hacer reparacion normal con mecanico ocupado con una reparacion prioritaria
	
	@Test
	public void testNoPuedoHacerReparacionNormalOcupadoConPrioritaria(){
		
		
		// el mecanico esta libre
		ReparacionPrioridad repPrioridad = null;
		try {
			repPrioridad = new ReparacionPrioridad(12,1, 20);
		} catch (ErrorNumeroCiclosIncorrecto e1) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		} catch (ErrorPrioridadIncorrecta e) {
            // TODO Auto-generated catch block
            fail("No debería ser incorrecta la prioridad");
        }
		try {
			// asigno reparacion con prioridad min a un mecanico libre
			tester.asignarReparacion(repPrioridad);
		} catch (ErrorTecnicoNoDisponible e) {
			// TODO Auto-generated catch block
			fail("No se puede asignar una reparacion con prioridad a un mecanico libre");
		}		
		try {
			assertFalse("Un mecanico ocupado con una reparacion con prioridad puede hacer una reparacion normal", tester.puedoHacerReparacion(new ReparacionNormal(11,10)));
		} catch (ErrorNumeroCiclosIncorrecto e) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		}		
	}
	
	
	// NO puedo hacer reparacion prioritaria con mecanico ocupado con una reparacion mas prioritaria
	
	@Test
	public void testNoPuedoHacerReparacionPrioritariaOcupadoConMasPrioritaria(){
		
		
		// el mecanico esta libre
		ReparacionPrioridad repPrioridad = null;
		try {
			repPrioridad = new ReparacionPrioridad(11,2,10);
		} catch (ErrorNumeroCiclosIncorrecto e1) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		} catch (ErrorPrioridadIncorrecta e) {
            // TODO Auto-generated catch block
            fail("No debería ser incorrecta la prioridad");
        }
		try {
			// asigno reparacion con prioridad min a un mecanico libre
			tester.asignarReparacion(repPrioridad);
		} catch (ErrorTecnicoNoDisponible e) {
			// TODO Auto-generated catch block
			fail("No se puede asignar una reparacion con prioridad a un mecanico libre");
		}		
		try {
			assertFalse("Un mecanico ocupado con una reparacion con prioridad no puede hacer una reparacion menos prioritaria", tester.puedoHacerReparacion(
			        new ReparacionPrioridad(12,1, 20)));
			assertFalse("Un mecanico ocupado con una reparacion con prioridad no puede hacer una reparacion con igual prioridad", tester.puedoHacerReparacion(
                    new ReparacionPrioridad(12,2, 20)));
		} catch (ErrorNumeroCiclosIncorrecto e) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		}	catch (ErrorPrioridadIncorrecta e) {
            // TODO Auto-generated catch block
            fail("No debería ser incorrecta la prioridad");
        }
	}
		
	
	// probamos los casos que generan excepciones en el asignarReparacion()	
	
	// NO puedo asignar reparacion normal a mecanico ocupado con otra normal
	
	@Test(expected=ErrorTecnicoNoDisponible.class)
	public void testAsignarReparacionNormalAOcupadoConNormal() throws ErrorTecnicoNoDisponible{
		
		
		// el mecanico esta libre
		ReparacionNormal repNormal = null;
		try {
			repNormal = new ReparacionNormal(11,10);
		} catch (ErrorNumeroCiclosIncorrecto e1) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		}
		try {
			// asigno reparacion normal a un mecanico libre
			tester.asignarReparacion(repNormal);
		} catch (ErrorTecnicoNoDisponible e) {
			// TODO Auto-generated catch block
			fail("No se puede asignar una reparacion normal a un mecanico libre");
		}		
		try {
			tester.asignarReparacion(new ReparacionNormal(12,2));
		} catch (ErrorNumeroCiclosIncorrecto e) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		}
	}
	
	// NO puedo asignar reparacion normal a mecanico ocupado con una reparacion prioritaria
	
	@Test(expected=ErrorTecnicoNoDisponible.class)
	public void testAsignarReparacionNormalAOcupadoConPrioritaria() throws ErrorTecnicoNoDisponible{
		
		
		// el mecanico esta libre
		ReparacionPrioridad repPrioridad = null;
		try {
			repPrioridad = new ReparacionPrioridad(12,1, 20);
		} catch (ErrorNumeroCiclosIncorrecto e1) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		} catch (ErrorPrioridadIncorrecta e) {
            // TODO Auto-generated catch block
            fail("No debería ser incorrecta la prioridad");
        }
		try {
			// asigno reparacion con prioridad min a un mecanico libre
			tester.asignarReparacion(repPrioridad);
		} catch (ErrorTecnicoNoDisponible e) {
			// TODO Auto-generated catch block
			fail("No se puede asignar una reparacion con prioridad a un mecanico libre");
		}		
		try {
			tester.asignarReparacion(new ReparacionNormal(12,2));
		} catch (ErrorNumeroCiclosIncorrecto e) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		}
	}
	
	// NO puedo asignar reparacion prioritaria a mecanico ocupado con una reparacion mas prioritaria
	
	@Test(expected=ErrorTecnicoNoDisponible.class)
	public void testAsignarReparacionPrioritariaAOcupadoConPrioritaria() throws ErrorTecnicoNoDisponible{
		
		
		// el mecanico esta libre
		ReparacionPrioridad repPrioridad = null;
		try {
			repPrioridad = new ReparacionPrioridad(11,2,10);
		} catch (ErrorNumeroCiclosIncorrecto e1) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		} catch (ErrorPrioridadIncorrecta e) {
            // TODO Auto-generated catch block
            fail("No debería ser incorrecta la prioridad");
        }
		try {
			// asigno reparacion con prioridad min a un mecanico libre
			tester.asignarReparacion(repPrioridad);
		} catch (ErrorTecnicoNoDisponible e) {
			// TODO Auto-generated catch block
			fail("No se puede asignar una reparacion con prioridad a un mecanico libre");
		}		
		try {
			tester.asignarReparacion(new ReparacionPrioridad(12,1, 20));
		} catch (ErrorNumeroCiclosIncorrecto e) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		} catch (ErrorPrioridadIncorrecta e) {
            // TODO Auto-generated catch block
            fail("No debería ser incorrecta la prioridad");
        }		
	}
	
	// Pruebas de TRABAJAR
	
	// Un mecanico libre NO puede trabajar hasta que se le asigne una reparacion
	
	@Test(expected=ErrorTecnicoOcioso.class)
	public void testTrabajarTecnicoLibre() throws ErrorTecnicoOcioso {
		
		
		try {
			tester.trabajar();
		} catch (ErrorReparacionYaConcluida e) {
			// TODO Auto-generated catch block
			fail("El mecanico libre ha intentado trabajar en una reparacion ya concluida");
		}
	}
	
	// trabajar 2 veces y acaba
	
	@Test
	public void testCompletarReparacion(){
		
		Mecanico tester = null;
		try {
			tester = new Mecanico(1, supervisor);
		} catch (ErrorSupervisorNulo e1) {
			// TODO Auto-generated catch block
			fail("Supervisor nulo");
		}
		// el mecanico esta libre
		ReparacionPrioridad repPrioridad = null;
		try {
			repPrioridad = new ReparacionPrioridad(11,2,2);
		} catch (ErrorNumeroCiclosIncorrecto e1) {
			// TODO Auto-generated catch block
			fail("No debería ser incorrecto el no. de ciclos");
		} catch (ErrorPrioridadIncorrecta e) {
            // TODO Auto-generated catch block
            fail("No debería ser incorrecta la prioridad");
        }
		try {
			// asigno reparacion con prioridad min a un mecanico libre
			tester.asignarReparacion(repPrioridad);
		} catch (ErrorTecnicoNoDisponible e) {
			// TODO Auto-generated catch block
			fail("No se puede asignar una reparacion con prioridad a un mecanico libre");
		}
		
		try {
			tester.trabajar();
			
			// el mecanico ha realizado un ciclo, pero le queda otro		
			//assertTrue("El mecanico no esta ocupado", TEstado.OCUPADO == tester.getEstado());
			assertTrue("El id de la reparacion asignada no coincide", 11 == tester.getIdReparacionAsignada());
			assertTrue("La prioridad de la reparacion asignada no coincide", 2 == tester.getPrioridadReparacionAsignada());
			assertTrue("Los ciclos pendientes de la reparacion asignada no coinciden", 1 == tester.getCiclosPendientesReparacionAsignada());
						
			
			// comprobamos la notificacion de progreso
			assertTrue("No se ha notificado al supervisor el progreso en la reparacion", supervisor.isRealizadaNotificacionProgresoReparacion());		
			assertSame("No se ha notificado al supervisor la referencia al mecanico", tester, supervisor.getNotificador());
		
			assertFalse("Se ha notificado incorrectamente al supervisor que se ha pospuesto una reparacion", supervisor.isRealizadaNotificacionReparacionPospuesta());
			
			
		} catch (ErrorTecnicoOcioso e) {
			fail("El mecanico no debería estar libre tras asignarle una reparacion");
		} catch (ErrorReparacionYaConcluida e) {
			fail("El mecanico no debería haber completado la reparacion todavía");
		}		
		
		try {
			tester.trabajar();
			
			// el mecanico ya ha acabado
			assertTrue("El mecanico no esta libre", TEstado.LIBRE == tester.getEstado());	
			
			// comprobamos la notificacion de finalizacion
			assertTrue("No se ha notificado al supervisor el progreso en la reparacion", supervisor.isRealizadaNotificacionReparacionFinalizada());
			assertSame("No se ha notificado al supervisor la reparacion finalizada", repPrioridad, supervisor.getRepFinalizada());		
			assertSame("No se ha notificado al supervisor la referencia al mecanico", tester, supervisor.getNotificador());
					
			assertFalse("Se ha notificado incorrectamente al supervisor que se ha pospuesto una reparacion", supervisor.isRealizadaNotificacionReparacionPospuesta());
						
		} catch (ErrorTecnicoOcioso e) {
			fail("El mecanico no debería estar libre todavía");
		} catch (ErrorReparacionYaConcluida e) {
			fail("El mecanico no debería haber completado la reparacion todavía");
		}
		
	}
	
	
}
