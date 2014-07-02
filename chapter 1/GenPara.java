/****************************** GenPara.java ******************************************************************
**------ solves the following problem -----
**Write a program that takes from standard input an expression without left parentheses
**and prints the equivalent infix expression with the parentheses inserted. For
**example, given the input:
**1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) )
**your program should print
**( ( 1 + 2 ) * ( ( 3 - 4 ) * ( 5 - 6 ) )
**
**use 1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) ) in your input file
**
**although implementing Iterable was not necessary i've done it only for learning 
**author :: amit nagarkoti
**contact :: amitnagarkoti3@gmail.com
**************************************************************************************************************/

import java.util.Scanner;
import java.util.Iterator;

public class GenPara implements Iterable<String> {

	private Node first;
	private static final String _space = " ";
	private class Node{
		Node next;
		String value;
	}
	
	public Iterator<String> iterator() {
		return new ListIterator();
	} 
	
	private class ListIterator implements Iterator<String> {
		private Node current = first;
		
		public boolean hasNext() {
			return current != null;
		}
		
		public void remove() {}
		
		public String next() {
			String str = current.value;
			current = current.next;
			return str;
		}
	}

	public void push(String value) {
		Node oldFirst = first;
		first = new Node();
		first.next = oldFirst;
		first.value = value;
	}	
	
	public String pop() {
		String value = first.value;
		first = first.next;
		return value;
	}

	public static void main(String[] args) {
		Scanner scn = new Scanner(new java.io.BufferedInputStream(System.in));
		GenPara genP =  new GenPara();
		String currentChar, operator, operandOne, operandTwo;
		while(scn.hasNext()) {
			currentChar = scn.next();
			if(currentChar.equals(")")) {
				operandOne = genP.pop();
				operator = genP.pop();
				operandTwo = genP.pop();
				genP.push("(" + _space  + operandTwo + _space + operator + _space + operandOne + _space + ")");				
			} else {
				genP.push(currentChar);
			} 			
		}

		for(String s : genP) {
			System.out.println(s);
		}
	}
}