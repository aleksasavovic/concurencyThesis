package jmh;
import counter.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JMHCounter {
    private Counter counter;
   /* @Param({"low", "moderate", "high"})
    private String contentionLeve
    @Param({"intrisic","reentrant","semaphore","atomics"})
    private String synchronizationType;/*
    @Param({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "12", "14", "16", "18", "20", "25", "30", "35", "40", "45", "50"})
    private int threadCount;



    @Setup
    public void setup() {
        switch (synchronizationType) {
            case "intrisic": {
                counter = new SynchronizedCounter();
                break;
            }
            case "reentrant": {
                counter = new ReentrantLockCounter();
                break;
            }
            case "semaphore": {
                counter = new SemaphoreCounter();
                break;
            }
            case "atomics": {
                counter = new AtomicCounter();
                break;
            }
        }
    }

*/
    @Setup
    public  void setup(){
        counter = new AtomicCounter();
    }

    /*
    @Benchmark
    public void incrementBenchmark() {
        switch (contentionLevel) {
            case "low":
                for (int i = 0; i < 10000; i++) {
                    Math.sqrt(i);
                }
                counter.increment();
                break;
            case "moderate":
                for (int i = 0; i < 1000; i++) {
                    Math.sqrt(i);
                }
                counter.increment();
                break;
            case "high":
                // High contention: Introduce heavy contention using a loop
                counter.increment();
                break;
        }
        counter.increment();
    }*/


    @Benchmark
    @Threads(1)
    public void oneThreadBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(2)
    public void twoThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(3)
    public void threeThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(4)
    public void fourThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(5)
    public void fiveThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(6)
    public void sixThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(7)
    public void sevenThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(8)
    public void eightThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(9)
    public void nineThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(10)
    public void tenThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(12)
    public void twelveThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(14)
    public void fourteenThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(16)
    public void sixteenThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(18)
    public void eighteenThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(20)
    public void twentyThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(25)
    public void twentyFiveThreadsBenchmark() {
        counter.increment();
    }

    @Benchmark
    @Threads(30)
    public void thirtyThreadsBenchmark() {
        counter.increment();
    }
    /*@Benchmark
    @Threads(4)
    public void  incrementWith14hread(){
        counter.increment();
    }*/

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHCounter.class.getSimpleName())
                .forks(2)
                .warmupIterations(5)
                .measurementIterations(5)
                .timeUnit(TimeUnit.MILLISECONDS)
                .build();

        new Runner(options).run();
    }
}