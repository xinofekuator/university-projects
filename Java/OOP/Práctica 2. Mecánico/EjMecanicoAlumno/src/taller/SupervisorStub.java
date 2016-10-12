package taller;

import mecanico.Mecanico;
import reparacion.Reparacion;

public class SupervisorStub implements SupervisorTrabajo {
	
	private boolean realizadaNotificacionReparacionFinalizada, realizadaNotificacionProgresoReparacion, realizadaNotificacionReparacionPospuesta;

	private Reparacion repPospuesta;
	
	private Reparacion repFinalizada;
	
	private Mecanico notificador;
	
	public SupervisorStub() {
		reset();
	}

	@Override
	public void notificarReparacionFinalizada(Mecanico mecanico,
			Reparacion reparacionFinalizada) {
		// TODO Auto-generated method stub
		realizadaNotificacionReparacionFinalizada = true;
		notificador = mecanico;
		repFinalizada = reparacionFinalizada;
	}

	@Override
	public void notificarProgresoReparacion(Mecanico mecanico) {
		// TODO Auto-generated method stub
		realizadaNotificacionProgresoReparacion = true;
		notificador = mecanico;
	}

	@Override
	public void notificarReparacionPospuesta(Mecanico mecanico,
			Reparacion reparacionPospuesta) {
		// TODO Auto-generated method stub
		realizadaNotificacionReparacionPospuesta = true;
		repPospuesta = reparacionPospuesta;
		notificador = mecanico;

	}

	public boolean isRealizadaNotificacionReparacionFinalizada() {
		return realizadaNotificacionReparacionFinalizada;
	}

	public boolean isRealizadaNotificacionProgresoReparacion() {
		return realizadaNotificacionProgresoReparacion;
	}

	public boolean isRealizadaNotificacionReparacionPospuesta() {
		return realizadaNotificacionReparacionPospuesta;
	}
		
	public Reparacion getRepPospuesta() {
		return repPospuesta;
	}
	
	public Reparacion getRepFinalizada() {
		return repFinalizada;
	}

	public Mecanico getNotificador() {
		return notificador;
	}

	public void reset() {
		realizadaNotificacionReparacionFinalizada = false;
		realizadaNotificacionProgresoReparacion = false;
		realizadaNotificacionReparacionPospuesta = false;
		this.repFinalizada = this.repPospuesta = null;
		this.notificador = null;
	}
	

}
