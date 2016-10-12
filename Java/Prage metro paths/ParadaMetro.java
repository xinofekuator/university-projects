
package practica;

/*Autores: Ignacio Amaya de la Peña
		   Adrián Cámara Caunedo
		   Borja Mas García
*/

//Clase para la creación de paradas de metro
public class ParadaMetro {

	private int id; //Indicador de parada
	private String nombre; //Nombre de parada
	private ParadaMetro siguiente; //Parada siguiente
	private ParadaMetro anterior; //Parada anterior
	private int costeSiguiente; //Coste de ir a la parada siguiente (en tiempo)
	private int costeAnterior; //Coste de ir a la parada anterior (en tiempo)
	private int zona; //Zona en la que se encuentra la parada

	public ParadaMetro(int id, String nombre, ParadaMetro siguiente, ParadaMetro anterior, int costeSiguiente, int costeAnterior, int zona){
		this.id=id;
		this.nombre=nombre;
		this.siguiente=siguiente;
		this.anterior=anterior;
		this.costeAnterior=costeAnterior;
		this.costeSiguiente=costeSiguiente;
		this.zona=zona;
	}

	//Metodos para coger los atributos de la parada
	public int getId(){
		return this.id;
	}

	public String getNombre(){
		return this.nombre;
	}

	public ParadaMetro getSiguiente(){
		return this.siguiente;
	}

	public ParadaMetro getAnterior(){
		return this.anterior;
	}

	public int getCosteSiguiente(){
		return this.costeSiguiente;
	}

	public int getCosteAnterior(){
		return this.costeAnterior;
	}

	public int getZona(){
		return this.zona;
	}

	//Metodos para conectar las paradas entre si
	public void setSiguiente(ParadaMetro p){
		this.siguiente=p;
	}

	public void setAnterior(ParadaMetro p){
		this.anterior=p;
	}


}
