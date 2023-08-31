package matrixMultiplication.lockPerfield;

import matrixMultiplication.MatrixMultiplication;

public class SynchronizedLockPerFieldMatrixMultiplication implements MatrixMultiplication {
    Object[][] locks;
    int size;
    int[][] resultMatrix;
    public SynchronizedLockPerFieldMatrixMultiplication(int size){
        this.size = size;
        resultMatrix = new int[size][size];
        locks = new Object[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                locks[i][j] = new Object();
            }
        }
    }

    @Override
    public void multiply(int[][] matrixA, int[][] matrixB, int startColumn, int endColumn) {
        for (int j = startColumn; j < endColumn; j++) {
            for (int i = 0; i < size; i++) {
                for (int k = 0; k < size; k++) {
                    synchronized (locks[k][i]) {
                        resultMatrix[k][i] += matrixA[k][j] * matrixB[j][i];
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
