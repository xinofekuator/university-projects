package mecanico;

public interface iEstadoMecanico {

	/**
	 * Metodo que retorna el estado del mecanico
	 * <B>PRE:</B> Cierto
	 * @return the estado
	 */
	public abstract TEstado getEstado();

	/**
	 * Metodo que retorna la reparacion asignada en el caso de que el mecanico
	 * este ocupado
	 * <B>PRE:</B> estado==OCUPADO
	 * @return the idReparacion
	 */
	public abstract int getIdReparacionAsignada()
			throws mecanico.excepciones.ErrorTecnicoOcioso;

	/** Metodo que retorna los ciclos pendientes de la reparacion asignada en el caso de que el mecanico
	 * este ocupado
	 * <B>PRE:</B> estado==OCUPADO
	 * @return the ciclosPendientes
	 */
	public abstract int getCiclosPendientesReparacionAsignada()
			throws mecanico.excepciones.ErrorTecnicoOcioso;

}