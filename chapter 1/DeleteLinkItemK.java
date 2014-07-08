/****************************** DeleteLinkItemK.java ******************************************************************
* ------ solves the following problem -----
* Write a method delete() that takes an int argument k and deletes the kth element
* in a linked list, if it exists.
* This is a generic implmentation 
* @author :: amit nagarkoti
**************************************************************************************************************/


import java.util.Scanner;
import java.util.Iterator;

public class DeleteLinkItemK<Item> implements Iterable<Item> {
	
	private Node root;

	private class Node {
		Item item;
		Node next;		
	}
	
	// adds new item to list end
	private void add(Item item) {

		Node current = root;

		if(current == null) {
			root = new Node();
			root.item = item; 
		} else { 
			while(current.next != null) {
				current = current.next;				
			}
			current.next = new Node();
			current.next.item = item;	
		}

	}
	
	// deletes kth item from list 
	public void deleteK(int k) {
	
		Node previous = null; // the item previous to the item to be deleted
		Node current = root; // will point to the item to be deleted
		
		// special root case
		if(k==1) {
			root = root.next;
		} else {
			while(--k > 0 && current != null) {
				previous = current;
				current = current.next;
			} 	

			if(k > 0) 
				System.out.println("operation failed not enough items");
			else {
				try {
					previous.next = current.next;
				} catch (Exception e) {
					System.out.println("an exception occurred");
					System.exit(1);
				}
			
			}
		}
		
	}

	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
		
		private Node current = root;
		
		public boolean hasNext() {
			return current != null;	
		}

		public Item next() {
			Item retVal = current.item;
			current = current.next;
			return retVal; 
		}
		
		public void remove() {}
	
	}
	
	// test method
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(new java.io.BufferedInputStream(System.in));
		DeleteLinkItemK<String> dli = new DeleteLinkItemK<String>();

		while(sc.hasNext()) 
			dli.add(sc.next());
		
		for(String s : dli) 
			System.out.println(s);
		
		dli.deleteK(5); 
		dli.deleteK(1);
		dli.deleteK(10);

		System.out.println("after deleting");
		
		for(String s : dli) 
			System.out.println(s);

	}

}