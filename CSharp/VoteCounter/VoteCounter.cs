using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace VoteCounter
{
    public class VoteCounter
    {
        private Dictionary<int, int> votesCounted;
	
	    private int[] votes;
	    private int firstPos;
	    private int lastPos;
	    private int id;
	    
        public int NumberCandidates { get; set; }
	    public String Name { get; set; }

	    Barrier barrier;
	
	    public VoteCounter(int id, int[] votes, int firstPos, int lastPos, int numberCandidates, Barrier barrier) {
		    this.Name = "Vote_Counter_" + id;
		    this.id = id;
		    this.votes = votes;
		    this.firstPos = firstPos;
		    this.lastPos = lastPos;
		    this.NumberCandidates = numberCandidates;

		    this.votesCounted = new Dictionary<int, int>();
		    for(int i = 0; i < numberCandidates; i++) {
			    this.votesCounted[i] = 0;
		    }
		
		    this.barrier = barrier;
	    }
	
	    public void Run() {
		    int counter = 0;
		    for(int i = firstPos; i < lastPos; i++) {
			    votesCounted[votes[i]]++;
			
			    counter++;
			    if(counter % 100 == 0) {
					VoteCounter.PrintStatus(this);
				    barrier.SignalAndWait();
				}
		    }
	    }	
	
	    public int CandidateVotesCounted(int candidate) {
		    return votesCounted[candidate];
	    }
	
        private static object Lock = new object();
	    public static void PrintStatus(VoteCounter vc) {
            Monitor.Enter(Lock);
		    try
            {
                System.Console.WriteLine(vc.Name + ":");
		        for(int i = 0; i < vc.NumberCandidates; i++) {
			        System.Console.WriteLine("   Candidate " + i + ": " + vc.CandidateVotesCounted(i) + " votes.");
		        }
            }
            finally{
                Monitor.Exit(Lock);
            }
	    }
    }
}
