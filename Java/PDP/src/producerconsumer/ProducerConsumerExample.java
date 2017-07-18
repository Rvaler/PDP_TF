package producerconsumer;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerExample {
	public static void main(String[] args) {
		
		int numberProducer = 10;
		int numberConsumer = 10;		
		int numberItens = 10;
		
		Buffer<Integer> buffer = new Buffer<Integer>();
		
		List<Producer> lstProducer = new ArrayList<Producer>(numberProducer);
		List<Consumer> lstConsumer = new ArrayList<Consumer>(numberConsumer);
		
		for(int i = 0; i < numberProducer; i++) {
			lstProducer.add(new Producer(i, buffer));
		}
		
		for(int i = 0; i < numberConsumer; i++) {
			lstConsumer.add(new Consumer(i, buffer));
		}
		
		for(Producer p : lstProducer) {
			p.start();
		}
		
		for(Consumer c : lstConsumer) {
			c.start();
		}
		
		try {
			for(Producer p : lstProducer) {
				p.join();
			}
			
			for(Consumer c : lstConsumer) {
				c.join();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}		
		
	}
	
	
}
