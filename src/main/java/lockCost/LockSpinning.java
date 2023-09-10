package lockCost;

public class LockSpinning {
   public static void main(String[] args) {
        class Counter {
            int counter;
            synchronized void increment() {
                counter++;
            }
        }
       int numberOfIterations = 100000;
       int numberOfThreads = 12;
       Counter counter = new Counter();
       Thread[] threads = new Thread[numberOfThreads];
       long startTime = System.currentTimeMillis();
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
       long endTime = System.currentTimeMillis();
       System.out.println(endTime-startTime);
       System.out.println(counter.counter);
   }
}