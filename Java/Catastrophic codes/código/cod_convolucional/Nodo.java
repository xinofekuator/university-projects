package cod_convolucional;
//import java.util.ArrayList;

public class Nodo {
	private int[] Salida0;
	private int[] Salida1;

	public Nodo(int[] sal0, int[] sal1){
		Salida0 = sal0;
		Salida1 = sal1;
	}

	public int[] Codificador_Salida(int bit){
		if(bit == 0)
			return Salida0;
		else 
			return Salida1;
	}

	public int[] Descodificador_Salida(int []entrada,int h){
		int sal_cero = 0;
		for(int i = 2; i < Salida0.length ; i++){
			if( Salida0[i] == entrada[h])
				sal_cero = sal_cero + 1;
			h++;
		}
		if (sal_cero == (Salida0.length - 2))
			return Salida0;
		else 
			return Salida1;
	}


}
