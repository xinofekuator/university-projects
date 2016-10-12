package extendedNodePositionList;

import net.datastructures.Position;
import net.datastructures.PositionList;
import net.datastructures.NodePositionList;
import net.datastructures.BoundaryViolationException;

/**
 * This class extends the <code>NodePositionList</code> class with
 * two new methods:
 * <code>nth</code> (access the nth node of a list)
 * and <code>fairMerge</code> (merge two lists into a new list).
 * You may add new methods and variables if you so wish.
 */
public class ExtendedNodePositionList<E> 
    extends NodePositionList<E> 
    implements PositionList<E>
{
    public ExtendedNodePositionList() {
	// Don't change this definition
	super();
    }

    /**
     * Return the <code>n</code>th node in the position list,
     * where the indices in the list are numbered 1,2,3,...
     * If there are too few nodes in the list the exception
     * <code>BoundaryViolationException</code> should be thrown.
     * Examples using a list containing nodes with elements 1,2,5,4,5:<p>
     * nth(1) should return the node containing 1<br>
     * nth(3) should return the node containing 5<br>
     * nth(6) should throw an exception BoundaryViolationException.
     * <p>You may assume that n is a positive integer.
     */
    public Position<E> nth(int n) throws BoundaryViolationException {
    	if (n>numElts)
    		throw new BoundaryViolationException("La posición pedida " +
    				"está fuera de rango");
    	else{
    		Position<E> resultado=this.first();
    		boolean encontrado=false;
    		for(int i=1;i<=numElts && !encontrado;i++){
    			if(n==i)
    				encontrado=true;
    			else
    				resultado=this.next(resultado);
    		}
    		return resultado;
    	}
    }

    /**
     * Returns a <strong>new</strong> list containing a "fair" merge of
     * the object list (accessible through <code>this</code>) and the argument
     * <code>otherList</code>.
     *
     * The resulting list contains the elements in
     * <code>this</code> alternating with elements from <code>otherList</code>,
     * beginning with an element from <code>this</code>,
     * and respecting the order of elements in <code>this</code> 
     * and <code>otherList</code>. <p>
     * That is, the first element of resulting list 
     * is the first element of <code>this</code>, the second element
     * is the first element of <code>otherList</code>, the third
     * element is the second element of <code>this</code>, the fourth
     * element is the second element of <code>otherList</code>, the
     * fifth element is the third element of <code>this</code>, and so on.
     * 
     * <p>Examples:
     * given a list <code>l1</code> with the elements 1,2,3
     * and another list <code>l2</code> with the elements -1,-2,-3,-4,-5
     * then:<br>calling <code>l1.fairMerge(l2)</code> returns a
     * new list 1,-1,2,-2,3,-3,-4,-5;<br>calling
     * <code>l2.fairMerge(l1)</code> returns a new list
     * -1,1,-2,2,-3,3,-4,-5.<p>
     * Note that the argument lists should not
     * be changed by calling <code>fairMerge</code>.
     * Example:</br>
     * if <code>l1</code> contains the element 1 and
     * <code>l2</code> contains the element -2 then
     * <code>l1.fairMerge(l2)</code> returns the new list 1,-2 and afterwards
     * <code>l1</code> still contains the single element 1 and 
     * <code>l2</code> still contains the single element -2.
     *<p>It can be assumed that otherList is not null.
     */
    public ExtendedNodePositionList<E> fairMerge(PositionList<E> otherList) {
	ExtendedNodePositionList<E> mergedList =
	    new ExtendedNodePositionList<E>();
	ExtendedNodePositionList<E> list2=
			(ExtendedNodePositionList<E>)otherList;
	Position<E> p1=this.first();
	Position<E> p2=otherList.first();
	int size1=this.size();
	int size2=otherList.size();
	boolean turno=false;
	
	for(int i=1;i<=this.numElts + otherList.size();i++){
		try{
			if(i==1)
				mergedList.addFirst(this.nth(i).element());
			else{
				if()
					mergedList.addAfter(mergedList.nth(i/2),list2.nth(i/2).element());				

				else
					mergedList.addAfter(mergedList.nth(i%2+i/2),this.nth(i%2+i/2).element());

			}
		}
		catch(BoundaryViolationException("")){
			
		}
	// Add new code below, but do return mergedList
	
	return mergedList;
    }
}
}
