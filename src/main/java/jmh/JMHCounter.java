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

   /* @Param({"low", "moderate", "high"})
    private String contentionLevel;*/
    @Param({"intrisic","reentrant","semaphore","atomics"})
    private String synchronizationType;
    /*@Param({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "12", "14", "16", "18", "20", "25", "30", "35", "40", "45", "50"})
    private int threadCount;

*/
    private Counter counter;
    @Setup
    public void setup() {
            switch(synchronizationType){
                case "intrisic":{
                    counter = new SynchronizedCounter();
                    break;
                }
                case "reentrant":{
                    counter = new ReentrantLockCounter();
                    break;
                }
                case "semaphore":{
                    counter = new SemaphoreCounter();
                    break;
                }
                case "atomics":{
                    counter = new AtomicCounter();
                    break;
                }
            }


    }

    /*@Benchmark
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
    @Threads(4)
    public void  incrementWithFourThreads(){
        counter.increment();
    }
   /* @Benchmark
    @Threads(2)
    public void  incrementWithTwoThreads(){
        counter.increment();
    }*/
    /*@Benchmark
    @Threads(4)
    public void  incrementWith14hread(){
        counter.increment();
    }*/

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHCounter.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .timeUnit(TimeUnit.MILLISECONDS)
                .build();

        new Runner(options).run();
    }
}