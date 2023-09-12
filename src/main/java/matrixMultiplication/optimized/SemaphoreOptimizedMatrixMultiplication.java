package matrixMultiplication.optimized;

import matrixMultiplication.MatrixMultiplication;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreOptimizedMatrixMultiplication implements MatrixMultiplication {
    Semaphore[] mutexes;
    int size;
    int[][] resultMatrix;

    public SemaphoreOptimizedMatrixMultiplication(int size) {
        this.size = size;
        resultMatrix = new int[size][size];
        mutexes = new Semaphore[size];
        for (int i = 0; i < size; i++) {
            mutexes[i] = new Semaphore(1);
        }
    }

    @Override
    public void multiply(int[][] matrixA, int[][] matrixB, int startColumn, int endColumn) {
        for (int j = startColumn; j < endColumn; j++) {
            for (int i = 0; i < size; i++) {
                for (int k = 0; k < size; k++) {
                    try {
                        mutexes[k].acquire();
                        resultMatrix[k][i] += matrixA[k][j] * matrixB[j][i];
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        mutexes[k].release();
                    }
                }
            }
        }
    }
    @Override
    public boolean checkAuthenticity(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;
        int[][] regularResult = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < matrixA[0].length; k++) {
                    regularResult[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        for (int row = 0; row < resultMatrix.length; row++) {
            for (int col = 0; col < resultMatrix[row].length; col++) {
                if (resultMatrix[row][col] != regularResult[row][col])
                    return false;
            }
        }
        return true;
    }
}
