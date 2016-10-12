package cod_convolucional;
import java.util.ArrayList;
//Para Códigos Convolucionales (n,1,m)
public class Main {
		
		public static void main(String[] args) {
			int [] Codigo_Convolucional_1 = {3,1,2};
			int N_1 = Codigo_Convolucional_1[0];
			
			int [] Codigo_Catastrofico = {2,1,2};
			int N_Cat = Codigo_Catastrofico[0];
			
			
			Automata automata1;
			Automata catastrofico;
			
			//Inicialización del 1er autómata
			int []s00 = {0,0,0,0,0};
			int []s01 = {2,1,1,1,1};
			Nodo S0 = new Nodo(s00,s01);
			
			int []s10 = {0,0,0,1,0};
			int []s11 = {2,1,1,0,0};
			Nodo S1 = new Nodo(s10,s11);
			
			int []s20 = {1,0,1,0,1};
			int []s21 = {3,1,0,1,0};
			Nodo S2 = new Nodo(s20,s21);
			
			int []s30 = {1,0,1,1,0};
			int []s31 = {3,1,0,0,1};
			Nodo S3 = new Nodo(s30,s31);
		
			Nodo [] nodos_1 = {S0,S1,S2,S3};
			automata1 = new Automata(nodos_1,N_1);
			
			//Inicialización del autómata catastrófico
			int []s00a = {0,0,0,0};
			int []s01a = {2,1,1,1};
			Nodo S0a = new Nodo(s00a,s01a);
			
			int []s10a = {0,0,0,1};
			int []s11a = {2,1,1,0};
			Nodo S1a = new Nodo(s10a,s11a);
			
			int []s20a = {1,0,1,0};
			int []s21a = {3,1,0,1};
			Nodo S2a = new Nodo(s20a,s21a);
			
			int []s30a = {1,0,1,1};
			int []s31a = {3,1,0,0};
			Nodo S3a = new Nodo(s30a,s31a);
			
			Nodo []nodos_cat = {S0a,S1a,S2a,S3a};
			catastrofico = new Automata(nodos_cat,N_Cat);
			
			int [] entrada1 = {1,1,0,1,0,0,0,1,1,1,0,1,0,0,1,1,1,0,0,1,0,0};
			int [] entrada2 = {1,0,1,1,0,0,0};
			int [] entrada4 = {1,1,1,1,1,1,1,0,0,1,1,0,1,1,0,0,1,0,0,0,0};
			int [] entrada5 = {1,1,1,1,1,1,1,1,1,1,1,1};
			
			//Pruebas sobre el automata no catastrófico
			int []salida1 = automata1.Codificar(entrada1);
			int []salida2 = automata1.Codificar(entrada2);
			
			int []salida3 = automata1.Descodificar(salida1);
			int []salida4 = automata1.Descodificar(salida2);
			
			ArrayList<Integer> [][] salida5 = automata1.Viterbi(entrada4);
			
			System.out.print("\n---------------------------------------------------------------------------------------\n");
			System.out.print("\n  Prueba del Autómata no catastrófico\n ");
			System.out.print("\n---------------------------------------------------------------------------------------\n");
			System.out.print("\n----------------  Codificar  ------------------------ ");
			automata1.imprime(entrada1,salida1);
			automata1.imprime(entrada2,salida2);
			
			System.out.print("\n\n:::::::::::::::  Descodificar  :::::::::::::::::::::: ");
			automata1.imprime(salida1,salida3);
			automata1.imprime(salida2,salida4);
			
			automata1.imprimeViterbi(entrada4, salida5);
			
			//Pruebas sobre el automata catastrófico
			int []salida1_Cat = catastrofico.Codificar(entrada2);		
			int []salida2_Cat = catastrofico.Descodificar(salida1_Cat);
			
			//ArrayList<Integer> [][] salida5_Cat = catastrofico.Viterbi(salida2_Cat);
			
			System.out.print("\n---------------------------------------------------------------------------------------\n");
			System.out.print("\n  Prueba del Autómata catastrófico\n ");
			System.out.print("\n---------------------------------------------------------------------------------------\n");
			System.out.print("\n----------------  Codificar  ------------------------ ");
			catastrofico.imprime(entrada2,salida1_Cat);
			
			System.out.print("\n\n:::::::::::::::  Descodificar  :::::::::::::::::::::: ");
			catastrofico.imprime(salida1_Cat,salida2_Cat);
			
			ArrayList<Integer> [][] salida3_Cat = catastrofico.Viterbi(salida1_Cat);
			catastrofico.imprimeViterbi(salida1_Cat, salida3_Cat);
			
			System.out.print("\n---------------------------------------------------------------------------------------\n");
			System.out.print("\n  Prueba del Autómata catastrófico con un error catastrófico\n ");
			
			int [] entrada_catastrofica = {0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
			
			System.out.print("\n  La entrada original y la codificada son: ");
			catastrofico.imprime(entrada5, catastrofico.Codificar(entrada5));
			System.out.print("\n\n  Pero en la transmisión el mensaje codificado que llega es: ");
			ArrayList<Integer> [][] salida_catastrofica = catastrofico.Viterbi(entrada_catastrofica);
			catastrofico.imprimeViterbi(entrada_catastrofica, salida_catastrofica);
			System.out.print("\n  Se produce el peor error posible entre el mensaje original 111111... y el mensaje descodificado 00000... ");
		}

	}
