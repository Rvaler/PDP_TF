using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;

namespace ProducerConsumer
{
    /// <summary>
    /// Thread responsável por produzir itens e colocar em um buffer.
    /// </summary>
    public class Producer
    {
        private int id;

        /// <summary>
        /// Buffer na qual será colocado itens
        /// </summary>
	    private Buffer<int> buffer;
	    private bool stop;
        public String Name { get; set; }
	
        /// <summary>
        /// Construtor do Producer
        /// </summary>
        /// <param name="id">Id do produtor</param>
        /// <param name="buffer">buffer na qual será colocado os itens</param>
	    public Producer(int id, Buffer<int> buffer)
        {
		    this.Name = "Producer_" + id;
		    this.buffer = buffer;
		    this.id = id;
		    this.stop = false;
	    }
	
        /// <summary>
        /// Método responsável por produzir e colocar novos itens no buffer.
        /// </summary>
	    public void Run() 
        {
		    int item;
		    Random r = new Random(this.id);
		    while(!stop)
            {
                Thread.Sleep(r.Next(1000));   // Dorme por um tempo randômico

                item = r.Next(1000);    // Produz um número aleatório
                buffer.AddElement(item, this.Name); // Adiciona item produzido no buffer
		    }

		    System.Console.WriteLine(this.Name + " finished!");
	    }	
    }
}
