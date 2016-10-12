
package practica;

/*Autores: Ignacio Amaya de la Peña
		   Adrián Cámara Caunedo
		   Borja Mas García
*/


//CLase para la creacion de paradas con trasbordo (hereda de ParadaMetro)
public class ParadaConTrasbordo extends ParadaMetro {

	private int costeTrasbordo; //Coste de realizar trasbordo(en tiempo)
	private ParadaMetro siguiente2; //Parada siguiente adicional (al ser parada con trasbordo tiene mas de 2 paradas adyacentes)
	private ParadaMetro anterior2; //Parada anterior adicional (al ser parada con trasbordo tiene mas de 2 paradas adyacentes)
	private int costeSiguiente2; //Coste de la parada siguiente adicional (en tiempo)
	private int costeAnterior2; //Coste de la parada anterior adicional (en tiempo)

	public ParadaConTrasbordo(int id, String nombre, ParadaMetro siguiente, ParadaMetro anterior, int costeSiguiente, int costeAnterior, int zona, int costeTrasbordo, ParadaMetro siguiente2, ParadaMetro anterior2, int costeSiguiente2, int costeAnterior2) {
		super(id, nombre, siguiente, anterior, costeSiguiente, costeAnterior, zona);
		this.costeTrasbordo=costeTrasbordo;
		this.siguiente2=siguiente2;
		this.anterior2=anterior2;
		this.costeSiguiente2=costeSiguiente2;
		this.costeAnterior2=costeAnterior2;
	}

	//Metodos para coger los atributos de la parada con trasbordo
	public int getCosteTrasbordo(){
		return this.costeTrasbordo;
	}

	public ParadaMetro getSiguiente2(){
		return this.siguiente2;
	}

	public ParadaMetro getAnterior2(){
		return this.anterior2;
	}

	public int getCosteSiguiente2(){
		return this.costeSiguiente2;
	}

	public int getCosteAnterior2(){
		return this.costeAnterior2;
	}

	//Metodos para establecer las paradas adyacentes adicionales
	public void setSiguiente2(ParadaMetro p){
		this.siguiente2=p;
	}

	public void setAnterior2(ParadaMetro p){
		this.anterior2=p;
	}
}

