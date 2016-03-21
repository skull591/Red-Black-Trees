package com.Alex.RedBlackTree;


public class Tree {
	Node root;
	
	public Tree(){
		root=null;
	}
	
	public void insert(double value){
		Node newNode=new Node(value);
		Node now,lastnow;
		if(root==null){
			
			root=newNode;
			insertFixUp(newNode,false);
			return;
		}else{
			now=root;
		}
		boolean isLeft=false;
		lastnow=newNode;
		while (now!=null) {
			lastnow=now;
			newNode.layer=newNode.layer+1;
			if(now.value>=value){
				now=now.left;
				isLeft=true;
			}else{
				now=now.right;
			    isLeft=false;
			}
		}
		newNode.parent=lastnow;
		if (isLeft) {
			lastnow.left=newNode;	
		}else {
			lastnow.right=newNode;
		}
		
		insertFixUp(newNode,isLeft);
	}
	
	private void insertFixUp(Node now, boolean isleft) {
		if(now==root){
			now.isRed=false;
			return;
		}
		if (!now.parent.isRed) {
			return;
		}
		Node uncle=null;
		boolean isUncleLeft;
		if (now.parent==now.parent.parent.left) {
			uncle=now.parent.parent.right;
			isUncleLeft=false;
		}else {
			uncle=now.parent.parent.left;
			isUncleLeft=true;
		}
		Node tem=uncle;
		if (uncle==null) {
			uncle=new Node(-1);
			uncle.isRed=false;
		}
		if (uncle.isRed) {
			uncle.isRed=false;
			now.parent.isRed=false;
			now.parent.parent.isRed=true;
			boolean left;
			if (now.parent.parent.parent!=null && now.parent.parent.parent.left==now.parent.parent.parent) {
				left=true;
			}else {
				left=false;
			}
			insertFixUp(now.parent.parent,left);
			uncle=tem;
			return;
		}
		if (!isUncleLeft) {
			RightFixUp(now,isleft);
		}else {
			LeftFixUp(now,isleft);
		}
		uncle=tem;
	}
	private void RightFixUp(Node now, boolean isleft) {
		if (isleft) {
			rightRotate(now.parent);
			now.parent.isRed=false;
			now.parent.right.isRed=true;
		}else{
			leftRotate(now);
			rightRotate(now);
			now.isRed=false;
			now.right.isRed=true;
		}
	}
	private void LeftFixUp(Node now, boolean isleft) {
		if(!isleft){
			leftRotate(now.parent);
			now.parent.isRed=false;
			now.parent.left.isRed=true;
		}else {
			rightRotate(now);
			leftRotate(now);
			now.isRed=false;
			now.left.isRed=true;
		}
	}
	
	private void leftRotate(Node now) {
		Node parent=now.parent;
		if(parent.parent!=null && parent==parent.parent.left){
			parent.parent.left=now;
		}
		if(parent.parent!=null && parent==parent.parent.right){
			parent.parent.right=now;
		}	
		if(now.left!=null){
			now.left.parent=parent;
		}
		parent.right=now.left;
		now.parent=parent.parent;
		parent.parent=now;
		now.left=parent;
		if (root==parent) {
			root=now;
		}
	}
	
	private void rightRotate(Node now) {
		Node parent=now.parent;
		if(parent.parent!=null && parent==parent.parent.left){
				parent.parent.left=now;
		}
		if(parent.parent!=null && parent==parent.parent.right){
			parent.parent.right=now;
		}
		if(now.right!=null){
			now.right.parent=parent;
		}
		parent.left=now.right;
		now.parent=parent.parent;
		parent.parent=now;
		now.right=parent;
		if (root==parent) {
			root=now;
		}
	}
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
				return true;
			}
			if (now.right==null) {
				remove(now,now.left,isleft);
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
	}
}
