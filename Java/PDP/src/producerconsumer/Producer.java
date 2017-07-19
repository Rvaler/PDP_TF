package producerconsumer;

import java.util.Random;

/**
 * Thread respons�vel por produzir itens e colocar em um buffer.
 * @author Bruno Freitas
 *
 */
public class Producer extends Thread{
	private int id;
	
	/**
	 * Buffer na qual ser� colocado itens
	 */
	private Buffer<Integer> buffer;
	
	private boolean stop;
	
	/**
	 * Construtor do Producer
	 * @param id - Id do produtor
	 * @param buffer - buffer na qual ser� colocado os itens
	 */
	public Producer(int id, Buffer<Integer> buffer) {
		super("Producer_" + id);
		this.buffer = buffer;
		this.id = id;
		this.stop = false;
	}
	
	/**
	 * Overrid do m�todo run da classe Thread.
	 * M�todo respons�vel por produzir e colocar novos itens no buffer.
	 */
	@Override
	public void run() {
		int item;
		Random r = new Random();
		while(!stop) {
			try {
				Thread.sleep( r.nextInt(1000) );	// Dorme por um tempo rand�mico
			} catch (Exception e) {
			}
			
			item = r.nextInt(1000);	// Produz um n�mero aleat�rio
			
			buffer.addElement(item, this.getName());	// Adiciona item produzido no buffer 
			
		}
		System.out.println(this.getName() + " finished!");
	}	
}