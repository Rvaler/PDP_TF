using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ProducerConsumer
{
    /// <summary>
    /// Programa que reproduz o problema do consumidor/produtor utilizando multithreads.
    /// </summary>
    public class ProducerConsumerMain
    {
        static void Main(string[] args)
        {
            int numberProducer = 10;    // Número de produtores
            int numberConsumer = 10;	// Número de consumidores
		
		    Buffer<int> buffer = new Buffer<int>();

            List<Thread> lstProducer = new List<Thread>();  // Lista de threads produtores
            List<Thread> lstConsumer = new List<Thread>();  // Lista de threads consumidores

            // Inicializa os produtores
		    for(int i = 0; i < numberProducer; i++) {
			    lstProducer.Add(new Thread( new Producer(i, buffer).Run ));
		    }

            // Inicializa os consumidores
		    for(int i = 0; i < numberConsumer; i++) {
			    lstConsumer.Add(new Thread ( new Consumer(i, buffer).Run ));
		    }

            // Executa os produtores
		    foreach(Thread p in lstProducer) {
			    p.Start();
		    }

            // Executa os consumidores
		    foreach(Thread c in lstConsumer) {
			    c.Start();
		    }
		
		    try {
                // Espera o término de execução dos produtores
			    foreach(Thread p in lstProducer) {
				    p.Join();
			    }

                // Espera o término de execução dos consumidores
			    foreach(Thread c in lstConsumer) {
				    c.Join();
			    }
		    } catch (Exception e) {
			    // TODO: handle exception
		    }
        }
    }
}
