package producerConsumer;


import producerConsumer.instrict.SynchronizedProducerConsumer;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int min = 1;
        int max = 10000;
        Random random = new Random();

        int numberOfThreads = 10;
        //ProducereConsumer producereConsumer = new SynchronizedProducerConsumer();
        //ProducereConsumer producereConsumer = new ImprovedSynchronizedProducerConsumer();
        ProducereConsumer producereConsumer = new ReentrantLockProducerConsumer();
        //ProducereConsumer producereConsumer = new SemaphoreProducerConsumer();
        //ProducereConsumer producereConsumer = new AtomicProducerConsumer();
        //ProducereConsumer producereConsumer = new BlockingProducerConsumer();
        CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        Runnable producers = () -> {
            for (int j = 0; j < 100000; j++) {
                try {
                    int value = random.nextInt(max - min + 1) + min;
                    producereConsumer.produce(value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable consumers = () -> {
            for (int j = 0; j < 100000; j++) {
                try {
                    System.out.println(producereConsumer.consume());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads / 2; i++) {
            executorService.submit(producers);
            executorService.submit(consumers);
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
