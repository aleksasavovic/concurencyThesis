package ConcurencyAdventage;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurencyAdvantage {

    public static void main(String[] args) throws InterruptedException {
        for (int matrixSize = 5; matrixSize < 1000; matrixSize += 10) {
            int numberOfThreads = 12;
            int[][] matrixA, matrixB, matrixThreadResult, matrixRegularResult;
            matrixA = createRandomMatrix(matrixSize, matrixSize);
            matrixB = createRandomMatrix(matrixSize, matrixSize);
            matrixThreadResult = new int[matrixSize][matrixSize];
            Thread[] threads = new Thread[numberOfThreads];
            ;
            int rowsPerThread = matrixSize / numberOfThreads;
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < numberOfThreads; i++) {
                int startRow = i * rowsPerThread;
                int endRow;
                if (i == numberOfThreads - 1)
                    endRow = matrixSize;
                else
                    endRow = startRow + rowsPerThread;
                threads[i] = new Thread(() -> {
                    multiply(matrixA, matrixB, matrixThreadResult, startRow, endRow);
                });
                threads[i].start();
            }
            for (int i = 0; i < numberOfThreads; i++)
                threads[i].join();
            long endTime = System.currentTimeMillis();
            System.out.println("matrix size: " + matrixSize);
            System.out.println("multiple threads: " + (endTime - startTime));
            matrixRegularResult = new int[matrixSize][matrixSize];
            long startTimeSingleThread = System.currentTimeMillis();
            for (int i = 0; i < matrixSize; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    for (int k = 0; k < matrixSize; k++) {
                        matrixRegularResult[i][j] += matrixA[i][k] * matrixB[k][j];
                    }
                }
            }
            long endtTimeSingleThread = System.currentTimeMillis();
            System.out.println("single thread: " + (endtTimeSingleThread - startTimeSingleThread));

        }
    }
    public static boolean checkAuthenticity(int[][] matrix1, int[][] matrix2) {
        for (int row = 0; row < matrix1.length; row++) {
            for (int col = 0; col < matrix1[row].length; col++) {
                if (matrix1[row][col] != matrix2[row][col])
                    return false;
            }
        }
        return true;
    }

    public static void multiply(int[][] matrixA, int[][] matrixB, int[][] matrixC, int startIndex, int endIndex) {
        int size = matrixA.length;
        for (int i = startIndex; i < endIndex; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
    }

    private static int[][] createRandomMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(10);
            }
        }
        return matrix;
    }

    private static void printMatrix(int[][] matrix) {
        System.out.println("Result:");
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
}
