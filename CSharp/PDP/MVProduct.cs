using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PDP
{
    public class MVProduct
    {
        private int firstLine;
        private int lastLine;
        private int matrixSize;

        private int[] vector;
        private int[,] matrix;
        private int[] resultVector;

        public MVProduct(int firstLine, int lastLine, int matrixSize, int[] vector, int[,] matrix, int[] resultVector)
        {
            this.firstLine = firstLine;
            this.lastLine = lastLine;
            this.vector = vector;
            this.matrix = matrix;
            this.resultVector = resultVector;
            this.matrixSize = matrixSize;
        }

        public void Run()
        {
            for (int i = firstLine; i <= lastLine; i++)
            {
                MultiplyLine(i);
            }
        }

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
