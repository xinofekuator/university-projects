
public class TestConcesionario {

	/**
	 * @param args
	 */
	public static void main(String[] args) {//main

		Persona persona = new Persona("pepe", "gonzalez", "85498594");
		
		System.out.println(persona); // probamos el toString de Persona
		
		Coche coche = new Coche(TMarca.AUDI, "A3");
		
		System.out.println(coche); // probamos el toString de Coche
		
		coche.venderCoche(persona);
		
		System.out.println("Despues de comprarlo"); 
		
		System.out.println(coche); // probamos el toString de Coche
		
		//Probamos la fábrica
		FabricaCoches fabrica = new FabricaCoches(TMarca.AUDI,"A3");
		
		Coche[] pedidoCoches1 = fabrica.pedirCoches(3);
		
		for(int i=0; i<pedidoCoches1.length; i++)
			System.out.println(pedidoCoches1[i].toString());
		
		// pepe compra el coche que esta en la pos 1 del vector
		
		pedidoCoches1[1].venderCoche(persona);
		
		//probamos el concesionario
		Concesionario concesionario = new Concesionario("B3333", 12,new FabricaCoches(TMarca.SEAT,"IBIZA"));
		coche= concesionario.comprarCoche(persona);
		System.out.println ("El coche vendido es:"+coche);
		//Ahora vamos a forzar a que el concesionario tenga que pedir más choches
		for (int i =0;i< 20; i++)
		{
			System.out.println ("Se pide el coche: "+(i+2)+" al concesionario");
			coche = concesionario.comprarCoche(new Persona ("pepe"+(i+2), "gonzalez", "85498594"));
			System.out.println("El coche vendido es: "+ coche);
		}

	}//main	
	
}




