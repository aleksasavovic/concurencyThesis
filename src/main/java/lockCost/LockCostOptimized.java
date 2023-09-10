package lockCost;


public class LockCostOptimized {
    public static void main(String[] args) {
        class Counter {
            int counter;
            void increment() {
                counter++;
            }
        }
        class SynchronizedCounter {
            int counter;
            synchronized void increment() {
                counter++;
            }
        }
            int numberOfIncrements = 25000000;
            Counter counter = new Counter();
            SynchronizedCounter synchronizedCounter = new SynchronizedCounter();

            long startTimeSync, endTimeSync;
            startTimeSync = System.currentTimeMillis();
            for (int j = 0; j < numberOfIncrements; j++) {
                synchronizedCounter.increment();
            }
            endTimeSync = System.currentTimeMillis();
            System.out.println("Synchronized Counter : " + (endTimeSync - startTimeSync));
            System.out.println("Number of increments : " + numberOfIncrements);
        }
}
