using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ProducerConsumer
{
    public class Consumer
    {
        private int id;
	    private Buffer<int> buffer;
        private bool stop;
        public String Name { get; set; }

        public Consumer(int id, Buffer<int> buffer)
        {
            this.Name = "Consumer_" + id;
		    this.buffer = buffer;
		    this.id = id;
		    this.stop = false;
	    }
	
	    public void Run() 
        {
		    int item;
		    int count = 0;
		    Random r = new Random(this.id);
		    while(!stop)
            {
                Thread.Sleep(r.Next(1000));

			    item = buffer.GetElement(this.Name);
			    if(item != null) 
                {
				    count++;
			    }						    
		    }
		    System.Console.WriteLine(this.Name + " finished!");
	    }
    }
}
