package com.Alex.RedBlackTree;

public class Node {
	double value;
	boolean isRed;
	Node parent;
	Node left;
	Node right;
	int layer;
	int time;
	boolean isReal;
	
	public Node(double i){
		layer=0;
		time=1;
		value=i;
		isRed=true;
		parent=left=right=null;
	}
}
