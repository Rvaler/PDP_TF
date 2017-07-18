using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ProducerConsumer
{
    public class ProducerConsumerExample
    {
        static void Main(string[] args)
        {
            int numberProducer = 10;
		    int numberConsumer = 10;		
		    int numberItens = 10;
		
		    Buffer<int> buffer = new Buffer<int>();
		
            List<Thread> lstProducer = new List<Thread>();
		    List<Thread> lstConsumer = new List<Thread>();
		
		    for(int i = 0; i < numberProducer; i++) {
			    lstProducer.Add(new Thread( new Producer(i, buffer).Run ));
		    }
		
		    for(int i = 0; i < numberConsumer; i++) {
			    lstConsumer.Add(new Thread ( new Consumer(i, buffer).Run ));
		    }
		
		    foreach(Thread p in lstProducer) {
			    p.Start();
		    }
		
		    foreach(Thread c in lstConsumer) {
			    c.Start();
		    }
		
		    try {
			    foreach(Thread p in lstProducer) {
				    p.Join();
			    }
			
			    foreach(Thread c in lstConsumer) {
				    c.Join();
			    }
		    } catch (Exception e) {
			    // TODO: handle exception
		    }
        }
    }
}
