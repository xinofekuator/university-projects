package binaryTrees;

import java.util.Iterator;
import java.util.Comparator;
import net.datastructures.LinkedBinaryTree;
import net.datastructures.Position;

/**
 * Implements a binary tree with an insert method and an iterator
 * over the leaves of the tree using the LinkedBinaryTree class.
 * <p><strong>YOU SHOULD MODIFY THIS CLASS.</strong>
 */
public class BinTree<E> extends LinkedBinaryTree<E> {
	private Comparator<E> cmp;

	/**
	 * Create a binary tree.
	 */
	public BinTree(Comparator<E> cmp) {
		this.cmp = cmp;
	}

	/**
	 * Inserts a new element into the binary tree.
	 * Each node in the tree contains an element.
	 * For an internal node, its element compares greater than
	 * all elements in the left subtree, and the element compares
	 * less or equal to all elements in the right subtree.
	 * <p>MODIFY THE BODY OF THIS METHOD.
	 */
	public void insert(E element) {
		if(this.isEmpty())
			this.addRoot(element);
		else{
			Position<E> actual=this.root();
			boolean terminado=false;
			while(!terminado ){
				if(this.cmp.compare(element,actual.element())>=0){
					if(this.hasRight(actual))
						actual=this.right(actual);
					else{
						this.insertRight(actual, element);
						terminado=true;
					}
				}
				else{
					if(this.hasLeft(actual))
						actual=this.left(actual);
					else{
						this.insertLeft(actual, element);
						terminado=true;
					}
				}

			}
		}
	}

	/**
	 * An iterator over the leaves of the tree.
	 * Do NOT modify this method, rather modify the
	 * implementation of the iterator in the file
	 * BinTreeLeafIterator.java.
	 */
	public Iterator<E> leaves() {
		return new BinTreeLeafIterator<E>(this);
	}
}
