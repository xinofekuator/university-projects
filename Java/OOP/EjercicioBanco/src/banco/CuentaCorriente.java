package banco;
import persona.Cliente;
import banco.excepciones.ExcepcionNoHayDineroSuficiente;

public class CuentaCorriente {//CuentaCorriente

	/**
	 * Saldo de la cuenta
	 */
	private double saldo;

	/**
	 * Cliente al que pertenece la cuenta
	 */
	private Cliente cliente;

	public CuentaCorriente(double saldo, Cliente cliente) {//CuentaCorriente		
		this.saldo = saldo;
		this.cliente = cliente;		
	}//CuentaCorriente

	/**
	 * Retira dinero de la cuenta
	 * @param valor Cantidad de dinero a retirar
	 */
	public void retirarDinero (double valor) throws ExcepcionNoHayDineroSuficiente{//retirarDinero
		if (this.saldo < valor) {	//Saldo insuficente		
			throw new ExcepcionNoHayDineroSuficiente("Sólo dispone de " + this.saldo + "€. No puede retirar "+valor+"€.");
		}
		this.saldo =this.saldo - valor;
	}//retirarDinero

	/**
	 * Ingresa dinero en la cuenta
	 * @param valor Cantidad de dinero a ingresar
	 */
	public void ingresarDinero (double valor) {//ingresarDinero
		this.saldo = this.saldo + valor;
	}//ingresarDinero

	public double getSaldo() {//getSaldo
		return saldo;
	}//getSaldo


	public Cliente getCliente() {//getCliente
		return cliente;
	}//getCliente

	/**
	 * Crea una nueva tarjeta para la cuenta
	 * @return
	 */


	@Override
	public String toString() {//toString
		return "Saldo: " + this.saldo +
		" Cliente: [" + this.cliente +"]";
	}//toString
}//CuentaCorriente
