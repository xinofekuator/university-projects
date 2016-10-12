package binaryTreesWithDelete;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
    public int compare(Node n1, Node n2) {
	return n1.number() - n2.number();
    }
}
