package binaryTreesWithDelete;

import java.util.Comparator;

public class PathComparator implements Comparator<Node> {
    public int compare(Node n1, Node n2) {
	int len_path_node1 = n1.path().length();
	int len_path_node2 = n2.path().length();
	
	if (len_path_node1 < len_path_node2) return -1;
	else if (len_path_node1 > len_path_node2) return 1;
	else {
	    byte[] s1 = n1.path().getBytes();
	    byte[] s2 = n2.path().getBytes();
	    for (int i=0; i<s1.length; i++)
		if (s1[i] < s2[i]) return -1;
		else if (s1[i] > s2[i]) return 1;
	    return 0;
	}
    }
}

