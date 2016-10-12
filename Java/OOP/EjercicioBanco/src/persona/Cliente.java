package persona;


/**
 * Clase que representa a un clientes 
 *
 */
public class Cliente {//Cliente

	/**
	 * DNI del cliente
	 */
	private String dni;

	/**
	 * Nombre del cliente
	 */
	private String nombre;

	public Cliente(String dni, String nombre) {//Cliente
		this.dni = dni;
		this.nombre = nombre;
	}//Cliente
	
	public String getDni() {//getDni
		return dni;
	}//getDni
	
	public String getNombre() {//getNombre
		return nombre;
	}//getNombre

	@Override
	public String toString() {//toString
		return "(DNI: " + this.dni + ", NOMBRE: " + this.nombre + ")";
	}//toString
}//Cliente
