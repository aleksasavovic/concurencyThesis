package readerWritter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int numberOfThreads = 20;
        ReaderWritter readerWritter = new ReentrantLockReaderWritter();
        //ReaderWritter readerWritter = new AtomicReaderWritter();
        CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        Runnable readers = () -> {
            for (int j = 0; j <1000; j++) {
                readerWritter.read();
            }
        };
        Runnable writters = () -> {
            for (int j = 0; j < 200; j++) {
                    readerWritter.write();
            }
        };
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads-2; i++) {
            executorService.submit(readers);
        }
        executorService.submit(writters);
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
