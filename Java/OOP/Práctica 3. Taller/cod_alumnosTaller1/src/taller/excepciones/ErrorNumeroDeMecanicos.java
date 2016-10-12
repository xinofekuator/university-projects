/**
 * 
 */
package taller.excepciones;

/**
 * @author agonzalez
 *
 */
@SuppressWarnings("serial")
public class ErrorNumeroDeMecanicos extends Exception {

	/**
	 * 
	 */
	public ErrorNumeroDeMecanicos() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ErrorNumeroDeMecanicos(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ErrorNumeroDeMecanicos(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorNumeroDeMecanicos(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
