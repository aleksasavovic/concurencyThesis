package JMMBuildingBlocks;

public class VolatileCounter {
    public static void main(String[] args) {
        class Counter {
            volatile int counter;
            void increment(){
                counter++;
            }
        }
        int numberOfIterations = 10000;
        int numberOfThreads = 12;
        Counter counter = new Counter();
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < numberOfIterations; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(counter.counter);
    }
}
