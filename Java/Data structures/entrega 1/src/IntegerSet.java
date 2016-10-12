package integerSet;

/**
 * This class provides methods to interact with a set of integers
 * implemented using a normal array of integers.
 * You <b>should</b> at least modify the
 * methods <code>member</code> (is an integer in the set) 
 * and <code>insert</code> (add a new integer to the set).
 * You may add new methods if you so wish.
 */
public class IntegerSet {
    // You may define new variables here if needed

    /**
     * Returns <code>true</code> if the array <code>setArray</code>
     * contains <code>element</code>.
     * <p>
     * This method <strong>must</strong> be implemented using
     * <em>recursion</em>. That is, you are not allowed to use
     * iterators, <code>for</code> loops, <code>while</code> loops
     * and similar techniques to implement member, rather you should
     * call a method defined by you recursively.
     * <p>
     * Note that the <code>null</code>
     * reference indicates that the array does not contain an element
     * at that position.
     */

	public static boolean member2(int element, Integer setArray[],int indice) {
		if (indice<=setArray.length-1){	
			if(setArray[indice]!=null && setArray[indice]==element)
				return true;
			else
				return member2(element,setArray,++indice);	
		}
		else
			return false;
	}

	public static boolean member(int element, Integer setArray[]) {
    	if (setArray==null || setArray.length==0)
    		return false;
    	else
    		return member2(element,setArray,0);
    	}

    /**
     * Inserts the integer <code>element</code> 
     * in the <code>setArray</code> array argument, if it not
     * already present (and if there is space to insert it).
     * Integers can be inserted in array cells which contain <code>null</code>.
     * <p>
     * Returns <code>true</code> if the element was inserted,
     * and <code>false</code> otherwise (the integer was already in
     * the array, or there is no space in the array).
     * <p>
     * This method <strong>must</strong> be implemented <em>without</em>
     * using recursion. That is, you should use a <code>for</code> loop, 
     * or a <code>while</code> loop to implement insert, you should
     * not use recursion nor should you use iterators.
     * <p>
     * You <em>may</em> however call your method
     * <code>member</code> to check whether the array
     * already contains the element to add.
     */
    public static boolean insert(int element, Integer setArray[]) {
    	boolean resultado=false;
    	if(!member(element,setArray))
    		for(int i=0;i<=setArray.length-1 && !resultado;i++){
    			if(setArray[i]==null){
    			setArray[i]=element;
    			resultado=true;
    		}
    	}
    	return resultado;
    }
}




