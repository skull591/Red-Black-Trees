package com.Alex.RedBlackTree;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.table.TableStringConverter;

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
			return;
		}else {
			now=root;
		}
		boolean isLeft=false;
		lastNow=now;
		while (now.isReal) {
			lastNow=now;
			if (now.value==value) {
				now.time++;
				return;
			}
			if (now.value>value) {
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
			if (parent==parent.parent.left) {
				parent.parent.left=now;
			}else {
				parent.parent.right=now;
			}
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
			if (parent==parent.parent.left) {
				parent.parent.left=now;
			}else {
				parent.parent.right=now;
			}
		}
		parent.parent=now;
	}
	public boolean delete(double value) {
		Node now=root;
		while(now.isReal && now.value!=value){
			if (now.value>=value) {
				now=now.left;
			}else {
				now=now.right;
			}
		}
		if (now.isReal) {
			remove(now);
			return true;
		}
		return false;
	}
	private void deleteFixUp(Node now) {
		if (now.isRed) {
			now.isRed=false;
			return;
		}
		if (now==root) {
			return;
		}
		boolean isLeft=now==now.parent.left? true:false;
		Node parent=now.parent;
		Node brother=isLeft? parent.right:parent.left;
		Node leftN=brother.left;
		Node rightN=brother.left;
		if (!leftN.isRed && !rightN.isRed) {
			bothNeBlack(parent,brother,now,isLeft);
			return;
		}
		
		if (isLeft) {
			rightDFixUp(now,parent,brother,leftN,rightN);
		}else {
			leftDFixUp(now,parent,brother,leftN,rightN);
		}
	}
	private void rightDFixUp(Node now,Node parent, Node brother,Node leftN, Node rightN) {
		if (rightN.isRed) {
			leftRotate(brother);
			brother.isRed=parent.isRed;
			parent.isRed=false;
			rightN.isRed=false;
		}else{
			rightRotate(leftN);
			leftRotate(leftN);
			leftN.isRed=parent.isRed;
			parent.isRed=false;
		}
	}
	private void leftDFixUp(Node now,Node parent, Node brother,Node leftN, Node rightN) {
		if (leftN.isRed) {
			rightRotate(brother);
			brother.isRed=parent.isRed;
			parent.isRed=false;
			leftN.isRed=false;
		}else {
			leftRotate(rightN);
			rightRotate(rightN);
			rightN.isRed=parent.isRed;
			parent.isRed=false;
		}
	}
	private void bothNeBlack(Node parent,Node brother,Node now,boolean isLeft) {
		if (brother.isRed) {
			if (isLeft) {
				rightRotate(brother);
			}else {
				leftRotate(brother);
			}
			brother.isRed=false;
			parent.isRed=true;
			deleteFixUp(now);
		}else {
			brother.isRed=true;
			if (parent.isRed) {
				parent.isRed=false;
			}else {
				deleteFixUp(parent);
			}
		}
	}
	private void remove(Node now) {
		boolean isLeft=now.parent.left==now? true:false;
		if (!now.left.isReal && !now.right.isReal) {
			now.left.parent=now==root? now.left:now.parent;
			if (now==root) {
				root=now.left;
			}
			if (isLeft) {
				now.parent.left=now.left;
			}else {
				now.parent.right=now.left;
			}
			if (!now.isRed) {
				deleteFixUp(now.left);
			}
		}else {
			removeCase2(now,isLeft);
		}
	}
	
	private void removeCase2(Node now,boolean isLeft) {
		if (!now.left.isReal) {
			now.right.parent=now==root? now.right:now.parent;
			if (now==root) {
				root=now.right;
			}
			if (isLeft) {
				now.parent.left=now.right;
			}else {
				now.parent.right=now.right;
			}
			if (!now.isRed) {
				deleteFixUp(now.right);
			}
		}else {
			removeCase3(now, isLeft);
		}
	}
	
	private void removeCase3(Node now, boolean isLeft) {
		if (!now.right.isReal) {
			now.left.parent=now==root? now.left:now.parent;
			if (now==root) {
				root=now.left;
			}
			if (isLeft) {
				now.parent.left=now.left;
			}else {
				now.parent.right=now.left;
			}
			if (!now.isRed) {
				deleteFixUp(now.left);
			}
		}else {
			removeExchange(now, isLeft);
		}
	}
	
	private void removeExchange(Node now, boolean isLeft) {
		Node min=now.right;
		boolean once=false;
		while (min.left.isReal) {
			min=min.left;
			once=true;
		}
		exchange(min, now);
		if (once) {
			min.right.parent=min.parent;
			min.parent.left=min.right;
		}else {
			min.right.parent=min.parent;
			min.parent.right=min.right;

		}			
		if (!min.isRed) {
			deleteFixUp(min.right);
		}
	}
	
	private void exchange(Node min, Node now) {
		double tem=min.value;
		min.value=now.value;
		now.value=tem;
	}
	public void printall() {
		print(root);
	}
	public void print(Node now) {
		if(now.isReal){
			print(now.left);
			System.out.print(now.value+" "+now.isRed+" "+now.time);
			if(now.parent!=null){
				System.out.println(" "+now.parent.isRed+" "+now.parent.value);
			}else {
				System.out.println();
			}
			print(now.right);
		}
	}
	public void validate() {
		if (root.isRed) {
			System.out.println("Error: root is red!");
		}
		LinkedList<Node> list=new LinkedList<>();
		int length=0;
		Node now=root;
		while(now.isReal && !now.isRed){
			length++;
			now=now.left;
		}
		test(list,root,length);
	}
	
	public void test(LinkedList<Node> list,Node now,int length) {
		if (!now.isReal) {
			int len=list.size();
			if (len!=length) {
				System.out.println("Error: not the same lenght! "+len+" "+length);
				for(Node tem:list){
					System.out.print(tem.value+" ");
				}
				System.out.println();
			}
		}else {
			if (now.isRed && now.left.isRed) {
				System.out.println("Error: two red in a row! "+now.value+" "+now.left.value);
			}
			if (now.isRed && now.right.isRed) {
				System.out.println("Error: two red in a row! "+now.value+" "+now.right.value);
			}
			if (!now.isRed) {
				list.push(now);
			}
			test(list, now.left, length);
			test(list, now.right, length);
			if (!now.isRed) {
				list.pop();
			}
		}
	}
}
