package votecounter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class VoteCounter extends Thread {
	private Map<Integer, Integer> votesCounted;
	
	private int[] votes;
	private int firstPos;
	private int lastPos;
	private int numberCandidates;
	private int id;
	
	CyclicBarrier barrier;
	
	public VoteCounter(int id, int[] votes, int firstPos, int lastPos, int numberCandidates, CyclicBarrier barrier) {
		super("Vote_Counter_" + id);
		this.id = id;
		this.votes = votes;
		this.firstPos = firstPos;
		this.lastPos = lastPos;
		this.numberCandidates = numberCandidates;
		this.votesCounted = new HashMap<Integer, Integer>();
		for(int i = 0; i < numberCandidates; i++) {
			this.votesCounted.put(i, 0);
		}
		
		this.barrier = barrier;
	}
	
	@Override
	public void run() {
		int counter = 0;
		for(int i = firstPos; i < lastPos; i++) {
			votesCounted.put(votes[i], votesCounted.get(votes[i]) + 1);
			
			counter++;
			if(counter % 100 == 0) {
				try {
					VoteCounter.PrintStatus(this);
					barrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getNumberCandidates() {
		return numberCandidates;
	}
	
	
	public int getCandidateVotesCounted(int candidate) {
		return votesCounted.get(candidate);
	}
	
	public synchronized static void PrintStatus(VoteCounter vc) {
		System.out.println(vc.getName() + ":");
		for(int i = 0; i < vc.getNumberCandidates(); i++) {
			System.out.println("   Candidate " + i + ": " + vc.getCandidateVotesCounted(i) + " votes.");
		}
	}

}
