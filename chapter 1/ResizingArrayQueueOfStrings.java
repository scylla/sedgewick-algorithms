/****************************** ResizingArrayQueueOfStrings.java ******************************************************************
**------ solves the following problem -----
**Develop a class ResizingArrayQueueOfStrings that implements the queue
**abstraction with a fixed-size array, and then extend your implementation to use array
**resizing to remove the size restriction.
**sample input will be space separated strings ex: a b c d  
**author :: amit nagarkoti
**contact :: amitnagarkoti3@gmail.com
**************************************************************************************************************/

import java.util.Scanner;
import java.util.Iterator;

public class ResizingArrayQueueOfStrings implements Iterable<String> {

	private String[] list;
	private int front, rear, N;
	
	// adds new element to queue and initialize queue if not exists
	public void enqueue(String item) {
		if(N == 0) list = new String[1];
		if(list.length == N)
			resize(2*N);
		list[(rear++)%(list.length)] = item;
		N++;
					
	}
	
	// removes elements queue
	public String dequeue() {
		String returnVal = list[(front++)%(list.length)];
		N--;
		if(N<0) N = 0;
		if(N>0 && N <= list.length/4)
			resize(2*N);
		if(N == 0) resize(1);
		if(returnVal == null) {
			System.out.println("no items try adding some items to list");
		} 
		return returnVal;		
	}
	
	// resizes queue if full double the size else if N < length/4 reduce the length to half, also resets front and rear to zero and current element size
	private void resize(int size) {
		String[] temp = new String[size];
		int tempIndex = 0;
		
		if(N > 0) {
			if(front <= rear) 
				for(int listIndex = front;listIndex <= rear-1; listIndex++, tempIndex++)
					temp[tempIndex] = list[listIndex];
			else {
				int listSize = list.length;
				for(int listIndex = front;listIndex == rear-1; listIndex = listIndex % listSize, tempIndex++)
					temp[tempIndex] = list[(listIndex++)%listSize];
			}
		} 
		
		front = 0;
		rear = tempIndex;
		list = temp;
	}

	public Iterator<String> iterator() {
		return new QueueIterator();
	}	
	
	private class QueueIterator implements Iterator<String> {
		int first = front, listSize = list.length, n = N;
		
		public boolean hasNext() {
			return n != 0;			
		}
		
		public String next() {
			String val = list[first++];
			n--;
			return val;
		}

		public void remove() {}
	}
	
	// test method for resizing queue array
	public static void main(String[] args) {
	
		Scanner scn = new Scanner(new java.io.BufferedInputStream(System.in));
		ResizingArrayQueueOfStrings raq = new ResizingArrayQueueOfStrings();
		
		while(scn.hasNext()) {
			raq.enqueue(scn.next());
		}		
	 
		for(String s: raq) {
			System.out.println(s);
		}
		
		raq.dequeue();
		raq.dequeue();
		raq.dequeue();
		raq.dequeue();
		raq.dequeue();
		raq.dequeue();
		raq.dequeue();
		raq.enqueue("200");
		
		System.out.println("after change");

		for(String s: raq) {
			System.out.println(s);
		}
		
		
	}
}