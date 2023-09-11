package producerConsumer;


import producerConsumer.instrict.SynchronizedProducerConsumer;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int min = 1;
        int max = 10000000;
        AtomicInteger cnt = new AtomicInteger();

        int numberOfThreads = 10;
        //ProducereConsumer producereConsumer = new SynchronizedProducerConsumer();
        //ProducereConsumer producereConsumer = new ImprovedSynchronizedProducerConsumer();
        //ProducereConsumer producereConsumer = new ReentrantLockProducerConsumer();
        ProducereConsumer producereConsumer = new SemaphoreProducerConsumer();
        //ProducereConsumer producereConsumer = new AtomicProducerConsumer();
        //ProducereConsumer producereConsumer = new BlockingProducerConsumer();
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);


        Runnable consumers = () -> {

        };
        long startTime = System.currentTimeMillis();
        final CountDownLatch startLatch = new CountDownLatch(0);
        final CountDownLatch finishLatch = new CountDownLatch(numberOfThreads);
        for (int i = 0; i < numberOfThreads / 2; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                for (int j = 0; j < max / numberOfThreads; j++) {
                    int item = (int) (Math.random() * 100);
                    try {
                        producereConsumer.produce(item);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                finishLatch.countDown();
            });
        }
        for (int i = 0; i < numberOfThreads / 2; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                for (int j = 0; j < max / numberOfThreads; j++) {
                    try {
                        producereConsumer.consume();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                finishLatch.countDown();
            });
        }
        startLatch.countDown();
        finishLatch.await();
        long endTime = System.currentTimeMillis();
        executorService.shutdown();
        System.out.println(endTime - startTime);
    }
}
