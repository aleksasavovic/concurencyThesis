package atomics;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicExample {
    public static void main(String[] args) throws InterruptedException {
        class OptimisticIncrement{
            AtomicInteger counter = new AtomicInteger();
            public void increment() {
                while (true) {
                    int oldValue = counter.get();
                    int newValue = oldValue + 1;
                    if(counter.compareAndSet(oldValue,newValue))
                        break;
                }
            }
        }


        int numberOfThreads = 10;
        int numberOfIncreasesPerThread = 100000;
        OptimisticIncrement twoOperationModifier = new OptimisticIncrement();
        Thread[] thread = new Thread[numberOfThreads];
        for (
                int i = 0;
                i < numberOfThreads; i++) {
            thread[i] = new Thread(() -> {
                for (int j = 0; j < numberOfIncreasesPerThread; j++)
                    twoOperationModifier.increment();
            });
            thread[i].start();
        }
        for (
                int i = 0;
                i < numberOfThreads; i++)
            thread[i].

                    join();
        System.out.println("Vrednost brojača je " + twoOperationModifier.counter.get());
    }

}
