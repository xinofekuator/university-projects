package iterateNodePositionList;

import java.util.Iterator;

import net.datastructures.PositionList;
import net.datastructures.NodePositionList;
import net.datastructures.BoundaryViolationException;

/**
 * This class extends the <code>NodePositionList</code> class with
 * two new methods:
 * <code>nth</code> (access the nth node of a list)
 * and <code>unique()</code> -- returns a new list where duplicate
 * elements of the object list have been removed.
 * You may add new methods and variables if you so wish.
 */
public class IterateNodePositionList<E> 
    extends NodePositionList<E> 
    implements PositionList<E>
{
    public IterateNodePositionList() {
	// Don't change this definition
	super();
    }

    /**
     * Return the element of the <code>n</code>th node in the position list,
     * where the indices in the list are numbered 1,2,3,...
     * as returned by the <code>iterator</code> method.
     * If there are too few nodes in the list the exception
     * <code>BoundaryViolationException</code> should be thrown.
     * Examples using a list containing nodes with elements 1,2,5,4,5:<p>
     * nth(1) should return the node containing 1<br>
     * nth(3) should return the node containing 5<br>
     * nth(6) should throw an exception BoundaryViolationException.
     * <p>You may assume that n is a positive integer.
     * <p><strong>It is mandatory to use an iterator in the 
     * implementation of <code>nth</code></strong>.
     * <p>You may not call <code>first()</code>,<code>last()</code>,
     * <code>next(p)</code> or <code>prev(p)</code> of the 
     * <code>PositionList</code> API. Rather, call the <code>iterator()</code>
     * method to return an iterator, and use that to traverse the list.
     * <p>The order (indices) of elements in the list are assumed to be
     * given by the <code>iterator()</code> method.
     */
    public E nth(int n) throws BoundaryViolationException {
    	if(n<=0 || n>this.size())
    		throw new BoundaryViolationException("La posición pedida está " +
    				"fuera de rango");
    	else{
    		Iterator<E> it=this.iterator();
    		E p=null;
    		int k=1;
    		while(it.hasNext()&& (k <= n)){
    			p=it.next();
    			k++;
    		}
    		return p;
    	}
    }

    /**
     * Returns a new list which is identical to the original list
     * except that duplicate elements have been removed. 
     * In case an element is duplicated, its first occurrence in 
     * the list should be preserved (i.e., the element
     * closest to the head of the list).
     *
     * <p>Examples:<br><ul>
     * <li>If <code>l</code> is 0,3,1,2,3  then the call <code>l.unique()</code>
     * should return the new list 0,3,1,2.</li>
     * <li>If <code>l</code> is the empty list then <code>l.unique()</code>
     * should of course return the empty list.</li>
     * <li>If <code>l</code> is 0,3,0,2,0 the call to <code>unique</code>
     * should return the list 0,3,2.
     * </li>
     * </ul>
     * <p>Use <code>e1.equals(e2)</code> to compare if two elements 
     * <code>E e1</code> and <code>E e2</code>
     * are equal.
     * <p>You should <strong>not modify</strong>
     * the original list.
     * <p><strong>It is mandatory to use iterators in the 
     * implementation of <code>unique</code></strong>.
     * <p>You may not call <code>first()</code>,<code>last()</code>,
     * <code>next(p)</code> or <code>prev(p)</code> of the 
     * <code>PositionList</code> API. Rather, call the <code>iterator()</code>
     * method to return an iterator, and use that to traverse the list.
     */
    public IterateNodePositionList<E> unique() {
    	IterateNodePositionList<E> l=new IterateNodePositionList<E>(); 
    	if(!this.isEmpty()){
    		Iterator<E> it=this.iterator();
    		E p=null;
    		while (it.hasNext()){
    			p = it.next();
    			if(!isContained(l,p))
    				l.addLast(p);
    		}
    	}
    	// Modify this method.
    	// Original list in this.
    	// Use e1.equals(e2) to compare if two elements
    	// E e1 and E e2 are equal.
    	return l;
    }

    private boolean isContained(IterateNodePositionList<E> l,E p){
    	boolean encontrado=false;
    	E p1 = null;
    	if(!l.isEmpty()){
    		Iterator<E> it=l.iterator();
    		while(it.hasNext()&& !encontrado){
    			p1 = it.next();
    			if(p1.equals(p))
    				encontrado=true;
    		}
    	}
    	return encontrado;
    }
}
