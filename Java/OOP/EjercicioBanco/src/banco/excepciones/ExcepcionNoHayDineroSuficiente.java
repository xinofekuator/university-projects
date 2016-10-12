package banco.excepciones;

public class ExcepcionNoHayDineroSuficiente extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8983010961677849190L;
	
	public ExcepcionNoHayDineroSuficiente(String message){
		super(message);
	}

}
