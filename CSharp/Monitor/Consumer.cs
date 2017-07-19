using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ProducerConsumer
{
    /// <summary>
    ///  Classe que consome os dados de um determinado buffer.
    /// </summary>
    public class Consumer
    {
        private int id;

        /// <summary>
        /// Buffer na qual será consumido dados.
        /// </summary>
	    private Buffer<int> buffer;
        private bool stop;
        public String Name { get; set; }

        /// <summary>
        /// Construtor do Consumer
        /// </summary>
        /// <param name="id">Id do consumidor</param>
        /// <param name="buffer">buffer na qual será consumidos os itens</param>
        public Consumer(int id, Buffer<int> buffer)
        {
            this.Name = "Consumer_" + id;
		    this.buffer = buffer;
		    this.id = id;
		    this.stop = false;
	    }
	
        /// <summary>
        /// Método responsável por consumir um item do buffer.
        /// </summary>
	    public void Run() 
        {
		    int item;
		    int count = 0;
		    Random r = new Random(this.id);
            while (!stop)    // Executa até que seja informado para parar
            {
                Thread.Sleep(r.Next(1000)); // Dorme durante um tempo randômico

                item = buffer.GetElement(this.Name);    // Consome um item no buffer
			    if(item != null) 
                {
                    count++;    // Conta o número de itens consumidos
			    }						    
		    }
		    System.Console.WriteLine(this.Name + " finished!");
	    }
    }
}
