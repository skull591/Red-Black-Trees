package com.Alex.RedBlackTree;


public class Tree {
	Node root;
	
	public Tree(){
		root=null;
	}
	
	public void insert(double value) {
		Node newNode = new Node(value);
		newNode.isReal=true;
		Node now,lastNow;
		if (root==null) {
			root=newNode;
			newNode.parent=newNode;
			fakeNode(newNode);
			insertFixUp(newNode,newNode.parent);
			printall();
			System.out.println();
			return;
		}else {
			now=root;
		}
		boolean isLeft=false;
		lastNow=now;
		while (now.isReal) {
			lastNow=now;
			if (now.value>=value) {
				now=now.left;
				isLeft=true;
			}else {
				now=now.right;
				isLeft=false;
			}
		}
		newNode.parent=lastNow;
		if (isLeft) {
			lastNow.left=newNode;
		}else {
			lastNow.right=newNode;
		}
		fakeNode(newNode);
		insertFixUp(newNode,newNode.parent);
		printall();
		System.out.println();
	}
	private void fakeNode(Node now) {
		Node tem=new Node(-1);
		tem.isReal=false;
		tem.isRed=false;
		now.left=tem;
		tem.parent=now;
		Node tem2=new Node(-1);
		tem2.isReal=false;
		tem2.isRed=false;
		now.right=tem2;
		tem2.parent=now;
	}
	private void insertFixUp(Node now, Node parent) {
		if (now==root) {
			now.isRed=false;
			return;
		}
		if (parent.isRed) {
			Node uncle;
			boolean isUncleLeft;
			if (parent==parent.parent.left) {
				uncle=parent.parent.right;
				isUncleLeft=false;
			}else {
				uncle=parent.parent.left;
				isUncleLeft=true;
			}
			if (uncle.isRed) {
				uncle.isRed=false;
				parent.isRed=false;
				parent.parent.isRed=true;
				insertFixUp(parent.parent, parent.parent.parent);
				return;
			}
			boolean isLeft=parent.left==now? true:false;
			if (isUncleLeft) {
				rightFixUp(now,parent,uncle,isLeft);
			}else {
				leftFixUp(now,parent,uncle,isLeft);
			}
		}
	}
	private void rightFixUp(Node now, Node parent, Node uncle, boolean isLeft) {
		if (isLeft) {
			rightRotate(now);
			leftRotate(now);
			now.isRed=false;
			now.left.isRed=true;
		}else {
			leftRotate(parent);
			parent.isRed=false;
			parent.left.isRed=true;
		}
	}
	private void leftFixUp(Node now, Node parent, Node uncle, boolean isLeft) {
		if (isLeft) {
			rightRotate(parent);
			parent.isRed=false;
			parent.right.isRed=true;
		}else {
			leftRotate(now);
			rightRotate(now);
			now.isRed=false;
			now.right.isRed=true;
		}
	}
	
	private void leftRotate(Node now) {
		Node parent=now.parent;
		now.left.parent=parent;
		parent.right=now.left;
		now.left=parent;
		if (root==parent) {
			root=now;
			now.parent=now;
		}else {
			now.parent=parent.parent;
		}
		parent.parent=now;
	}
	
	private void rightRotate(Node now) {
		Node parent=now.parent;
		now.right.parent=parent;
		parent.left=now.right;
		now.right=parent;
		if (root==parent) {
			root=now;
			now.parent=now;
		}else {
			now.parent=parent.parent;
		}
		parent.parent=now;
	}
	public void deleteAll(double value) {
		//while(delete(value)){}
	}
	public void printall() {
		print(root);
	}
	public void print(Node now) {
		if(now.isReal){
			print(now.left);
			System.out.print(now.value+" "+now.isRed);
			if(now.parent!=null){
				System.out.println(" "+now.parent.isRed+" "+now.parent.value);
			}else {
				System.out.println();
			}
			print(now.right);
		}
	}
	/*
	private void exchange(Node one, Node two) {
		double tem=one.value;
		one.value=two.value;
		two.value=tem;
	}
	private void remove(Node n, Node son, boolean isleft) {
		if(n.parent==null){
			root=son;
			son.parent=null;
			return;
			
		}
		if (son==null && isleft) {
			n.parent.left=null;
			return;
		}
		if (son==null && !isleft) {
			n.parent.right=null;
			return;
		}
		if(isleft){
			son.parent=n.parent;
			n.parent.left=son;
		}else{
			son.parent=n.parent;
			n.parent.right=son;
		}
	}
	public boolean delete(double value){
		Node now=root;
		boolean isleft=false;
		while(now!=null && now.value!=value){
			if (now.value>=value) {
				now=now.left;
				isleft=true;
			}else {
				now=now.right;
				isleft=false;
			}
		}
		if(now!=null){
			if (now.left==null) {
				remove(now,now.right,isleft);
				if (!now.isRed) {
					deleteFixUp(now.right,now.parent,isleft);
				}
				
				return true;
			}
			if (now.right==null) {
				remove(now,now.left,isleft);
				if (!now.isRed) {
					deleteFixUp(now.left,now.parent,isleft);
				}
				
				return true;
			}
			
			Node min=now.right;
			isleft=false;
			while(min.left!=null){
				min=min.left;
				isleft=true;
			}
			exchange(min,now);
			remove(min,min.right,isleft);
			if (!min.isRed) {
				deleteFixUp(min.right,min.parent,isleft);
			}
			
			return true;
		}else{
			return false;
		}
	}
	public void deleteAll(double value) {
		while(delete(value)){}
	}
	public void printall() {
		print(root);
	}
	public void print(Node now) {
		if(now!=null){
			print(now.left);
			System.out.print(now.value+" "+now.isRed);
			if(now.parent!=null){
				System.out.println(" "+now.parent.isRed+" "+now.parent.value);
			}else {
				System.out.println();
			}
			print(now.right);
		}
	}*/
}
