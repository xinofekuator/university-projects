package moreExtendedNodePositionList;

import net.datastructures.Position;
import net.datastructures.NodePositionList;

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
	System.out.println("Testing reverse...");


	// First reverse test
	int a0[] = {0,1,2,3,4}; 
	int a0_rev[] = {4,3,2,1,0};
	MoreExtendedNodePositionList<Integer> l0 = mkPositionList(a0);
	MoreExtendedNodePositionList<Integer> l0_saved = mkPositionList(a0);
	MoreExtendedNodePositionList<Integer> l0_answer = mkPositionList(a0_rev);

	MoreExtendedNodePositionList<Integer> l0_rev = l0.reverse();
	
	if (!eqLists(l0_answer,l0_rev)) {
	    System.out.println
		("*** Error: the reverse of list "+printList(l0_saved)+
		 " should be "+printList(l0_answer)+
		 " but is "+printList(l0_rev));
	    throw new Error();
	}
	checkOrig(l0,l0_saved);

	// Second reverse test
	int a1[] = {5,1,4,5}; 
	int a1_rev[] = {5,4,1,5};
	MoreExtendedNodePositionList<Integer> l1 = mkPositionList(a1);
	MoreExtendedNodePositionList<Integer> l1_saved = mkPositionList(a1);
	MoreExtendedNodePositionList<Integer> l1_answer = mkPositionList(a1_rev);

	MoreExtendedNodePositionList<Integer> l1_rev = l1.reverse();
	
	if (!eqLists(l1_answer,l1_rev)) {
	    System.out.println
		("*** Error: the reverse of list "+printList(l1_saved)+
		 " should be "+printList(l1_answer)+
		 " but is "+printList(l1_rev));
	    throw new Error();
	}
	checkOrig(l1,l1_saved);



	// Third reverse test
	int a2[] = {}; 
	int a2_rev[] = {};
	MoreExtendedNodePositionList<Integer> l2 = mkPositionList(a2);
	MoreExtendedNodePositionList<Integer> l2_saved = mkPositionList(a2);
	MoreExtendedNodePositionList<Integer> l2_answer = mkPositionList(a2_rev);

	MoreExtendedNodePositionList<Integer> l2_rev = l2.reverse();
	
	if (!eqLists(l2_answer,l2_rev)) {
	    System.out.println
		("*** Error: the reverse of list "+printList(l2_saved)+
		 " should be "+printList(l2_answer)+
		 " but is "+printList(l2_rev));
	    throw new Error();
	}
	checkOrig(l2,l2_saved);

	
	System.out.println
	    ("\nFinished testing reverse; will test unique...\n");


	// First unique test

	int a3[]  = {1,5,2,8,-3}; 
	MoreExtendedNodePositionList<Integer> l3 = mkPositionList(a3);
	MoreExtendedNodePositionList<Integer> l3_saved = mkPositionList(a3);
	int a3_answer[] = {1,5,2,8,-3}; 
	MoreExtendedNodePositionList<Integer> l3_answer = mkPositionList(a3_answer);

	MoreExtendedNodePositionList<Integer> l3_unique = l3.unique();
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
	MoreExtendedNodePositionList<Integer> l4 = mkPositionList(a4);
	MoreExtendedNodePositionList<Integer> l4_saved = mkPositionList(a4);
	int a4_answer[] = {1,5,8}; 
	MoreExtendedNodePositionList<Integer> l4_answer = mkPositionList(a4_answer);

	MoreExtendedNodePositionList<Integer> l4_unique = l4.unique();
	if (!eqLists(l4_answer,l4_unique)) {
	    System.out.println
		("*** Error: unique("+printList(l4_saved)+
		 ") should be "+printList(l4_answer)+
		 " but is "+printList(l4_unique));
	    throw new Error();
	}
	checkOrig(l4_saved,l4);


	// Third unique test

	int a5[]  = {1,5,3,3,1}; 
	MoreExtendedNodePositionList<Integer> l5 = mkPositionList(a5);
	MoreExtendedNodePositionList<Integer> l5_saved = mkPositionList(a5);
	int a5_answer[] = {1,5,3}; 
	MoreExtendedNodePositionList<Integer> l5_answer = mkPositionList(a5_answer);

	MoreExtendedNodePositionList<Integer> l5_unique = l5.unique();
	if (!eqLists(l5_answer,l5_unique)) {
	    System.out.println
		("*** Error: unique("+printList(l5_saved)+
		 ") should be "+printList(l5_answer)+
		 " but is "+printList(l5_unique));
	    throw new Error();
	}
	checkOrig(l5_saved,l5);


	System.out.println("Test finalizado correctamente.");
    }

    static MoreExtendedNodePositionList<Integer> mkPositionList(int arr[]) {
	MoreExtendedNodePositionList<Integer> list = 
	    new MoreExtendedNodePositionList<Integer>();
	
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
