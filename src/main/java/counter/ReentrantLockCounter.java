package counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounter implements Counter {
    private int counter = 0;
    private Lock lock = new ReentrantLock();
    @Override
    public void increment() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }
    @Override
    public synchronized int getCounter() {
        lock.lock();
        try {
            return counter;
        }
        finally {
            lock.unlock();
        }
    }
}
