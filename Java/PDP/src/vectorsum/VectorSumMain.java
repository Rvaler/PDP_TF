package vectorsum;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

public class VectorSumMain {

	public static void main(String[] args) throws InterruptedException {
		
		int nThreads = 2;
		int N = 1000;
		int[] vector;
		
		CyclicBarrier barrier = new CyclicBarrier(nThreads + 1);
		
		try {
			vector = new int[N];
		}
		catch(OutOfMemoryError e) {
			System.out.println(e.getMessage());
			return;
		}
		
		Random r = new Random();
		for(int i = 0; i < N; i++){
			vector[i] = r.nextInt(10);
//			System.out.print(vector[i] + " ");
		}
		
		List<VectorSum> threads = new ArrayList<VectorSum>();
	
		int linesPerThread = N/nThreads;
		for(int i = 0; i < nThreads; i++) {
			threads.add(new VectorSum(i * linesPerThread, i * linesPerThread + linesPerThread - 1, N, vector, barrier));
		}
		
		for(int i = 0; i < nThreads; i++) {
			threads.get(i).start();
		}
		
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int result = 0;
		for(int i = 0; i < nThreads; i++) {
			result = result + threads.get(i).getResult();
		}
		System.out.println("SOMA " + result);
		
	}

}

