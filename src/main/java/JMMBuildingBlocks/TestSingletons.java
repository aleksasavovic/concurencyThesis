package JMMBuildingBlocks;

public class TestSingletons {
    public static void main(String[] args) {
            int numThreads = 10; // Number of threads
            int numInstancesPerThread =250000; // Number of instances to create per thread
            int totalInstances = numThreads * numInstancesPerThread;

            long startTime, endTime;

            // Test synchronized Singleton
            startTime = System.currentTimeMillis();
            Thread[] syncThreads = new Thread[numThreads];
            for (int i = 0; i < numThreads; i++) {
                syncThreads[i] = new Thread(() -> {
                    for (int j = 0; j < numInstancesPerThread; j++) {
                        Singleton.getInstance();
                    }
                });
                syncThreads[i].start();
            }

            // Wait for all threads to finish
            for (Thread thread : syncThreads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            endTime = System.currentTimeMillis();
            long syncSingletonTime = endTime - startTime;

            // Test double-checked locking Singleton
            startTime = System.currentTimeMillis();
            Thread[] dclThreads = new Thread[numThreads];
            for (int i = 0; i < numThreads; i++) {
                dclThreads[i] = new Thread(() -> {
                    for (int j = 0; j < numInstancesPerThread; j++) {
                        DoubleCheckedLockingSingleton.getInstance();
                    }
                });
                dclThreads[i].start();
            }

            // Wait for all threads to finish
            for (Thread thread : dclThreads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            endTime = System.currentTimeMillis();
            long dclSingletonTime = endTime - startTime;

            System.out.println("Singleton: " + syncSingletonTime );
            System.out.println("DCH Singleton: " + dclSingletonTime);
            System.out.println("Instances: " + totalInstances);
        }
}
