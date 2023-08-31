package matrixMultiplication;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockMatrixMultiplication implements MatrixMultiplication {
    Lock lock = new ReentrantLock();
    int size;
    int [][]resultMatrix;

    public ReentrantLockMatrixMultiplication(int size){
        this.size = size;
        resultMatrix = new int[size][size];
    }

    @Override
    public void multiply(int[][] matrixA, int[][] matrixB, int startColumn, int endColumn) {
        int length = matrixA.length;
        for (int j = startColumn; j < endColumn; j++) {
            for (int i = 0; i < length; i++) {
                for (int k = 0; k < length; k++) {
                    lock.lock();
                    resultMatrix[k][i] += matrixA[k][j] * matrixB[j][i];
                    lock.unlock();
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
//        printMatrix(result);
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        printMatrix(regularResult);
        for (int row = 0; row < resultMatrix.length; row++) {
            for (int col = 0; col < resultMatrix[row].length; col++) {
                if (resultMatrix[row][col] != regularResult[row][col])
                    return false;
            }
        }
        return true;
    }
}
