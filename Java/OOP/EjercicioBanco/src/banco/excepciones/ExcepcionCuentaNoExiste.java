package banco.excepciones;

public class ExcepcionCuentaNoExiste extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8983010961677849191L;
	
	public ExcepcionCuentaNoExiste(String message){
		super(message);
	}

}
