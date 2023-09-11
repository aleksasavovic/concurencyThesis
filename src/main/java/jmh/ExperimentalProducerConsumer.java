package jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import producerConsumer.AtomicProducerConsumer;
import producerConsumer.ProducereConsumer;
import producerConsumer.ReentrantLockProducerConsumer;
import producerConsumer.SemaphoreProducerConsumer;
import producerConsumer.instrict.SynchronizedProducerConsumer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Threads(1) // Adjust the number of threads as needed
public class ExperimentalProducerConsumer {

    private SemaphoreProducerConsumer producerConsumer;
    private int numItems = 10000000; // Adjust this based on your testing requirements
    private int numProducers; // Adjust the number of producer threads
    private int numConsumers; // Adjust the number of consumer threads
    private int numThreads;
    private int operationPerThread;
    CountDownLatch startLatch;
    CountDownLatch finishLatch;
    ExecutorService executor;

    @Setup
    public void setup() {
        numThreads = 10;
        numConsumers = numThreads / 2;
        numProducers = numThreads / 2;
        operationPerThread = numItems / numThreads;
        executor = Executors.newFixedThreadPool(numThreads);
        producerConsumer = new SemaphoreProducerConsumer();
    }
    @TearDown
    public void tearDown(){
        executor.shutdown();
    }

    @Benchmark
    public void testProducerConsumer() throws InterruptedException {
        startLatch = new CountDownLatch(1);
        finishLatch = new CountDownLatch(numThreads);
        for (int i = 0; i < numProducers; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                for (int j = 0; j < operationPerThread; j++) {
                    int item = (int) (Math.random() * 100);
                    try {
                        producerConsumer.produce(item);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                finishLatch.countDown();
            });
        }
        for (int i = 0; i < numConsumers; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                for (int j = 0; j < operationPerThread; j++) {
                    try {
                        producerConsumer.consume();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                finishLatch.countDown();
            });
        }
        startLatch.countDown();
        finishLatch.await();
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(ExperimentalProducerConsumer.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .build();

        new Runner(options).run();
    }
}
