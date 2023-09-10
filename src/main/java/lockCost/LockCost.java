package lockCost;


public class LockCost {
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
            int numberOfIncrements = 50000;
            Counter counter = new Counter();
            SynchronizedCounter synchronizedCounter = new SynchronizedCounter();
            long startTime, endTime;

            startTime = System.currentTimeMillis();

            for (int j = 0; j < numberOfIncrements; j++) {
                counter.increment();
            }

            endTime = System.currentTimeMillis();

            long startTimeSync, endTimeSync;
            startTimeSync = System.currentTimeMillis();
            for (int j = 0; j < numberOfIncrements; j++) {
                synchronizedCounter.increment();
            }
            endTimeSync = System.currentTimeMillis();
            System.out.println("Counter : " + (endTime - startTime));
            System.out.println("Synchronized Counter : " + (endTimeSync - startTimeSync));
            System.out.println("Number of increments : " + numberOfIncrements);
        }
}
