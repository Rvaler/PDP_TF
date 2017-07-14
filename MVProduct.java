public class MVProduct extends Thread {

	int firstLine, lastLine;

	public MVProduct(int a, int b) {
		this.firstLine = a;
		this.lastLine = b;
	}

	public void run() {

		// init matrix and vector
		for(int i = firstLine; i < lastLine; i++){
			vector[i] = (i%10) + 1;
			for(int j = 0; j < N; j++) {
				matrix[i][j] = j%10 + 1;
			}
		}

		calculateForLines(firstLine, lastLine);
	}
	
	private static int N = 20000;
	private static int[][] matrix;
	private static int[] vector;
	private static int[] resultVector;
	
	public static void main(String[] args) throws InterruptedException{

		int nThreads = 2;

        matrix = new int[N][N];
        vector = new int[N];
        resultVector = new int[N];

        runExample(nThreads);
        runExample(nThreads);
        runExample(nThreads);
        runExample(nThreads);

  		// print result
    // 	for(int i = 0; i < N; i++){
  		// 	System.out.print(resultVector[i] + " ");
  		// }
    }

    private static void runExample(int nThreads) throws InterruptedException{
    	MVProduct[] threads = new MVProduct[nThreads];
    	for(int i = 0; i < nThreads; i++){
        	threads[i] = new MVProduct(i * (N/nThreads), (i + 1) * (N/nThreads));
        }

  		for(int i = 0; i < nThreads; i++){
  			threads[i].start();
  		}

  		for(int i = 0; i < nThreads; i++){
  			threads[i].join();
  		}
    }

    private static void calculateForLines(int firstLine, int lastLine) {
    	for(int i = firstLine; i < lastLine; i++) {
    		multiplyLine(i);
    	}
    }

    private static void multiplyLine(int line) {
    	int result = 0;
    	for(int i = 0; i < N; i++){
    		result = result + (vector[i] * matrix[line][i]);
    	}
    	resultVector[line] = result;
    }
}