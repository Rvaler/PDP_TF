
public class MVProduct extends Thread {

	private int firstLine;
	private int lastLine;
	private int matrixSize;
	
	private int[] vector;
	private int[][] matrix;	
	private int[] resultVector;
	
	public MVProduct(int firstLine, int lastLine, int matrixSize, int[] vector, int[][] matrix, int[] resultVector) {
		this.firstLine = firstLine;
		this.lastLine = lastLine;
		this.vector = vector;
		this.matrix = matrix;
		this.resultVector = resultVector;
		this.matrixSize = matrixSize;
	}	

	public void run() {
		for(int i = firstLine; i <= lastLine; i++) {
			multiplyLine(i);
		}
	}
	
	private void multiplyLine(int line) {
		int result = 0;
    	for(int i = 0; i < matrixSize; i++){
    		result = result + (vector[i] * matrix[line][i]);
    	}
    	resultVector[line] = result;
	}
}