using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace PDP
{
    /// <summary>
    ///  Programa que multiplica uma matriz (NxN) por um vetor (N) utilizando nThreads.
    /// </summary>
    public class MVPProducMain
    {
        static void Main(string[] args)
        {
            int nThreads = 8;   // Número de threads a serem utilizadas
            int N = 35000;      // Dimensão da matriz e dos vetores

            int[,] matrix = new int[N, N];      // Matriz NxN
            int[] vector = new int[N];          // Vetor a ser multiplicado com a matriz
            int[] resultVector = new int[N];    // Vetor que armazenará o resultado
		
            Random r = new Random();

            // Inicializa a matriz e o vetor com valores randômicos
            for(int i = 0; i < N; i++)
            {
				for(int j = 0; j < N; j++) 
                {
					matrix[i,j] = r.Next(1000);
				}
				vector[i] = r.Next(1000);
            }

            // Inicializa as threads. Cada thread será responável por calcular N/nThreads linhas
		    List<Thread> threads = new List<Thread>();
		    int linesPerThread = N/nThreads;
		    for(int i = 0; i < nThreads; i++) {
			    threads.Add( new Thread(new MVProduct(i * linesPerThread, i * linesPerThread + linesPerThread, N, vector, matrix, resultVector).Run ));
		    }

            // Executa as threads
            System.Console.WriteLine("Starting threads...");
            Stopwatch sw = new Stopwatch();
            DateTime dt = DateTime.Now;     // Marca o tempo inicial de execução da multiplicação
            sw.Start();
		    for(int i = 0; i < nThreads; i++) {
			    threads[i].Start();
		    }

            // Thread main aguarda o término das threads
		    for(int i = 0; i < nThreads; i++) {
			    threads[i].Join();
		    }
            DateTime dtf = DateTime.Now;    // Marca o tempo final da execução da multiplicação
            sw.Stop();
		    System.Console.WriteLine("Finished...");

            System.Console.WriteLine("Execution time " + nThreads + " thread(s): " + (sw.ElapsedMilliseconds) + "ms");  // Imprime o tempo de execução das multiplicações
            System.Console.Read();
        }
    }
}
