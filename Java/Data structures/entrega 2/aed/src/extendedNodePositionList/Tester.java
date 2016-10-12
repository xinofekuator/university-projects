package extendedNodePositionList;

import net.datastructures.Position;
import net.datastructures.BoundaryViolationException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.management.ManagementFactory;


/**
 * This class provides methods to test your implementation of
 * the <code>IntegerSet</code> implementation.
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
	int a0[] = {0,1,2,3,4};
	ExtendedNodePositionList<Integer> l0 = mkPositionList(a0);

	Position<Integer> p1 = l0.nth(1);
	if (p1.element() != 0) {
	    System.out.println("Position 1 should have the value 0");
	    throw new Error();
	}
	Position<Integer> p3 = l0.nth(3);
	if (p3.element() != 2) {
	    System.out.println("Position 3 should have the value 2");
	    throw new Error();
	}
	Position<Integer> p5 = l0.nth(5);
	if (p5.element() != 4) {
	    System.out.println("Position 5 should have the value 4");
	    throw new Error();
	}
	try { 
	    Position<Integer> pExc = l0.nth(6);
	    System.out.println("afterwards");
	    System.out.println
		("Accessing position 6 should throw an "+
		 "exception net.datastructures.BoundaryViolationException\n"+
		 "but returned the value "+pExc);
	    throw new Error();
	} catch (BoundaryViolationException e) { };

	int m1[] = {0,1,2,3,4};
	ExtendedNodePositionList<Integer> l1 = mkPositionList(m1);
	int m2[] = {-1,-2,-3,-4,-5};
	ExtendedNodePositionList<Integer> l2 = mkPositionList(m2);

	ExtendedNodePositionList<Integer> l3 = l1.fairMerge(l2);
	if (l3.size() != 10) {
	    System.out.println
		("The merged array should have a size of 10"+
		 " but has a size of "+l3.size());
	    throw new Error();
	}
	Position<Integer> p6 = l3.nth(1);
	if (p6.element() != 0) {
	    System.out.println
		("Position 1 should have the value 0"+
		 " but has value "+p6.element());
	    throw new Error();
	}
	Position<Integer> p7 = l3.nth(2);
	if (p7.element() != -1) {
	    System.out.println
		("Position 2 should have the value -1"+
		 " but has value "+p7.element());
	    throw new Error();
	}

	ExtendedNodePositionList<Integer> l4 = l2.fairMerge(l1);
	if (l4.size() != 10) {
	    System.out.println
		("The merged array should have a size of 10"+
		 " but has a size of "+l4.size());
	    throw new Error();
	}
	Position<Integer> p8 = l4.nth(1);
	if (p8.element() != -1) {
	    System.out.println
		("Position 1 should have the value -1"+
		 " but has value "+p8.element());
	    throw new Error();
	}
	Position<Integer> p9 = l4.nth(2);
	if (p9.element() != 0) {
	    System.out.println
		("Position 2 should have the value 0"+
		 " but has value "+p9.element());
	    throw new Error();
	}

	int m3[] = {0};
	ExtendedNodePositionList<Integer> l5 = mkPositionList(m3);
	int m4[] = {1,2};
	ExtendedNodePositionList<Integer> l6 = mkPositionList(m4);

	ExtendedNodePositionList<Integer> l7 = l5.fairMerge(l6);
	if (l7.size() != 3) {
	    System.out.println
		("The merged array should have a size of 3"+
		 " but has a size of "+l7.size());
	    throw new Error();
	}
	Position<Integer> p10 = l7.nth(3);
	if (p10.element() != 2) {
	    System.out.println
		("Position 3 should have the value 2"+
		 " but has value "+p10.element());
	    throw new Error();
	}

	System.out.println("Test finalizado correctamente.");
    }

    static ExtendedNodePositionList<Integer> mkPositionList(int arr[]) {
	ExtendedNodePositionList<Integer> list = 
	    new ExtendedNodePositionList<Integer>();
	
	for (int i=0; i<arr.length; i++)
	    list.addLast(arr[i]);
	return list;
    }
}
