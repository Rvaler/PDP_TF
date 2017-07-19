using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Printer
{
 
    /// <summary>
    /// Gerenciador de impressoras que utiliza semáforos para gerenciar os recursos disponíveis
    /// </summary>    
    public class PrinterManager 
    {
	    
        /// <summary>
        /// Semáforo utilizado para gerenciar os recursos
        /// </summary>
	    private Semaphore available;
	
	    /// <summary>
	    /// Número de recursos máximo disponíveis
	    /// </summary>
	    private int numberResources;
	
	    /// <summary>
	    /// Lista de recursos onde true indica que o recurso está disponível e false que está em uso
	    /// </summary>
	    private List<Boolean> resources; 
	
	    
        /// <summary>
        /// Construtor do PrinterManager
        /// </summary>
        /// <param name="numberResources">Número máximo de recursos disponíveis</param>
	    public PrinterManager(int numberResources) 
        {
		    this.numberResources = numberResources;

            available = new Semaphore(numberResources, numberResources);	// Inicializa o semáforo com o valor máximo de recursos que podem estar disponíveis
		
		    // Inicializa todos os recursos como liberados
		    resources = new List<Boolean>();
		    for(int i = 0; i < numberResources; i++) {
			    resources.Add(true);
		    }
	    }
	
	    
        /// <summary>
        /// Método utilizado para solicitar uma impressora.
        /// </summary>
        /// <returns>Retorna o Id da impressora</returns>
	    public int getPrinter() 
        {
		    available.WaitOne();	// Antes de adquirir um recuros, verifica com o semáforo se existe pelo menos um item liberado, senão fica bloqueado
			
			return getNextAvailablePrinter();	// Pega a primeira impressora que não estaja em uso			
	    }
	
        /// <summary>
        /// Método para liberar um impressora.
        /// </summary>
        /// <param name="printer">Id da impressora que não está mais em uso</param>
	    public void releasePrinter(int printer) 
        {
		    setPrinterAvailable(printer);	// Chama método para liberar impressora 
		
		    available.Release();	// Libera um recurso no semáforo
	    }
	

        /// <summary>
        /// Método sincronizado para recuperar a primeira impressora disponível
        /// </summary>
        /// <returns>Id da primeira impressora disponível.</returns>
	    private int getNextAvailablePrinter() 
        {
            Monitor.Enter(this);
		    try
            {
                int p = -1;
                for (int i = 0; i < numberResources; i++)
                {
                    if (resources[i])
                    {
                        resources[i] = false;
                        return i;
                    }
                }
		        return p;
            }
            finally
            {
                Monitor.Exit(this);
            }
	    }

        /// <summary>
        /// Método sincronizado para liberar um impressora que estava em uso.
        /// </summary>
        /// <param name="printer">Id da impressora que não está mais em uso.</param>
        private void setPrinterAvailable(int printer)
        {
            Monitor.Enter(this);
            try
            {
                resources[printer] = true;
            }
            finally
            {
                Monitor.Exit(this);
            }
        }
    }
}
