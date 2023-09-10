package Semaphores;

import java.util.concurrent.Semaphore;

public class TestSemaphoreReentrancy {
    Semaphore semaphore = new Semaphore(1);

    public void m1() throws InterruptedException {
        semaphore.acquire();
        try {
            System.out.println("telo metode m1");
        } finally {
            semaphore.release();
        }
    }

    public void m2() throws InterruptedException {
        semaphore.acquire();
        try {
            System.out.println("telo metode m2");
            m1();
            System.out.println("povratak u metodu m2");
        } finally {
            semaphore.release();
        }
    }
}

