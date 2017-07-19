package votecounter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 * Thread resons�vel por contar n votes de um array de votos. A cada n votos contados, imprime uma o resultado parcial da contagem.
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
	 * @param numberCandidates - n�mero de candidatos
	 * @param numberVotesPartial - n�mero de votos que devem ser contabilizados antes de uma impress�o parcial
	 * @param barrier - barreira para sincroniza��o com as outras threads
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
	 * Override do m�todo run da classe Thread. 
	 * M�todo respons�vel por executar a contagem dos votos e da impress�o parcial.
	 */
	@Override
	public void run() {
		int counter = 0;
		for(int i = firstPos; i < lastPos; i++) {
			votesCounted.put(votes[i], votesCounted.get(votes[i]) + 1);	// Adiciona um voto para o canditado votes[i]
			
			counter++;
			if(counter % numberVotesPartial == 0) {	// Verifica se n�o contou o n�mero necess�rios de votos para a impress�o parcial
				try {
					VoteCounter.PrintStatus(this);	// Imprime o resultado parcial
					
					barrier.await();	// Aguarda o t�rmino das outras threads para voltar a contar os votos
					
				} catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Retorna o n�mero de candidatos.
	 * @return n�mero de candidatos
	 */
	public int getNumberCandidates() {
		return numberCandidates;
	}
	
	
	/**
	 * Retorna o n�mero de votos de um determinado candidato
	 * @param candidate - candidato na qual se deseja saber o n�mero de votos
	 * @return n�mero de votos do candidato
	 */
	public int getCandidateVotesCounted(int candidate) {
		return votesCounted.get(candidate);
	}
	
	
	/**
	 * M�todo est�tico para impress�o de forma sincronizada dos resultados contabilizados por um VoteCounter
	 * @param vc - objeto na qual se deseja saber o resultado
	 */
	public synchronized static void PrintStatus(VoteCounter vc) {
		System.out.println(vc.getName() + ":");
		for(int i = 0; i < vc.getNumberCandidates(); i++) {
			System.out.println("   Candidate " + i + ": " + vc.getCandidateVotesCounted(i) + " votes.");
		}
	}
}
