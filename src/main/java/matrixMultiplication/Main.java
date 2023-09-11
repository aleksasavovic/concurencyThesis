package matrixMultiplication;

import matrixMultiplication.lockPerfield.ReentrantLockPerFieldMatrixMultiplication;
import matrixMultiplication.optimized.ReentrantLockOptimizedMatrixMultiplication;
import matrixMultiplication.optimized.SynchronizedOptimizedMatrixMultiplication;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final int matrixSize = 500;
        final int numberOfThreads = 12;
        //MatrixMultiplication matrixMultiplication = new SynchronizedMatrixMultiplication(matrixSize);
        //MatrixMultiplication matrixMultiplication = new ReentrantLockMatrixMultiplication(matrixSize);
        //MatrixMultiplication matrixMultiplication = new SynchronizedLockPerFieldMatrixMultiplication(matrixSize);
        //MatrixMultiplication matrixMultiplication = new ReentrantLockPerFieldMatrixMultiplication(matrixSize);
        //MatrixMultiplication matrixMultiplication = new SynchronizedOptimizedMatrixMultiplication(matrixSize);
        //MatrixMultiplication matrixMultiplication = new ReentrantLockOptimizedMatrixMultiplication(matrixSize);
        MatrixMultiplication matrixMultiplication = new AtomicMatrixMultiplication(matrixSize);

        int[][] matrixA, matrixB;
        matrixA = createRandomMatrix(matrixSize, matrixSize);
        matrixB = createRandomMatrix(matrixSize, matrixSize);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        int columnsPerThread = matrixSize / numberOfThreads;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; i++) {
            int startColumn = i * columnsPerThread;
            int endColumn;
            if (i == numberOfThreads - 1)
                endColumn = matrixSize;
            else
                endColumn = startColumn + columnsPerThread;
            Runnable mupltiplier = () -> {
                matrixMultiplication.multiply(matrixA, matrixB, startColumn, endColumn);
            };
            executorService.submit(mupltiplier);

        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);

        long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);


    }

    private static boolean checkAuthenticity(int[][] matrixA, int[][] matrixB, int[][] result) {
        int size = matrixA.length;
        int[][] regularResult = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < matrixA[0].length; k++) {
                    regularResult[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        printMatrix(result);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        printMatrix(regularResult);
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                if (result[row][col] != regularResult[row][col])
                    return false;
            }
        }
        return true;

    }

    private static void printMatrix(int[][] matrix) {
        int size = matrix.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
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
}
