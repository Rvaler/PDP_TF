package votecounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;


/**
 * Programa que conta a quantidade de votos recebidos por cada candidato utilizando multithread e � imprimido
 * na tela um resultado parcial ap�s cada thread contar n votos
 * 
 * @author Bruno Freitas, Rafael Valer
 *
 */
public class VoteCounterMain {
	public static void main(String[] args) {
		
		int nThreads = 4;				// N�mero de threads
		int numberCandidates = 4;		// N�mero de candidatos
		int numberVotes = 800;			// N�mero total de votos
		int numberVotesPartial = 100;	// N�mero de quantos votos devem ser contados at� a thread imprimir um resultado parcial
		
		int[] votes = new int[numberVotes];	// Lista de votes a serem contados		
		
		// Inicia a lista de votos com valores rand�micos
		Random r = new Random();
		for(int i = 0; i < numberVotes; i++) {
			votes[i] = r.nextInt(numberCandidates);
		}
		
		int votesPerThread = numberVotes/nThreads;	// N�mero de votos que cada thread dever� contar
		
		CyclicBarrier barrier = new CyclicBarrier(nThreads);	// Barreira utilizado para sincronizar as threads durante o resultado parcial
		
		// Inicializa��o das threads
		List<VoteCounter> lstVoteCounter = new ArrayList<VoteCounter>();
		for(int i = 0; i < nThreads; i++) {
			lstVoteCounter.add(new VoteCounter(i, votes, i * votesPerThread, i * votesPerThread + votesPerThread, numberCandidates, numberVotesPartial, barrier));
		}
		
		// Executa as threads
		for(VoteCounter vc : lstVoteCounter) {
			vc.start();
		}
		
		// Aguarda o t�rmino das threads
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
