using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace PDP
{
    public class MVPProducMain
    {
        static void Main(string[] args)
        {
            int nThreads = 8;
		    int N = 35000;
		 
            int[,] matrix = new int[N,N];
		    int[] vector = new int[N];
		    int[] resultVector = new int[N];
		
            Random r = new Random();
            
		    for(int i = 0; i < N; i++)
            {
				for(int j = 0; j < N; j++) 
                {
					matrix[i,j] = r.Next(1000);
				}
				vector[i] = r.Next(1000);
            }

		    List<Thread> threads = new List<Thread>();
		    int linesPerThread = N/nThreads;
		    for(int i = 0; i < nThreads; i++) {
			    threads.Add( new Thread(new MVProduct(i * linesPerThread, i * linesPerThread + linesPerThread - 1, N, vector, matrix, resultVector).Run ));
		    }
		
		    System.Console.WriteLine("Starting threads...");
            Stopwatch sw = new Stopwatch();
            DateTime dt = DateTime.Now;
            sw.Start();
		    for(int i = 0; i < nThreads; i++) {
			    threads[i].Start();
		    }
		
		    for(int i = 0; i < nThreads; i++) {
			    threads[i].Join();
		    }
            DateTime dtf = DateTime.Now;
            sw.Stop();
		    System.Console.WriteLine("Finished...");
		
		    System.Console.WriteLine("Execution time " + nThreads + " thread(s): " + (sw.ElapsedMilliseconds) + "ms");
            System.Console.Read();
        }
    }
}
