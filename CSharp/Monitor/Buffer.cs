using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ProducerConsumer
{
    /// <summary>
    /// Buffer de acesso sincronizado que armazena itens em uma lista FIFO sem limite.
    /// </summary>
    /// <typeparam name="T">Tipo de objetos que serão armazenados no buffer.</typeparam>
    public class Buffer<T>
    {
        /// <summary>
        /// Fila do tipo FIFO para armazenamento dos dados.
        /// </summary>
        private Queue<T> queue;
	
        /// <summary>
        /// Construtor do Buffer
        /// </summary>
	    public Buffer() 
        {
		    queue = new Queue<T>();
	    }


        /// <summary>
        /// Método síncrono para remoção de itens do buffer.
        /// </summary>
        /// <param name="consumerName">Nome do consumidor do item.</param>
        /// <returns>Item consumido</returns>
	    public T GetElement(String consumerName) 
        {
		    Monitor.Enter(this);    // Thread tenta pegar o lock

            T item;
            try
            {
                while (queue.Count == 0)    // Verifica se a fila está vazia// Verifica se a fila está vazia
                {
                    Monitor.Wait(this);     // Thread fica bloqueada até que algum item seja incluído na lista
		        }

                item = queue.Dequeue();		// Remove um item da fila FIFO
                System.Console.WriteLine(consumerName + " consumed item " + item);  // Imprime qual item o consumidor consumiu
	        }
            finally // Bloco finally para garantir a liberação do lock em caso de erro
            {
                Monitor.Exit(this); // Thread libera o lock
            }
		    return item;
	    }

	    /// <summary>
        /// Método síncrono para inclusão de itens no buffer.
	    /// </summary>
        /// <param name="element">Elemento a ser incluído.</param>
        /// <param name="producerName">Nome do produtor do item.</param>
	    public void AddElement(T element, String producerName) {
            Monitor.Enter(this);    // Thread tenta pegar o lock
            try
            {
                queue.Enqueue(element);    // Insere um item na fila
                System.Console.WriteLine(producerName + " produced item " + element);
                Monitor.Pulse(this);    // Acorda um consumidor adormecido
            }
            finally // Bloco finally para garantir a liberação do lock em caso de erro
            {
                Monitor.Exit(this); // Thread libera o lock
            }
	    }	
    }
}
