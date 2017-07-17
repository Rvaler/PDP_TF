package monitor;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer<T> {
	private Queue<T> queue;
	
	public Buffer() {
		queue = new LinkedList<T>();
	}
	
	public synchronized T getNextElement(String consumerName) {
		T item = queue.poll();
		if(item != null) {
			System.out.println(consumerName + " consumed item " + item);
		}
		return item;
	}
	
	public synchronized void addElement(T element, String producerName) {
		if(queue.offer(element)) {
			System.out.println(producerName + " produced item " + element);
		}
	}
	
	
}
