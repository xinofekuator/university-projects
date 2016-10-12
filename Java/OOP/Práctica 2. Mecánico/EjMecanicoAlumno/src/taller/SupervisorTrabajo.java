package taller;


import mecanico.*;
import reparacion.Reparacion;

/**
 * @author agonzalez
 *
 */
public interface  SupervisorTrabajo {
	 /**
	  * Método que trata la notificación de reparación terminada por el mecánico
	  * <B>PRE:</B> La reparación está concluida
	  * @param mecanico mecánico que ha terminado la reparación
	  * @param reparacionFinalizada Reparación que se acaba de concluir
	  */
	public void notificarReparacionFinalizada(Mecanico mecanico , Reparacion reparacionFinalizada);
	
	/**
	 * Método que recibe la notificación de que el mecánico ha trabajdo y progresado en una reparación
	 * <B>PRE:</B> El mécniaco está realizando una reparación
	 * @param mecanico Que notifica el progreso de la reparación
	 */
	public void notificarProgresoReparacion(Mecanico mecanico );
	
	/**
	 * Método que recibe la notificación  de un mecánico que acaba de posponer una reparación.
	 * <B>PRE</B>: El mecánico deja sin terminar la reparacionPospuesta y reparacionPospuesta no está terminada
	 * @param mecanico
	 * @param reparacionPospuesta
	 */
	public void	notificarReparacionPospuesta(Mecanico mecanico,Reparacion reparacionPospuesta);
}