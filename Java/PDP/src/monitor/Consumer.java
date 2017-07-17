package monitor;

import java.util.Random;

public class Consumer extends Thread {
	private int id;
	private Buffer<Integer> buffer;
	private boolean stop;
	
	public Consumer(int id, Buffer<Integer> buffer) {
		super("Consumer_" + id);
		this.buffer = buffer;
		this.id = id;
		this.stop = false;
	}
	
	@Override
	public void run() {
		Integer item;
		int count = 0;
		Random r = new Random();
		while(!stop) {
			item = buffer.getNextElement(this.getName());
			if(item != null) {
				count++;
			}
			try {
				Thread.sleep( r.nextInt(1000) );
			} catch (Exception e) {
				
			}
		}
		System.out.println(this.getName() + " finished!");
	}
}
