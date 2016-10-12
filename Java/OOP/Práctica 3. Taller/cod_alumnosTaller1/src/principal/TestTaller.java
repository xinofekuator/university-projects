/**
 * 
 */
package principal;

import taller.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import mecanico.TEstado;
import mecanico.excepciones.ErrorTecnicoOcioso;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import pilas.ExcepcionPilaVacia;
import pilas.Pila;
import reparacion.excepciones.ErrorNumeroCiclosIncorrecto;
import reparacion.excepciones.ErrorPrioridadIncorrecta;

import taller.excepciones.*;

import colas.Circular;
import colas.ExcepcionColaVacia;
import colas.iColas;

/**
 * @author agonzalez
 *
 */
public class TestTaller {

	private Taller taller;
	private final static int NUMERO_MECANICOS = 2;
	private final static int NUMERO_PRIORIDADES = 3;
	/**
	 * Para cada prueba creamos un taller con dos mecánicos y 3 prioridades
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		taller = new Taller (NUMERO_MECANICOS, NUMERO_PRIORIDADES);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		taller=null;
	}
	
	/**
	 * Este Método prueba el constructor cuando hay errores en los argumentos.
	 * Esta prueba verifica que se genere una excepción cuando el número de mecánicos es inadecuado
	 * @throws ErrorNumeroDePrioridades 
	 * @throws ErrorNumeroDeMecanicos 
	 */
	@Test (expected= ErrorNumeroDeMecanicos.class, timeout=100)
	public void testTaller() throws ErrorNumeroDeMecanicos, ErrorNumeroDePrioridades
	{
		new Taller (0,NUMERO_PRIORIDADES);
	}
	
	/**
	 * Este Método prueba el constructor cuando hay errores en los argumentos.
	 * Esta prueba verifica que se genere una excepción cuando el número de mecánicos es inadecuado
	 * @throws ErrorNumeroDePrioridades 
	 * @throws ErrorNumeroDeMecanicos 
	 */
	@Test (expected= ErrorNumeroDePrioridades.class, timeout=100)
	public void testTaller2() throws ErrorNumeroDeMecanicos, ErrorNumeroDePrioridades
	{
		new Taller (NUMERO_MECANICOS,-1);
	}
	
	/**
	 * Este Método verifica que el trabajo avanza correctamente cuando sólo se tienen reparaciones normales
	 * Test method for {@link taller.Taller#avanzarTrabajo()}.
	 * @throws ErrorNumeroCiclosIncorrecto 
	 */
	@Test (timeout=300)
	public void testAvanzarTrabajoReparacionesNormales() throws ErrorNumeroCiclosIncorrecto {//testAvanzarTrabajoReparacionesNormales
		this.crearReparacionesNormales();
		//Modificación metida por hacer EstadMecanico una interfaz en lugar de una clase independiente
		//Es preciso guardar el esado en un momento determinado
		class EstadoMecanico implements mecanico.iEstadoMecanico{
			private TEstado estado;
			private int idReparacionAsignada;
			private int ciclosPendientesReparacionAsignada;
			//Constructor copia
			public EstadoMecanico (mecanico.iEstadoMecanico estado){
				this.estado=estado.getEstado();
				try {
					this.idReparacionAsignada=(this.estado==TEstado.OCUPADO) ? estado.getIdReparacionAsignada() : -1;
					this.ciclosPendientesReparacionAsignada=(this.estado==TEstado.OCUPADO) ? estado.getCiclosPendientesReparacionAsignada() : -1;
				} catch (ErrorTecnicoOcioso e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public TEstado getEstado() {	
				return this.estado;
			}

			@Override
			public int getIdReparacionAsignada() throws ErrorTecnicoOcioso {
				if (this.estado!=TEstado.OCUPADO)
					throw new ErrorTecnicoOcioso();
				return this.idReparacionAsignada; 
			}

			@Override
			public int getCiclosPendientesReparacionAsignada()
					throws ErrorTecnicoOcioso {
				if (this.estado!=TEstado.OCUPADO)
					throw new ErrorTecnicoOcioso();
				return this.ciclosPendientesReparacionAsignada;
			}
			
		}
		
		mecanico.iEstadoMecanico estados[]= new mecanico.iEstadoMecanico[NUMERO_MECANICOS]; 
		try {
			//Recogemos el estado de todos los mecánicos
			for (int i=0;i<NUMERO_MECANICOS;i++)
				estados[i]=new EstadoMecanico(taller.getEstadoMecanico(i+1));
			
			
			for (int i=1;i<=NUMERO_MECANICOS*2;i++)
			{
				this.taller.avanzarTrabajo();
				for (int j=0;j<NUMERO_MECANICOS;j++){//FOR
					mecanico.iEstadoMecanico estado = taller.getEstadoMecanico(j+1);
					assertTrue ("Hay un problema con el mecánicos "+(j+1)+" en el ciclo de trabajo "+i ,
							(estados[j].getCiclosPendientesReparacionAsignada()-i <= 0 && estado.getEstado()==TEstado.LIBRE) ||
							(estados[j].getCiclosPendientesReparacionAsignada()-i==estado.getCiclosPendientesReparacionAsignada() &&
							estado.getIdReparacionAsignada()==estados[j].getIdReparacionAsignada()
							&& estado.getEstado()==estados[j].getEstado()));

				}//FOR
			}

			//Comprobamos que todos están libres
			for (int j=0;j<NUMERO_MECANICOS;j++){//FOR
				mecanico.iEstadoMecanico estado = taller.getEstadoMecanico(j+1);
				assertTrue (estado.getEstado()==TEstado.LIBRE);
			}//FOR
			
		} catch (ErrorIdentificadorMecanicoNoValido e) {
			e.printStackTrace();
			fail("La excepción ErrorIdentificadorMecanicoNoValido no tenía que haber pasado");
		} catch (ErrorTecnicoOcioso e) {
			e.printStackTrace();
			fail("La excepción ErrorTecnicoOcioso no tenía que haber pasado");
			
		}
	}//testAvanzarTrabajoReparacionesNormales
	
	
	/**
	 * Este Método verifica que el trabajo avanza correctamente cuando sólo se tienen reparaciones normales y hay más reparaciones que mecánicos
	 * Test method for {@link taller.Taller#avanzarTrabajo()}.
	 * @throws ErrorNumeroCiclosIncorrecto 
	 */
	@Test (timeout=300)
	public void testAvanzarTrabajoReparacionesNormales2() throws ErrorNumeroCiclosIncorrecto {//testAvanzarTrabajoReparacionesNormales2
		this.crearReparacionesNormales();
		//Se crean dos reparaciones nuevas. La primera deberá ser asignada al primer mecánicos que quede libre la segunda al segundo
		taller.recibirReparacionNormal(100, 5);
		taller.recibirReparacionNormal(101, 5);
		iColas<Integer> reparacionesPendientes = new Circular<Integer>();
		reparacionesPendientes.insertar(100);
		reparacionesPendientes.insertar(101);
		try {
			for (int i=1;i<=NUMERO_MECANICOS*2;i++)
			{
				//Mecánicos que van a terminar en el próximo ciclo
				iColas<Integer> mecanicosQueTerminaran = new Circular<Integer>();
				for (int j=1; j<=NUMERO_MECANICOS;j++)
				{//For
					mecanico.iEstadoMecanico estado = taller.getEstadoMecanico(j);
					if (estado.getEstado()==TEstado.OCUPADO && estado.getCiclosPendientesReparacionAsignada()==1)
						mecanicosQueTerminaran.insertar(j);
				}//For
				this.taller.avanzarTrabajo();
				
				//Comprobamos que las reparacioens pendientes se han asignado según el orden de espera
				while (!mecanicosQueTerminaran.estaVacia() && !reparacionesPendientes.estaVacia()){//W
						int idReparacion=reparacionesPendientes.sacarPrimero();
						assertTrue ("la reparacion pendiente "+idReparacion+" no ha sido correctamente asignada cuando ha sido retomada",
								idReparacion==taller.getEstadoMecanico(mecanicosQueTerminaran.sacarPrimero()).getIdReparacionAsignada());
				}//W
			}
		} catch (ErrorIdentificadorMecanicoNoValido e) {
			e.printStackTrace();
			fail("La excepción ErrorIdentificadorMecanicoNoValido no tenía que haber pasado");
		} catch (ErrorTecnicoOcioso e) {
			e.printStackTrace();
			fail("La excepción ErrorTecnicoOcioso no tenía que haber pasado");
			
		} catch (ExcepcionColaVacia e) {
			e.printStackTrace();
			fail("La excepción ExcepcionColaVacia no tenía que haber pasado");
		}
	}//testAvanzarTrabajoReparacionesNormales2

	
	/**
	 * Este Método verifica que se descuentan correctamente los ciclos en una reparación prioritaria
	 * Test method for {@link taller.Taller#avanzarTrabajo()}.
	 */
	@Test (timeout=300)
	public void testAvanzarTrabajoReparacionesPrioritarias() {//testAvanzarTrabajoReparacionesPrioritarias
		
		try {
			this.taller.recibirReparacionPrioritaria(1,1); //Tiene incialmente 3 ciclos se asigna al mecánico 1
			this.taller.recibirReparacionPrioritaria(2, 3); //Tiene incialmente un ciclo se asigna al mecánico 2
			this.taller.avanzarTrabajo();
			mecanico.iEstadoMecanico estado = taller.getEstadoMecanico(1);
			assertTrue("No se han calculado bien los ciclos para una reparación de prioridad 1",
					estado.getCiclosPendientesReparacionAsignada()==(TestTaller.NUMERO_PRIORIDADES+1-1)-1);	
			estado = taller.getEstadoMecanico(2);
			assertTrue("No se han calculado bien los ciclos para una reparación de prioridad 2",
					estado.getEstado()==TEstado.LIBRE);	
		} catch (ErrorIdentificadorMecanicoNoValido e) {
			e.printStackTrace();
			fail("La excepción ErrorIdentificadorMecanicoNoValido no tenía que haber pasado");
		} catch (ErrorTecnicoOcioso e) {
			e.printStackTrace();
			fail("La excepción ErrorTecnicoOcioso no tenía que haber pasado");
		} catch (ErrorPrioridadIncorrecta e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("La excepción ErrorPrioridadIncorrecta no tenía que haber pasado");
		}
	}//testAvanzarTrabajoReparacionesPrioritarias
	
	
	/**
	 * Este Método prueba que se encolan correctamente las reparaciones con distintas prioridades.
	 * Para ello se procede a meter distintas reparaciones con diversas prioridades y luego se procede 
	 * a avanzar el trabajo verificando que las reparaciones van siendo asignadas en el orden correcto
	 * @throws ErrorNumeroDeMecanicos
	 * @throws ErrorNumeroDePrioridades
	 * @throws ErrorNumeroCiclosIncorrecto 
	 * @throws ErrorPrioridadIncorrecta 
	 */
	@Test (timeout=300)
	public void testAvanzarTrabajo() throws  ErrorNumeroDeMecanicos, ErrorNumeroDePrioridades, ErrorNumeroCiclosIncorrecto, ErrorPrioridadIncorrecta {//testAvanzarTrabajo
		final int NUMERO_MECANICOS=2; //Forzamos a que sea dos el número de mecánicos
		final int NUMERO_PRIORIDADES = 3;
		iColas<Integer> ordenFinalizacionReparaciones = new Circular<Integer>();
		//Para esta prueba necesitamos un taller que tenga dos mecánicos
		//Vamos a ignorar la constante que indica el número de mecánicos y de prioridades, usando las locales
		taller = new Taller (NUMERO_MECANICOS, NUMERO_PRIORIDADES);
		//Hay dos mecánicos y tres prioridades. Se van a crear dos reparaciones por cada prioridad y dos más para la rep normal
		//Los IDs de las reparaciones sera prioridad*10+id. Donde id será 1 ó 2.
		for (int i =3; i>=1; i--){//for
			this.taller.recibirReparacionPrioritaria(10*i+1, i);
			ordenFinalizacionReparaciones.insertar(10*i+1);
			this.taller.recibirReparacionPrioritaria(10*i+2, i);
			ordenFinalizacionReparaciones.insertar(10*i+2);
		}//for
		
		//Ahora se añaden dos normales
		this.taller.recibirReparacionNormal(1, 5);
		ordenFinalizacionReparaciones.insertar(1);
		this.taller.recibirReparacionNormal(2, 5);
		ordenFinalizacionReparaciones.insertar(2);
		try {
			//Verificamos que actualmente están asignadas correctamente las reparaciones a los mecánicos
			for (int i= 1; i<=NUMERO_MECANICOS;i++)
			{//FOR
				int idReparacion=ordenFinalizacionReparaciones.sacarPrimero();
				assertTrue ("la reparacion  "+idReparacion+" no ha sido correctamente asignada",
						idReparacion==taller.getEstadoMecanico(i).getIdReparacionAsignada());
			}//FOR

		//Ahora avanzamos ciclos hasta uqe la cola con los ids de las reparaciones quede vacía y verificamos que el orden en el 
		//que van terminado las reparacioens es el correcto
		while (!ordenFinalizacionReparaciones.estaVacia())
		{//W
			iColas<Integer> mecanicosQueTerminaran = new Circular<Integer>();
			for (int j=1; j<=NUMERO_MECANICOS;j++)
			{//For
				mecanico.iEstadoMecanico estado;
				estado = taller.getEstadoMecanico(j);
				if (estado.getEstado()==TEstado.OCUPADO && estado.getCiclosPendientesReparacionAsignada()==1)
					mecanicosQueTerminaran.insertar(j);
			}//For
			
			this.taller.avanzarTrabajo(); //Ahora indicamos a todos los mecánicos que trabajen
			
			//Verificamos los ids de las reparaciones asignadas a los mecánicos que terminaron
			//Comprobamos que las reparacioens pendientes se han asignado según el orden de espera
			while (!mecanicosQueTerminaran.estaVacia() && !ordenFinalizacionReparaciones.estaVacia()){//W
					int idReparacion=ordenFinalizacionReparaciones.sacarPrimero();
					assertTrue ("la reparacion pendiente "+idReparacion+" no ha sido correctamente asignada",
							idReparacion==taller.getEstadoMecanico(mecanicosQueTerminaran.sacarPrimero()).getIdReparacionAsignada());
			}//W

		}//W
		} catch (ErrorIdentificadorMecanicoNoValido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("La excepción ErrorIdentificadorMecanicoNoValido no tenía que haber pasado");
		} catch (ErrorTecnicoOcioso e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("La excepción ErrorTecnicoOcioso no tenía que haber pasado");
		} catch (ExcepcionColaVacia e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("La excepción ExcepcionColaVacia no tenía que haber pasado");
		}
		
	}//testAvanzarTrabajo
	
	/**
	 * Este Método verifica que el trabajo avanza correctamente cuando tiene reparaciones normales y con prioridad.
	 * Test method for {@link taller.Taller#avanzarTrabajo()}.
	 * @throws ErrorNumeroDePrioridades 
	 * @throws ErrorNumeroDeMecanicos 
	 * @throws ErrorNumeroCiclosIncorrecto 
	 * @throws ErrorPrioridadIncorrecta 
	 */
	@Test (timeout=300)
	public void testAvanzarTrabajo2() throws  ErrorNumeroDeMecanicos, ErrorNumeroDePrioridades, ErrorNumeroCiclosIncorrecto, ErrorPrioridadIncorrecta {//testAvanzarTrabajo
		final int NUMERO_MECANICOS=2; //Forzamos a que sea dos el número de mecánicos
		final int NUMERO_PRIORIDADES = 3;
		boolean bPrimeraVez=true;
		//Mecánicos que van a estar ociosos en el próximo ciclo
		iColas<Integer> mecanicosQueTerminaran = new Circular<Integer>();
		//Para esta prueba necesitamos un taller que tenga dos mecánicos
		//Vamos a ignorar la constante que indica el número de mecánicos y de prioridades, usando las locales
		taller = new Taller (NUMERO_MECANICOS, NUMERO_PRIORIDADES);
		this.crearReparacionesNormales(NUMERO_MECANICOS); //Todos los mecánicos tienen reparaciones normales	
		//Se crean dos reparaciones nuevas. La primera deberá ser asignada al primer mecánicos que quede libre la segunda al segundo
		taller.recibirReparacionPrioritaria(100, 2);//Se asigna al mecánico 1
		taller.recibirReparacionPrioritaria(101, 1); //Se asigna al mecánico 2
		Pila<Integer> reparacionesPendientes = new Pila<Integer>();
		//Se posponen la reparación uno y luego la dos
		reparacionesPendientes.apilar (1);
		reparacionesPendientes.apilar(2);
		try {
			while (bPrimeraVez || reparacionesPendientes.cima()!=2)
			{//W
				mecanicosQueTerminaran.vaciar(); //Se vacía la cola de mecánicos ociosos
				for (int j=1; j<=NUMERO_MECANICOS;j++)
				{//For
					mecanico.iEstadoMecanico estado = taller.getEstadoMecanico(j);
					if (estado.getEstado()==TEstado.OCUPADO && estado.getCiclosPendientesReparacionAsignada()==1)
						mecanicosQueTerminaran.insertar(j);
				}//For
				
				this.taller.avanzarTrabajo();
				
				if (bPrimeraVez) //Tras el primer ciclo de trabajo llegan dos reparaciones más prioritarias que posponen la 100 y la 101
				{//IF
					bPrimeraVez=false;
					taller.recibirReparacionPrioritaria(103, 3);//Se asigna al mecánicos 2
					taller.recibirReparacionPrioritaria(102, 2); //Queda en espera
					reparacionesPendientes.apilar(101); //Primero se pospone la de menos prioridad
					reparacionesPendientes.apilar(102); //Esta será la primera que salga
				}//IF
				
				//Comprobamos que las reparaciones pendientes se han asignado según el orden de espera
				while (!mecanicosQueTerminaran.estaVacia() && !reparacionesPendientes.estaVacia()){//W
						int idReparacion=reparacionesPendientes.sacarCima();
						assertTrue ("la reparacion pendiente "+idReparacion+" no ha sido correctamente asignada cuando ha sido retomada",
								idReparacion==taller.getEstadoMecanico(mecanicosQueTerminaran.sacarPrimero()).getIdReparacionAsignada());
				}//W
			}//W
			
			//Se comprueba el estado final

			mecanico.iEstadoMecanico estado=taller.getEstadoMecanico(1);
			assertTrue("El mecánicos 1 debería estar ocupado con la reparación 102", estado.getEstado()==TEstado.OCUPADO &&
					estado.getIdReparacionAsignada()==102);
			
			estado=taller.getEstadoMecanico(2);
			assertTrue("El mecánicos 2 debería estar ocupado con la reparación 100", estado.getEstado()==TEstado.OCUPADO &&
					estado.getIdReparacionAsignada()==101);
			
			
			
		} catch (ErrorIdentificadorMecanicoNoValido e) {
			e.printStackTrace();
			fail("La excepción ErrorIdentificadorMecanicoNoValido no tenía que haber pasado");
		} catch (ErrorTecnicoOcioso e) {
			e.printStackTrace();
			fail("La excepción ErrorTecnicoOcioso no tenía que haber pasado");
			
		} catch (ExcepcionColaVacia e) {
			e.printStackTrace();
			fail("La excepción ExcepcionColaVacia no tenía que haber pasado");
		} catch (ExcepcionPilaVacia e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("La excepción ExcepcionPilaVacia no tenía que haber pasado");
		}
	}//testAvanzarTrabajo
	
	/**
	 * Esta prueba consiste en probar la recepción de una reparación normal.
	 * El resultado esperado es que se asigne al primer mecánico
	 * Test method for {@link taller.Taller#recibirReparacionNormal(int, int)}.
	 * @throws ErrorNumeroCiclosIncorrecto 
	 */
	@Test (timeout=300)
	public void testRecibirReparacionNormal() throws ErrorNumeroCiclosIncorrecto {
		taller.recibirReparacionNormal(1, 5);
		try {
			mecanico.iEstadoMecanico estado = taller.getEstadoMecanico(1);
			assertTrue ("No se ha asignado correctamente una reparación normal",estado.getEstado()==TEstado.OCUPADO && estado.getCiclosPendientesReparacionAsignada()==5 && estado.getIdReparacionAsignada()==1);
		} catch (ErrorIdentificadorMecanicoNoValido e) {
			// TODO Auto-generated catch block
			fail("La excepción ErrorIdentificadorMecanicoNoValido no tenía que haber pasado");
		} catch (ErrorTecnicoOcioso e) {
			// TODO Auto-generated catch block
			fail("La excepción ErrorTecnicoOcioso no tenía que haber pasado");;
		}

	}

	/**
	 * Esta prueba consiste en probar la recepción de reparaciones normales. Se reciben las mismas reparaciones que mecanicos.
	 * El número de ciclos cada reparación es una unidad menor que la anterior.
	 * El resultado esperado es que se asigne al primer mecánicos la reparación 1 al segundo la dos al tercero la 3.
	 * Test method for {@link taller.Taller#recibirReparacionNormal(int, int)}.
	 * @throws ErrorNumeroCiclosIncorrecto 
	 */
	@Test (timeout=300)
	public void testRecibirReparacionNormal2() throws ErrorNumeroCiclosIncorrecto {
		this.crearReparacionesNormales();

		//Ahora verificamos que cada mecánicos tenga su reparación asignada
		for (int i=0; i<NUMERO_MECANICOS;i++){//FOR 
			try {
				mecanico.iEstadoMecanico estado = taller.getEstadoMecanico(i+1);
				assertTrue ("La reparacion "+(i+1)+" no ha sido correctametne asiganada", estado.getEstado()==TEstado.OCUPADO && estado.getCiclosPendientesReparacionAsignada()==(NUMERO_MECANICOS*2-i) && estado.getIdReparacionAsignada()==i+1);
			} catch (ErrorIdentificadorMecanicoNoValido e) {
				// TODO Auto-generated catch block
				fail("La excepción ErrorIdentificadorMecanicoNoValido no tenía que haber pasado");
			} catch (ErrorTecnicoOcioso e) {
				// TODO Auto-generated catch block
				fail("La excepción ErrorTecnicoOcioso no tenía que haber pasado");;
			}
		}//FOR
	}

	/**
	 * Esta prueba consiste en probar la recepción de reparaciones normales con un número de ciclos incorrectos
	 * Test method for {@link taller.Taller#recibirReparacionNormal(int, int)}.
	 * @throws ErrorNumeroCiclosIncorrecto 
	 */
	
	@Test (expected= ErrorNumeroCiclosIncorrecto.class, timeout=300)
	public void testRecibirReparacionNormal3() throws ErrorNumeroCiclosIncorrecto {
		taller.recibirReparacionNormal(1, 0); //Número de ciclos incorrectos
	}
	
	
	
	/**
	 * Este Método prueba la recepción de reparaciones con prioridad, y que se calculan bien los ciclos que se han de asignar a cada reparacion
	 * Test method for {@link taller.Taller#recibirReparacionPrioritaria(int, int)}.
	 * @throws ErrorNumeroDePrioridades 
	 * @throws ErrorNumeroDeMecanicos 
	 * @throws ErrorNumeroCiclosIncorrecto 
	 * @throws ErrorPrioridadIncorrecta 
	 */
	@Test(timeout=300)
	public void testRecibirReparacionPrioritaria() throws ErrorNumeroDeMecanicos, ErrorNumeroDePrioridades, ErrorNumeroCiclosIncorrecto, ErrorPrioridadIncorrecta {
		//Para esta prueba necesitamos un taller que tenga dos mecánicos
		//Vamos a ignorar la constante que indica el número de mecánicos y de prioridades
		taller = new Taller (2, 3);
		taller.recibirReparacionNormal(1, 5); //Se asigna al mecánicos 1
		try{
			mecanico.iEstadoMecanico estado;
			taller.recibirReparacionPrioritaria(2,1);//Se asgina al 2
			estado = taller.getEstadoMecanico(2);
			assertTrue("El estado del mecánicos 2 no es el esperado",estado.getEstado()==TEstado.OCUPADO && estado.getIdReparacionAsignada() == 2);
			taller.recibirReparacionPrioritaria(3, 2); //Se asigna al 1
			estado = taller.getEstadoMecanico(1);
			assertTrue("El estado del mecánicos 1 no es el esperado tras recibir una reparación más prioritaria",
					estado.getEstado()==TEstado.OCUPADO && estado.getIdReparacionAsignada() == 3);
			taller.recibirReparacionPrioritaria(4, 3); //Se asigna al 2
			estado = taller.getEstadoMecanico(2);
			assertTrue("El estado del mecánicos 2 no es el esperado tras recibir una reparación más prioritaria",
					estado.getEstado()==TEstado.OCUPADO && estado.getIdReparacionAsignada() == 4);
		} catch (ErrorIdentificadorMecanicoNoValido e) {
			// TODO Auto-generated catch block
			fail("La excepción ErrorIdentificadorMecanicoNoValido no tenía que haber pasado");
		} catch (ErrorTecnicoOcioso e) {
			// TODO Auto-generated catch block
			fail("La excepción ErrorTecnicoOcioso no tenía que haber pasado");;
		}
	}


	
	/**
	 * Este Método prueba la recepción de reparaciones con prioridad, cuando la prioridad que se pasa es menor que la mínima perimitada
	 * Test method for {@link taller.Taller#recibirReparacionPrioritaria(int, int)}.
	 * @throws ErrorPrioridadIncorrecta 
	 */
	@Test (expected= ErrorPrioridadIncorrecta.class, timeout=10)
	public void testRecibirReparacionPrioritaria2() throws ErrorPrioridadIncorrecta {
		taller.recibirReparacionPrioritaria(2,0);
	}
	
	
	/**
	 * Este Método prueba el cálculo de los ciclos asociados a un reparación prioritaria
	 * Test method for {@link taller.Taller#recibirReparacionPrioritaria(int, int)}.
	 * @throws ErrorPrioridadIncorrecta 
	 * @throws ErrorNumeroDePrioridades 
	 * @throws ErrorNumeroDeMecanicos 
	 */
	@Test(timeout=100)
	public void testRecibirReparacionPrioritaria3() throws ErrorPrioridadIncorrecta, ErrorNumeroDeMecanicos, ErrorNumeroDePrioridades {
		//Para esta prueba necesitamos un taller que tenga dos mecánicos
		//Vamos a ignorar la constante que indica el número de mecánicos y de prioridades
		final int MAX_PRIORIDAD=3;
		taller = new Taller (1, MAX_PRIORIDAD);
		try{
			mecanico.iEstadoMecanico estado;
			for (int i=1;i <=MAX_PRIORIDAD;i++)
			{//for
				taller.recibirReparacionPrioritaria(i,i);//Se asgina al 1
				estado = taller.getEstadoMecanico(1);
				assertTrue("No se han calculado bien los ciclos para una reparación de prioridad "+i,
						estado.getCiclosPendientesReparacionAsignada()==MAX_PRIORIDAD+1-i);	
			}//for
		} catch (ErrorIdentificadorMecanicoNoValido e) {
			// TODO Auto-generated catch block
			fail("La excepción ErrorIdentificadorMecanicoNoValido no tenía que haber pasado");
		} catch (ErrorTecnicoOcioso e) {
			// TODO Auto-generated catch block
			fail("La excepción ErrorTecnicoOcioso no tenía que haber pasado");;
		}
	}
	
	/**
	 * Este Método prueba la recepción de reparaciones con prioridad, cuando la prioridad que se pasa es mayor que la máxima perimitada
	 * Test method for {@link taller.Taller#recibirReparacionPrioritaria(int, int)}.
	 * @throws ErrorPrioridadIncorrecta 
	 */
	@Test (expected= ErrorPrioridadIncorrecta.class, timeout=10)
	public void testRecibirReparacionPrioritaria4() throws ErrorPrioridadIncorrecta {
		taller.recibirReparacionPrioritaria(2,TestTaller.NUMERO_PRIORIDADES+1);
	}
	
	/**
	 * Esta prueba comprueba que cuando hay varias reparaciones con la misma prioraridad
	 * pospuestas anteinde a la que lleva más tiempo.
	 */
	@Test(timeout=100)
	public void testAvanzarTrabajoReparacionPrioritaria2(){
		//Se crea una reparación más que mecánicos hay
		try {
			for (int i =0; i <= TestTaller.NUMERO_MECANICOS;i++){//FOR
				taller.recibirReparacionPrioritaria(i+1, TestTaller.NUMERO_PRIORIDADES-2);
			}//FOR
			//Se desaloja la reparacion id =1 
			taller.recibirReparacionPrioritaria(TestTaller.NUMERO_MECANICOS+1, TestTaller.NUMERO_PRIORIDADES);
			//Se desaloja la reparacion id =2 
			taller.recibirReparacionPrioritaria(TestTaller.NUMERO_MECANICOS+2, TestTaller.NUMERO_PRIORIDADES-1);
			this.taller.avanzarTrabajo();
			//Ahora el mecánico 1 debe tener la reparacion id = 2 que es la última que se pospuso
			//El mecánico dos debe seguir con la reparacion id=NUMERO_MECANICOS+2
			mecanico.iEstadoMecanico estado = taller.getEstadoMecanico(1);
			assertTrue("El mecánicico 1 no ha retomado la reparación adecuada ",
					estado.getIdReparacionAsignada()==2);	
			estado = taller.getEstadoMecanico(2);
			assertTrue("El mecáncio 2 no ha mantenido la reparación adecuada",
					estado.getIdReparacionAsignada()==TestTaller.NUMERO_MECANICOS+2);
			//Ahora terima la reparación con id = TestTaller.NUMERO_MECANICOS+2 y el mecáncio 2 toma la reparación 1
			this.taller.avanzarTrabajo();
			estado = taller.getEstadoMecanico(1);
			assertTrue("El mecánicico 1 no ha seguido con la reparación adecuada ",
					estado.getIdReparacionAsignada()==2);	
			estado = taller.getEstadoMecanico(2);
			assertTrue("El mecáncio 2 no ha retomado la reparación adecuada",
					estado.getIdReparacionAsignada()==1);
		} catch (ErrorPrioridadIncorrecta e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorIdentificadorMecanicoNoValido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorTecnicoOcioso e) {
			// TODO Auto-generated catch block
			fail ("Ningún técnico debía estar ocioso");
		}
	}
/*
 * Cuando se usan colas hay que usar este test
 * 	public void testRecibirReparacionPrioritaria5(){
		//Se crea una reparación más que mecánicos hay
		try {
			for (int i =0; i <= TestTaller.NUMERO_MECANICOS;i++){//FOR
				taller.recibirReparacionPrioritaria(i+1, TestTaller.NUMERO_PRIORIDADES-2);
			}//FOR
			//Se desaloja la reparacion id =1 
			taller.recibirReparacionPrioritaria(TestTaller.NUMERO_MECANICOS+1, TestTaller.NUMERO_PRIORIDADES);
			//Se desaloja la reparacion id =2 
			taller.recibirReparacionPrioritaria(TestTaller.NUMERO_MECANICOS+2, TestTaller.NUMERO_PRIORIDADES-1);
			this.taller.avanzarTrabajo();
			//Ahora el mecánico 1 debe tener la reparacion id = 1 que es la más vieja con esa prioridad
			//El mecánico dos debe seguir con la reparacion id=NUMERO_MECANICOS+2
			mecanico.iEstadoMecanico estado = taller.getEstadoMecanico(1);
			assertTrue("El mecánicico 1 no ha retomado la reparación adecuada ",
					estado.getIdReparacionAsignada()==1);	
			estado = taller.getEstadoMecanico(2);
			assertTrue("El mecáncio 2 no ha mantenido la reparación adecuada",
					estado.getIdReparacionAsignada()==TestTaller.NUMERO_MECANICOS+2);
			//Ahora terima la reparación con id = TestTaller.NUMERO_MECANICOS+2 y el mecáncio 2 toma la reparación 2
			this.taller.avanzarTrabajo();
			estado = taller.getEstadoMecanico(1);
			assertTrue("El mecánicico 1 no ha seguido con la reparación adecuada ",
					estado.getIdReparacionAsignada()==1);	
			estado = taller.getEstadoMecanico(2);
			assertTrue("El mecáncio 2 no ha retomado la reparación adecuada",
					estado.getIdReparacionAsignada()==2);
		} catch (ErrorPrioridadIncorrecta e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorIdentificadorMecanicoNoValido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorTecnicoOcioso e) {
			// TODO Auto-generated catch block
			fail ("Ningún técnico debía estar ocioso");
		}
	}*/

	/**
	 * Este Método comprueba que getEstadoMecanico retorna el estado correcto de los mecánicos del taller
	 * Test method for {@link taller.Taller#getEstadoMecanico(int, int)}.
	 * @throws ErrorNumeroCiclosIncorrecto 
	 */
	@Test (timeout=10)
	public void testGetEstadoMecanico() throws ErrorNumeroCiclosIncorrecto{//testGetEstadoMecanico
		taller.recibirReparacionNormal(1, 5);
		//En este punto se ha probado que se recuepra el estado de un mecánico ocupado
		try {
			mecanico.iEstadoMecanico estado = taller.getEstadoMecanico(2);
			assertTrue("El Método getEstadoMecanico no retorna la información correcta cuando el mecánicos está libre y no es el primer mecánicos",
					estado.getEstado()==TEstado.LIBRE);
		} catch (ErrorIdentificadorMecanicoNoValido e) {
			// TODO Auto-generated catch block
			fail("La excepción ErrorIdentificadorMecanicoNoValido no tenía que haber pasado");
		} //Esá desocupado
	}//testGetEstadoMecanico



	/**
	 * Este Método comprueba que getEstadoMecanico genera una excepción cuando se pide el estado del mecánico 0 (no existe)
	 * Test method for {@link taller.Taller#getEstadoMecanico(int, int)}.
	 * @throws ErrorIdentificadorMecanicoNoValido 
	 */
	@Test (expected= ErrorIdentificadorMecanicoNoValido.class, timeout=10)
	public void testGetEstadoMecanico1() throws ErrorIdentificadorMecanicoNoValido{//testGetEstadoMecanico1
		@SuppressWarnings("unused")
		mecanico.iEstadoMecanico estado = taller.getEstadoMecanico(0);
	}//testGetEstadoMecanico1


	/**
	 * Este Método comprueba que getEstadoMecanico genera una excepción cuando se pide el estado del mecánico 3 (no existe)
	 * Test method for {@link taller.Taller#getEstadoMecanico(int, int)}.
	 * @throws ErrorIdentificadorMecanicoNoValido 
	 */
	@Test (expected= ErrorIdentificadorMecanicoNoValido.class, timeout=10)
	public void testGetEstadoMecanico2() throws ErrorIdentificadorMecanicoNoValido{//testGetEstadoMecanico2
		@SuppressWarnings("unused")
		mecanico.iEstadoMecanico estado = taller.getEstadoMecanico(NUMERO_MECANICOS+1);
	}//testGetEstadoMecanico2
	
	/*******************
	 * Métodos auxiliares
	 * 
	 */
	

	/**
	 * Método que crea tantas reparaciones normales como mecánicos hay.
	 * Los ciclos asignados a cada reparación normal siguien la fórmula: NUMERO_MECANICOS*2-i donde i es idMecanico-1 
	 * @throws ErrorNumeroCiclosIncorrecto 
	 * 
	 */
	private void crearReparacionesNormales () throws ErrorNumeroCiclosIncorrecto
	{//crearReparacionesNormales
		this.crearReparacionesNormales(NUMERO_MECANICOS);
	}//crearReparacionesNormales

	private void crearReparacionesNormales (int nMecanicos) throws ErrorNumeroCiclosIncorrecto
	{//crearReparacionesNormales
		for (int i = 0; i< nMecanicos;i++){
			taller.recibirReparacionNormal(i+1, nMecanicos*2-i);	
		}
		
	}//crearReparacionesNormales
	
	
	//Se añade para generar un log
	
    
    private static PrintStream registro;
    private static int fails=0;
    private static int success=0;
    @BeforeClass
    public static void setupLog()
    {
    	try {
			registro=new PrintStream (new FileOutputStream ("junitResult.txt"),true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    @AfterClass
    public static void closeLog()
    {
     registro.printf("Número de pruebas superadas %d\n número de pruebas no superadoas %d\n", success, fails);
     registro.close();
     registro=null;
    }
    
    @Rule
    public MethodRule watchman= new TestWatchman() {
            @Override
            public void failed(Throwable e, FrameworkMethod method) {
            	registro.println( method.getName() + " " + e.getClass().getSimpleName()
                        + "\n");
                fails++;    
            }

            @Override
            public void succeeded(FrameworkMethod method) {
            	registro.println( method.getName() + " " + "success!\n");
            	success++;
            }
    };
}
