using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace VoteCounter
{
    class VoteCounterMain
    {
        static void Main(string[] args)
        {
            int nThreads = 4;
		    int numberCandidates = 4;
		    int numberVotes = 800;
		    int[] votes = new int[numberVotes];
		
		    Random r = new Random();
		    for(int i = 0; i < numberVotes; i++) {
			    votes[i] = r.Next(numberCandidates);
		    }
		
		    int votesPerThread = numberVotes/nThreads;
		    List<Thread> lstVoteCounter = new List<Thread>();
		    Barrier barrier = new Barrier(nThreads);
		    for(int i = 0; i < nThreads; i++) {
			    lstVoteCounter.Add(new Thread(new VoteCounter(i, votes, i * votesPerThread, i * votesPerThread + votesPerThread, numberCandidates, barrier).Run));
		    }
		
		    foreach(Thread vc in lstVoteCounter) {
			    vc.Start();
		    }
		
		    foreach(Thread vc in lstVoteCounter) {
			    vc.Join();
			}

            System.Console.WriteLine("Press any button...");
            System.Console.Read();
        }
    }
}
