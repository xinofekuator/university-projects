package banco.excepciones;

public class ExcepcionCuentaYaExiste extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8983010961677849192L;
	
	public ExcepcionCuentaYaExiste(String message){
		super(message);
	}

}
