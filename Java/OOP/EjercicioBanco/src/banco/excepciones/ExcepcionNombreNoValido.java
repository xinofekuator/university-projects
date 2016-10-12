package banco.excepciones;

public class ExcepcionNombreNoValido extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8983010961677842190L;
	
	public ExcepcionNombreNoValido(String message){
		super(message);
	}
}
