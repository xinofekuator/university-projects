package comparadores;

import java.util.Comparator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.management.ManagementFactory;


/**
 * This class provides methods to test your
 * <code>MyElementIterator</code> and <code>MySparseElementIterator</code>
 * implementations.
 */
public class Tester {
    /**
     * Runs the test suite.
     */
    public static void main(String args[]) {
	try {
	      String[] ids = ManagementFactory.getRuntimeMXBean().getName()
		  .split("@");
	      BufferedWriter bw = new BufferedWriter(new FileWriter("pid"));
	      bw.write(ids[0]);
	      bw.close();
	} catch (Exception e) {
	    System.out.println("Avisa al profesor de fallo sacando el PID");
	}

	doTest();
    }

    /**
     * Executes the test suite.
     */
    public static void doTest() {

	System.out.println("\nTesting Comparator...\n");

	int[] jv_results = {7,5,10};
	Alumno jv =
	    new Alumno("Jorge Valdano","01932",jv_results);
        int[] ms_results = {8,9};
	Alumno ms =
	    new Alumno("Maria Salvo","019959",ms_results);
	Alumno jp =
	    new Alumno("Jorge Pantano","01935",jv_results);
	Alumno jv2 =
	    new Alumno("Jorge Valdano","01933",jv_results);
        int[] sr_results = {7,7,5,5,11};
	Alumno sr = 
	    new Alumno("Susana Ramon","05932",sr_results);
        int[] fp_results = {7};
	Alumno fp = 
	    new Alumno("Francisco Perez","105932",fp_results);

	compareAlumnos(jv,jv,0);
	compareAlumnos(jv,ms,-1);
	compareAlumnos(jv,jp,1);
	compareAlumnos(jv,jv2,-1);
	compareAlumnos(jv,sr,-1);
	compareAlumnos(jv,fp,1);



	System.out.println("\n\nTesting Insert...\n");

	Integer[] arr = {1,2,3,3,5,null,null,null};
	Integer[] arr0 = {1,2,3,3,5,null,null,null};
	Integer[] arr_sol = {1,2,2,3,3,5,null,null};
	Integer[] arr_sol2 = {1,2,2,3,3,5,6,null};
	Integer[] arr_sol3 = {0,1,2,2,3,3,5,6};

	Insert.insert(2,arr,new IntegerComparator());
	if (!eqArrays(arr,arr_sol)) {
	    System.out.println
		("*** Error: after inserting 2 in "+printArray(arr0)+
		 " the array should be "+
		 printArray(arr_sol)+" but is "+printArray(arr));
	    throw new Error();
	}

	Insert.insert(6,arr,new IntegerComparator());
	if (!eqArrays(arr,arr_sol2)) {
	    System.out.println
		("*** Error: after inserting 6 in "+printArray(arr_sol)+
		 " the array should be "+
		 printArray(arr_sol2)+" but is "+printArray(arr));
	    throw new Error();
	}

	Insert.insert(0,arr,new IntegerComparator());
	if (!eqArrays(arr,arr_sol3)) {
	    System.out.println
 		("*** Error: after inserting 0 in "+printArray(arr_sol2)+
		 " the array should be "+
		 printArray(arr_sol3)+" but is "+printArray(arr));
	    throw new Error();
	}

	System.out.println("\n\nTest finalizado correctamente.");
    }


    static void compareAlumnos(Alumno a1, Alumno a2, int expected) {

	Comparator<Alumno> cmp = new CompareAlumnos();

	int cmp_a1_a2 = cmp.compare(a1,a2);
	int normalized_cmp_a1_a2 = cmp_a1_a2 < 0 ? -1 : (cmp_a1_a2 > 0 ? 1 : 0);

	if (normalized_cmp_a1_a2 != expected) {
	    String sign = 
		expected == 0 ? "=" : (expected < 0 ? "<" : ">");
	    System.out.println
		("a1=Alumno "+a1+" "+sign+" a2=Alumno "+a2+
		 " but compare(a1,a2) returns "+cmp_a1_a2+"\n");
	    throw new Error();
	}
    }


    public static String printArray(Integer[] a) {
	String resultString = "";
	for (Integer result : a)
	    if (resultString == "") 
		resultString = result.toString();
	    else
		resultString =
		    resultString + "," + 
		    (result == null ? "null" : result.toString());
	return resultString;
    }

    static boolean eqArrays(Integer[] a1, Integer[] a2) {
	boolean equal = true;
	
	if (a1.length != a2.length)
	    equal=false;
	else {
	    int pos=0;
	    while (pos < a1.length && equal) {
		if (a1[pos] == null) 
		    equal = (a2[pos] == null);
		else if (!a1[pos].equals(a2[pos]))
		    equal = false;
		pos++;
	    }
	}
	return equal;
    }
}

class IntegerComparator implements Comparator<Integer> {
    public int compare(Integer i1,Integer i2) {
	return i1.compareTo(i2);
    }    
}
