using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace VoteCounter
{
    /// <summary>
    /// Thread resonsável por contar n votes de um array de votos. A cada n votos contados, imprime uma o resultado parcial da contagem.
    /// </summary>
    public class VoteCounter
    {
        /// <summary>
        /// Objeto Map utilizado para armazenar a contagem dos votos
        /// </summary>
        private Dictionary<int, int> votesCounted;
	
	    private int[] votes;
	    private int firstPos;
        private int lastPos;
        private int numberVotesPartial;
	    private int id;
	    
        public int NumberCandidates { get; set; }
	    public String Name { get; set; }

        /// <summary>
        /// Objeto do tipo CyclicBarrier utilziado para sincronizar com as outras threads o print dos resultados parciais
        /// </summary>
	    Barrier barrier;
	
        /// <summary>
        /// Contrutor da classe VoteCoutner
        /// </summary>
        /// <param name="id">id da thread</param>
        /// <param name="votes">array de votos</param>
        /// <param name="firstPos">faixa inicial de votos contados (incluso)</param>
        /// <param name="lastPos">faixa final de votos a serem contados (excluso)</param>
        /// <param name="numberCandidates">número de candidatos</param>
        /// <param name="numberVotesPartial">número de votos que devem ser contabilizados antes de uma impressão parcial</param>
        /// <param name="barrier">barreira para sincronização com as outras threads</param>
	    public VoteCounter(int id, int[] votes, int firstPos, int lastPos, int numberCandidates, int numberVotesPartial, Barrier barrier) {
		    this.Name = "Vote_Counter_" + id;
		    this.id = id;
		    this.votes = votes;
		    this.firstPos = firstPos;
		    this.lastPos = lastPos;
		    this.NumberCandidates = numberCandidates;

            //	Inicia o objeto do tipo Map com o valor 0 para cada candidato
		    this.votesCounted = new Dictionary<int, int>();
		    for(int i = 0; i < numberCandidates; i++) {
			    this.votesCounted[i] = 0;
		    }

            this.numberVotesPartial = numberVotesPartial;
		    this.barrier = barrier;
	    }
	
        /// <summary>
        /// Método responsável por executar a contagem dos votos e da impressão parcial.
        /// </summary>
	    public void Run() 
        {
		    int counter = 0;
		    for(int i = firstPos; i < lastPos; i++) 
            {
                votesCounted[votes[i]]++;   // Adiciona um voto para o canditado votes[i]
			
			    counter++;
                if (counter % numberVotesPartial == 0)  // Verifica se não contou o número necessários de votos para a impressão parcial
                {
                    VoteCounter.PrintStatus(this);  // Imprime o resultado parcial
                    barrier.SignalAndWait();    // Aguarda o término das outras threads para voltar a contar os votos
				}
		    }
	    }	
	
        /// <summary>
        /// Retorna o número de votos de um determinado candidato
        /// </summary>
        /// <param name="candidate">candidato na qual se deseja saber o número de votos</param>
        /// <returns>número de votos do candidato</returns>
	    public int CandidateVotesCounted(int candidate) {
		    return votesCounted[candidate];
	    }
	
        /// <summary>
        /// Objeto static utilizado como lock para a impressão sincronizada
        /// </summary>
        private static object Lock = new object(); 

        /// <summary>
        /// Método estático para impressão de forma sincronizada dos resultados contabilizados por um VoteCounter
        /// </summary>
        /// <param name="vc">objeto na qual se deseja saber o resultado</param>
	    public static void PrintStatus(VoteCounter vc) {
            Monitor.Enter(Lock);    // Thread tenta pegar o lock
		    try
            {
                System.Console.WriteLine(vc.Name + ":");
		        for(int i = 0; i < vc.NumberCandidates; i++) {
			        System.Console.WriteLine("   Candidate " + i + ": " + vc.CandidateVotesCounted(i) + " votes.");
		        }
            }
            finally     // Bloco finally para garantir a liberação do lock em caso de erro
            {
                Monitor.Exit(Lock); // Thread libera o lock
            }
	    }
    }
}
