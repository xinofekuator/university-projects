package banco;
import persona.Cliente;
import banco.excepciones.ExcepcionCantidadErronea;
import banco.excepciones.ExcepcionClienteNoValido;
import banco.excepciones.ExcepcionNoHayDineroSuficiente;
import banco.excepciones.ExcepcionCuentaNoExiste;
import banco.excepciones.ExcepcionCuentaYaExiste;
import banco.excepciones.ExcepcionBancoLleno;
import banco.excepciones.ExcepcionNombreNoValido;

/**
 * @author [alumno1: apellidos; nombre; n matricula]<br> [alumno2: apellidos; nombre; n matricula]
 */

public class Banco {//Banco

	/*
	 * Nombre del banco
	 */
	private String nombre;

	/**
	 * Cuentas corrientes del banco
	 * Suponemos:
	 * Cada cliente solo puede tener una cuenta y
	 * las cuentas nunca se dan de baja
	 */
	private CuentaCorriente [] cuentas;
	private int ocupacion=0;

	/**
	 * Atributo que representa los gastos
	 * soportados por una cuenta cuando esta se abre
	 */
	private double gastosApertura=0;

	/**
	 * Constructor de un banco
	 * PRE: nombreBanco!=null && numCuentas > 0 && gastosApertura>=0
	 * @param nombreBanco Nombre del banco
	 * @param numCuentas Numero de cuentas del banco
	 * @param gastosApertura indica los gastos que se cobral al cliente por abrir una cuenta
	 * @throws ExcepcionCantidadErronea,ExcepcionNombreNoValido
	 */
	public Banco (String nombre, int numCuentas, double gastosApertura) throws  ExcepcionCantidadErronea, ExcepcionNombreNoValido{//Banco
		if (nombre==null)
			throw new ExcepcionNombreNoValido("El nombre del banco no puede ser null");
		if (numCuentas<=0)
			throw new ExcepcionCantidadErronea("El numero de cuentas"+numCuentas+"debe ser mayor que 0");
		if (gastosApertura<0)
			throw new ExcepcionCantidadErronea("El gasto de apertura"+gastosApertura+"debe ser mayor que cero");
		
		this.nombre=nombre;
		this.cuentas=new CuentaCorriente[numCuentas];
		this.gastosApertura=gastosApertura;
	}//Banco

	/**
	 * MÃ©todo que busca una cuanta corriente apartir del DNI.
	 * PRE: dni != null
	 * @param dni
	 * @return Si hay cuenta asociada al dni retorna la posiciÃ³n en la que se encuentra la cuenta, en caso contrario devuelve -1
	 * @throws ExcepcionClienteNoValido 
	 */
	
	
	private int buscarCuenta (String dni) throws ExcepcionClienteNoValido{//buscarCuenta
		if (dni==null) 
			throw new ExcepcionClienteNoValido("El cliente tiene DNI: nulo");
		int pos = -1; //Es -1 mientras no se encuentre la cuenta
		int i=0;
		while (i<ocupacion && pos==-1){
			if (cuentas[i].getCliente().getDni().equals(dni))
				pos = i; //Se ha encontrado la cuenta
			i++;
		}
		
		return pos;
	}//buscarCuenta

	/**
	 * MÃ©todo que devuelve la CC de un cliente.
	 * PRE: dni != null
	 * @param dni
	 * @return Si hay cuenta asociada al dni retorna la cuenta, en caso contrario devuelve null
	 * @throws ExcepcionClienteNoValido 
	 */
	public CuentaCorriente getCuenta(String dni) throws ExcepcionClienteNoValido{
		if (dni==null)
			throw new ExcepcionClienteNoValido("El dni del cliente no puede ser null");
		CuentaCorriente resultado;
		if(buscarCuenta(dni)==-1)
			resultado=null;
		else
			resultado=cuentas[buscarCuenta(dni)];
		
		return resultado; 
	}
	
	/**
	 * Abre una cuenta corriente en el primer hueco disponible
	 * PRE : !EstaLleno && cliente no tiene cuenta && saldoInicial > gastosApertura && cliente != null
	 * @param cliente Cliente al que pertenecera la cuenta
	 * @param saldoInicial Saldo con el que se abre la cuenta
	 * @return La cuenta corriente que se haya abierto
	 * @throws ExcepcionBancoLleno 
	 * @throws ExcepcionClienteNoValido 
	 * @throws ExcepcionCuentaYaExiste 
	 * @throws ExcepcionNoHayDineroSuficiente 
	 */
	public CuentaCorriente abrirCuentaCorriente (Cliente cliente, double saldoInicial) throws ExcepcionBancoLleno, ExcepcionClienteNoValido, ExcepcionCuentaYaExiste, ExcepcionNoHayDineroSuficiente{//abrirCuentaCorriente
		if (cliente==null)
			throw new ExcepcionClienteNoValido("El cliente dado no puede ser null");
		if (buscarCuenta(cliente.getDni())!=-1)
			throw new ExcepcionCuentaYaExiste("La cuenta dada ya existe");
		if (ocupacion>=cuentas.length)
			throw new ExcepcionBancoLleno("El banco está lleno y no caben más cuentas");
		if (saldoInicial<=gastosApertura)
			throw new ExcepcionNoHayDineroSuficiente("El dinero aportado"+saldoInicial+"debe ser mayor que"+gastosApertura);
		cuentas[ocupacion]=new CuentaCorriente(saldoInicial-gastosApertura,cliente);
		ocupacion++;
		return cuentas[ocupacion-1];
	
	}//abrirCuentaCorriente

	/**
	 * Saca el dinero a traves del DNI del cliente
	 * PRE : cliente tiene cuenta && cantidad>0 && saldo > cantidad
	 * @param dni DNI del cliente
	 * @param cantidad Dinero que quiere sacar el cliente
	 * @return cierto si el cliente dni tiene cuenta y hay suficiente saldo, falso en caso contrario
	 * @throws ExcepcionCuentaNoExiste 
	 * @throws ExcepcionCantidadErronea 
	 * @throws ExcepcionNoHayDineroSuficiente 
	 */
	public void retirarDinero (String dni, double cantidad) throws ExcepcionCuentaNoExiste, ExcepcionCantidadErronea, ExcepcionNoHayDineroSuficiente {//retirarDinero
		
		try {
			if(dni==null)
				throw new ExcepcionCuentaNoExiste("El dni no puede ser nulo");
			if(buscarCuenta(dni)==-1)
				throw new ExcepcionCuentaNoExiste("El dni "+dni+" no pertenece a ninguna cuenta");
			if (cantidad<=0)
				throw new ExcepcionCantidadErronea("La cantidad "+cantidad+" debe ser mayor que 0" );
			if (cuentas[buscarCuenta(dni)].getSaldo()<=cantidad)
				throw new ExcepcionNoHayDineroSuficiente("No hay dinero suficiente");
			cuentas[buscarCuenta(dni)].retirarDinero(cantidad);
		} catch (ExcepcionClienteNoValido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}//retirarDinero
	

	/**
	 * Ingresa dinero en la cuenta del cliente a traves del DNI del cliente
	 * PRE : cliente tiene cuenta && cantidad>0
	 * @param dni DNI del cliente
	 * @param cantidad Dinero que se quiere ingresar
	 * @throws ExcepcionCuentaNoExiste 
	 * @throws ExcepcionCantidadErronea 
	 */
	public void ingresarDinero (String dni, double cantidad) throws ExcepcionCuentaNoExiste, ExcepcionCantidadErronea   {//ingresarDinero
		try {
			
			if(dni==null)
				throw new ExcepcionCuentaNoExiste("El dni no puede ser nulo");
			if(buscarCuenta(dni)==-1)
				throw new ExcepcionCuentaNoExiste("El dni "+dni+" no pertenece a ninguna cuenta");
			if (cantidad<=0)
				throw new ExcepcionCantidadErronea("La cantidad "+cantidad+" debe ser mayor que 0" );
			CuentaCorriente cuentaUsada=cuentas[buscarCuenta(dni)];
			cuentaUsada.ingresarDinero(cantidad);
		} catch (ExcepcionClienteNoValido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}//ingresarDinero

	public String getNombre() {//getNombre
		return this.nombre;
	}//getNombre
	
	public int getNumeroCuentas(){//getNumeroCuentas
		return this.ocupacion;
	}//getNumeroCuentas

}//Banco
