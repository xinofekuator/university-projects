package practica;

/*Autores: Ignacio Amaya de la Peña
		   Adrián Cámara Caunedo
		   Borja Mas García
 */

//Clase para la creación de los datos del problema(Paradas de metro y matriz H)

public class Datos {

	//Paradas de metro del Metro de Praga
	ParadaMetro a1=null;
	ParadaMetro a2=null;
	ParadaMetro a3=null;
	ParadaMetro a4=null;
	ParadaMetro a5=null;
	ParadaMetro a6=null;
	ParadaMetro a7=null;
	ParadaMetro a8=null;
	ParadaMetro a9=null;
	ParadaMetro a10=null;
	ParadaMetro a11=null;
	ParadaMetro a12=null;
	ParadaConTrasbordo a13=null;
	ParadaMetro a14=null;
	ParadaConTrasbordo a15=null;
	ParadaMetro a16=null;
	ParadaMetro a17=null;
	ParadaMetro a18=null;
	ParadaMetro a19=null;
	ParadaMetro a20=null;
	ParadaMetro a21=null;
	ParadaMetro a22=null;
	ParadaMetro a23=null;
	ParadaMetro v1=null;
	ParadaMetro v2=null;
	ParadaMetro v3=null;
	ParadaMetro v4=null;
	ParadaConTrasbordo v5=null;
	ParadaMetro v6=null;
	ParadaMetro v7=null;
	ParadaMetro v8=null;
	ParadaMetro v9=null;
	ParadaMetro v10=null;
	ParadaMetro v11=null;
	ParadaMetro r1=null;
	ParadaMetro r2=null;
	ParadaMetro r3=null;
	ParadaMetro r4=null;
	ParadaMetro r5=null;
	ParadaMetro r6=null;
	ParadaMetro r7=null;
	ParadaMetro r8=null;
	ParadaMetro r9=null;
	ParadaMetro r10=null;
	ParadaMetro r11=null;
	ParadaMetro r12=null;
	ParadaMetro r13=null;
	ParadaMetro r14=null;
	ParadaMetro r15=null;

	//Array donde almacenamos las paradas
	ParadaMetro[] paradas=new ParadaMetro[49];

	//Matriz de las valores de la funcion heuristica
	public final int[][] h = new int[11][11];
	
	//Metodo para facilitar la entrada de los datos para la matriz de las heurísticas
	public void rellenarFila(int fila,int[] a){
		for(int i = 0; i < 11; i++){
			h[fila][i] = a[i]; 
		}
	}

	//Inicializamos los datos
	public Datos(){

		a1=new ParadaMetro(0,"Zli�?ín",a2,null,100,0,5);
		a2=new ParadaMetro(1,"Stodůlky",a3,a1,72,100,5);
		a3=new ParadaMetro(2,"Luka",a4,a2,81,72,5);
		a4=new ParadaMetro(3,"Lužiny",a5,a3,65,81,5);
		a5=new ParadaMetro(4,"Hůrka",a6,a4,111,65,5);
		a6=new ParadaMetro(5,"Nové Butovice",a7,a5,90,111,5);
		a7=new ParadaMetro(6,"Jinonice",a8,a6,98,90,5);
		a8=new ParadaMetro(7,"Radlická",a9,a7,76,98,5);
		a9=new ParadaMetro(8, "Smíchovské nádraží", a10, a8, 111, 76, 5);
		a10=new ParadaMetro(9, "Anděl", a11, a9, 89, 111, 5);
		a11=new ParadaMetro(10, "Karlovo náměstí", a12, a10, 68, 89, 5);
		a12=new ParadaMetro(11, "Národní třída", a13, a11, 94, 68, 5);
		a13=new ParadaConTrasbordo(12, "Můstek", a14, a12, 78, 94, 0, 300, v4, v5, 76, 121);
		a14=new ParadaMetro(13, "Náměstí Republiky", a15, a13, 61, 78, 3);
		a15=new ParadaConTrasbordo(14,"Florenc",a16,a14,80,61,1,300,r5,r4,98,76);
		a16=new ParadaMetro(15,"Křižíkova",a17,a15,87,80,8);
		a17=new ParadaMetro(16,"Invalidovna",a18,a16,105,87,8);
		a18=new ParadaMetro(17,"Palmovka",a19,a17,100,105,8);
		a19=new ParadaMetro(18,"Českomoravská",a20,a18,77,100,8);
		a20=new ParadaMetro(19,"Vyso�?anská",a21,a19,109,77,8);
		a21=new ParadaMetro(20,"Hloubětín",a22,a20,90,109,8);
		a22=new ParadaMetro(21,"RajskáZahrada",a23,a21,71,90,8);
		a23=new ParadaMetro(22,"ČernýMost",null,a22,0,71,8);
		v1=new ParadaMetro(23, "Dejvická", v2, null, 96, 0, 6);
		v2=new ParadaMetro(24, "Hrad�?anská", v3, v1, 68, 96, 6);
		v3=new ParadaMetro(25, "Malostranská", v4, v2, 111, 68, 6);
		v4=new ParadaMetro(26, "Staroměstská",v5, v3, 87, 111, 6);
		v5=new ParadaConTrasbordo(27, "Muzeum", v6, a13, 133, 121, 2, 300, r5, r6, 76, 99);
		v6=new ParadaMetro(28, "Náměstí Míru", v7, v5, 79, 133, 9);
		v7=new ParadaMetro(29, "Jiřího z Poděbrad", v8, v6, 89, 79, 9);
		v8=new ParadaMetro(30, "Flora", v9, v7, 123, 89, 9);
		v9=new ParadaMetro(31, "Želivského", v10, v8, 86, 123, 9);
		v10=new ParadaMetro(32, "Strašnická", v11, v9, 77, 86, 9);
		v11=new ParadaMetro(33, "Skalka", null, v10, 0, 77, 9);
		r1=new ParadaMetro(34, "Ládví", r2, null, 94, 0, 7);
		r2=new ParadaMetro(35, "Kobylisy", r3, r1, 76, 94, 7);
		r3=new ParadaMetro(36, "Nádraží Holešovice", r4, r2, 108, 76, 7);
		r4=new ParadaMetro(37, "Vltavská", a13, r3, 76, 108, 7);
		r5=new ParadaMetro(38, "Hlavní nádraží", v5, a13, 65, 98, 4);
		r6=new ParadaMetro(39, "I.P.Pavlova", r7, v5, 113, 104, 10);
		r7=new ParadaMetro(40, "Vyšehrad", r8, r6, 120, 113, 10);
		r8=new ParadaMetro(41, "Pražského povstání", r9, r7, 61, 120, 10);
		r9=new ParadaMetro(42, "Pankrác", r10, r8, 89, 61, 10);
		r10=new ParadaMetro(43, "Budějovická", r11, r9, 111, 89, 10);
		r11=new ParadaMetro(44, "Ka�?erov", r12, r10, 72, 111, 10);
		r12=new ParadaMetro(45, "Roztyly", r13, r11, 102, 72, 10);
		r13=new ParadaMetro(46, "Chodov", r14, r12, 62, 102, 10);
		r14=new ParadaMetro(47, "Opatov", r15, r13, 87, 62, 10);
		r15=new ParadaMetro(48, "Háje", null, r14, 0, 87, 10);

		a1.setSiguiente(a2); 
		a2.setSiguiente(a3);a2.setAnterior(a1);
		a3.setSiguiente(a4);a3.setAnterior(a2);
		a4.setSiguiente(a5);a4.setAnterior(a3);
		a5.setSiguiente(a6);a5.setAnterior(a4);
		a6.setSiguiente(a7);a6.setAnterior(a5);
		a7.setSiguiente(a8);a7.setAnterior(a6);
		a8.setSiguiente(a9);a8.setAnterior(a7);
		a9.setSiguiente(a10);a9.setAnterior(a8);
		a10.setSiguiente(a11);a10.setAnterior(a9);
		a11.setSiguiente(a12);a11.setAnterior(a10);
		a12.setSiguiente(a13);a12.setAnterior(a11);
		a13.setSiguiente(a14);a13.setAnterior(a12);a13.setSiguiente2(v5);a13.setAnterior2(v4);
		a14.setSiguiente(a15);a14.setAnterior(a13);
		a15.setSiguiente(a16);a15.setAnterior(a14);a15.setSiguiente2(r5);a15.setAnterior2(r4);
		a16.setSiguiente(a17);a16.setAnterior(a15);
		a17.setSiguiente(a18);a17.setAnterior(a16);
		a18.setSiguiente(a19);a18.setAnterior(a17);
		a19.setSiguiente(a20);a19.setAnterior(a18);
		a20.setSiguiente(a21);a20.setAnterior(a19);
		a21.setSiguiente(a22);a21.setAnterior(a20);
		a22.setSiguiente(a23);a22.setAnterior(a21);
		a23.setAnterior(a22);
		v1.setSiguiente(v2); 
		v2.setSiguiente(v3);v2.setAnterior(v1);
		v3.setSiguiente(v4);v3.setAnterior(v2);
		v4.setSiguiente(a13);v4.setAnterior(v3);
		v5.setSiguiente(v6);v5.setAnterior(a13);v5.setSiguiente2(r6);v5.setAnterior2(r5);
		v6.setSiguiente(v7);v6.setAnterior(v5);
		v7.setSiguiente(v8);v7.setAnterior(v6);
		v8.setSiguiente(v9);v8.setAnterior(v7);
		v9.setSiguiente(v10);v9.setAnterior(v8);
		v10.setSiguiente(v11);v10.setAnterior(v9);
		v11.setAnterior(v10);
		r1.setSiguiente(r2); 
		r2.setSiguiente(r3);r2.setAnterior(r1);
		r3.setSiguiente(r4);r3.setAnterior(r2);
		r4.setSiguiente(a15);r4.setAnterior(r3);
		r5.setSiguiente(v5);r5.setAnterior(a15);
		r6.setSiguiente(r7);r6.setAnterior(v5);
		r7.setSiguiente(r8);r7.setAnterior(r6);
		r8.setSiguiente(r9);r8.setAnterior(r7);
		r9.setSiguiente(r10);r9.setAnterior(r8);
		r10.setSiguiente(r11);r10.setAnterior(r9);
		r11.setSiguiente(r12);r11.setAnterior(r10);
		r12.setSiguiente(r13);r12.setAnterior(r11);
		r13.setSiguiente(r14);r13.setAnterior(r12);
		r14.setSiguiente(r15);r14.setAnterior(r13);
		r15.setAnterior(r14);

		paradas[0]=a1;
		paradas[1]=a2;
		paradas[2]=a3;
		paradas[3]=a4;
		paradas[4]=a5;
		paradas[5]=a6;
		paradas[6]=a7;
		paradas[7]=a8;
		paradas[8]=a9;
		paradas[9]=a10;
		paradas[10]=a11;
		paradas[11]=a12;
		paradas[12]=a13;
		paradas[13]=a14;
		paradas[14]=a15;
		paradas[15]=a16;
		paradas[16]=a17;
		paradas[17]=a18;
		paradas[18]=a19;
		paradas[19]=a20;
		paradas[20]=a21;
		paradas[21]=a22;
		paradas[22]=a23;
		paradas[23]=v1;
		paradas[24]=v2;
		paradas[25]=v3;
		paradas[26]=v4;
		paradas[27]=v5;
		paradas[28]=v6;
		paradas[29]=v7;
		paradas[30]=v8;
		paradas[31]=v9;
		paradas[32]=v10;
		paradas[33]=v11;
		paradas[34]=r1;
		paradas[35]=r2;
		paradas[36]=r3;
		paradas[37]=r4;
		paradas[38]=r5;
		paradas[39]=r6;
		paradas[40]=r7;
		paradas[41]=r8;
		paradas[42]=r9;
		paradas[43]=r10;
		paradas[44]=r11;
		paradas[45]=r12;
		paradas[46]=r13;
		paradas[47]=r14;
		paradas[48]=r15;

		int[] filaAux0 = {0 ,   120 , 60 ,  160/*60*/ ,  420 , 60 ,  60 ,  480 , 180 , 120 , 420};
		int[] filaAux1 = {120 , 0 ,   120 , 60 ,  60 ,  180 , 480 , 60 ,  60 ,  480 , 180};
		int[] filaAux2 = {60 ,  120 , 0 ,   420 , 160 /*60*/,  420 , 120 , 180 , 480 , 60 ,  60};
		int[] filaAux3 = {60 ,  60 ,  420 , 0 ,   420 , 120 , 420 , 420 , 120 , 480 , 480};
		int[] filaAux4 = {420 , 60 ,  60 ,  420 , 0 ,   480 , 480 , 120 , 420 , 420 , 120};
		int[] filaAux5 = {60 ,  180 , 420 , 120 , 480 , 0 ,   420 , 540 , 240 , 480 , 480};
		int[] filaAux6 = {60 ,  480 , 120 , 420 , 480,  420 , 0 ,   540 , 540 , 180 , 480};
		int[] filaAux7 = {480 , 60 ,  180 , 420 , 120 , 540 , 540 ,  0 ,   420 , 540 , 240};
		int[] filaAux8 = {180 , 60 ,  480 , 120 , 420,  240 , 540 ,  420 , 0 ,   540 , 540};
		int[] filaAux9 = {120 , 480 , 60 ,  480 , 420 , 480 , 180 ,  540 , 540 , 0 ,   420};
		int[] filaAux10= {420 , 180 , 60 ,  480 , 120 , 480 , 480 ,  240 , 540 , 420 , 0};

		rellenarFila(0,filaAux0);
		rellenarFila(1,filaAux1);
		rellenarFila(2,filaAux2);
		rellenarFila(3,filaAux3);
		rellenarFila(4,filaAux4);
		rellenarFila(5,filaAux5);
		rellenarFila(6,filaAux6);
		rellenarFila(7,filaAux7);
		rellenarFila(8,filaAux8);
		rellenarFila(9,filaAux9);
		rellenarFila(10,filaAux10);

	}

	//Metodos get para poder coger los datos de esta clase
	public ParadaMetro[] getParadas(){
		return this.paradas;
	}
	public int[][] getH(){
		return this.h;
	}
}
