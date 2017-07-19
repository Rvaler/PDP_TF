using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Printer
{
    /// <summary>
    /// Programa que simula o gerenciamento de um certo número de recursos (impressoras) utilizando semáforos utilizados por diversos clientes (threads)
    /// </summary>
    public class PrinterMain
    {
        static void Main(string[] args)
        {
            int numberResources = 5;	// Número de impressoras
            int numberThreads = 100;	// Número de threads

            PrinterManager pm = new PrinterManager(numberResources);	// Inicializa o genrenciador de recursos

            // Inicialização as threads que iram consumir os recursos
            List<Thread> lstThreads = new List<Thread>(numberThreads);
            for (int i = 0; i < numberThreads; i++)
            {
                lstThreads.Add(new Thread(new PrinterConsumer("Thread_" + i, pm).run));
            }

            // Executa as threads
            foreach (Thread pc in lstThreads)
            {
                pc.Start();
            }

            // Aguarda o término das threads
            foreach (Thread pc in lstThreads)
            {
                pc.Join();
            }

            System.Console.WriteLine("Finished...");
            System.Console.Read();
        }
    }
}
