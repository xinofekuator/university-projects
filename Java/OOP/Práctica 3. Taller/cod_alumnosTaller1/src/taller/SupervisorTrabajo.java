package taller;


import mecanico.*;
import reparacion.Reparacion;

/**
 * @author agonzalez
 *
 */
public interface  SupervisorTrabajo {
	 /**
	  * Método que trata la notificación de reparación terminada por el mecánico</br>
	  * <B>PRE:</B> La reparación está concluida</br>
	  * @param mecanico mecánico que ha terminado la reparación
	  * @param reparacionFinalizada Reparación que se acaba de concluir
	  */
	public void notificarReparacionFinalizada(Mecanico mecanico , Reparacion reparacionFinalizada);
	
//	/**
//	 * Método que recibe la notificación de que el mecánico ha trabajado y progresado en una reparación
//	 * <B>PRE:</B> El mecániaco está realizando una reparación
//	 * @param mecanico Que notifica el progreso de la reparación
//	 */
//	public void notificarProgresoReparacion(Mecanico mecanico );
	
	/**
	 * Método que recibe la notificación  de un mecánico que acaba de posponer una reparación.</br>
	 * <B>PRE</B>: El mecánico deja sin terminar la reparacionPospuesta y reparacionPospuesta no está terminada</br>
	 * @param mecanico mecámico que pospone la reparación
	 * @param reparacionPospuesta reparación que se pospone
	 */
	public void	notificarReparacionPospuesta(Mecanico mecanico,Reparacion reparacionPospuesta);
}