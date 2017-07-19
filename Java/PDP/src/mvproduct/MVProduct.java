package mvproduct;

/**
 * Classe que extende a classe Thread e é responsável por multiplicar uma faixa de n linhas de uma matriz NxN com um determinado vetor.
 * 
 * @author Bruno Freitas, Rafael Valer
 * 
 */
public class MVProduct extends Thread {

	private int firstLine;
	private int lastLine;
	private int matrixSize;
	
	private int[] vector;
	private int[][] matrix;	
	private int[] resultVector;
	
	/**
	 * Construtor da classe.
	 * @param firstLine - faixa inicial (incluso)
	 * @param lastLine - faixa final (excluso)
	 * @param matrixSize - dimensão da matriz (obs: matriz deve ser NxN)
	 * @param vector - vetor que multiplicara com a matriz (deve ter tamanho N)
	 * @param matrix - matriz NxN
	 * @param resultVector - vetor resultado de tamanho N
	 */
	public MVProduct(int firstLine, int lastLine, int matrixSize, int[] vector, int[][] matrix, int[] resultVector) {
		this.firstLine = firstLine;
		this.lastLine = lastLine;
		this.vector = vector;
		this.matrix = matrix;
		this.resultVector = resultVector;
		this.matrixSize = matrixSize;
	}	

	/**
	 * Override do método run da classe Thread. 
 	 * Método responsável por executar a multiplicação entre as faixas de linhas da matrix com o vetor.
	 */
	@Override
	public void run() {
		for(int i = firstLine; i < lastLine; i++) {
			multiplyLine(i);
		}
	}
	
	/**
	 * Função responsável por multiplicar uma linha da matriz com o vetor
	 * @param line: linha da matriz que será multiplicada.
	 */
	private void multiplyLine(int line) {
		int result = 0;
    	for(int i = 0; i < matrixSize; i++){
    		result = result + (vector[i] * matrix[line][i]);
    	}
    	resultVector[line] = result;
	}
}