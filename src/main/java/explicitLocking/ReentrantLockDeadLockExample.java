package explicitLocking;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDeadLockExample {
    Lock lock = new ReentrantLock();

    public void m1() {
        lock.lock();
        try {
            System.out.println("telo metode m1");
        } finally {
            lock.unlock();
        }
    }

    public void m2() {
        lock.lock();
        try {
            System.out.println("telo metode m2");
            m1();
            System.out.println("povratak u metodu m2");
        } finally {
            lock.unlock();
        }
    }
}
