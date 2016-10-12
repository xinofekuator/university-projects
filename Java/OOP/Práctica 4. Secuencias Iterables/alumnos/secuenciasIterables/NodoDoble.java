package secuenciasIterables;

//Solo es visible dentro del paquete
//class NodoDoble<Informacion extends iCloneableComparable> {
class NodoDoble<Informacion> {
    private Informacion dato=null;
    private NodoDoble<Informacion> siguiente=null;
    private NodoDoble<Informacion> anterior=null;
    NodoDoble (Informacion dato, NodoDoble<Informacion> anterior, NodoDoble<Informacion> siguiente)
    {
     this.dato=dato;
     this.setSiguiente(siguiente);
     this.setAnterior(anterior);
    }
	public Informacion getDato() {
		return dato;
	}
	public NodoDoble<Informacion> getSiguiente() {
		return siguiente;
	}
	public NodoDoble<Informacion> getAnterior() {
		return anterior;
	}
	public void setSiguiente(NodoDoble<Informacion> siguiente) {
		this.siguiente = siguiente;
	}
	public void setAnterior(NodoDoble<Informacion> anterior) {
		this.anterior = anterior;
	}
    
}
