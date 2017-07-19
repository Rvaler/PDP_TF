using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PDP
{
    /// <summary>
    /// Classe que extende a classe Thread e é responsável por multiplicar uma faixa de n linhas de uma matriz NxN com um determinado vetor.
    /// </summary>
    public class MVProduct
    {
        private int firstLine;
        private int lastLine;
        private int matrixSize;

        private int[] vector;
        private int[,] matrix;
        private int[] resultVector;

        /// <summary>
        /// Construtor da classe.
        /// </summary>
        /// <param name="firstLine">faixa inicial (incluso)</param>
        /// <param name="lastLine">faixa final (excluso)</param>
        /// <param name="matrixSize">dimensão da matriz (obs: matriz deve ser NxN)</param>
        /// <param name="vector">vetor que multiplicara com a matriz (deve ter tamanho N)</param>
        /// <param name="matrix">matriz NxN</param>
        /// <param name="resultVector">vetor resultado de tamanho N</param>
        public MVProduct(int firstLine, int lastLine, int matrixSize, int[] vector, int[,] matrix, int[] resultVector)
        {
            this.firstLine = firstLine;
            this.lastLine = lastLine;
            this.vector = vector;
            this.matrix = matrix;
            this.resultVector = resultVector;
            this.matrixSize = matrixSize;
        }

        /// <summary>
        /// Método responsável por executar a multiplicação entre as faixas de linhas da matrix com o vetor.
        /// </summary>
        public void Run()
        {
            for (int i = firstLine; i < lastLine; i++)
            {
                MultiplyLine(i);
            }
        }


        /// <summary>
        /// Função responsável por multiplicar uma linha da matriz com o vetor
        /// </summary>
        /// <param name="line">linha da matriz que será multiplicada.</param>
        private void MultiplyLine(int line)
        {
            int result = 0;
            for (int i = 0; i < matrixSize; i++)
            {
                result = result + (vector[i] * matrix[line,i]);
            }
            resultVector[line] = result;
        }
    }
}
