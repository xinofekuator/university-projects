package iterateNodePositionList;

import net.datastructures.NodePositionList;
import net.datastructures.BoundaryViolationException;
import net.datastructures.Position;

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
	System.out.println("Testing nth...");


	int a0[] = {8,3,22,5,3};
	IterateNodePositionList<Integer> l0 = mkPositionList(a0);

	Integer i1 = l0.nth(1);
	if (i1 != 8) {
	    System.out.println
		("Position 1 should have the value 8 "+
		 "but has the value "+i1);
	    throw new Error();
	}

	Integer i3 = l0.nth(3);
	if (i3 != 22) {
	    System.out.println
		("Position 3 should have the value 22 "+
		 "but has the value "+i3);
	    throw new Error();
	}

	Integer i5 = l0.nth(5);
	if (i5 != 3) {
	    System.out.println
		("Position 5 should have the value 3 "+
		 "but has the value "+i5);
	    throw new Error();
	}

	try { 
	    Integer iExc = l0.nth(6);
	    System.out.println
		("Accessing position 6 should throw an "+
		 "exception net.datastructures.BoundaryViolationException\n"+
		 "but returned the value "+iExc);
	    throw new Error();
	} catch (BoundaryViolationException e) { };

	int a1[] = {};
	IterateNodePositionList<Integer> l1 = mkPositionList(a1);

	try { 
	    Integer iExc = l1.nth(1);
	    System.out.println
		("Accessing position 1 should throw an "+
		 "exception net.datastructures.BoundaryViolationException\n"+
		 "but returned the value "+iExc);
	    throw new Error();
	} catch (BoundaryViolationException e) { };

	System.out.println
	    ("\nFinished testing nth; will test unique...\n");


	// First unique test

	int a3[]  = {1,5,2,8,-3}; 
	IterateNodePositionList<Integer> l3 = mkPositionList(a3);
	IterateNodePositionList<Integer> l3_saved = mkPositionList(a3);
	int a3_answer[] = {1,5,2,8,-3}; 
	IterateNodePositionList<Integer> l3_answer = mkPositionList(a3_answer);

	IterateNodePositionList<Integer> l3_unique = l3.unique();

	if (l3 == l3_unique) {
	    System.out.println
		("*** Error: unique returned the original list. "+
		 "It should return a new list.");
	    throw new Error();
	}

	if (!eqLists(l3_answer,l3_unique)) {
	    System.out.println
		("*** Error: unique("+printList(l3_saved)+
		 ") should be "+printList(l3_answer)+
		 " but is "+printList(l3_unique));
	    throw new Error();
	}
	checkOrig(l3_saved,l3);


	// Second unique test

	int a4[]  = {1,5,1,8}; 
	IterateNodePositionList<Integer> l4 = mkPositionList(a4);
	IterateNodePositionList<Integer> l4_saved = mkPositionList(a4);
	int a4_answer[] = {1,5,8}; 
	IterateNodePositionList<Integer> l4_answer = mkPositionList(a4_answer);

	IterateNodePositionList<Integer> l4_unique = l4.unique();
	if (!eqLists(l4_answer,l4_unique)) {
	    System.out.println
		("*** Error: unique("+printList(l4_saved)+
		 ") should be "+printList(l4_answer)+
		 " but is "+printList(l4_unique));
	    throw new Error();
	}
	checkOrig(l4_saved,l4);

	if (l4 == l4_unique) {
	    System.out.println
		("*** Error: unique returned the original list. "+
		 "It should return a new list.");
	    throw new Error();
	}

	// Third unique test

	int a5[]  = {1,5,3,3,1}; 
	IterateNodePositionList<Integer> l5 = mkPositionList(a5);
	IterateNodePositionList<Integer> l5_saved = mkPositionList(a5);
	int a5_answer[] = {1,5,3}; 
	IterateNodePositionList<Integer> l5_answer = mkPositionList(a5_answer);

	IterateNodePositionList<Integer> l5_unique = l5.unique();
	if (!eqLists(l5_answer,l5_unique)) {
	    System.out.println
		("*** Error: unique("+printList(l5_saved)+
		 ") should be "+printList(l5_answer)+
		 " but is "+printList(l5_unique));
	    throw new Error();
	}
	checkOrig(l5_saved,l5);

	if (l5 == l5_unique) {
	    System.out.println
		("*** Error: unique returned the original list. "+
		 "It should return a new list.");
	    throw new Error();
	}


	// Fourth unique test

	int a6[]  = {}; 
	IterateNodePositionList<Integer> l6 = mkPositionList(a6);
	IterateNodePositionList<Integer> l6_saved = mkPositionList(a6);
	int a6_answer[] = {}; 
	IterateNodePositionList<Integer> l6_answer = mkPositionList(a6_answer);

	IterateNodePositionList<Integer> l6_unique = l6.unique();
	if (!eqLists(l6_answer,l6_unique)) {
	    System.out.println
		("*** Error: unique("+printList(l6_saved)+
		 ") should be "+printList(l6_answer)+
		 " but is "+printList(l6_unique));
	    throw new Error();
	}
	checkOrig(l6_saved,l6);

	if (l6 == l6_unique) {
	    System.out.println
		("*** Error: unique returned the original list. "+
		 "It should return a new list.");
	    throw new Error();
	}


	System.out.println("Test finalizado correctamente.");
    }

    static IterateNodePositionList<Integer> mkPositionList(int arr[]) {
	IterateNodePositionList<Integer> list = 
	    new IterateNodePositionList<Integer>();
	
	for (int i=0; i<arr.length; i++)
	    list.addLast(arr[i]);
	return list;
    }

    static <E> void checkOrig(NodePositionList<E> orig, NodePositionList<E> l) {
	if (!eqLists(orig,l)) {
	    System.out.println
		("*** Error: the reverse() method has modified the original "+
		 "list.\nIt should still be "+printList(orig)+
		 " but is "+printList(l));
	    throw new Error();
	}
    }


    /**
     * Returns a string representation of the position list argument.
     */
    static <E> String printList(NodePositionList<E> l) {
	StringBuffer sl = new StringBuffer(); 

	if (l == null) sl.append("null");
	else {
	    int size = l.size();
	    if (size == 0) sl.append("empty");
	    else {
		sl.append("{");
		Position<E> currPos = l.first();
		while (size > 0) {
		    sl.append(currPos.element());
		    if (--size > 0) {
			currPos = l.next(currPos);
			sl.append(",");
		    }
		}
		sl.append("}");
	    }
	}
	return sl.toString();
    }

    static <E> boolean eqLists(NodePositionList<E> l1,
			       NodePositionList<E> l2) {

	if (l1==null && l2==null) return true;
	else if (l1 == null) return false;
	else if (l2 == null) return false;

	int size_l1 = l1.size();
	int size_l2 = l2.size();
	boolean equal = true;
	
	if (size_l1 != size_l2) 
	    equal=false;
	else {
	    Position<E> l1pos = null;
	    Position<E> l2pos = null;

	    if (size_l1 > 0) l1pos = l1.first();
	    if (size_l2 > 0) l2pos = l2.first();

	    while (size_l1 > 0 && equal) {
		if (!l1pos.element().equals(l2pos.element()))
		    equal = false;
		if (--size_l1 > 0) {
		    l1pos = l1.next(l1pos);
		    l2pos = l2.next(l2pos);
		}
	    }
	}
	return equal;
    }
}
