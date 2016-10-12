package banco.excepciones;

public class ExcepcionBancoLleno extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8513585456883341988L;
	
	public ExcepcionBancoLleno(String message){
		super(message);
	}

}
