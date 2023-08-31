package bankAccount;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 8;
        int numberOfIterations = 10000000;

        // Test with AtomicBankAccount
        long startTime = System.currentTimeMillis();
        testWithAtomicBankAccount(numberOfThreads, numberOfIterations);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken with AtomicBankAccount: " + (endTime - startTime) + " ms");

        // Test with ReentrantBankAccount
        startTime = System.currentTimeMillis();
        testWithReentrantBankAccount(numberOfThreads, numberOfIterations);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken with ReentrantBankAccount: " + (endTime - startTime) + " ms");
    }

    private static void testWithAtomicBankAccount(int numberOfThreads, int numberOfIterations) throws InterruptedException {
        BankAccount account = new AtomicBankAccount();

        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < numberOfIterations; j++) {
                    account.withdraw(100);
                }
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();
    }

    private static void testWithReentrantBankAccount(int numberOfThreads, int numberOfIterations) throws InterruptedException {
        BankAccount account = new ReentrantBankAccount();

        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < numberOfIterations; j++) {
                    account.withdraw(100);
                }
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();
    }
}

