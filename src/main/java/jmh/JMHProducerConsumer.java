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
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JMHProducerConsumer {
    private ProducereConsumer producerConsumer;
    private static final int MIN = 1;
    private static final int max = 10000;
    private Random random;

    @Setup
    public void setup() {
        producerConsumer = new AtomicProducerConsumer();
        random = new Random();
    }


    @Threads(2)
    @Benchmark
    public void th2(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(4)
    @Benchmark
    public void th4(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(6)
    @Benchmark
    public void th6(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(8)
    @Benchmark
    public void th8(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(10)
    @Benchmark
    public void th10(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(12)
    @Benchmark
    public void th12(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(14)
    @Benchmark
    public void th14(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(16)
    @Benchmark
    public void th16(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(18)
    @Benchmark
    public void th18(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(20)
    @Benchmark
    public void th20(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(22)
    @Benchmark
    public void th22(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(24)
    @Benchmark
    public void th24(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(26)
    @Benchmark
    public void th26(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(28)
    @Benchmark
    public void th28(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }
    @Threads(30)
    @Benchmark
    public void th30(Blackhole blackhole) throws InterruptedException {
        int value = random.nextInt();
        if (Thread.currentThread().getId() % 2 == 0) {
            producerConsumer.produce(value);
        } else {
            blackhole.consume(producerConsumer.consume());
        }
    }




    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHProducerConsumer.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .timeUnit(TimeUnit.MILLISECONDS)
                .build();

        new Runner(options).run();
    }
}