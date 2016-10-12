package binaryTrees;

import net.datastructures.LinkedBinaryTree;
import net.datastructures.Position;

import java.util.Iterator;
import java.util.Comparator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.NoSuchElementException;

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

	System.out.println("\nTesting insert...\n");

	BinTree<Integer> bt = new BinTree<Integer>(new IntegerComparator());
	bt.insert(10);
	bt.insert(5);
	bt.insert(15);
	bt.insert(5);
	int elements[] = {10,5,15,5};
	int len = elements.length;

	if (bt.size() != len) {
	    System.out.println
		("The size of the tree should be "+len+" but is "+bt.size());
	    System.out.println
		("\nTree:\n"+printTree(bt)+"\n");
	    throw new Error();
	}
	checkAllElements(bt,elements);


	bt = new BinTree<Integer>(new IntegerComparator());
	bt.insert(10);
	bt.insert(5);
	bt.insert(15);
	bt.insert(5);
	bt.insert(11);
	bt.insert(12);
	bt.insert(13);
	int elements1[] = {10,5,15,5,11,12,13};
	len = elements1.length;

	if (bt.size() != len) {
	    System.out.println
		("The size of the tree should be "+len+" but is "+bt.size());
	    System.out.println
		("\nTree:\n"+printTree(bt)+"\n");
	    throw new Error();
	}
	checkAllElements(bt,elements1);

	bt = new BinTree<Integer>(new IntegerComparator());
	bt.insert(10);
	bt.insert(10);
	bt.insert(10);
	bt.insert(10);
	bt.insert(10);
	bt.insert(5);
	bt.insert(3);
	bt.insert(7);
	bt.insert(9);
	bt.insert(20);
	bt.insert(15);
	bt.insert(25);
	bt.insert(10);
	int elements2[] = {10,10,10,10,10,5,3,7,9,20,15,25,10};
	len = elements2.length;

	if (bt.size() != len) {
	    System.out.println
		("The size of the tree should be "+len+" but is "+bt.size());
	    System.out.println
		("\nTree:\n"+printTree(bt)+"\n");
	    throw new Error();
	}
	checkAllElements(bt,elements2);



	System.out.println
	    ("\n\nContinuing testing insert; "+
	     "now also testing comparator...\n");

	bt = new BinTree<Integer>(new IntegerComparator());
	bt.insert(10);
	bt.insert(5);
	bt.insert(15);
	bt.insert(5);
	int elements4[] = {5,15};
	checkLeaves(bt,elements4);

	bt = new BinTree<Integer>(new IntegerComparator());
	bt.insert(10);
	bt.insert(5);
	bt.insert(15);
	bt.insert(5);
	bt.insert(11);
	bt.insert(12);
	bt.insert(13);
	int elements5[] = {5,13};
	checkLeaves(bt,elements5);

	bt = new BinTree<Integer>(new IntegerComparator());
	bt.insert(100);
	bt.insert(25);
	bt.insert(125);
	bt.insert(15);
	bt.insert(40);
	bt.insert(115);
	bt.insert(140);
	int elements6[] = {15,40,115,140};
	checkLeaves(bt,elements6);

	System.out.println("\n\nTest finalizado correctamente.");
    }


    static int[] iteratorElements(BinTree<Integer> t, Iterator<Integer> it) {
	ArrayList<Integer> aList = new ArrayList<Integer>();
	
	while (it.hasNext()) {
	    try {
		Integer i = it.next();
		if (i == null) {
		    System.out.println
			("*** Error: leaf iterator returns null element "+
			 "but no null elements have been stored in the "+
			 "array");
		    System.out.println
			("\nTree:\n"+printTree(t)+"\n");
		    throw new Error();
		}
		aList.add(i);
	    } catch (NoSuchElementException exc) {
		System.out.println
		    ("*** Error: hasNext() returned true but next() "+
		     "raised an exception??");
		System.out.println
		    ("Iterator elements generated "+printArrayList(aList)+"\n");
		exc.printStackTrace();
		System.out.println
		    ("\nTree:\n"+printTree(t)+"\n");
		throw new Error();
	    } catch (Exception exc) {
		System.out.println
		    ("*** Error: next() "+
		     "raised an exception??");
		System.out.println
		    ("Iterator elements generated "+printArrayList(aList)+"\n");
		exc.printStackTrace();
		System.out.println
		    ("\nTree:\n"+printTree(t)+"\n");
		throw new Error();
	    }
	}
	
	int[] retArray = new int[aList.size()];
	for (int i=0; i<aList.size(); i++)
	    retArray[i] = aList.get(i);
	return retArray;
    }

    static void checkAllElements(BinTree<Integer> t, int elements[]) {
	int[] tree_elements = new int[t.size()];
	int i=0;
	for (Position<Integer> p : t.positions()) {
	    if (p.element() == null) {
		System.out.println
		    ("*** Error: position iterator returns null element "+
		     "but no null elements have been stored in the "+
		     "array");
		System.out.println
		    ("\nTree:\n"+printTree(t)+"\n");
		throw new Error();
	    }
	    tree_elements[i++] = p.element();
	}
	checkElements("Tree has",t,true,elements,tree_elements);
    }

    static void checkLeaves(BinTree<Integer> t, int elements[]) {
	checkElements("Leaves iterator returns",t,false,elements,
		      iteratorElements(t,t.leaves()));
    }

    static void checkElements(String msg, BinTree<Integer> t, boolean sort, int elements[], int[] tree_elements) {
	if (sort) {
	    Arrays.sort(elements);
	    Arrays.sort(tree_elements);
	}
	checkArrayEqual(msg,t,elements,tree_elements);
    }

    static void checkArrayEqual(String msg, BinTree<Integer> bt, int[] arr, int[] tree_arr) {
	if (arr.length != tree_arr.length) {
		System.out.println
		    (msg+" elements "+printArray(tree_arr)+
		     " but should have elements "+printArray(arr));
		System.out.println
		    ("Check that the tree is correct.\n");
		System.out.println
		    ("\nTree:\n"+printTree(bt)+"\n");
		throw new Error();
	}

	for (int i=0; i<arr.length; i++) {
	    if (arr[i] != tree_arr[i]) {
		System.out.println
		    (msg+" elements "+printArray(tree_arr)+
		     " but should have elements "+printArray(arr));
		System.out.println
		    ("Check that the tree is correct.\n");
		System.out.println
		    ("\nTree:\n"+printTree(bt)+"\n");
		throw new Error();
	    }
	}
    }

    static String printArray(int[] arr) {
	String ret = "";
	for (int elem : arr)
	    ret += (ret=="" ? elem : "," + elem);
	return ret;
    }

    static String printArrayList(ArrayList<Integer> arr) {
	String ret = "";
	for (int elem : arr)
	    ret += (ret=="" ? elem : "," + elem);
	return ret;
    }

    //----------------------------------------------------------------------
    /**
     * Return a string representation of the tree argument.
     */
    public static <E> String printTree(BinTree<E> bt) {
	if (bt.isEmpty()) return "<<<empty>>>";

	String treeRep = "";
	for (String level : printTreeLevels(bt, bt.root())) {
		treeRep += addEdges(level) + "\n" + level + "\n";
	}
	return treeRep;
    }
	
    private static String addEdges(String level) {
	StringBuffer ret = new StringBuffer();
	boolean atNode = false;
	for (char ch : level.toCharArray()) {
	    if (ch != ' ' && ch != '-') {
		if (!atNode) {
		    ret.append('|');
		    atNode = true;
		} else ret.append(' ');
	    } else { 
		ret.append(' ');
		atNode = false;
	    }
	}
	return ret.toString();
    }

    private static <E> String[] printTreeLevels(BinTree<E> t, Position<E> p) {
	if (t.isEmpty()) return new String[0];

	String[] levels = new String[height(t,p)];
	for (int i=0; i<levels.length;i++)
	    levels[i]="";

	String pStr;
	if (p.element() == null) 
	    pStr = "null?";
	else 
	    pStr = p.element().toString();

	if (t.isExternal(p)) {
	    levels[0] = pStr;
	} else {
	    String[] tLeft; 
	    String[] tRight;

	    if (t.hasLeft(p)) tLeft = printTreeLevels(t,t.left(p));
	    else tLeft = new String[0];

	    if (t.hasRight(p)) tRight = printTreeLevels(t,t.right(p));
	    else tRight = new String[0];

	    int maxLeft=0;
	    for (int i=0; i<tLeft.length; i++) {
		maxLeft = Math.max(maxLeft,tLeft[i].length());
	    }
	    
	    int beginningRight;
	    if (maxLeft>0) beginningRight=maxLeft+2+pStr.length()+2;
	    else beginningRight=pStr.length()+2;

	    if (tLeft.length > 0) {
		int firstLeft = lastNonSpace(tLeft[0]);
		levels[0] =
		    indent(' ',firstLeft-1)+
		    indent('-',maxLeft-firstLeft+2)+
		    pStr;
	    } else levels[0] = pStr;

	    if (tRight.length > 0) {
		int firstRight = firstNonSpace(tRight[0]);
		levels[0] =
		    levels[0] + indent('-',firstRight+3);
	    }

	    for (int i=0; i<tLeft.length || i<tRight.length; i++) {
		if (i<tLeft.length) 
		    levels[i+1] += tLeft[i];
		if (i<tRight.length) {
		    levels[i+1] +=
			indent(' ',beginningRight-levels[i+1].length())+
			tRight[i];
		}
	    }
	}
	return levels;
    }

    private static int lastNonSpace(String s) {
	char chars[] = s.toCharArray();

	for (int i=chars.length-1; i>=0; i--)
	    if (chars[i] != ' ' && chars[i] != '-') return i;
	return 0;
    }

    private static int firstNonSpace(String s) {
	char chars[] = s.toCharArray();

	for (int i=0; i<chars.length; i++)
	    if (chars[i] != ' ' && chars[i] != '-') return i;
	return chars.length;
    }

    private static <E> int height(BinTree<E> t, Position<E> p) {
	int heightLeft = 0;
	int heightRight = 0;

	if (t.hasLeft(p)) heightLeft = height(t, t.left(p));
	if (t.hasRight(p)) heightRight = height(t, t.right(p));

	return Math.max(heightLeft,heightRight)+1;
    }
    
    private static String indent(char ch,int n) {
	if (n>0) {
	    char[] chars = new char[n];
	    for (int i=0; i<n; i++) chars[i]=ch;
	    return new String(chars);
	} else return "";
    }
}

