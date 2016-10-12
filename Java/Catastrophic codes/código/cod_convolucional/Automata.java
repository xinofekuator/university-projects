package cod_convolucional;

import java.util.ArrayList;

public class Automata {
	private Nodo [] automata;
	private  int n;
	private int[] corregido;
	private String viterbiText;
	
	public Automata(Nodo [] nodos,int N){
		this.automata = nodos;
		this.n = N;
		this.viterbiText="";
	}
	public int[] getCorregido(){
		return this.corregido;
	}
	public String getViterbiText(){
		return this.viterbiText;
	}
	public void imprime(int[] entrada, int[] salida){
		System.out.print("\n\nEntrada ---> ");
		for(int i = 0; i < entrada.length ;i++){
			System.out.print(entrada[i]);			
		}
		System.out.print("\nSalida  ---> ");
		for(int i = 0; i < salida.length ;i++){
			System.out.print(salida[i]);			
		}
		
	}
	
	public void imprimeViterbi(int [] entrada, ArrayList<Integer> [][] matriz){
		int filas = matriz.length;
		int [] corregida= new int[entrada.length];
		int []salida=new int[filas-1];
		int actual=0,anterior=0,siguiente=0;
		int [] sal0,sal1;
		int [] distancias = new int[filas];
		int [] estados = new int[filas];
		int h=0;
		System.out.print("\n\n :::::::::::::::  Descodificar Viterbi:::::::::  \n\n Entrada   ---> ");
		for(int i = 0; i < entrada.length ;i++){
			System.out.print(entrada[i]);			
		}
		estados[0]=0;
		for(int i=filas-1;i>0;i--){			
			anterior = matriz[i][actual].get(0);
			distancias[i]= matriz[i][actual].get(1);
			estados[i] = actual;
			actual = anterior;
		}
		
		for(int i=0;i<estados.length-1;i++){			
			actual = estados[i];
			siguiente = estados[i+1];

			sal0 = automata[actual].Codificador_Salida(0);
			sal1 = automata[actual].Codificador_Salida(1);
			if(sal0[0] == siguiente){
				for(int j = 2;j<sal0.length;j++){
					corregida[h] = sal0[j];
					h++;
				}
				salida[i] = 0;
			}
			else {
				for(int j = 2;j<sal1.length;j++){
					corregida[h] = sal1[j];
					h++;
				}
				salida[i] = 1;
			}
		}
		viterbiText=viterbiText+"\nEntrada corregida ----> ";
		System.out.print("\nCorregida ----> ");
		for(int i = 0; i < corregida.length ;i++){
			System.out.print(corregida[i]);
			viterbiText=viterbiText+corregida[i];
		}
		viterbiText=viterbiText+ "\nErrores = "+distancias[filas-1];
		System.out.print(",   Errores = "+distancias[filas-1]);
		viterbiText=viterbiText+"\nSalida    ----> ";
		System.out.print("\nSalida    ----> ");
		for(int i = 0; i < salida.length ;i++){
			System.out.print(salida[i]);
			viterbiText=viterbiText+salida[i];
		}
		this.corregido=salida;
		viterbiText=viterbiText+"\n Secuencia :: S"+estados[0];
		System.out.print("\n Secuencia :: S"+estados[0]);
		for(int i = 1; i < estados.length ;i++){
			System.out.print("---> S"+estados[i]);
			viterbiText=viterbiText+"---> S"+estados[i];
		}
		
		System.out.print("\n\nMatriz de distancias y estados \n"
							+ "Las filas son los instantes y las columnas los estados\n"
							+ "Los valores son el camino y la distancia\n\n");
		viterbiText=viterbiText+"\n\nMatriz de distancias y estados \n"
				+ "Las filas son los instantes y las columnas los estados\n"
				+ "Los valores son el camino y la distancia\n\n";
		System.out.print("(S"+0+","+0+")   ");
		viterbiText=viterbiText+"(S"+0+","+0+")   ";
		for (int i = 0;i<filas;i++){
			for (int j = 0;j < automata.length;j++){
				if(matriz[i][j].get(0)!=-1){
					System.out.print("(S"+matriz[i][j].get(0)+","+matriz[i][j].get(1)+")   ");
					viterbiText=viterbiText+"(S"+matriz[i][j].get(0)+","+matriz[i][j].get(1)+")   ";
				}
				else{
					System.out.print("         ");
					viterbiText=viterbiText+"         ";
				}
			}	
			System.out.print("\n");
			viterbiText=viterbiText+"\n";
		}
		
	}
	
	public int[] Codificar(int []entrada){
		int [] respuesta;
		int []salida = new int[n*(entrada.length)];
		int j = 0;
		int l = 0;
		for(int i = 0; i < entrada.length ;i++){
			respuesta = automata[j].Codificador_Salida(entrada[i]);
			j = respuesta[0];
			for(int h = 2; h<respuesta.length ;h++){
				salida[l] = respuesta[h]; 
				l++;
			}
		}
		return salida;
	}
	
	public int[] Descodificar(int []entrada){
		int [] respuesta;
		int []salida = new int[(entrada.length/n)];
		int j = 0;
		int l = 0;
		int h=0;
		for(int i = 0; i < entrada.length ;i = i+n){
			respuesta = automata[j].Descodificador_Salida(entrada,h);
			j = respuesta[0];
			salida[l] = respuesta[1]; 
			h = h+n;
			l++;
		}
		return salida;
	}
	
	public int Distancia(int [] cadena,int [] entrada,int indice){
		int d=0;
		int j=2;
		for(int i=indice; i<indice+n ;i++){
			if( cadena[j] != entrada[i])
				d = d+1;
			j++;
		}
		return d;
	}
	
	public ArrayList<Integer>[][] Viterbi(int [] entrada){
		
		int m = (entrada.length/n)+1;
		int h=0;

		ArrayList<Integer> [][] trellis =  new ArrayList[(entrada.length/n+1)][automata.length];
		for (int i = 0;i<=entrada.length/n;i++){
			for (int j = 0;j < automata.length;j++){
				trellis[i][j]= new ArrayList<Integer>();
			}			
		}
		for (int i = 0;i<=entrada.length/n;i++){
			for (int j = 0;j < automata.length;j++){
				trellis[i][j].add(0, -1);trellis[i][j].add(1, entrada.length+1);
			}	
		}
		
		//Instante 0
		int d00 = 0;
		int d01 = 0;
		int [] sal0 = new int[5];
		int [] sal1 = new int[5];
		sal0 = automata[0].Codificador_Salida(0);
		sal1 = automata[0].Codificador_Salida(1);
		
		d00 = Distancia(sal0,entrada,h);
		d01 = Distancia(sal1,entrada,h);
		
/*		System.out.print("\n sal0 = "+sal0[0]+" "+sal0[1]+" "+sal0[2]+" "+sal0[3]+" "+sal0[4]);
		System.out.print("\n sal0 = "+sal0[0]+" d00 = "+d00+" sal1 = "+sal1[0]+"  d01 = "+d01);
		System.out.print("\n entrada = "+entrada3[0]+entrada3[1]+entrada3[2]);*/
		
		trellis[1][sal0[0]].set(0,0);
		trellis[1][sal0[0]].set(1, d00);
		
		trellis[1][sal1[0]].set(0,0);
		trellis[1][sal1[0]].set(1, d01);
		
		h = h+n;
		
		//Instante 1
		int [] sal20 = new int[5];
		int [] sal21 = new int[5];
		int actual,siguiente1,siguiente2;
		for (int i = 0;i<2;i++){
			if (i==0){
				actual = sal0[0];
				sal20 = automata[actual].Codificador_Salida(0);
				sal21 = automata[actual].Codificador_Salida(1);
				siguiente1 = sal20[0];
				siguiente2 = sal21[0];
			}
			else{
				actual = sal1[0];
				sal20 = automata[actual].Codificador_Salida(0);
				sal21 = automata[actual].Codificador_Salida(1);
				siguiente1 = sal20[0];
				siguiente2 = sal21[0];
			}
			d00 = trellis[1][actual].get(1);
			d01 = d00;
			d00 = d00 + Distancia(sal20,entrada,h);
			d01 = d01 + Distancia(sal21,entrada,h);
			
			if (i==0){
				if(trellis[2][siguiente1].get(1) > d00){
					trellis[2][siguiente1].set(0,actual);
					trellis[2][siguiente1].set(1, d00);
				}
				if(trellis[2][siguiente2].get(1) > d01){
					trellis[2][siguiente2].set(0,actual);
					trellis[2][siguiente2].set(1, d01);
				}
				
			}
			else{
				if(trellis[2][siguiente1].get(1) > d00){
					trellis[2][siguiente1].set(0,actual);
					trellis[2][siguiente1].set(1, d00);
				}
				if(trellis[2][siguiente2].get(1) > d01){
					trellis[2][siguiente2].set(0,actual);
					trellis[2][siguiente2].set(1, d01);
				}	
			}
		}
		
		h = h + n;
		//Instantes 2 a m-3
		for(int i = 2; i< m-3;i++){
			for(int j = 0;j < automata.length;j++){
					sal0 = automata[j].Codificador_Salida(0);
					sal1 = automata[j].Codificador_Salida(1);
					d00 = trellis[i][j].get(1);
					d01 = d00;
					d00 = d00 + Distancia(sal0,entrada,h);
					d01 = d01 + Distancia(sal1,entrada,h);
					
					if(trellis[i+1][sal0[0]].get(1) > d00){
						trellis[i+1][sal0[0]].set(0,j);
						trellis[i+1][sal0[0]].set(1, d00);
					}					
					if(trellis[i+1][sal1[0]].get(1) > d01){
						trellis[i+1][sal1[0]].set(0,j);
						trellis[i+1][sal1[0]].set(1, d01);
					}
			}
			h = h +n;		
		}
		//Instantes m-3 y m-2
		for(int i = m-3; i < m-1;i++){
			for(int j = 0;j < automata.length;j++){
				if(trellis[i][j].get(0) != -1){
					actual = j;
					sal0 = automata[actual].Codificador_Salida(0);
					siguiente1 = sal0[0];
					d00 = trellis[i][j].get(1);
					d00 = d00 + Distancia(sal0,entrada,h);
					
					if(trellis[i+1][siguiente1].get(1) > d00){
						trellis[i+1][siguiente1].set(0,actual);
						trellis[i+1][siguiente1].set(1, d00);
					}
				}
			}
			h = h +n;
		}
		return trellis;
		
	}
	
	
	

}
