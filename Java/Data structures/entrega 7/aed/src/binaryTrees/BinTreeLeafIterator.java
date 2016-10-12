package binaryTrees;

import java.util.Iterator;
import java.util.NoSuchElementException;
import net.datastructures.LinkedBinaryTree;
import net.datastructures.Position;

/**
 * Implements an iterator over the leaves of a binary tree.
 * <p><strong>YOU SHOULD MODIFY THIS CLASS.</strong>
 */
public class BinTreeLeafIterator<E> implements Iterator<E> {
	private LinkedBinaryTree<E> tree;
	private Position<E> actual=null;
	// YOU MAY ADD NEW (private) ATTRIBUTES IF YOU NEED

	/**
	 * Creates the iterator for the tree argument.
	 */
	public BinTreeLeafIterator(BinTree<E> tree) {
		this.tree = tree;
		if(!tree.isEmpty())
			actual=this.firstLeaf();

	}

	/**
	 * Returns true if the tree has an additional leaf, 
	 * otherwise false.
	 * <p>YOU SHOULD MODIFY THE BODY OF THIS FUNCTION.
	 */
	public boolean hasNext() {
		return actual!=null;		
	}

	/**
	 * Returns the next leaf element, or throws the exception
	 * NoSuchElementException if no such leaf element exists.
	 * <p>YOU SHOULD MODIFY THE BODY OF THIS FUNCTION.
	 */
	public E next() throws NoSuchElementException{
		Position<E> aux=actual;
		if (aux == null) 
			throw new NoSuchElementException();		
		this.avanzar();
		return aux.element();
	}

	// DO NOT MODIFY THIS FUNCTION 
	public void remove() {
		throw new UnsupportedOperationException();
	}
	//PRE:The tree is not empty
	private Position<E> firstLeaf(){
		Position<E> aux=null;
		if(tree.isEmpty())
			return aux;
		else
			aux=tree.root();
		while(this.tree.hasLeft(aux)){
			aux=tree.left(aux);
		}
		return aux;
	}
	private Position<E> nextLeaf(Position<E> p){
		Position<E> aux=p;
		while(this.tree.hasLeft(aux)){
			aux=tree.left(aux);
		}
		if(this.tree.isInternal(aux))
			while(this.tree.hasRight(aux)){
				aux=tree.right(aux);
			}
		return aux;
	}

	private void avanzar(){

		Position<E> aux=actual;
		boolean terminado=false;
		while(actual!=null && !terminado){
			if(this.tree.parent(aux)==this.tree.root()){
				if(this.tree.right(this.tree.root())==aux){
					actual=null;
					terminado=true;
				}
				else{
					if(this.tree.hasRight(this.tree.root())){
						actual=this.nextLeaf(this.tree.right(this.tree.root()));
						terminado=true;}
					else{
						actual=null;
						terminado=true;
					}		
				}					
			}
			else if(this.tree.hasRight(this.tree.parent(aux)) && this.tree.right(this.tree.parent(aux))==aux)			
				aux=this.tree.parent(aux);	

			else{
				if(this.tree.hasRight(this.tree.parent(aux))){
					actual=this.nextLeaf(this.tree.right(this.tree.parent(aux)));
					terminado=true;
				}	
				else
					aux=this.tree.parent(aux);
			}

		}
	}
}