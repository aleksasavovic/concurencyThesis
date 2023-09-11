package jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import producerConsumer.AtomicProducerConsumer;
import producerConsumer.ProducereConsumer;
import producerConsumer.ReentrantLockProducerConsumer;
import producerConsumer.SemaphoreProducerConsumer;
import producerConsumer.instrict.SynchronizedProducerConsumer;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JMHProducerConsumer {
    private ProducereConsumer producerConsumer;
    private static final int THREAD_COUNT = 10; // Adjust as needed
    private static final int ITERATIONS = 10000; // Adjust as needed
    private Random random;
    private ExecutorService executorService;
    CountDownLatch latch;

    @Setup
    public void setup() {
        executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        random = new Random();
        latch = new CountDownLatch(THREAD_COUNT);
        producerConsumer = new AtomicProducerConsumer();
    }

    /*@Threads(1)
    @Benchmark
    public void produceConsume(Blackhole blackhole) {
        Runnable producers = () -> {
            for (int j = 0; j < ITERATIONS; j++) {
                try {
                    int value = random.nextInt();
                    producerConsumer.produce(value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            latch.countDown();
        };
        Runnable consumers = () -> {
            for (int j = 0; j < ITERATIONS; j++) {
                try {
                    producerConsumer.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            latch.countDown();
        };
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT / 2; i++) {
            executorService.submit(producers);
            executorService.submit(consumers);
        }
        try {
            latch.await(); // Wait for all threads to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
*/
    @Group("cp")
    @GroupThreads(1)
    @Benchmark
    public void produced() throws InterruptedException {
        producerConsumer.produce(random.nextInt());
    }
    @Group("cp")
    @GroupThreads(3)
    @Benchmark
    public void consume() throws InterruptedException {
        producerConsumer.consume();
    }


    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHProducerConsumer.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .build();

        new Runner(options).run();
    }
}