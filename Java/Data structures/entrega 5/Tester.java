package positionListIterators;

import java.util.Iterator;

import net.datastructures.Position;
import net.datastructures.PositionList;
import net.datastructures.NodePositionList;

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
	System.out.println("Testing MyElementIterator...");

	Integer a0[] = {8,3,22,5,3};
	Integer a0_answer[] = {8,3,22,5,3};
	PositionList<Integer> l0 = mkPositionList(a0);
	PositionList<Integer> l0_copy = mkPositionList(a0);
	PositionList<Integer> l0_answer = mkPositionList(a0_answer);
	Iterator<Integer> it0 = new MyElementIterator<Integer>(l0);
	PositionList<Integer> l0_op = toList(it0);
	
	if (!eqLists(l0_answer,l0_op)) {
	    System.out.println
		("*** Error: the iterator for ("+printList(l0_copy)+
		 ") should return the elements "+printList(l0_answer)+
		 " but returns the elements "+printList(l0_op));
	    throw new Error();
	}

	Iterator<Integer> l0_it = new MyElementIterator<Integer>(l0);

	try {
	    l0_it.remove();
	    System.out.println
		("*** Error: Calling remove() without first calling next() "+
		 "should cause the "+
		 "exception IllegalStateException to be raised");
	    throw new Error();
	} catch (IllegalStateException exc) { };

	l0_it = new MyElementIterator<Integer>(l0);
	l0_it.next(); l0_it.next(); l0_it.next(); l0_it.next();
	if (!l0_it.hasNext()) {
	    System.out.println
		("*** Error: hasNext at the fourth position of "+
		 printList(l0)+
		 " returns false");
	    throw new Error();
	}
	l0_it.remove(); 
	
	Integer a0_answer_rm1[] = {8,3,22,3};
	PositionList<Integer> l0_rm1 = mkPositionList(a0_answer_rm1);

	// Check that an element was removed from the list
	if (!eqLists(l0,l0_rm1)) {
	    System.out.println
		("*** Error: the list"+printList(l0)+
		 " should be equal to "+printList(l0_rm1));
	    throw new Error();
	}

	if (!l0_it.hasNext()) {
	    System.out.println
		("*** Error: hasNext at the new fourth position of "+
		 printList(l0)+
		 " returns false");
	    throw new Error();
	}

	try {
	    l0_it.remove();
	    System.out.println
		("*** Error: Calling remove() twice on an iterator "+
		 " without a next inbetween should cause the "+
		 "exception IllegalStateException to be raised");
	    throw new Error();
	} catch (IllegalStateException exc) { };

	l0 = mkPositionList(a0);
	l0_it = new MyElementIterator<Integer>(l0);
	l0_it.next(); l0_it.next(); l0_it.next(); l0_it.next();
	l0_it.remove(); l0_it.next(); l0_it.remove();
	if (l0_it.hasNext()) {
	    System.out.println
		("*** Error: hasNext at the final third position of "+
		 printList(l0)+
		 " returns true");
	    throw new Error();
	}

	Integer a0_answer_rm2[] = {8,3,22};
	PositionList<Integer> l0_rm2 = mkPositionList(a0_answer_rm2);

	// Check that two elements were removed from the list
	if (!eqLists(l0,l0_rm2)) {
	    System.out.println
		("*** Error: the list"+printList(l0)+
		 ") should be equal to "+printList(l0_rm2));
	    throw new Error();
	}

	// ******************************************************************

	System.out.println
	    ("\nFinished testing MyElementIterator; "+
	     "will test MySparseElementIterator...\n");

	Integer s_a0[] = {8,3,22,5,3};
	Integer s_a0_answer[] = {8,3,22,5,3};
	PositionList<Integer> s_l0 = mkPositionList(s_a0);
	PositionList<Integer> s_l0_copy = mkPositionList(s_a0);
	PositionList<Integer> s_l0_answer = mkPositionList(s_a0_answer);
	Iterator<Integer> s_it0 = new MySparseElementIterator<Integer>(s_l0);
	PositionList<Integer> s_l0_op = toList(s_it0);
	
	if (!eqLists(s_l0_answer,s_l0_op)) {
	    System.out.println
		("*** Error: the iterator for ("+printList(s_l0_copy)+
		 ") should return the elements "+printList(s_l0_answer)+
		 " but returns "+printList(s_l0_op));
	    throw new Error();
	}

	Integer s_a1[] = {8,null,3,22,5,null,3};
	Integer s_a1_answer[] = {8,3,22,5,3};
	PositionList<Integer> s_l1 = mkPositionList(s_a1);
	PositionList<Integer> s_l1_copy = mkPositionList(s_a1);
	PositionList<Integer> s_l1_answer = mkPositionList(s_a1_answer);
	Iterator<Integer> s_it1 = new MySparseElementIterator<Integer>(s_l1);
	PositionList<Integer> s_l1_op = toList(s_it1);
	
	if (!eqLists(s_l1_answer,s_l1_op)) {
	    System.out.println
		("*** Error: the iterator for ("+printList(s_l1_copy)+
		 ") should return the elements "+printList(s_l1_answer)+
		 " but returns "+printList(s_l1_op));
	    throw new Error();
	}

	Integer s_a2[] = {8,null,null,null};
	Integer s_a2_answer[] = {8};
	PositionList<Integer> s_l2 = mkPositionList(s_a2);
	PositionList<Integer> s_l2_copy = mkPositionList(s_a2);
	PositionList<Integer> s_l2_answer = mkPositionList(s_a2_answer);
	Iterator<Integer> s_it2 = new MySparseElementIterator<Integer>(s_l2);
	PositionList<Integer> s_l2_op = toList(s_it2);
	
	if (!eqLists(s_l2_answer,s_l2_op)) {
	    System.out.println
		("*** Error: the iterator for ("+printList(s_l2_copy)+
		 ") should return the elements "+printList(s_l2_answer)+
		 " but returns "+printList(s_l2_op));
	    throw new Error();
	}

	Integer s_a3[] = {null};
	Integer s_a3_answer[] = {};
	PositionList<Integer> s_l3 = mkPositionList(s_a3);
	PositionList<Integer> s_l3_copy = mkPositionList(s_a3);
	PositionList<Integer> s_l3_answer = mkPositionList(s_a3_answer);
	Iterator<Integer> s_it3 = new MySparseElementIterator<Integer>(s_l3);
	PositionList<Integer> s_l3_op = toList(s_it3);
	
	if (!eqLists(s_l3_answer,s_l3_op)) {
	    System.out.println
		("*** Error: the iterator for ("+printList(s_l3_copy)+
		 ") should return the elements "+printList(s_l3_answer)+
		 " but returns "+printList(s_l3_op));
	    throw new Error();
	}

	Integer a4[] = {8,3,22,9,5,3};
	Integer a4_answer[] = {8,3,22,9,5,3};
	PositionList<Integer> l4 = mkPositionList(a4);
	PositionList<Integer> l4_copy = mkPositionList(a4);
	PositionList<Integer> l4_answer = mkPositionList(a4_answer);
	Iterator<Integer> it4a = new MyElementIterator<Integer>(l4);
	Iterator<Integer> it4b = new MyElementIterator<Integer>(l4);

	it4a.next(); it4b.next(); it4b.next();
	Integer e4a = it4a.next();
	Integer e4b = it4b.next();

	if (e4a != 3) {
	    System.out.println
		("*** Error: the second element in "+
		 printList(l4_copy)+" should be 3 but is "+e4a);
	    throw new Error();
	}

	if (e4b != 22) {
	    System.out.println
		("*** Error: the third element in "+
		 printList(l4_copy)+" should be 22 but is "+e4b);
	    throw new Error();
	}


	System.out.println("Test finalizado correctamente.");
    }

    static PositionList<Integer> mkPositionList(Integer arr[]) {
	PositionList<Integer> list = new NodePositionList<Integer>();
	
	for (int i=0; i<arr.length; i++)
	    list.addLast(arr[i]);
	return list;
    }

    static <E> void checkOrig(PositionList<E> orig, PositionList<E> l) {
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
    static <E> String printList(PositionList<E> l) {
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

    static <E> PositionList<E> toList(Iterator<E> it) {
	PositionList<E> l = new NodePositionList<E>();
	while (it.hasNext()) {
	    E element = null;
	    try { element = it.next(); }
	    catch (Exception exc) { 
		System.out.println
		    ("*** Error: the iterator threw an exception "+
		     "on calling next even though hasNext returned true");
		throw new Error();
	    }
	    l.addLast(element);
	}
	return l;
    }

    static <E> boolean eqLists(PositionList<E> l1,
			       PositionList<E> l2) {

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
