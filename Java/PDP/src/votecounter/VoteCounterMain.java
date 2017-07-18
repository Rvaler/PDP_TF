package votecounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class VoteCounterMain {
	public static void main(String[] args) {
		int nThreads = 4;
		int numberCandidates = 4;
		int numberVotes = 800;
		int[] votes = new int[numberVotes];
		
		Random r = new Random();
		for(int i = 0; i < numberVotes; i++) {
			votes[i] = r.nextInt(numberCandidates);
		}
		
		int votesPerThread = numberVotes/nThreads;
		List<VoteCounter> lstVoteCounter = new ArrayList<VoteCounter>();
		CyclicBarrier barrier = new CyclicBarrier(nThreads);
		for(int i = 0; i < nThreads; i++) {
			lstVoteCounter.add(new VoteCounter(i, votes, i * votesPerThread, i * votesPerThread + votesPerThread, numberCandidates, barrier));
		}
		
		for(VoteCounter vc : lstVoteCounter) {
			vc.start();
		}
		
		for(VoteCounter vc : lstVoteCounter) {
			try {
				vc.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
