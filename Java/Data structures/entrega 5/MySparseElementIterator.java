package positionListIterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

import net.datastructures.Position;
import net.datastructures.PositionList;

/**
 *  A simple iterator class for lists, which skips null elements.  
 * The non-null elements of a list are
 *  returned by this iterator.  No copy of the list is made, so any
 *  changes to the list are reflected in the iterator.
 *
 *  @author Michael Goodrich, Eric Zamore, Roberto Tamassia
 * Changes by Lars-Ake Fredlund
 */
public class MySparseElementIterator<E> implements Iterator<E> {
  protected PositionList<E> list; // the underlying list
  protected Position<E> cursor; // the next position

  /** Creates an element iterator over the given list. */
  public MySparseElementIterator(PositionList<E> L) {
    list = L;
    if(list.isEmpty())
    	cursor=null;
    else { 
       cursor = list.first();
       while (cursor.element()==null)
    	 if (cursor == list.last()) {cursor = null; return;}
    	 else cursor = list.next(cursor);
    	}
  }

  /** Returns whether the iterator has a non-null next object. */ 
  public boolean hasNext() { 
	  return (cursor != null);  
	  }

  /** Returns the next non-null object in the iterator. */
  public E next() throws NoSuchElementException {
    if (list.isEmpty() )
      throw new NoSuchElementException("No next element");
   	E toReturn = cursor.element();
      
    if(cursor == list.last()) {cursor = null; return toReturn;}
    else {		
    	cursor = list.next(cursor);
        while (cursor.element()==null)
       	 if (cursor == list.last()) {cursor = null; return toReturn;}
       	 else cursor = list.next(cursor);
       	}
    return toReturn;
  }

  /** Does not need to be implemented. */
  public void remove() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("remove");
  }
  }


