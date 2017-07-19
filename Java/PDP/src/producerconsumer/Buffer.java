package producerconsumer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Buffer de acesso sincronizado que armazena itens em uma lista FIFO sem limite.
 * @author Bruno Freitas, Rafael Valer
 *
 * @param <T> - Tipo de objetos que ser�o armazenados no buffer.
 */
public class Buffer<T> {
	
	/**
	 * Fila do tipo FIFO para armazenamento dos dados.
	 */
	private Queue<T> queue;
	
	/**
	 * Construtor do Buffer
	 */
	public Buffer() {
		queue = new LinkedList<T>();
	}
	
	/**
	 * M�todo s�ncrono para remo��o de itens do buffer.
	 * @param consumerName - Nome do consumidor do item.
	 * @return	Item consumido
	 */
	public synchronized T getElement(String consumerName) {
		while ( queue.size() == 0) {	// Verifica se a fila est� vazia
			try {
				wait();		// Thread fica bloqueada at� que algum item seja inclu�do na lista
			} catch (InterruptedException e) {
				return null;
			}
		}
		
		T item = queue.poll();	// Remove um item da fila FIFO
		
		System.out.println(consumerName + " consumed item " + item);	// Imprime qual item o consumidor consumiu
		
		return item;
	}
	
	/**
	 * M�todo s�ncrono para inclus�o de itens no buffer.
	 * @param element - Elemento a ser inclu�do.
	 * @param producerName - Nome do produtor do item.
	 */
	public synchronized void addElement(T element, String producerName) {
		
		if(queue.offer(element)) {	// Tenta inserir um item na fila
			System.out.println(producerName + " produced item " + element);
			
			notify();	// Acorda um consumidor adormecido
		}
	}	
	
}
