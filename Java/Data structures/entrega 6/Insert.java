package comparadores;

import java.util.Comparator;

/**
 * Implements a method for inserting an element into a sorted array
 * <em>using a comparator</em>.
 * <p><strong>YOU SHOULD MODIFY THIS CLASS.</strong>
 */
public class Insert {

	/** Inserts an element <code>elem</code> 
	 * into the sorted element array <code>arr</code>,
	 * keeping the array sorted.
	 * The array is sorted in ascending order, i.e., an element e1
	 * that compares less than an element e2 
	 * (according to the comparator <code>cmp</code>,
	 * should occur before e2 in the array.
	 * <p>An empty array cell stores the <code>null</code> value.
	 * <p>
	 * To insert an element e1 in the array first search
	 * for the correct index in the array, i.e., the first cell
	 * whose contents is either <code>null</code> or an element e2
	 * such that <code>cmp(e2,e1)</code> returns an integer greater
	 * than zero.
	 * If that "correct" array cell
	 * is already occupied (i.e., it is not <code>null</code>),
	 * move that cell and the succeeding non-<code>null</code>
	 * array cells "to the right"
	 * in the array. 
	 * <p>
	 * You can assume that there is always free space in the array
	 * to insert the element. 
	 * <p>
	 * <p>Examples:<br><ul>
	 * <li>If <code>arr</code> is an array of Integers 
	 * <code>0,1,2,4,null,null</code> and <code>I</code> is the integer 3,
	 * then the call <code>insert(I,arr,cmp)</code> (where cmp is a
	 * standard comparator over integers) should
	 * modify the array <code>arr</code> so that it contains the values
	 * <code>0,1,2,3,4,null</code>.
	 * </li>
	 * <li>Similarly the call <code>insert(J,arr,cmp)</code> when <code>J</code>
	 * is 2 should 
	 * modify the array <code>arr</code> so that it contains the values
	 * <code>0,1,2,2,4,null</code>.
	 * </li>
	 * </ul>
	 * <p><strong> YOU SHOULD MODIFY THIS FUNCTION.</strong>
	 */
	public static <E> void insert(E elem, E[] arr, Comparator<E> cmp) {
		boolean terminado=false;
		for(int  i=0;i<=arr.length-1&&!terminado;i++){
			if((arr[i]==null)||cmp.compare(elem, arr[i])<0){
				recolocar(arr,i);
				arr[i]=elem;
				terminado=true;
			}
		}
	}


	private static <E> void recolocar(E[] arr,int pos){
		for (int i = arr.length-2; pos<=i ;i-- )
			arr[i+1]=arr[i];
	}
}
