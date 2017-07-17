package vectorsum;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

public class VectorSum extends Thread {
	
	private int firstPos;
	private int lastPos;
	private int[] vector;
	private int result;
	private CyclicBarrier barrier;
	
	public VectorSum(int firstPos, int lastPos, int matrixSize, int[] vector, CyclicBarrier barrier) {
		this.firstPos = firstPos;
		this.lastPos = lastPos;
		this.vector = vector;
		this.barrier = barrier;
	}	

	public void run() {
		this.setResult(sum(firstPos, lastPos));
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int sum(int firstPos, int lastPos) {
		
		int resultAux = 0;
		for(int i = firstPos; i <= lastPos; i++) {
			resultAux = resultAux + vector[i];
		}
		
		return resultAux;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
}
