package producerconsumer;

import java.util.Random;

public class Producer extends Thread{
	private int id;
	private Buffer<Integer> buffer;
	private boolean stop;
	
	public Producer(int id, Buffer<Integer> buffer) {
		super("Producer_" + id);
		this.buffer = buffer;
		this.id = id;
		this.stop = false;
	}
	
	@Override
	public void run() {
		int item;
		Random r = new Random();
		while(!stop) {
			item = r.nextInt(1000);
			buffer.addElement(item, this.getName());
			try {
				Thread.sleep( r.nextInt(1000) );
			} catch (Exception e) {
			}
		}
		System.out.println(this.getName() + " finished!");
	}	
}