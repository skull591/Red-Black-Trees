package com.Alex.RedBlackTree;

import java.util.concurrent.Exchanger;

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
	public void delete(double value){
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
				return;
			}
			if (now.right==null) {
				remove(now,now.left,isleft);
				return;
			}
			
			Node min=now.right;
			while(min.left!=null){
				min=min.left;
			}
			
			exchange(min,now);
			remove(min,min.right,true);
		}
	}
	
	public void printall() {
		print(root);
	}
	public void print(Node now) {
		if(now!=null){
			print(now.left);
			System.out.println(now.value);
			print(now.right);
		}
	}
}
