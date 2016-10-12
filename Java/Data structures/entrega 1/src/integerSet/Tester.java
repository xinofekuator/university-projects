package integerSet;

/**
 * This class provides methods to test your implementation of
 * the <code>IntegerSet</code> implementation.
 */
public class Tester {
    /**
     * Runs the test suite.
     */
    public static void main(String args[]) {
	doTest();
    }

    /**
     * Executes the test suite.
     */
    public static void doTest() {
	Integer s0[] = {0,1,2,3,4};

	if (!IntegerSet.member(3,s0)) {
	    System.out.println("3 should be a member of {0,1,2,3,4}");
	    throw new Error();
	}

	if (IntegerSet.member(7,s0)) {
	    System.out.println("7 should not be a member of {0,1,2,3,4}");
	    throw new Error();
	}

	if (IntegerSet.insert(7,s0)) {
	    System.out.println
		("it should not be possible to insert 7 in {0,1,2,3,4}");
	    throw new Error();
	}

	if (IntegerSet.member(7,s0)) {
	    System.out.println("7 should not be a member of {0,1,2,3,4}");
	    throw new Error();
	}


	Integer s1[] = {4,1,2,null,3};

	if (!IntegerSet.member(4,s1)) {
	    System.out.println("4 should be a member of {4,1,2,null,3}");
	    throw new Error();
	}

	if (!IntegerSet.member(3,s1)) {
	    System.out.println("3 should be a member of {4,1,2,null,3}");
	    throw new Error();
	}

	if (IntegerSet.member(7,s1)) {
	    System.out.println("7 should not be a member of {4,1,2,null,3}");
	    throw new Error();
	}

	if (!IntegerSet.insert(7,s1)) {
	    System.out.println
		("it should be possible to insert 7 in {4,1,2,null,3}");
	    throw new Error();
	}

	if (!IntegerSet.member(7,s1)) {
	    System.out.println("7 should be a member of {4,1,2,7,3}");
	    throw new Error();
	}

	if (IntegerSet.insert(7,s1)) {
	    System.out.println
		("it should not possible to insert 7 in {4,1,2,7,3}");
	    throw new Error();
	}

	if (!IntegerSet.member(7,s1)) {
	    System.out.println("7 should be a member of {4,1,2,7,3}");
	    throw new Error();
	}

	
	Integer s2[] = {};
	
	if (IntegerSet.member(1,s2)) {
	    System.out.println("1 should not be a member of {}");
	    throw new Error();
	}

	if (IntegerSet.insert(1,s2)) {
	    System.out.println
		("it should not possible to insert 1 in {}");
	    throw new Error();
	}

	if (IntegerSet.member(1,s2)) {
	    System.out.println("1 should not be a member of {}");
	    throw new Error();
	}

	Integer s3[] = {null};
	
	if (IntegerSet.member(-1,s3)) {
	    System.out.println("-1 should not be a member of {null}");
	    throw new Error();
	}

	if (!IntegerSet.insert(-1,s3)) {
	    System.out.println
		("it should be possible to insert -1 in {null}");
	    throw new Error();
	}

	if (!IntegerSet.member(-1,s3)) {
	    System.out.println("-1 should be a member of {-1}");
	    throw new Error();
	}
	System.out.println("Test finalizado correctamente.");
    }
}
