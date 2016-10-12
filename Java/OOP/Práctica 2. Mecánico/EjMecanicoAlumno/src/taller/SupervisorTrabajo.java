package taller;


import mecanico.*;
import reparacion.Reparacion;

/**
 * @author agonzalez
 *
 */
public interface  SupervisorTrabajo {
	 /**
	  * M�todo que trata la notificaci�n de reparaci�n terminada por el mec�nico
	  * <B>PRE:</B> La reparaci�n est� concluida
	  * @param mecanico mec�nico que ha terminado la reparaci�n
	  * @param reparacionFinalizada Reparaci�n que se acaba de concluir
	  */
	public void notificarReparacionFinalizada(Mecanico mecanico , Reparacion reparacionFinalizada);
	
	/**
	 * M�todo que recibe la notificaci�n de que el mec�nico ha trabajdo y progresado en una reparaci�n
	 * <B>PRE:</B> El m�cniaco est� realizando una reparaci�n
	 * @param mecanico Que notifica el progreso de la reparaci�n
	 */
	public void notificarProgresoReparacion(Mecanico mecanico );
	
	/**
	 * M�todo que recibe la notificaci�n  de un mec�nico que acaba de posponer una reparaci�n.
	 * <B>PRE</B>: El mec�nico deja sin terminar la reparacionPospuesta y reparacionPospuesta no est� terminada
	 * @param mecanico
	 * @param reparacionPospuesta
	 */
	public void	notificarReparacionPospuesta(Mecanico mecanico,Reparacion reparacionPospuesta);
}