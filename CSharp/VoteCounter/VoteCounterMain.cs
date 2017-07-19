using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace VoteCounter
{
    /// <summary>
    /// Programa que conta a quantidade de votos recebidos por cada candidato utilizando multithread e é imprimido
    /// na tela um resultado parcial após cada thread contar n votos
    /// </summary>
    class VoteCounterMain
    {
        static void Main(string[] args)
        {
            int nThreads = 4;               // Número de threads
            int numberCandidates = 4;		// Número de candidatos
            int numberVotes = 800;			// Número total de votos
            int numberVotesPartial = 100;	// Número de quantos votos devem ser contados até a thread imprimir um resultado parcial

            int[] votes = new int[numberVotes]; // Lista de votes a serem contados

            // Inicia a lista de votos com valores randômicos
		    Random r = new Random();
		    for(int i = 0; i < numberVotes; i++) {
			    votes[i] = r.Next(numberCandidates);
		    }

            int votesPerThread = numberVotes / nThreads;  // Número de votos que cada thread deverá contar
		   
            Barrier barrier = new Barrier(nThreads);      // Barreira utilizado para sincronizar as threads durante o resultado parcial

            // Inicialização das threads
            List<Thread> lstVoteCounter = new List<Thread>();
            for (int i = 0; i < nThreads; i++)
            {
                lstVoteCounter.Add(new Thread(new VoteCounter(i, votes, i * votesPerThread, i * votesPerThread + votesPerThread, numberCandidates, numberVotesPartial, barrier).Run));
		    }

            // Executa as threads
            foreach (Thread vc in lstVoteCounter)
            {
			    vc.Start();
		    }

            // Aguarda o término das threads
            foreach (Thread vc in lstVoteCounter)
            {
			    vc.Join();
			}

            System.Console.WriteLine("Press any button...");
            System.Console.Read();
        }
    }
}
