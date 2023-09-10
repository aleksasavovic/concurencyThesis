package Semaphores;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        TestSemaphoreReentrancy testSemaphoreReentrancy = new TestSemaphoreReentrancy();
        Thread thread = new Thread(()->{
            try {
                testSemaphoreReentrancy.m2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(1000);//
        testSemaphoreReentrancy.semaphore.release();
        thread.join();
        System.out.println(testSemaphoreReentrancy.semaphore.availablePermits());
    }
}
