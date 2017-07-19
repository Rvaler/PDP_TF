package printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Gerenciador de impressoras que utiliza semáforos para gerenciar os recursos disponíveis
 * @author Bruno Freitas, Rafael Valer
 *
 */
public class PrinterManager {
	/**
	 * Semáforo utilizado para gerenciar os recursos
	 */
	private Semaphore available;
	
	/**
	 * Número de recursos máximo disponíveis
	 */
	private int numberResources;
	
	/**
	 * Lista de recursos onde true indica que o recurso está disponível e false que está em uso
	 */
	private List<Boolean> resources; 
	
	/**
	 * Construtor do PrinterManager
	 * @param numberResources - Número máximo de recursos disponíveis
	 */
	public PrinterManager(int numberResources) {
		this.numberResources = numberResources;
		
		available = new Semaphore(numberResources);	// Inicializa o semáforo com o valor máximo de recursos que podem estar disponíveis
		
		// Inicializa todos os recursos como liberados
		resources = new ArrayList<Boolean>(numberResources);
		for(int i = 0; i < numberResources; i++) {
			resources.add(true);
		}
	}
	
	/**
	 * Método utilizado para solicitar uma impressora.
	 * @return Retorna o Id da impressora
	 */
	public int getPrinter() {
		try {
			available.acquire();	// Antes de adquirir um recuros, verifica com o semáforo se existe pelo menos um item liberado, senão fica bloqueado
			
			return getNextAvailablePrinter();	// Pega a primeira impressora que não estaja em uso
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Método para liberar um impressora.
	 * @param printer - Id da impressora que não está mais em uso
	 */
	public void releasePrinter(int printer) {
		setPrinterAvailable(printer);	// Chama método para liberar impressora 
		
		available.release();	// Libera um recurso no semáforo
	}
	
	
	/**
	 * Método sincronizado para recuperar a primeira impressora disponível
	 * @return - Id da primeira impressora disponível.
	 */
	private synchronized int getNextAvailablePrinter() {
		int p = -1; 
		for(int i = 0; i < numberResources; i++) {
			if(resources.get(i)) {
				resources.set(i, false);
				return i;
			}
		}
		return p;
	}


	/**
	 * Método sincronizado para liberar um impressora que estava em uso.
	 * @param printer - Id da impressora que não está mais em uso.
	 */
	private synchronized void setPrinterAvailable(int printer) {
		resources.set(printer, true);
	}
}
