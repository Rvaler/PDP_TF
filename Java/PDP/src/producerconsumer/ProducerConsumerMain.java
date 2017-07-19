package producerconsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Programa que reproduz o problema do consumidor/produtor utilizando multithreads.
 * 
 * @author Bruno Freitas, Rafael Valer
 *
 */
public class ProducerConsumerMain {
	public static void main(String[] args) {
		
		int numberProducer = 10;	// Número de produtores
		int numberConsumer = 10;	// Número de consumidores
		
		Buffer<Integer> buffer = new Buffer<Integer>();	
		
		List<Producer> lstProducer = new ArrayList<Producer>(numberProducer);	// Lista de produtores
		List<Consumer> lstConsumer = new ArrayList<Consumer>(numberConsumer);	// Lista de consumidores
		
		// Inicializa os produtores
		for(int i = 0; i < numberProducer; i++) {
			lstProducer.add(new Producer(i, buffer));
		}
		
		// Inicializa os consumidores
		for(int i = 0; i < numberConsumer; i++) {
			lstConsumer.add(new Consumer(i, buffer));
		}
		
		// Executa os produtores
		for(Producer p : lstProducer) {
			p.start();
		}
		
		// Executa os consumidores
		for(Consumer c : lstConsumer) {
			c.start();
		}
		
		try {
			// Espera o término de execução dos produtores
			for(Producer p : lstProducer) {
				p.join();
			}
			
			// Espera o término de execução dos consumidores
			for(Consumer c : lstConsumer) {
				c.join();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}		
		
	}
	
	
}
