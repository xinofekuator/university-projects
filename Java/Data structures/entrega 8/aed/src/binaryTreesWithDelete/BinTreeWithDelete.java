package binaryTreesWithDelete;

import java.util.Iterator;
import java.util.Comparator;
import net.datastructures.LinkedBinaryTree;
import net.datastructures.Position;
import net.datastructures.PositionList;
import net.datastructures.NodePositionList;;

/**
 * Implements a binary tree with a delete method.
 * <p><strong>YOU SHOULD MODIFY THIS CLASS.</strong>
 */
public class BinTreeWithDelete<E> extends LinkedBinaryTree<E> {
    /**
     *  Use this comparator to compare elements of type <code>E</code>.
     */
    public Comparator<E> cmp;

    /**
     * Create a binary tree.
     */
    public BinTreeWithDelete(Comparator<E> cmp) {
	this.cmp = cmp;
    }

     /**
      * Returns a list of all elements in the tree which compare
      * equal to <code>element</code>
      * <strong>using the comparator <code>cmp</code></strong>.
     * <p>MODIFY THE BODY OF THIS METHOD.
      */
     public PositionList<E> findAll(E element) {
    	 boolean terminado=false;
    	 Position<E> aux=this.root();
    	 PositionList<E> resultado=new NodePositionList<E>();
    	 while(!this.isEmpty() && !terminado){
    		 if(this.cmp.compare(element, aux.element())<0){
    			 if(this.hasLeft(aux))
    				aux=this.left(aux);
    			 else
    				 terminado=true;
    		 }
    		 else {
    			 if(this.cmp.compare(element, aux.element())==0)
    				 resultado.addLast(aux.element());
    			 if(this.hasRight(aux))
    				 aux=this.right(aux);
    			 else
    				 terminado=true;
    		 } 
    	 }
    	 return resultado;
     }

    /**
     * Deletes an arbitrary node from an ordered binary tree.
     * After deletion, the tree should still be ordered.
     * <p>The ordering condition states that
     * for an internal node, its element compares greater than
     * all elements in the left subtree, and the element compares
     * less or equal to all elements in the right subtree.
     * <p>MODIFY THE BODY OF THIS METHOD.
     */
     public void delete(Position<E> pos) {
    	 if(!this.isEmpty() && this.hasLeft(pos)&& this.hasRight(pos)){
    		 Position<E> aux=elementoABorrar(pos);
    		 remove(aux);
    		 replace(pos,aux.element());
    		 }	 
    	 else{
    		 this.remove(pos);
    	 }
     }
     
     //PRE: The argument has two children.
     private Position<E> elementoABorrar(Position<E> pos){
    	 boolean terminado=false;
    	 Position<E> aux=this.right(pos);
    	 while(!this.isEmpty() && !terminado){
    		 if(this.hasLeft(aux))
    			 aux=this.left(aux);
    		 else
    			 terminado=true;
    	 }
    	 return aux;
     }

}
