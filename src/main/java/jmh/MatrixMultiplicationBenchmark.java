package jmh;

import matrixMultiplication.AtomicMatrixMultiplication;
import matrixMultiplication.MatrixMultiplication;
import matrixMultiplication.SynchronizedMatrixMultiplication;
import matrixMultiplication.lockPerfield.ReentrantLockPerFieldMatrixMultiplication;
import matrixMultiplication.lockPerfield.SynchronizedLockPerFieldMatrixMultiplication;
import matrixMultiplication.optimized.ReentrantLockOptimizedMatrixMultiplication;
import matrixMultiplication.optimized.SynchronizedOptimizedMatrixMultiplication;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class MatrixMultiplicationBenchmark {
    @Param({"500"})
    int matrixSize;

    @Param({"12"})
    int numberOfThreads;

    int[][] matrixA;
    int[][] matrixB;
    MatrixMultiplication matrixMultiplication;

    @Setup
    public void setup() {
        matrixA = createRandomMatrix(matrixSize, matrixSize);
        matrixB = createRandomMatrix(matrixSize, matrixSize);

    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 1)
    public void matrixMultiplicationBenchmark() throws InterruptedException {
        //MatrixMultiplication matrixMultiplication = new SynchronizedMatrixMultiplication(matrixSize);
        //MatrixMultiplication matrixMultiplication = new ReentrantLockMatrixMultiplication(matrixSize);
        //MatrixMultiplication matrixMultiplication = new SynchronizedLockPerFieldMatrixMultiplication(matrixSize);
        //MatrixMultiplication matrixMultiplication = new ReentrantLockPerFieldMatrixMultiplication(matrixSize);
        //MatrixMultiplication matrixMultiplication = new SynchronizedOptimizedMatrixMultiplication(matrixSize);
        //MatrixMultiplication matrixMultiplication = new ReentrantLockOptimizedMatrixMultiplication(matrixSize);
        MatrixMultiplication matrixMultiplication = new AtomicMatrixMultiplication(matrixSize);

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        int columnsPerThread = matrixSize / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            int startColumn = i * columnsPerThread;
            int endColumn = (i == numberOfThreads - 1) ? matrixSize : startColumn + columnsPerThread;

            Runnable multiplier = () -> {
                matrixMultiplication.multiply(matrixA, matrixB, startColumn, endColumn);
            };

            executorService.submit(multiplier);
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(MatrixMultiplicationBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .timeUnit(TimeUnit.MILLISECONDS)
                .build();
        new Runner(options).run();
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
