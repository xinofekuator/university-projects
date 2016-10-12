package binaryTreesWithDelete;

public class Node {
    private String path;
    private int number; 
    private String id;

    public Node(String path, int number, String id) {
	this.path = path;
	this.number = number;
	this.id = id;
    }

    public String path() {
	return path;
    }

    public int number() {
	return number;
    }

    public String id() {
	return id;
    }

    public String toString() {
	return "{"+number()+","+id()+"}";
    }
}
