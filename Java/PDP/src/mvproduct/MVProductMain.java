package mvproduct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MVProductMain {
		
	public static void main(String[] args) throws InterruptedException{

		int nThreads = 8;
		int N = 35000;

		int[][] matrix;
		int[] vector;
		int[] resultVector;

		System.out.println("Starting...");
		String error = "matrix";
		try {
			matrix = new int[N][N];
			error = "vector";
			vector = new int[N];
			error = "resultVector";
			resultVector = new int[N];
		}
		catch(OutOfMemoryError e) {
			System.out.println("Start error: " + error);
			System.out.println(e.getMessage());
			return;
		}
		
		Random r = new Random();
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++) {
				matrix[i][j] = r.nextInt(1000);
			}
			vector[i] = r.nextInt(1000);
		}

		List<MVProduct> threads = new ArrayList<MVProduct>();
		int linesPerThread = N/nThreads;
		for(int i = 0; i < nThreads; i++) {
			threads.add(new MVProduct(i * linesPerThread, i * linesPerThread + linesPerThread - 1, N, vector, matrix, resultVector));
		}
		
		System.out.println("Starting threads...");
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < nThreads; i++) {
			threads.get(i).start();
		}
		
		for(int i = 0; i < nThreads; i++) {
			threads.get(i).join();
		}
		long stopTime = System.currentTimeMillis();
		System.out.println("Finished...");
		
		System.out.println("Execution time " + nThreads + " thread(s): " + (stopTime - startTime) + "ms");        
    }
}
	