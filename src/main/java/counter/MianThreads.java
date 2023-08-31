package counter;

import java.util.concurrent.CountDownLatch;

public class MianThreads {

    public static void main(String[] args) throws InterruptedException {

        int numberOfThreads = 12;
        CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);
        //ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        Counter counter = new SynchronizedCounter();
        //Counter counter = new AtomicCounter();
        //Counter counter = new SemaphoreCounter();
        //Counter counter = new ReentrantLockCounter();
        Thread[] threads = new Thread[numberOfThreads];
        long startTime = System.currentTimeMillis();
        for(int i=0;i<numberOfThreads;i++){
            threads[i]=new Thread(()->{
                for(int j=0;j<1000000;j++){
                        countDownLatch.countDown();
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter.increment();
                }
            });
            threads[i].start();
        }
        for(int i=0;i<numberOfThreads;i++){
            threads[i].join();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
        System.out.println(counter.getCounter());

    }
}