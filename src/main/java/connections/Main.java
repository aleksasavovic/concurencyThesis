package connections;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 4;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        //ConnectionPool connectionPool = new SemaphoreConnectionPool();
        ConnectionPool connectionPool = new ReentrantLockConnectionPool();
        Runnable runnable = ()->{
            for(int i=0; i<10000; i++){
                try {
                    connectionPool.acquireConnection();
                    Thread.sleep(2);
                    connectionPool.releaseConnection();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        long startTime = System.currentTimeMillis();
        for(int i=0;i<numberOfThreads;i++){
            executorService.submit(runnable);
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
