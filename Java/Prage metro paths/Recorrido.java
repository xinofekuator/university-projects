
package practica;

/*Autores: Ignacio Amaya de la Peña
		   Adrián Cámara Caunedo
		   Borja Mas García
*/

import java.util.ArrayList;

//Clase con el metodo que calcula el recorrido optimo entre dos estaciones de metro
public class Recorrido {

	static int g; //Coste real (en segundos) de llegar a la parada n

	public Recorrido(){
		this.g=0;
	}

	//Metodo que calcula el recorrido optimo entre dos estaciones de metro
	public static ArrayList<ParadaMetro> calcularRecorrido(ParadaMetro origen, ParadaMetro destino, int[][] h){
		ArrayList<ParadaMetro> abierta = new ArrayList<ParadaMetro>();
		ArrayList<ParadaMetro> cerrada = new ArrayList<ParadaMetro>();
		ParadaMetro actual=origen;
		g=0;
		int idAnterior=-1;
		abierta.add(actual);
		cerrada.add(actual);
		while(actual.getId() != destino.getId()){
			if(actual.getZona()>4){
				if(actual.getZona()==destino.getZona()){
					if(actual.getId()>destino.getId()){
						cerrada.add(actual.getAnterior());
						g=g+actual.getCosteAnterior();
						actual=actual.getAnterior();
					}
					else{
						cerrada.add(actual.getSiguiente());
						g=g+actual.getCosteSiguiente();
						actual=actual.getSiguiente();
					}
				}
				else{
					if(actual.getZona()==5 || actual.getZona()==6 || actual.getZona()==7){
						idAnterior = actual.getId();
						cerrada.add(actual.getSiguiente());
						g=g+actual.getCosteSiguiente();
						actual=actual.getSiguiente();
					}
					else{
						idAnterior = actual.getId();
						cerrada.add(actual.getAnterior());
						g=g+actual.getCosteAnterior();
						actual=actual.getAnterior();
					}
				}
			}
			else{
				int f=0;
				int aux=0;
				int gAux=g;
				ParadaMetro paradaAux=actual.getAnterior();
				for(int i = 0; i<4; i++){
					if(i==0){
						f=g+actual.getCosteAnterior()+h[actual.getAnterior().getZona()][destino.getZona()];
						if (!estanMismaLinea(idAnterior, actual.getAnterior().getId()) && actual instanceof ParadaConTrasbordo)
							f = f + ((ParadaConTrasbordo)actual).getCosteTrasbordo();
						gAux=actual.getCosteAnterior();
					}
					else if (i==1){
						aux=g+actual.getCosteSiguiente()+h[actual.getSiguiente().getZona()][destino.getZona()];
						if (!estanMismaLinea(idAnterior, actual.getSiguiente().getId()) && actual instanceof ParadaConTrasbordo)
							aux = aux + ((ParadaConTrasbordo)actual).getCosteTrasbordo();
						if(f>aux){
							f=aux;
							paradaAux=actual.getSiguiente();
							gAux=actual.getCosteSiguiente();
						}
					}
					else if (i==2){
						if(actual instanceof ParadaConTrasbordo){
							aux=g+((ParadaConTrasbordo) actual).getCosteAnterior2()+h[((ParadaConTrasbordo) actual).getAnterior2().getZona()][destino.getZona()];
							if (!estanMismaLinea(idAnterior,((ParadaConTrasbordo) actual).getAnterior2().getId() ))
								aux = aux + ((ParadaConTrasbordo)actual).getCosteTrasbordo();
							if(f>aux){
								f=aux;
								paradaAux=((ParadaConTrasbordo) actual).getAnterior2();
								gAux=((ParadaConTrasbordo) actual).getCosteAnterior2();
							}
						}
					}
					else if (i==3){
						if(actual instanceof ParadaConTrasbordo){
							aux=g+((ParadaConTrasbordo) actual).getCosteSiguiente2()+h[((ParadaConTrasbordo) actual).getSiguiente2().getZona()][destino.getZona()];
							if (!estanMismaLinea(idAnterior, ((ParadaConTrasbordo) actual).getSiguiente2().getId()))
								aux = aux + ((ParadaConTrasbordo)actual).getCosteTrasbordo();
							if(f>aux){
								f=aux;
								paradaAux=((ParadaConTrasbordo) actual).getSiguiente2();
								gAux=((ParadaConTrasbordo) actual).getCosteSiguiente2();
							}
						}
					}
				}

				idAnterior = actual.getId();
				cerrada.add(paradaAux);
				g=g+gAux;
				actual=paradaAux;
			}
		}
		return cerrada;
	}

	//Metodo para comprobar si dos paradas estan en la misma linea
	public static boolean estanMismaLinea(int org,int dest){
		boolean resultado = false;
		if((org>=0 && org<=22) && (dest>=0 && dest<=22))
			resultado = true;
		if(((org>=23 && org<=33) || org==12) && ((dest>=23 && dest<=33) || dest==12))
			resultado = true;
		if(((org>=34 && org<=48) || org==27 || org==14) && ((dest>=34 && dest<=48) || dest==27 || dest==14))
			resultado = true;
		return resultado;
	}

}