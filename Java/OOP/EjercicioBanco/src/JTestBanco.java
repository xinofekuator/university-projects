import static org.junit.Assert.*;

import org.junit.Test;

import persona.Cliente;

import banco.Banco;
import banco.CuentaCorriente;
import banco.excepciones.ExcepcionBancoLleno;
import banco.excepciones.ExcepcionCantidadErronea;
import banco.excepciones.ExcepcionClienteNoValido;
import banco.excepciones.ExcepcionCuentaNoExiste;
import banco.excepciones.ExcepcionCuentaYaExiste;
import banco.excepciones.ExcepcionNoHayDineroSuficiente;
import banco.excepciones.ExcepcionNombreNoValido;

public class JTestBanco {

	// ///////////// PRUEBAS DEL CONSTRUCTOR
	/**
	 * Prueba del constructor, intenta crear un banco con un número de cuentas
	 * negativo
	 * 
	 * @throws ExcepcionCantidadErronea
	 */
	@Test(expected = ExcepcionCantidadErronea.class)
	public void testConstructorNumCuentasNegativo() throws ExcepcionCantidadErronea {
		try {
			@SuppressWarnings("unused")
			Banco tester = new Banco("Banca MPU", -1, 1);
		}catch (ExcepcionNombreNoValido e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		}
	}

	/**
	 * Prueba del constructor, intenta crear un banco con cero cuentas
	 * 
	 * @throws ExcepcionCantidadErronea
	 */
	@Test(expected = ExcepcionCantidadErronea.class)
	public void testConstructorNumCuentasCero() throws ExcepcionCantidadErronea  {
		try {
			@SuppressWarnings("unused")
			Banco tester = new Banco("Banca MPU", 0, 1);
		}catch (ExcepcionNombreNoValido e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		}
	}

	/**
	 * Prueba del constructor, intenta crear un banco con comisión de apertura
	 * negativa
	 * 
	 * @throws ExcepcionCantidadErronea
	 */
	@Test(expected = ExcepcionCantidadErronea.class)
	public void testConstructorComisionNegativo()
			throws ExcepcionCantidadErronea {
		try {
			@SuppressWarnings("unused")
			Banco tester = new Banco("Banca MPU", 1, -1);
		} catch (ExcepcionNombreNoValido e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		}
	}

	/**
	 * Prueba del constructor, intenta crear un banco con comisión de apertura
	 * cero
	 * 
	 * @throws ExcepcionCantidadErronea
	 */
	@Test
	public void testConstructorComisionCero() throws ExcepcionCantidadErronea {
		try {
			@SuppressWarnings("unused")
			Banco tester = new Banco("Banca MPU", 1, 0);
		} catch (ExcepcionNombreNoValido e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		}
	}

	/**
	 * Prueba del constructor, intenta crear un banco con un nombre no permitido
	 * 
	 * @throws ExcepcionCantidadErronea
	 */
	@Test(expected = ExcepcionNombreNoValido.class)
	public void testConstructorNombreNoValido() throws ExcepcionNombreNoValido{
		try {
			@SuppressWarnings("unused")
			Banco tester = new Banco(null, 1, 1);
		} catch (ExcepcionCantidadErronea  e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		}
	}

	/**
	 * Prueba del constructor, intenta crear un banco con con nombre, numero de
	 * cuentas y comision de apertura válidos
	 * 
	 */
	@Test
	public void testConstructorOk()  {
		// @SuppressWarnings("unused")
		String nombre = "B1";
		Banco tester = null;
		try {
			tester = new Banco(nombre, 1, 1);
		} catch (ExcepcionCantidadErronea e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		} catch (ExcepcionNombreNoValido e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		}
		assertTrue("Banco mal inicializado", nombre.equals(tester.getNombre()));
		// tester.getClass().getDeclaredFields();
	}

	// ///////////////// ABRIR CUENTA
	/**
	 * Se crea un banco válido. Se intenta crear una cuenta con cliente null
	 * @throws ExcepcionClienteNoValido
	 */
	@Test(expected = ExcepcionClienteNoValido.class)
	public void testAbrirCuentaClienteNull() throws ExcepcionClienteNoValido {
		Banco bankia = null;
		try {
			bankia = new Banco("Bankia", 10, 10);
			bankia.abrirCuentaCorriente(null, 100);
		}catch (ExcepcionCantidadErronea e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		} catch (ExcepcionNombreNoValido e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		} 
		catch (ExcepcionBancoLleno e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		} catch (ExcepcionCuentaYaExiste e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		} catch (ExcepcionNoHayDineroSuficiente e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		}
	}

	/**
	 * 
	 * Se crea un banco válido. Se intenta crear una cuenta con saldo
	 * insuficiente menor que la comision
	 * @throws ExcepcionNoHayDineroSuficiente
	 */
	@Test(expected = ExcepcionNoHayDineroSuficiente.class)
	public void testAbrirCuentaSaldoInsuficiente()
			throws  ExcepcionNoHayDineroSuficiente {
		Banco bankia = null;
		try {
			bankia = new Banco("Bankia", 10, 10);
		} catch (ExcepcionCantidadErronea e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		} catch (ExcepcionNombreNoValido e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		}
		Cliente cliente1 = new Cliente("11111A", "cliente-1");
		try {
			bankia.abrirCuentaCorriente(cliente1, 1);
		} catch (ExcepcionBancoLleno e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		} catch (ExcepcionClienteNoValido e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		} catch (ExcepcionCuentaYaExiste e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		}
	}

	/**
	 * Se crea un banco válido. Se intenta crear una cuenta con saldo
	 * insuficiente igual que la comision
	 * @throws ExcepcionNoHayDineroSuficiente
	 */
	@Test(expected = ExcepcionNoHayDineroSuficiente.class)
	public void testAbrirCuentaSaldoInsuficiente2() throws ExcepcionNoHayDineroSuficiente {
		Banco bankia = null;
		int comision = 10;
		try {
			bankia = new Banco("Bankia", 10, comision);
		} catch (ExcepcionCantidadErronea e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		} catch (ExcepcionNombreNoValido e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		}
		Cliente cliente1 = new Cliente("11111A", "cliente-1");
		try {
			bankia.abrirCuentaCorriente(cliente1, comision);
		} catch (ExcepcionBancoLleno e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		} catch (ExcepcionClienteNoValido e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		} catch (ExcepcionCuentaYaExiste e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		}
	}

	/**
	 * Se crea un banco válido con n cunetas. Se crean n cuentas validas Se
	 * intenta crear una cuenta más
	 * @throws ExcepcionBancoLleno
	 */
	@Test(expected = ExcepcionBancoLleno.class)
	public void testAbrirCuentaClienteBancoLleno() throws ExcepcionBancoLleno {
		Banco bankia = null;
		bankia=this.bancoConClientes(2, 10, 100);
	
		Cliente cliente3 = new Cliente("33333C", "cliente-3");
		try {
			bankia.abrirCuentaCorriente(cliente3, 100); //Se añade una cueta más de las que caben
		} catch (ExcepcionClienteNoValido e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		} catch (ExcepcionCuentaYaExiste e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		} catch (ExcepcionNoHayDineroSuficiente e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		}
		
	}

	/**
	 * Se crea un banco válido. Se crean n cuentas validas Se intenta crear una
	 * cuenta que ya existe
	 * @throws ExcepcionCuentaYaExiste
	 */
	@Test(expected = ExcepcionCuentaYaExiste.class)
	public void testAbrirCuentaClienteYaExiste() throws ExcepcionCuentaYaExiste {
		Banco bankia = null;
		try {
			bankia = new Banco("Bankia", 10, 10);
		} catch (ExcepcionCantidadErronea e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		} catch (ExcepcionNombreNoValido e) {
			fail ("Error al crear el Banco: "+e.getMessage());
		}
		Cliente cliente1 = new Cliente("11111A", "cliente-1");
		Cliente cliente2 = new Cliente("22222B", "cliente-2");
		
		try {
			bankia.abrirCuentaCorriente(cliente1, 100);
			bankia.abrirCuentaCorriente(cliente2, 100);
			bankia.abrirCuentaCorriente(cliente2, 100);
		} catch (ExcepcionBancoLleno e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		} catch (ExcepcionClienteNoValido e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		} catch (ExcepcionNoHayDineroSuficiente e) {
			fail ("Error abrir cuenta corriente: "+e.getMessage());
		}
		
	}

	/**
	 * Se crea un banco válido. Se crean n cuentas validas Se comprueban que se
	 * hayan creado las cuentas
	 */
	@Test
	public void testAbrirCuentaCliente() {

		int comision = 10;
		int dineroIngresado = 100;

		Banco bankia = bancoConClientes(3, comision, dineroIngresado);

		CuentaCorriente cc1 = null;
		CuentaCorriente cc2 = null;
		CuentaCorriente cc3 = null;
		try {
			cc1 = bankia.getCuenta("00001-X");
			cc2 = bankia.getCuenta("00002-X");
			cc3 = bankia.getCuenta("00003-X");
		} catch (ExcepcionClienteNoValido e) {
			fail ("Error al obtener cuenta corriente: "+e.getMessage());
		}
		

		assertTrue("Saldos distintos",
				dineroIngresado + 0 - comision == cc1.getSaldo());
		assertTrue("Saldos distintos",
				dineroIngresado + 1 - comision == cc2.getSaldo());
		assertTrue("Saldos distintos",
				dineroIngresado + 2 - comision == cc3.getSaldo());
	}

	// ////////////// GET CUENTA
	/**
	 * 
	 * @throws ExcepcionClienteNoValido
	 */
	@Test(expected = ExcepcionClienteNoValido.class)
	public void testGetCuentaClienteNull() throws ExcepcionClienteNoValido {
		Banco bankia = bancoConClientes(5, 10, 100);
		bankia.getCuenta(null);
	}
	
	
	/**
	 * 
	 */
	@Test
	public void testGetCuentaNoExiste() {
		Banco bankia = bancoConClientes(5, 10, 100);
		CuentaCorriente cc = null;
		try {
			cc = bankia.getCuenta("00001X");
		} catch (ExcepcionClienteNoValido e) {
			fail ("Error al obtener cuenta corriente: "+e.getMessage());
		}
		assertTrue("Saldos distintos",
				cc == null);
	}

	
	
	/*****************  ******************/

	// ////////////// INGRESAR DINERO
	/**
	 * Se crea un banco con cuentas validas Se intenta ingresar una cantidad
	 * negativa en una cuenta
	 * @throws ExcepcionCantidadErronea
	 */
	@Test(expected = ExcepcionCantidadErronea.class)
	public void testIngresarDineroCantidadNegativa()
			throws ExcepcionCantidadErronea {
		Banco bankia = bancoConClientes(5, 10, 100);
		try {
			bankia.ingresarDinero("00001-X", -2);
		} catch (ExcepcionCuentaNoExiste e) {
			fail ("Error al ingresar dinero en cuenta corriente: "+e.getMessage());
		}
	}

	/**
	 * Se crea un banco con cuentas validas Se intenta ingresar una cantidad
	 * cero en una cuenta
	 * 
	 * @throws ExcepcionCantidadErronea
	 */
	@Test(expected = ExcepcionCantidadErronea.class)
	public void testIngresarDineroCantidadCero()
			throws ExcepcionCantidadErronea {
		Banco bankia = bancoConClientes(5, 10, 100);
		try {
			bankia.ingresarDinero("00001-X", 0);
		} catch (ExcepcionCuentaNoExiste e) {
			fail ("Error al ingresar dinero en cuenta corriente: "+e.getMessage());
		}
	}

	/**
	 * Se crea un banco con cuentas validas Se intenta ingresar una cantidad a
	 * un cliente null
	 * 
	 * @throws ExcepcionCuentaNoExiste
	 */
	@Test(expected = ExcepcionCuentaNoExiste.class)
	public void testIngresarDineroClienteNull() throws ExcepcionCuentaNoExiste {
		Banco bankia = bancoConClientes(5, 10, 100);
		try {
			bankia.ingresarDinero(null, 5);
		} catch (ExcepcionCantidadErronea e) {
			fail ("Error al ingresar dinero en cuenta corriente: "+e.getMessage());
		}
	}

	/**
	 * Se crea un banco con cuentas validas Se intenta ingresar una cantidad
	 * a un cliente sin cuenta
	 * 
	 * @throws ExcepcionCuentaNoExiste
	 */
	@Test(expected = ExcepcionCuentaNoExiste.class)
	public void testIngresarDineroClienteSinCuenta()
			throws ExcepcionCuentaNoExiste {
		Banco bankia = bancoConClientes(5, 10, 100);
		try {
			bankia.ingresarDinero("55555E", 5);
		} catch (ExcepcionCantidadErronea e) {
			fail ("Error al ingresar dinero en cuenta corriente: "+e.getMessage());
		}
	}

	/**
	 * Se crea un banco con cuentas validas Se intenta ingresar una cantidad en
	 * una cuenta de un cliente válido
	 * 
	 */
	@Test
	public void testIngresarDinero() {
		Banco bankia = bancoConClientes(5, 10, 100);
		int ingreso = 5;
		try {
			bankia.ingresarDinero("00004-X", ingreso);
		} catch (ExcepcionCuentaNoExiste e) {
			fail ("Error al ingresar dinero en cuenta corriente: "+e.getMessage());
		} catch (ExcepcionCantidadErronea e) {
			fail ("Error al ingresar dinero en cuenta corriente: "+e.getMessage());
		}
		CuentaCorriente cc1 = null;
		try {
			cc1 = bankia.getCuenta("00004-X");
		} catch (ExcepcionClienteNoValido e) {
			fail ("Error al obtener cuenta corriente: "+e.getMessage());
		}
		assertTrue("Saldos distintos", 100 + 3 - 10 + ingreso == cc1.getSaldo());
	}

	/**
	 * Se crea un banco con cuentas validas Se intenta ingresar una cantidad
	 * valida en la ultima cuenta del Banco
	 * 
	 * @throws ExcepcionCuentaNoExiste
	 * @throws ExcepcionCantidadErronea
	 */
	@Test
	public void testIngresarDineroUltimaCuenta() {
		Banco bankia = bancoConClientes(5, 10, 100);
		int ingreso = 5;
		CuentaCorriente cc1 = null;
		try {
			cc1 = bankia.getCuenta("00005-X");
		} catch (ExcepcionClienteNoValido e) {
			fail ("Error al obtener cuenta corriente: "+e.getMessage());
		}
		try {
			bankia.ingresarDinero("00005-X", ingreso);
		} catch (ExcepcionCuentaNoExiste e) {
			fail ("Error al ingresar dinero en cuenta corriente: "+e.getMessage());
		} catch (ExcepcionCantidadErronea e) {
			fail ("Error al ingresar dinero en cuenta corriente: "+e.getMessage());
		}
		assertTrue("Saldo distinto", 100 + 4 - 10 + ingreso == cc1.getSaldo());
	}

	// ////////////////// RETIRAR DINERO
	/**
	 * Se crea un banco con cuentas validas Se intenta retirar una cantidad
	 * negativa
	 * 
	 * @throws ExcepcionCantidadErronea
	 */
	@Test(expected = ExcepcionCantidadErronea.class)
	public void testRetirarDineroCantidadNegativa()
			throws  ExcepcionCantidadErronea {
		Banco bankia = bancoConClientes(5, 10, 100);
		try {
			bankia.retirarDinero("00001-X", -2);
		} catch (ExcepcionCuentaNoExiste e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		} catch (ExcepcionNoHayDineroSuficiente e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		}
	}

	/**
	 * Se crea un banco con cuentas validas Se intenta retirar una cantidad cero
	 * 
	 * @throws ExcepcionCantidadErronea
	 */
	@Test(expected = ExcepcionCantidadErronea.class)
	public void testRetirarDineroCantidadCero() throws ExcepcionCantidadErronea {
		Banco bankia = bancoConClientes(5, 10, 100);
		try {
			bankia.retirarDinero("00001-X", 0);
		} catch (ExcepcionCuentaNoExiste e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		} catch (ExcepcionNoHayDineroSuficiente e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		}
	}

	/**
	 * Se crea un banco con cuentas validas Se intenta retirar una cantidad de
	 * un cliente null
	 * 
	 * @throws ExcepcionCuentaNoExiste
	 */
	@Test(expected = ExcepcionCuentaNoExiste.class)
	public void testRetirarDineroClienteNull() throws ExcepcionCuentaNoExiste {
		Banco bankia = bancoConClientes(5, 10, 100);
		try {
			bankia.retirarDinero(null, 2);
		} catch (ExcepcionCantidadErronea e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		} catch (ExcepcionNoHayDineroSuficiente e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		}
	}

	/**
	 * Se crea un banco con cuentas validas Se intenta retirar una cantidad de
	 * un cliente sin cuenta
	 * 
	 * @throws ExcepcionCuentaNoExiste
	 */
	@Test(expected = ExcepcionCuentaNoExiste.class)
	public void testRetirarDineroClienteSinCuenta()
			throws ExcepcionCuentaNoExiste {
		Banco bankia = bancoConClientes(5, 10, 100);
		try {
			bankia.retirarDinero("11111-A", 2);
		} catch (ExcepcionCantidadErronea e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		} catch (ExcepcionNoHayDineroSuficiente e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		}
	}

	/**
	 * Se crea un banco con cuentas validas Se intenta retirar una cantidad
	 * mayor a de la que posee la cuenta
	 * 
	 * @throws ExcepcionNoHayDineroSuficiente
	 */
	@Test(expected = ExcepcionNoHayDineroSuficiente.class)
	public void testRetirarDineroNoHayTanto() throws ExcepcionNoHayDineroSuficiente {
		Banco bankia = bancoConClientes(5, 10, 100);
		CuentaCorriente cc1 = null;
		try {
			cc1 = bankia.getCuenta("00001-X");
			assertTrue("No se a recuperado la cuneta correcta", cc1.getCliente().getDni().equals("00001-X"));
		} catch (ExcepcionClienteNoValido e1) {
			fail ("Error al obtener cuenta corriente: "+e1.getMessage());
		}
		try {
			bankia.retirarDinero("00001-X", cc1.getSaldo() + 1);
		} catch (ExcepcionCuentaNoExiste e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		} catch (ExcepcionCantidadErronea e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		}
	}

	/**
	 * Se crea un banco con cuentas validas Se intenta retirar una cantidad de
	 * un cliente valido
	 * 
	 */
	@Test
	public void testRetirarDinero()  {
		Banco bankia = bancoConClientes(5, 10, 100);
		CuentaCorriente cc1 = null;
		try {
			cc1 = bankia.getCuenta("00001-X");
		} catch (ExcepcionClienteNoValido e1) {
			fail ("Error al obtener cuenta corriente: "+e1.getMessage());
		}
		try {
			bankia.retirarDinero("00001-X", cc1.getSaldo() - 1);
		} catch (ExcepcionCuentaNoExiste e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		} catch (ExcepcionCantidadErronea e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		} catch (ExcepcionNoHayDineroSuficiente e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		}
		assertTrue("Saldo distinto", 1 == cc1.getSaldo());
	}

	/**
	 * Se crea un banco con cuentas validas Se intenta retirar una cantidad de
	 * un cliente valido (de la ultima cuenta del Banco)
	 * 
	 */
	@Test
	public void testRetirarDineroUltimaCuenta() {
		Banco bankia = bancoConClientes(5, 10, 100);
		CuentaCorriente cc1 = null;
		try {
			cc1 = bankia.getCuenta("00005-X");
		} catch (ExcepcionClienteNoValido e1) {
			fail ("Error al obtener cuenta corriente: "+e1.getMessage());
		}
		try {
			bankia.retirarDinero("00005-X", cc1.getSaldo() - 1);
		} catch (ExcepcionCuentaNoExiste e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		} catch (ExcepcionCantidadErronea e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		} catch (ExcepcionNoHayDineroSuficiente e) {
			fail ("Error al retirar dinero en cuenta corriente: "+e.getMessage());
		}
		assertTrue("Saldo distinto", 1 == cc1.getSaldo());
	}
	
	/**
	 * Se comprueba que no se usa un atributo de clase para el numero
	 * actual de cuentas abiertas
	 */
	@Test
	public void pruebaContador() {
		int nClientes = 10;
		int comision = 10;
		int dineroIngresado = 100;
		Banco bankia = null;
		try {
			bankia = new Banco("Bankia", nClientes, comision);
			assertTrue("Nombre del banco erroneo",
					"Bankia".equals(bankia.getNombre()));
		} catch (ExcepcionCantidadErronea e) {
			fail("No se creó el Banco: " + e.getMessage());
		} catch (ExcepcionNombreNoValido e) {
			fail("No se creó el Banco: " + e.getMessage());
		}
		Banco bancaCivica = null;
		try {
			bancaCivica = new Banco("Banca Civica", nClientes, comision);
			assertTrue("Nombre del banco erroneo",
					"Banca Civica".equals(bancaCivica.getNombre()));
		} catch (ExcepcionCantidadErronea e) {
			fail("No se creó el Banco: " + e.getMessage());
		} catch (ExcepcionNombreNoValido e) {
			fail("No se creó el Banco: " + e.getMessage());
		}
		Cliente[] clientes = new Cliente[nClientes];
		for (int i = 0; i < nClientes; i++) {
			clientes[i] = new Cliente("0000" + (i + 1) + "-X", "cliente-"
					+ (i + 1));
		}
		
		int i =0;
		CuentaCorriente[] cuentas = new CuentaCorriente[nClientes];
		try {
			for (i = 0; i < nClientes; i++) {
				cuentas[i] = bankia.abrirCuentaCorriente(clientes[i],
						dineroIngresado + i);

				assertTrue("Numero de cuentas distinto",
						i + 1 == bankia.getNumeroCuentas());
			}
		} catch (Exception e) {
			fail("Error al abrir cuenta corriente (" + i + ") :" + e.getMessage());
		}
		
		CuentaCorriente[] cuentas2 = new CuentaCorriente[nClientes];
		try {
			for (i = 0; i < nClientes; i++) {
				cuentas2[i] = bancaCivica.abrirCuentaCorriente(clientes[i],
						dineroIngresado + i);

				assertTrue("Numero de cuentas distinto",
						i + 1 == bancaCivica.getNumeroCuentas());
			}
		} catch (Exception e) {
			fail("Error al abrir cuenta corriente (" + i + ") :" + e.getMessage());
		}
	}

	// /////////// AUX ////////////////
	/**
	 * Función auxiliar que genera un Banco con parametros validos y cuentas
	 * validas
	 */
	private Banco bancoConClientes(int nClientes, double comision,
			double dineroIngresado) {
		if (comision <= 0 || nClientes <= 0 || dineroIngresado <= 0)
			return null;

		Banco bankia = null;
		try {
			bankia = new Banco("Bankia", nClientes, comision);
		} catch (ExcepcionCantidadErronea e) {
			fail("No se creó el Banco: " + e.getMessage());
		} catch (ExcepcionNombreNoValido e) {
			fail("No se creó el Banco: " + e.getMessage());
		}

		Cliente[] clientes = new Cliente[nClientes];
		for (int i = 0; i < nClientes; i++) {
			clientes[i] = new Cliente("0000" + (i + 1) + "-X", "cliente-"
					+ (i + 1));
		}

		int i =0;
		CuentaCorriente[] cuentas = new CuentaCorriente[nClientes];
		try {
			for (i = 0; i < nClientes; i++) {
				cuentas[i] = bankia.abrirCuentaCorriente(clientes[i],
						dineroIngresado + i);
			}
		} catch (Exception e) {
			fail("Error al abrir cuenta corriente (" + i + ") :" + e.getMessage());
		}

		for (i = 0; i < nClientes; i++)
			assertTrue("Saldo distinto",
					dineroIngresado + i - comision == cuentas[i].getSaldo());

		return bankia;
	}
	

}
