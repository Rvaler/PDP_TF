package producerconsumer;

import java.util.Random;

/**
 * Classe que consome os dados de um determinado buffer.
 * @author Bruno Freitas, Rafael Valer
 *
 */
public class Consumer extends Thread {
	private int id;
	
	/**
	 * Buffer na qual será consumido dados.
	 */
	private Buffer<Integer> buffer;
	
	
	private boolean stop;
	private int count;
	
	
	/**
	 * Construtor do Consumer
	 * @param id - Id do consumidor
	 * @param buffer - buffer na qual será consumidos os itens
	 */
	public Consumer(int id, Buffer<Integer> buffer) {
		super("Consumer_" + id);
		this.buffer = buffer;
		this.id = id;
		this.stop = false;
		this.count = 0;
	}
	
	/**
	 * Override do método run da classe Thread.
	 * Método responsável por consumir um item do buffer.
	 */
	@Override
	public void run() {
		Integer item;
		Random r = new Random();
	
		while(!stop) {	// Executa até que seja informado para parar
			try {
				Thread.sleep( r.nextInt(1000) );	// Dorme durante um tempo randômico
			} catch (Exception e) {				
			}
			
			item = buffer.getElement(this.getName());	// Consome um item no buffer
			if(item != null) {
				count++;	// Conta o número de itens consumidos
			}
		}
		System.out.println(this.getName() + " finished!");
	}
}
