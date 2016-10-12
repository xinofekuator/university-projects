/**
 * 
 */
package excepciones;

/**
 * @author susana
 *
 */
@SuppressWarnings("serial")
public class ErrorPrioridadIncorrecta extends Exception {

	/**
	 * 
	 */
	public ErrorPrioridadIncorrecta() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ErrorPrioridadIncorrecta(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ErrorPrioridadIncorrecta(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorPrioridadIncorrecta(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
