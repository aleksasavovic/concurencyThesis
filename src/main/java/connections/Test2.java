package connections;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        int numThreads = 10;
        int iterationsPerThread = 1000;

        ConnectionPool semaphorePool = new ReentrantLockConnectionPool();

        long startSemaphore = System.nanoTime();
        runTest(semaphorePool, numThreads, iterationsPerThread);
        long endSemaphore = System.nanoTime();
        long elapsedSemaphore = endSemaphore - startSemaphore;

        System.out.println("Semaphore elapsed time: " + elapsedSemaphore + " ns");
    }

    private static void runTest(ConnectionPool pool, int numThreads, int iterations) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < iterations; j++) {
                    try {
                        pool.acquireConnection();
                        Thread.sleep(20); // Simulate longer work
                        pool.releaseConnection();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}