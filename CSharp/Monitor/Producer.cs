using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;

namespace ProducerConsumer
{
    public class Producer
    {
        private int id;
	    private Buffer<int> buffer;
	    private bool stop;
        public String Name { get; set; }
	
	    public Producer(int id, Buffer<int> buffer)
        {
		    this.Name = "Producer_" + id;
		    this.buffer = buffer;
		    this.id = id;
		    this.stop = false;
	    }
	
	    public void Run() 
        {
		    int item;
		    Random r = new Random(this.id);
		    while(!stop) 
            {
			    item = r.Next(1000);
			    buffer.AddElement(item, this.Name);
			
                Thread.Sleep( r.Next(1000) );			    
		    }

		    System.Console.WriteLine(this.Name + " finished!");
	    }	
    }
}
