package moreExtendedNodePositionList;

import net.datastructures.Position;
import net.datastructures.PositionList;
import net.datastructures.NodePositionList;

/**
 * This class extends the <code>NodePositionList</code> class with
 * two new methods:
 * <code>reverse</code> -- returns a new list which is the reverse of the 
object list, 
 * and <code>unique()</code> -- returns a new list where duplicate
 * elements of the object list have been removed.
 * You may add new methods and variables if you so wish.
 */
public class MoreExtendedNodePositionList<E> 
extends NodePositionList<E> 
implements PositionList<E>
{
	public MoreExtendedNodePositionList() {
		// Don't change this definition
		super();
	}

	/**
	 * Returns a new list which is the reverse of
	 * the original list. 
	 * <p>Examples:<br><ul>
	 * <li><code>l.reverse()</code> when <code>l</code> is a list 1,2,3 should return a new list
	 * 3,2,1.</li>
	 * <li><code>l.reverse()</code> when <code>l</code> is a list 22,22 should return a new list
	 * 22,22.</li>
	 * <li><code>l.reverse()</code> when <code>l</code> is the empty list should return
	 * a new empty list.</ul>
	 * </ul>
	 * <p>You should <strong>not modify</strong>
	 * the original list.
	 */
	public MoreExtendedNodePositionList<E> reverse() {
		MoreExtendedNodePositionList<E> l=new MoreExtendedNodePositionList<E>(); 
		if(!this.isEmpty()){
			Position<E> p=this.first();
			while(l.size()<this.size()){
				l.addFirst(p.element());
				if(p!=this.last())
					p=this.next(p);
			}
		}
		return l;
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
	 */
	public MoreExtendedNodePositionList<E> unique() {
		MoreExtendedNodePositionList<E> l=new MoreExtendedNodePositionList<E>(); 
		if(!this.isEmpty()){
			Position<E> p=this.first();
			for(int i=1;i<=this.size();i++){
				if(!isContained(l,p))
					l.addLast(p.element());
				if(p!=this.last())
					p=this.next(p);
			}

		}
		return l;
	}
	private boolean isContained(MoreExtendedNodePositionList<E> l,Position<E> p){
		boolean encontrado=false;
		if(!l.isEmpty()){
			Position<E> p1=l.last();
			for(int i=1;i<=l.size()&&!encontrado;i++){			
				if(p.element().equals(p1.element()))
					encontrado=true;
				else
					if(p1!=l.first())
						p1=l.prev(p1);
			}
		}
		return encontrado;		

    }
}