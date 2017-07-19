package votecounter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 * Thread resonsável por contar n votes de um array de votos. A cada n votos contados, imprime uma o resultado parcial da contagem.
 * @author Bruno Freitas, Rafael Valer
 *
 */
public class VoteCounter extends Thread {
	/**
	 * Objeto Map utilizado para armazenar a contagem dos votos
	 */
	private Map<Integer, Integer> votesCounted;
	
	private int[] votes;
	private int firstPos;
	private int lastPos;
	private int numberCandidates;
	private int numberVotesPartial;
	private int id;
	
	/**
	 * Objeto do tipo CyclicBarrier utilziado para sincronizar com as outras threads o print dos resultados parciais
	 */
	CyclicBarrier barrier;
	
	/**
	 * Contrutor da classe VoteCoutner
	 * @param id - id da thread
	 * @param votes - array de votos
	 * @param firstPos - faixa inicial de votos contados (incluso)
	 * @param lastPos - faixa final de votos a serem contados (excluso)
	 * @param numberCandidates - número de candidatos
	 * @param numberVotesPartial - número de votos que devem ser contabilizados antes de uma impressão parcial
	 * @param barrier - barreira para sincronização com as outras threads
	 */
	public VoteCounter(int id, int[] votes, int firstPos, int lastPos, int numberCandidates, int numberVotesPartial, CyclicBarrier barrier) {
		super("Vote_Counter_" + id);
		this.id = id;
		this.votes = votes;
		this.firstPos = firstPos;
		this.lastPos = lastPos;
		this.numberCandidates = numberCandidates;
		
		//	Inicia o objeto do tipo Map com o valor 0 para cada candidato
		this.votesCounted = new HashMap<Integer, Integer>();
		for(int i = 0; i < numberCandidates; i++) {
			this.votesCounted.put(i, 0);
		}
		
		this.numberVotesPartial = numberVotesPartial;
		this.barrier = barrier;
	}
	
	/**
	 * Override do método run da classe Thread. 
	 * Método responsável por executar a contagem dos votos e da impressão parcial.
	 */
	@Override
	public void run() {
		int counter = 0;
		for(int i = firstPos; i < lastPos; i++) {
			votesCounted.put(votes[i], votesCounted.get(votes[i]) + 1);	// Adiciona um voto para o canditado votes[i]
			
			counter++;
			if(counter % numberVotesPartial == 0) {	// Verifica se não contou o número necessários de votos para a impressão parcial
				try {
					VoteCounter.PrintStatus(this);	// Imprime o resultado parcial
					
					barrier.await();	// Aguarda o término das outras threads para voltar a contar os votos
					
				} catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Retorna o número de candidatos.
	 * @return número de candidatos
	 */
	public int getNumberCandidates() {
		return numberCandidates;
	}
	
	
	/**
	 * Retorna o número de votos de um determinado candidato
	 * @param candidate - candidato na qual se deseja saber o número de votos
	 * @return número de votos do candidato
	 */
	public int getCandidateVotesCounted(int candidate) {
		return votesCounted.get(candidate);
	}
	
	
	/**
	 * Método estático para impressão de forma sincronizada dos resultados contabilizados por um VoteCounter
	 * @param vc - objeto na qual se deseja saber o resultado
	 */
	public synchronized static void PrintStatus(VoteCounter vc) {
		System.out.println(vc.getName() + ":");
		for(int i = 0; i < vc.getNumberCandidates(); i++) {
			System.out.println("   Candidate " + i + ": " + vc.getCandidateVotesCounted(i) + " votes.");
		}
	}
}
