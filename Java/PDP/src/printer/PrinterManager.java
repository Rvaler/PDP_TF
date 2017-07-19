package printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Gerenciador de impressoras que utiliza sem�foros para gerenciar os recursos dispon�veis
 * @author Bruno Freitas, Rafael Valer
 *
 */
public class PrinterManager {
	/**
	 * Sem�foro utilizado para gerenciar os recursos
	 */
	private Semaphore available;
	
	/**
	 * N�mero de recursos m�ximo dispon�veis
	 */
	private int numberResources;
	
	/**
	 * Lista de recursos onde true indica que o recurso est� dispon�vel e false que est� em uso
	 */
	private List<Boolean> resources; 
	
	/**
	 * Construtor do PrinterManager
	 * @param numberResources - N�mero m�ximo de recursos dispon�veis
	 */
	public PrinterManager(int numberResources) {
		this.numberResources = numberResources;
		
		available = new Semaphore(numberResources);	// Inicializa o sem�foro com o valor m�ximo de recursos que podem estar dispon�veis
		
		// Inicializa todos os recursos como liberados
		resources = new ArrayList<Boolean>(numberResources);
		for(int i = 0; i < numberResources; i++) {
			resources.add(true);
		}
	}
	
	/**
	 * M�todo utilizado para solicitar uma impressora.
	 * @return Retorna o Id da impressora
	 */
	public int getPrinter() {
		try {
			available.acquire();	// Antes de adquirir um recuros, verifica com o sem�foro se existe pelo menos um item liberado, sen�o fica bloqueado
			
			return getNextAvailablePrinter();	// Pega a primeira impressora que n�o estaja em uso
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * M�todo para liberar um impressora.
	 * @param printer - Id da impressora que n�o est� mais em uso
	 */
	public void releasePrinter(int printer) {
		setPrinterAvailable(printer);	// Chama m�todo para liberar impressora 
		
		available.release();	// Libera um recurso no sem�foro
	}
	
	
	/**
	 * M�todo sincronizado para recuperar a primeira impressora dispon�vel
	 * @return - Id da primeira impressora dispon�vel.
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
	 * M�todo sincronizado para liberar um impressora que estava em uso.
	 * @param printer - Id da impressora que n�o est� mais em uso.
	 */
	private synchronized void setPrinterAvailable(int printer) {
		resources.set(printer, true);
	}
}
