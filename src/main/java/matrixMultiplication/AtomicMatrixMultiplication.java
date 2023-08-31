package matrixMultiplication;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicMatrixMultiplication implements MatrixMultiplication {
    AtomicInteger[][] resultMatrix;
    int size;

    public AtomicMatrixMultiplication(int size) {
        this.size = size;
        resultMatrix = new AtomicInteger[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                resultMatrix[i][j] = new AtomicInteger(0);
            }
        }
    }

    @Override
    public void multiply(int[][] matrixA, int[][] matrixB, int startColumn, int endColumn) {
        for (int j = startColumn; j < endColumn; j++) {
            for (int i = 0; i < size; i++) {
                for (int k = 0; k < size; k++) {
                    while (true) {
                        int oldValue = resultMatrix[k][i].get();
                        int newValue = oldValue + matrixA[k][j] * matrixB[j][i];
                        if (resultMatrix[k][i].compareAndSet(oldValue, newValue)) {
                            break;
                        }
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
                if (resultMatrix[row][col].get() != regularResult[row][col])
                    return false;
            }
        }
        return true;
    }
}
