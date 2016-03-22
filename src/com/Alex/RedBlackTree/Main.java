package com.Alex.RedBlackTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
		int n=Integer.parseInt(reader.readLine().split(" ")[0]);
		String[] numbers=reader.readLine().split(" ");
		Tree tree=new Tree();
		for(int i=0;i<n;i++){
			tree.insert(Double.parseDouble(numbers[i]));
		}
		tree.printall();
		numbers=reader.readLine().split(" ");
		for(int i=0;i<numbers.length;i++){
			tree.delete(Double.parseDouble(numbers[i]));
		}
		tree.printall();
	}

}
