package mecanico;
/**
 * 
 */

import reparacion.*;
import taller.SupervisorTrabajo;


import reparacion.excepciones.ErrorReparacionYaConcluida;
import mecanico.excepciones.ErrorSupervisorNulo;
import mecanico.excepciones.ErrorTecnicoNoDisponible;
import mecanico.excepciones.ErrorTecnicoOcioso;

/**
 * Esta clase representa al mecánico que hace las reparaciones de un taller 
 *
 */
public class Mecanico implements iEstadoMecanico {
	//Definición de atributos

	
	/**
	 * Constructor del mecánico
	 *
	 * <B>PRE</B>: supervisor != null
	 * @param idTecnico Identificador del mecánico
	 * @param supervisor supervisa el trabajo del mecánico
	 */
	public Mecanico (int idTecnico, SupervisorTrabajo supervisor) throws ErrorSupervisorNulo
	{//Constructor
	
	}//Constructor
	
	/**
	 * Método que asigna una reparación al mecánico
	 *
	 * <B>PRE</B>: puedoHacerReparacion (nueva)
	 * @param nueva Reparación que se va a asignar al mecánico. El método se 
	 * queda con la referencia de la reparación
	 * recibida como parámetro.
	 */
	public void asignarReparacion (Reparacion nueva) throws ErrorTecnicoNoDisponible
	{//asignarReparacion
	
	}//asignarReparacion
	
	/**
	 * Método que se utiliza para preguntar al mecánico si puede atender la reparación que recibe como parámetro.
	 * 
	 *  <B>PRE</B>: cierto
	 *  @param nueva Reparación que se quiere asignar al mecánico
	 * Si puede realizarla retorna cierto, en caso contrario retorna falso. 
	 * El mecánico podrá atender la reparación no nula que recibe como parámetro si:
		o	Está libre,
		o	la reparación que está realizando es normal y la que recibe es prioritaria o
		o	la reparación que está realizando tiene prioridad p y la que recibe tiene prioridad p’ y se cumple p’ > p
	 */
	public boolean puedoHacerReparacion (Reparacion nueva)
	{//puedoHacerReparacion
	
	}//puedoHacerReparacion
	
	/**
	 * Servicio al que se llama para indicarle al mecánico que realice un ciclo de trabajo
	 * 
	 * <B>PRE:</B> El mecánico debe estar en estado Ocupado 
	 * @throws ErrorReparacionYaConcluida 
	 * @throws ErrorTecnicoOcioso
	 */
	public void trabajar () throws ErrorTecnicoOcioso, ErrorReparacionYaConcluida
	{//trabajar

	
	}//trabajar

	/**
	 * @return the estado
	 */
	public TEstado getEstado() {
	
	}
	
	/**
	 * Método que retorna la prioridad de la reparación que está realizando
	 * 
	 * <B>PRE:</B> El estado debe ser OCUPADO
	 * @return Retorna 0 si la reparación es normal, en otro caso retorna la prioridad
	 * @throws ErrorTecnicoOcioso
	 */
	public int getPrioridadReparacionAsignada () throws ErrorTecnicoOcioso
	{
		
	}

	/**
	 * @return the idTecnico
	 */
	public int getIdMecanico() {
	
	}
	
	
	/**
	 * Método que retorna el identificador de la reparación sobre la que trabaja
	 * 
	 * <B>PRE:</B> El estado debe ser OCUPADO
	 * @return retorna el id de la reparación asignada	
	 * @throws ErrorTecnicoOcioso
	 */
	public int getIdReparacionAsignada () throws ErrorTecnicoOcioso
	{
		
	}
	
	/**
	 * Método que retorna los ciclos pendientes de la reparación en curso
	 * 
	 * <B>PRE:</B> El estado debe ser OCUPADO 
	 * @return retorna los ciclos pendientes
	 * @throws ErrorTecnicoOcioso
	 */
	public int getCiclosPendientesReparacionAsignada() throws ErrorTecnicoOcioso{
	
	}

	
}
