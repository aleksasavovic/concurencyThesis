package jmh;

import matrixMultiplication.*;
import matrixMultiplication.lockPerfield.ReentrantLockPerFieldMatrixMultiplication;
import matrixMultiplication.lockPerfield.SemaphorePerFieldMatrixMultiplication;
import matrixMultiplication.lockPerfield.SynchronizedLockPerFieldMatrixMultiplication;
import matrixMultiplication.optimized.ReentrantLockOptimizedMatrixMultiplication;
import matrixMultiplication.optimized.SemaphoreOptimizedMatrixMultiplication;
import matrixMultiplication.optimized.SynchronizedOptimizedMatrixMultiplication;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
public class MatrixMultiplicationBenchmark {
    @Param({"500"})
    int matrixSize;

    @Param({"1","2","3","4","5","6","7","8","9","10","12","14","16","18","20","23","26","28","30"})
    int numberOfThreads;
    @Param({"ReentrantLockPerFieldMatrixMultiplication", "SemaphorePerFieldMatrixMultiplication", "SynchronizedLockPerFieldMatrixMultiplication",
            "ReentrantLockOptimizedMatrixMultiplication", "SemaphoreOptimizedMatrixMultiplication", "SynchronizedOptimizedMatrixMultiplication",
            "AtomicMatrixMultiplication", "ReentrantLockMatrixMultiplication", "SemaphoreMatrixMultiplication", "SynchronizedMatrixMultiplication"})
    String synchronizationType;


    int[][] matrixA;
    int[][] matrixB;
    MatrixMultiplication matrixMultiplication;
    ExecutorService executorService;
    CountDownLatch startLatch;
    CountDownLatch finishLatch;

    @Setup
    public void setup() {
        matrixA = createRandomMatrix(matrixSize, matrixSize);
        matrixB = createRandomMatrix(matrixSize, matrixSize);
        switch (synchronizationType) {
            case "ReentrantLockPerFieldMatrixMultiplication":
                matrixMultiplication = new ReentrantLockPerFieldMatrixMultiplication(matrixSize);
                break;
            case "SemaphorePerFieldMatrixMultiplication":
                matrixMultiplication = new SemaphorePerFieldMatrixMultiplication(matrixSize);
                break;
            case "SynchronizedLockPerFieldMatrixMultiplication":
                matrixMultiplication = new SynchronizedLockPerFieldMatrixMultiplication(matrixSize);
                break;
            case "ReentrantLockOptimizedMatrixMultiplication":
                matrixMultiplication = new ReentrantLockOptimizedMatrixMultiplication(matrixSize);
                break;
            case "SemaphoreOptimizedMatrixMultiplication":
                matrixMultiplication = new SemaphoreOptimizedMatrixMultiplication(matrixSize);
                break;
            case "SynchronizedOptimizedMatrixMultiplication":
                matrixMultiplication = new SynchronizedOptimizedMatrixMultiplication(matrixSize);
                break;
            case "AtomicMatrixMultiplication":
                matrixMultiplication = new AtomicMatrixMultiplication(matrixSize);
                break;
            case "ReentrantLockMatrixMultiplication":
                matrixMultiplication = new ReentrantLockMatrixMultiplication(matrixSize);
                break;
            case "SemaphoreMatrixMultiplication":
                matrixMultiplication = new SemaphoreMatrixMultiplication(matrixSize);
                break;
            case "SynchronizedMatrixMultiplication":
                matrixMultiplication = new SynchronizedMatrixMultiplication(matrixSize);
                break;
            default:
                throw new IllegalArgumentException("Invalid synchronization type: " + synchronizationType);
        }

    }
    @TearDown
    public  void teardown(){
        executorService.shutdown();
    }

    @Benchmark
    public void matrixMultiplicationBenchmark() throws InterruptedException {
        matrixMultiplication = new AtomicMatrixMultiplication(matrixSize);
        startLatch = new CountDownLatch(1);
        finishLatch = new CountDownLatch(numberOfThreads);
        int columnsPerThread = matrixSize / numberOfThreads;
        for (int i = 0; i < numberOfThreads; i++) {
            int startColumn = i * columnsPerThread;
            int endColumn = (i == numberOfThreads - 1) ? matrixSize : startColumn + columnsPerThread;
            Runnable multiplier = () -> {
                try {
                    startLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                matrixMultiplication.multiply(matrixA, matrixB, startColumn, endColumn);
                finishLatch.countDown();
            };
            executorService.submit(multiplier);
        }
        startLatch.countDown();
        finishLatch.await();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(MatrixMultiplicationBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .timeUnit(TimeUnit.SECONDS)
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
