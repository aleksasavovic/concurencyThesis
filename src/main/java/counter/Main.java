package counter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int numberOfThreads = 12;
        int numTestRuns = 10; // Number of times to run the test
        long totalAverageTime = 0L;
        Counter counter = new AtomicCounter();
        //Counter counter = new SynchronizedCounter();
        //Counter counter = new SemaphoreCounter();
        //Counter counter = new ReentrantLockCounter();
        long totalTime = 0L;
        for (int testRun = 0; testRun < numTestRuns; testRun++) {

            CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);
            ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
            Runnable incrementTask = () -> {
                for (int j = 0; j < 1000000; j++) {
                    counter.increment();
                }
            };
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < numberOfThreads; i++) {
                executorService.submit(incrementTask);
            }
            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
            long endTime = System.currentTimeMillis();
            totalTime += endTime - startTime;
            System.out.println("Test " + (testRun + 1) + " time: " + (endTime - startTime));
            System.out.println("Counter value: " + counter.getCounter());
        }

        totalAverageTime = totalTime / numTestRuns;
        System.out.println("Average time across " + numTestRuns + " runs: " + totalAverageTime);
        System.out.println("Final counter value: " + counter.getCounter());
    }
}
