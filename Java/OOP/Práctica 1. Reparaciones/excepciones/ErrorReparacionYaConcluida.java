/**
 *  Clase que implementa la excepcion que se lanza cuando
 *  se solicita realizar un ciclo de una reparacion y la
 *  misma ya no tiene ciclos pendientes (esta concluida)
 */
package excepciones;

/**
 * @author agonzalez
 *
 */
@SuppressWarnings("serial")
public class ErrorReparacionYaConcluida extends Exception {

	/**
	 * 
	 */
	public ErrorReparacionYaConcluida() {}

	/**
	 * @param message
	 */
	public ErrorReparacionYaConcluida(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ErrorReparacionYaConcluida(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorReparacionYaConcluida(String message, Throwable cause) {
		super(message, cause);
	}

}