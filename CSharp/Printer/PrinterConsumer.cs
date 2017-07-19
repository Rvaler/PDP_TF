using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Printer
{
   
    /// <summary>
    /// Classe que irá consumir uma impressora do PrinterManager
    /// </summary>
    public class PrinterConsumer
    {
        private String name;

        /// <summary>
        /// Gerenciador de impressoras
        /// </summary>
        private PrinterManager pm;

        /// <summary>
        /// Construtor do consumidor de impressora.
        /// </summary>
        /// <param name="name">Nome que identifica o consumidor de impressora</param>
        /// <param name="pm">Gerenciador de impressoras na qual será consumido uma impressora</param>
        public PrinterConsumer(String name, PrinterManager pm)
        {
            this.name = name;
            this.pm = pm;
        }

        /// <summary>
        /// Método responsável por utilzar uma impressora do PrinterManager
        /// </summary>
        public void run()
        {
            Random r = new Random();
            Thread.Sleep(r.Next(1000));	// Dorme por um tempo randômico antes de solicitar a impressora

            int printer = pm.getPrinter();		// Solicita uma impressora

            System.Console.WriteLine(name + " is using printer " + printer);

            Thread.Sleep(r.Next(1000) + 1000);	// Dorme por um tempo randômico (mínimo 1000 ms)


            System.Console.WriteLine(name + " is releasing printer " + printer);
            pm.releasePrinter(printer);		// Libera a impressora
        }
    }
}
