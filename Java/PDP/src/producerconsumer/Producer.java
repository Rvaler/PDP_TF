package producerconsumer;

import java.util.Random;

/**
 * Thread responsável por produzir itens e colocar em um buffer.
 * @author Bruno Freitas
 *
 */
public class Producer extends Thread{
	private int id;
	
	/**
	 * Buffer na qual será colocado itens
	 */
	private Buffer<Integer> buffer;
	
	private boolean stop;
	
	/**
	 * Construtor do Producer
	 * @param id - Id do produtor
	 * @param buffer - buffer na qual será colocado os itens
	 */
	public Producer(int id, Buffer<Integer> buffer) {
		super("Producer_" + id);
		this.buffer = buffer;
		this.id = id;
		this.stop = false;
	}
	
	/**
	 * Overrid do método run da classe Thread.
	 * Método responsável por produzir e colocar novos itens no buffer.
	 */
	@Override
	public void run() {
		int item;
		Random r = new Random();
		while(!stop) {
			try {
				Thread.sleep( r.nextInt(1000) );	// Dorme por um tempo randômico
			} catch (Exception e) {
			}
			
			item = r.nextInt(1000);	// Produz um número aleatório
			
			buffer.addElement(item, this.getName());	// Adiciona item produzido no buffer 
			
		}
		System.out.println(this.getName() + " finished!");
	}	
}