package positionListIterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

import net.datastructures.Position;
import net.datastructures.PositionList;

/**
 *  A simple iterator class for lists.  The elements of a list are
 *  returned by this iterator.  No copy of the list is made, so any
 *  changes to the list are reflected in the iterator.
 *
 *  @author Michael Goodrich, Eric Zamore, Roberto Tamassia
 * Changes by Lars-Ake Fredlund
 */
public class MyElementIterator<E> implements Iterator<E> {
  protected PositionList<E> list; // the underlying list
  protected Position<E> cursor; // the next position
  private boolean nextUsed = false;

  /** Creates an element iterator over the given list. */
  public MyElementIterator(PositionList<E> L) {
    list = L;
    cursor = (list.isEmpty())? null : list.first();
  }

  /** Returns whether the iterator has a next object. */
  public boolean hasNext() { return (cursor != null);  }

  /** Returns the next object in the iterator. */
  public E next() throws NoSuchElementException {
    if (cursor == null)
      throw new NoSuchElementException("No next element");
    E toReturn = cursor.element();
    cursor = (cursor == list.last())? null : list.next(cursor);
    nextUsed=true;
    return toReturn;
  }

  /** Removes the node containing the last element returned by 
   * calling <code>next</code>.
   **/
  public void remove() throws IllegalStateException {
	  if (!nextUsed) 
		  throw new IllegalStateException("remove");
	  if(cursor==null)
		  list.remove(list.last());
	  else{
	  list.remove(list.prev(cursor));
	  nextUsed=false;
	  }
	  }
}
