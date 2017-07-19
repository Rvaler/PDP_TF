package mvproduct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Programa que multiplica uma matriz (NxN) por um vetor (N) utilizando nThreads.
 * 
 * @author Bruno Freitas, Rafael Valer
 * 
 */
public class MVProductMain {
		
	public static void main(String[] args) throws InterruptedException{

		int nThreads = 8;	// Número de threads a serem utilizadas
		int N = 35000;		// Dimensão da matriz e dos vetores

		int[][] matrix;		// Matriz NxN
		int[] vector;		// Vetor a ser multiplicado com a matriz
		int[] resultVector; // Vetor que armazenará o resultado

		System.out.println("Starting...");
		String error = "matrix";
		try {
			matrix = new int[N][N];		// Inicia a matriz
			error = "vector";
			vector = new int[N];		// Inicia o vetor
			error = "resultVector";
			resultVector = new int[N];	// Inicia o vetor resultado
		}
		catch(OutOfMemoryError e) {		// Captura erros de estouro de memória
			System.out.println("Start error: " + error);	// Informa qual das estruturas de dados ocorreu o estouro de memória
			System.out.println(e.getMessage());
			return;
		}
		
		// Inicializa a matriz e o vetor com valores randômicos
		Random r = new Random();
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++) {
				matrix[i][j] = r.nextInt(1000);
			}
			vector[i] = r.nextInt(1000);
		}

		// Inicializa as threads. Cada thread será responável por calcular N/nThreads linhas
		List<MVProduct> threads = new ArrayList<MVProduct>();
		int linesPerThread = N/nThreads;
		for(int i = 0; i < nThreads; i++) {
			threads.add(new MVProduct(i * linesPerThread, i * linesPerThread + linesPerThread, N, vector, matrix, resultVector));
		}
		
		// Executa as threads
		System.out.println("Starting threads...");
		long startTime = System.currentTimeMillis();	// Marca o tempo inicial de execução da multiplicação
		for(int i = 0; i < nThreads; i++) {
			threads.get(i).start();		
		}
		
		// Thread main aguarda o término das threads
		for(int i = 0; i < nThreads; i++) {
			threads.get(i).join();
		}
		long stopTime = System.currentTimeMillis();		// Marca o tempo final da execução da multiplicação
		System.out.println("Finished...");
		
		System.out.println("Execution time " + nThreads + " thread(s): " + (stopTime - startTime) + "ms");	// Imprime o tempo de execução das multiplicações       
    }
}
	