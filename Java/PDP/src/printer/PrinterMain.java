package printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Programa que simula o gerenciamento de um certo número de recursos (impressoras) utilizando semáforos utilizados por diversos
 * clientes (threads)
 * @author Bruno Freitas, Rafael Valer
 *
 */
public class PrinterMain {
	public static void main(String[] args) {
		
		int numberResources = 5;	// Número de impressoras
		int numberThreads = 100;	// Número de threads
		
		PrinterManager pm = new PrinterManager(numberResources);	// Inicializa o genrenciador de recursos
		
		// Inicialização as threads que iram consumir os recursos
		List<PrinterConsumer> lstThreads = new ArrayList<PrinterConsumer>(numberThreads);
		for(int i = 0; i < numberThreads; i++) {
			lstThreads.add(new PrinterConsumer("Thread_" + i, pm));
		}
		
		// Executa as threads
		for(PrinterConsumer pc : lstThreads) {
			pc.start();
		}
		
		// Aguarda o término das threads
		for(PrinterConsumer pc : lstThreads) {
			try {
				pc.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Finished...");
	}
}
