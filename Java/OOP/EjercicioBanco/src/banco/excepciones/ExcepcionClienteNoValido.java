package banco.excepciones;

public class ExcepcionClienteNoValido extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8983010961672849191L;
	
	public ExcepcionClienteNoValido(String message){
		super(message);
	}
}
