
/*
 * Eduardo Cortes - t11m013
 * Ignacio Amaya  - t11m021
 * GRUPO MI
 */

import es.upm.babel.cclib.Monitor;
import es.upm.babel.cclib.Monitor.Cond;

public class GestorDeEventosMonitor implements GestorDeEventos{
	
	//Vamos a crear las variables que usaremos para controlar que se cumpla la especificacion formal que nos daban.
	
	//Matriz de enteros que relaciona los eventos con los observadores que estan subscritos a estos
	//(un 0 si no esta subscrito y un 1 si esta subscrito)
	int [][] subscritos;	
	
	//Matriz de enteros que relaciona los observadores con los eventos que tienen que escuchar
	//(un 1 si lo tiene que escuchar y un 0 si no)
	int [][] porEscuchar;
	
	//Monitor con el que trabajeremos y nos asegurara exclusion mutua 
	Monitor mutex = new Monitor();
	
	//Array de conditions que nos permitira cumplir la CPRE asegurando que el metodo escuchar solo se realice
	//cuando un observador tenga al menos un evento por escuchar. El tamano del array sera el mismo que el
	//de el numero de observadores.
	Monitor.Cond[] condiciones;
	
	//Constructor de la clase. Inicializamos la matriz de enteros a cero y asociamos la de condiciones a nuestro monitor.
	public GestorDeEventosMonitor(){
		this.subscritos = new int [N_EVENTOS][N_OBSERVADORES];
		this.porEscuchar = new int [N_EVENTOS][N_OBSERVADORES];
		this.condiciones = new Monitor.Cond [N_OBSERVADORES];
		
		for(int i = 0; i < N_EVENTOS; i++){
			for(int j = 0; j < N_OBSERVADORES; j++){
				this.subscritos[i][j] = 0;
				this.porEscuchar[i][j] = 0;
			}
		}
		for(int i = 0; i < N_OBSERVADORES; i++){
			this.condiciones[i] = mutex.newCond();
		}
	}
	
	//Busca en la matriz porEscuchar en la columna con identificador pid si existe por lo menos un uno.
	public boolean tieneUno(int pid){
		boolean terminado = false;
		for(int j = 0; !terminado && j < N_EVENTOS; j++){
			if(this.porEscuchar[j][pid] == 1){
				terminado = true;
			}
		}
		return terminado;
	}
	
	//Metodo que escribe un uno en los eventos de la matriz porEscuchar en los que hay alguien subscrito(y no esten ya
	//por escuchar).
	//Para ello buscamos previamente cuales estan subscritos en la matriz subscritos. Ademas hacemos un signal en nuestro
	//array de conditions siempre y cuando no haya ningun 1 en esa columna de porEscuchar, ya que el unico
	//caso en el que se hace el await en escuchar es cuando un observador no tiene ningun evento por escuchar.
	@Override
	public void emitir(int eid) {
		mutex.enter(); 
		for(int i = 0; i < N_OBSERVADORES; i++){
			if(this.subscritos[eid][i] == 1 && this.porEscuchar[eid][i] == 0){	
				if(!this.tieneUno(i)){
					this.condiciones[i].signal();
				}	
				this.porEscuchar[eid][i] = 1;
			}
		}
		mutex.leave();
	}

	//Actualiza la matriz de subcritos en la posicion acorde a los parametros de entrada con un 1, lo que significa que
	//la persona con identificador pid esta subscrita al evento de identificador eid
	@Override
	public void subscribir(int pid, int eid) {
		mutex.enter();
		this.subscritos[eid][pid] = 1;
		mutex.leave();
	}
	
	//Actualiza las matrices porEscuchar y subscitos con un cero en la posicion que nos indican.
	@Override
	public void desubscribir(int pid, int eid) {
		mutex.enter();
		this.subscritos[eid][pid] = 0;
		if(this.porEscuchar[eid][pid] != 0){
			this.porEscuchar[eid][pid] = 0;
		}
		mutex.leave();
	}
	
	//Si la id del observador que nos dan no tiene asociado ningun evento, su condition asociado hace un await para
	//esperar a que se cumpla la CPRE. Despues escucha uno y pone un cero en esa posicion de porEscuchar.
	@Override
	public int escuchar(int pid) {
		mutex.enter();
		if(!this.tieneUno(pid)){
			this.condiciones[pid].await();
		}
		int eid = 0;
		for(int i = 0; this.porEscuchar[i][pid] == 0; i++){
			eid = i + 1;
		}	
		this.porEscuchar[eid][pid] = 0;
		mutex.leave();
		return eid;
	}
}
