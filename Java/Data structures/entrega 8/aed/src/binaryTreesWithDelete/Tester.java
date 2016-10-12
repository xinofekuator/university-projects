package binaryTreesWithDelete;

import net.datastructures.LinkedBinaryTree;
import net.datastructures.Position;
import net.datastructures.PositionList;

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

	System.out.println
	    ("\nTesting findAll.....");
	System.out.println
	    ("  [We will insert elements {int,string} and order them\n"+
	     "   only using the integer component]\n\n");

	BinTreeWithDelete<Node> bt;

	String pathSpec1[][] =
	    {
		{"","10","a"},
		{"l","5","b"}
	    };
	bt = createTree(pathSpec1);
	String answers1[] = {"a"};
	check_findAll(10,answers1,bt);

	String answers1b[] = {};
	check_findAll(20,answers1b,bt);

	String pathSpec2[][] =
	    {
		{"","10","a"},
		{"l","5","b"},
		{"r","10","c"}
	    };
	bt = createTree(pathSpec2);
	String answers2[] = {"c","a"};
	check_findAll(10,answers2,bt);

	String pathSpec3[][] =
	    {
		{"","10","a"},
		{"ll","4","b"},
		{"l","5","c"},
		{"r","10","d"}
	    };
	bt = createTree(pathSpec3);
	String answers3[] = {"d","a"};
	check_findAll(10,answers3,bt);

	String answers3b[] = {"b"};
	check_findAll(4,answers3b,bt);

	String answers3c[] = {"c"};
	check_findAll(5,answers3c,bt);

	String pathSpec4[][] =
	    {
		{"","11","a"},
		{"ll","4","b"},
		{"l","5","c"},
		{"r","13","d"},
		{"rl","11","e"},
		{"rlr","11","f"},
		{"rlrr","11","g"},
		{"rlrrr","11","h"},
		{"rr","75","i"},
		{"rrl","65","j"}
	    };
	bt = createTree(pathSpec4);
	String answers4[] = {"a","h","g","f","e"};
	check_findAll(11,answers4,bt);

	String answers4b[] = {"j"};
	check_findAll(65,answers4b,bt);



	String pathSpec5[][] =
	    {
		{"","11","a"},
		{"r","15","b"},
		{"rl","13","c"},
		{"rll","11","d"}
	    };
	bt = createTree(pathSpec5);
	String answers5[] = {"a","d"};
	check_findAll(11,answers5,bt);


	String pathSpec6[][] =
	    {
		{"","11","a"},
		{"r","15","b"},
		{"rl","13","c"},
		{"rll","12","d"}
	    };
	bt = createTree(pathSpec6);
	String answers6[] = {"a"};
	check_findAll(11,answers6,bt);


	System.out.println("\nTesting delete...\n");


	BinTreeWithDelete<Node> orig;

	String pathSpec_d_1[][] =
	{
	    {"","11","a"}
	};
	bt = createTree(pathSpec_d_1);
	orig = createTree(pathSpec_d_1);
	deleteNode("",orig,bt);

	String pathSpec_d_2[][] =
	{
	    {"","11","a"},
	    {"l","5","c"},
	    {"r","13","d"},
	};
	bt = createTree(pathSpec_d_2);
	orig = createTree(pathSpec_d_2);
	deleteNode("",orig,bt);

	String pathSpec_d_3[][] =
	{
	    {"","11","a"},
	    {"r","13","d"},
	    {"l","5","c"},
	    {"ll","4","b"},
	    {"rl","11","e"}
	};
	bt = createTree(pathSpec_d_3);
	orig = createTree(pathSpec_d_3);
	deleteNode("",orig,bt);

	bt = createTree(pathSpec_d_3);
	orig = createTree(pathSpec_d_3);
	deleteNode("r",orig,bt);

	bt = createTree(pathSpec_d_3);
	orig = createTree(pathSpec_d_3);
	deleteNode("l",orig,bt);

	String pathSpec_d_4[][] =
	    {
		{"","11","a"},
		{"l","5","c"},
		{"r","20","b"},
		{"lr","5","d"},
		{"ll","2","e"},
		{"rl","15","f"},
		{"rr","30","g"},
		{"rll","12","h"},
		{"rlr","17","i"}
	    };

	bt = createTree(pathSpec_d_4);
	orig = createTree(pathSpec_d_4);
	deleteNode("",orig,bt);

	String pathSpec_d_5[][] =
	    {
		{"","11","a"},
		{"ll","4","b"},
		{"l","5","c"},
		{"r","13","d"},
		{"rl","11","e"},
		{"rlr","11","f"},
		{"rlrr","11","g"},
		{"rlrrr","11","h"},
		{"rr","75","i"},
		{"rrl","65","j"}
	    };
	bt = createTree(pathSpec_d_5);
	orig = createTree(pathSpec_d_5);
	deleteNode("rlr",orig,bt);

	bt = createTree(pathSpec_d_5);
	orig = createTree(pathSpec_d_5);
	deleteNode("",orig,bt);

	bt = createTree(pathSpec_d_5);
	orig = createTree(pathSpec_d_5);
	deleteNode("r",orig,bt);

	bt = createTree(pathSpec_d_5);
	orig = createTree(pathSpec_d_5);
	deleteNode("l",orig,bt);

	bt = createTree(pathSpec_d_5);
	orig = createTree(pathSpec_d_5);
	deleteNode("rl",orig,bt);
	
	System.out.println("\n\nTest finalizado correctamente.");
    }

    //----------------------------------------------------------------------
    static void check_findAll(int number, String[] answers, BinTreeWithDelete<Node> t) {
	Node n = new Node("",number,"");
	PositionList<Node> p = t.findAll(n);

	if (p == null) {
	    System.out.println("findAll({"+number+",*}) returned null?");
	    throw new Error();
	}

	if (p.size() > answers.length) {
	    System.out.println
		("The number of elements returned from findAll("+
		 "{"+number+",*})="+
		 p.size()+
		 " (list of elements returned: "+node_elements(p)+")"+
		 " is different from the number of elements expected: "+
		 answers.length);
	    System.out.println("\nTree:\n"+printTree(t)+"\n");
	    throw new Error();
	}

	for (String answer : answers) {
	    boolean found = false;
	    Iterator<Node> p_it = p.iterator();

	    while (p_it.hasNext() && !found) {
		Node node = p_it.next();
		if (node.id().equals(answer)) found=true;
	    }

	    if (!found) {
		System.out.println
		    ("The expected element "+(new Node("",number,answer))+
		     " was not found in the PositionList returned by "+
		     "findAll({"+number+",*}): "+
		     node_elements(p));
		System.out.println("\nTree:\n"+printTree(t)+"\n");
		throw new Error();
	    }
		
	}
    }

    static String node_elements(PositionList<Node> p) {
	if (p.isEmpty()) return "<<<empty>>>";
	String ret = "";
	
	for (Node node : p) {
	    ret += (ret.equals("") ? node : ","+node);
	}
	return ret;
    }

    //----------------------------------------------------------------------
    static void deleteNode(String path, BinTreeWithDelete<Node> orig, BinTreeWithDelete<Node> t) {
	int size = t.size();
	Position<Node> pos = followPath(path,t);
	Node deleted = pos.element();
	try {
	    t.delete(pos);
	    if (!isWellFormed(t)) {
		System.out.println
		    ("After deleting "+deleted+
		     " from the tree\n"+printTree(orig)+
		     "\nthe resulting tree\n"+printTree(t)+
		     "\nis not well-formed\n");
		throw new Error();
	    }
	    checkAllElements(t,orig,deleted);
	}
	catch (Exception exc) {
	    System.out.println
		("Deleting the node "+pos.element()+
		 " raises an exception "+exc);
	    exc.printStackTrace();
	    throw new Error();
	}
    }

    static <E> Position<E> followPath(String path, BinTreeWithDelete<E> t) {
	Position<E> pos = t.root();

	for (byte dir : path.getBytes()) {
	    if (dir == 'l') {
		if (t.hasLeft(pos)) pos = t.left(pos);
		else {
		    System.out.println
			("Cannot move to the left for pathspec "+
			 path+
			 " pos().element="+pos.element());
			System.out.println("\nTree:\n"+printTree(t)+"\n");
			throw new Error();
		}
	    }
	    else if (dir == 'r') {
		if (t.hasRight(pos)) pos = t.right(pos);
		else {
		    System.out.println
			("Cannot move to the right for pathspec "+
			 path+
			 " pos().element="+pos.element());
		    System.out.println("\nTree:\n"+printTree(t)+"\n");
		    throw new Error();
		}
	    } else {
		System.out.println("Incorrect path spec "+path);
		throw new Error();
	    }
	}
	return pos;
    }

    //----------------------------------------------------------------------
    static void checkAllElements(BinTreeWithDelete<Node> t, BinTreeWithDelete<Node> orig, Node deleted) {
	if (t.size() != orig.size()-1) {
	    System.out.println
		("The tree\n"+printTree(t)+"\nhas "+t.size()+" elements "+
		 "after calling delete on the tree\n"+printTree(orig)+
		 " but it should have "+(orig.size()-1)+
		 " elements");
	    throw new Error();
	}

	Iterator<Node> it_orig = orig.iterator();
	while (it_orig.hasNext()) {
	    String origId = it_orig.next().id();
	    Iterator<Node> it_t = t.iterator();
	    boolean found =
		deleted.id().equals(origId) ? true : false;

	    while (it_t.hasNext() && !found) {
		if (it_t.next().id() == origId)
		    found=true;
	    }

	    if (!found) {
		System.out.println
		    ("After deleting "+deleted+
		     " from the tree\n"+printTree(orig)+
		     "\nthe new tree should contain an element "+
		     "{_,"+origId+"} but it is missing in:\n"+
		     printTree(t)+"\n");
		throw new Error();
	    }
	}
    }

    static String printArray(String[] arr) {
	String ret = "";
	for (String elem : arr)
	    ret += (ret=="" ? elem : "," + elem);
	return ret;
    }

    //----------------------------------------------------------------------
    public static BinTreeWithDelete<Node> createTree(String[][] pathSpec) {
	Node[] nodes = new Node[pathSpec.length];

	for (int i=0; i<pathSpec.length; i++)
	    for (int j=i+1; j<pathSpec.length; j++)
		if (pathSpec[i][2]==pathSpec[j][2]) {
		    System.out.println
			("*** Internal error in tester: "+
			 "node ids "+pathSpec[i][2]+" and "+pathSpec[j][2]+
			 " are the same");
		    throw new Error();
		}

	for (int i=0; i<pathSpec.length; i++) 
	    nodes[i] =
		new Node(pathSpec[i][0],
			 Integer.parseInt(pathSpec[i][1]),
			 pathSpec[i][2]);
	Arrays.sort(nodes, new PathComparator());

	BinTreeWithDelete<Node> t =
	    new BinTreeWithDelete<Node>(new NodeComparator());
	for (Node n : nodes)
	    add_node(n,t);

	checkMustBeWellFormed(t);
	return t;
    }

    private static void add_node(Node n, BinTreeWithDelete<Node> t) {
	byte[] path = n.path().getBytes();
	
	if (path.length == 0) {
	    if (t.isEmpty()) {
		t.addRoot(n);
	    } else {
		System.out.println("Adding root twice: n="+n.number());
		throw new Error();
	    }
	} else {
	    Position<Node> pos = t.root();
	    int i = 0;

	    while (i < path.length-1) {
		if (path[i] == 'l') {
		    if (t.hasLeft(pos)) pos = t.left(pos);
		    else {
			System.out.println
			    ("Cannot move to the left for pathspec "+
			     n.path()+
			     " at index "+i+
			     " pos().element="+pos.element());
			System.out.println("\nTree:\n"+printTree(t)+"\n");
			throw new Error();
		    }
		}
		else if (path[i] == 'r') {
		    if (t.hasRight(pos)) pos = t.right(pos);
		    else {
			System.out.println
			    ("Cannot move to the right for pathspec "+
			     n.path()+
			     " at index "+
			     " pos().element="+pos.element());
			System.out.println("\nTree:\n"+printTree(t)+"\n");
			throw new Error();
		    }
		} else {
		    System.out.println("Incorrect path spec "+n.path());
		    throw new Error();
		}
		i++;
	    }

	    if (path[i] == 'l') {
		if (t.hasLeft(pos)) {
		    System.out.println
			("Cannot add node to the left; already occupied "+
			 "for pathspec "+n.path()+
			 " pos().element="+pos.element());
		    System.out.println("\nTree:\n"+printTree(t)+"\n");
		    throw new Error();
		} else t.insertLeft(pos, n);
	    }
	    else if (path[i] == 'r') {
		if (t.hasRight(pos)) {
		    System.out.println
			("Cannot add node to the right; already occupied "+
			 "for pathspec "+n.path()+
			 " pos().element="+pos.element());
		    System.out.println("\nTree:\n"+printTree(t)+"\n");
		    throw new Error();
		} else t.insertRight(pos, n);
	    } else {
		System.out.println("Incorrect path spec "+n.path());
		throw new Error();
	    }
	}
    }
    

    //----------------------------------------------------------------------

    static <E> void checkMustBeWellFormed(BinTreeWithDelete<E> t) {
	if (!isWellFormed(t)) {
	    System.out.println
		("*** Internal error in tester: tree not well-formed\n"+
		 "Please inform the teacher.\n");
	    System.out.println("\nTree:\n"+printTree(t)+"\n");
	    throw new Error();
	}
    }

     static <E> boolean isWellFormed(BinTreeWithDelete<E> t) {
	 if (t.isEmpty()) return true;
	 else return isWellFormed(t,t.root());
     }

    static <E> boolean isWellFormed(BinTreeWithDelete<E> t, Position<E> pos) {
	 if (t.hasLeft(pos)) {
	     E maxLeft = max(t,t.left(pos));
	     if (t.cmp.compare(maxLeft,pos.element()) >= 0) {
		 System.out.println
		     ("*** Error: the maximum element "+maxLeft+
		      " in the left subtree is greater or equal to the node "+
		      pos.element()+"\n");
		 return false;
	     }
	 }

	 if (t.hasRight(pos)) {
	     E minRight = min(t,t.right(pos));
	     if (t.cmp.compare(pos.element(),minRight) > 0) {
		 System.out.println
		     ("*** Error: the minimum element "+minRight+
		      " in the right subtree is less than the node "+
		      pos.element()+"\n");
		 return false;
	     }
	 }

	 boolean hasLeftRecursive =
	     t.hasLeft(pos) ? isWellFormed(t,t.left(pos)) : true;
	 boolean hasRightRecursive =
	     t.hasRight(pos) ? isWellFormed(t,t.right(pos)) : true;
	 
	 return hasLeftRecursive && hasRightRecursive;
     }

     static <E> E min(BinTreeWithDelete<E> t, Position<E> pos) {
	 if (t.hasLeft(pos)) {
	     return min(t,t.left(pos));
	 } else return pos.element();
     }

     static <E> E max(BinTreeWithDelete<E> t, Position<E> pos) {
	 if (t.hasRight(pos)) {
	     return max(t,t.right(pos));
	 } else return pos.element();
     }


    //----------------------------------------------------------------------
    /**
     * Return a string representation of the tree argument.
     */
    public static <E> String printTree(BinTreeWithDelete<E> bt) {
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

    private static <E> String[] printTreeLevels(BinTreeWithDelete<E> t, Position<E> p) {
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

    private static <E> int height(BinTreeWithDelete<E> t, Position<E> p) {
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

