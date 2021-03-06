# from multiprocessing import Pool

# def inner_prod(v1, v2):
#      'inner production of two vectors.'
#      sum = 0
#      for i in xrange(len(v1)):
#             sum += v1[i] * v2[i]
#      return sum

# def matmult3(m, v):
#      'matrix multiply vector by inner production.'
#      return [inner_prod(r, v) for r in m]


#!/usr/bin/env python

import numpy
import numpy.random
import numpy.linalg
import sys
import time

from multiprocessing import Process, Array

N = 0
matrix = []
vector = []

def calculate(firstLine, lastLine, vector, matrix, resultVector):
    # print("Im a process and I am calculating from %d to %d" % (firstLine, lastLine))
    calculateForLines(firstLine, lastLine, vector, matrix, resultVector)

# class MyThread(Thread):

#     firstLine = 0
#     lastLine = 0

#     def __init__(self, firstLine, lastLine):
#         Thread.__init__(self)
#         self.firstLine = firstLine
#         self.lastLine = lastLine

#     def run(self):
#         # print("Im a thread and I am calculating from %d to %d" % (self.firstLine, self.lastLine))
#         calculateForLines(self.firstLine, self.lastLine)


def multiplyLine(line, vector, matrix, resultVector):
    result = 0
    for i in range(0, len(vector)):
        result = result + (vector[i] * matrix[line][i])
    resultVector[line] = result

def calculateForLines(firstLine, lastLine, vector, matrix, resultVector):
    for i in range(firstLine, lastLine):
        multiplyLine(i, vector, matrix, resultVector)

def main(numberOfThreads, matrixSize):
    
    N = matrixSize
    resultVector = Array('i', range(N))
    
    for i in range(0, N):
        # resultVector.append(0)
        vector.append(3)
        new = []
        for j in range(0, N):
            new.append(3)
        matrix.append(new)

    processes = []
    for i in range(0, numberOfThreads):
        processes.append(Process(target=calculate, args=(i * (N/numberOfThreads), (i + 1) * (N/numberOfThreads), vector, matrix, resultVector)))
        # threads.append(MyThread(i * (N/numberOfThreads), (i + 1) * (N/numberOfThreads)))

    for i in range(0, numberOfThreads):
        processes[i].start()

    for i in range(0, numberOfThreads):
        processes[i].join()

    # print(resultVector[:])

if __name__ == "__main__":
    # Params: (number of threads, matrix size)
    sys.exit(main(int(sys.argv[1]), int(sys.argv[2])))


