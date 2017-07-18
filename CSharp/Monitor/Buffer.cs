using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ProducerConsumer
{
    public class Buffer<T>
    {
        private Queue<T> queue;
	
	    public Buffer() 
        {
		    queue = new Queue<T>();
	    }

	
	    public T GetElement(String consumerName) {
		    T item;
            Monitor.Enter(this);

            try {
		        while ( queue.Count == 0 ) {
                    Monitor.Wait(this);
		        }

                item = queue.Dequeue();		
		        System.Console.WriteLine(consumerName + " consumed item " + item);
	        }
            finally 
            {
                Monitor.Exit(this);
            }
		    return item;
	    }

	
	    public void AddElement(T element, String producerName) {
            Monitor.Enter(this);
            try
            {
                queue.Enqueue(element);
                System.Console.WriteLine(producerName + " produced item " + element);
                Monitor.Pulse(this);
            }
            finally
            {
                Monitor.Exit(this);
            }
	    }	
    }
}
