package printer;

import java.util.Random;

/**
 * Classe que irá consumir uma impressora do PrinterManager
 * @author Bruno Freitas, Rafael Valer
 *
 */
public class PrinterConsumer extends Thread {
	private String name;
	
	/**
	 * Gerenciador de impressoras
	 */
	private PrinterManager pm;
	
	/**
	 * Construtor do consumidor de impressora.
	 * @param name - Nome que identifica o consumidor de impressora
	 * @param pm - Gerenciador de impressoras na qual será consumido uma impressora
	 */
	public PrinterConsumer(String name, PrinterManager pm) {
		this.name = name;
		this.pm = pm;
	}
	
	/**
	 * Override do método run da classe Thread
	 * Método responsável por utilzar uma impressora do PrinterManager
	 */
	@Override
	public void run() {
		Random r = new Random();
		try {
			Thread.sleep(r.nextInt(1000));	// Dorme por um tempo randômico antes de solicitar a impressora
			
			int printer = pm.getPrinter();		// Solicita uma impressora			
			System.out.println(name + " is using printer " + printer);
			
			Thread.sleep(r.nextInt(1000) + 1000);	// Dorme por um tempo randômico (mínimo 1000 ms)

			System.out.println(name + " is releasing printer " + printer);
			pm.releasePrinter(printer);		// Libera a impressora
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
