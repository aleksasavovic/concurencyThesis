package counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounter implements Counter{
    private int coutner = 0;
    private Lock lock = new ReentrantLock();
    @Override
    public int getCounter() {
        return coutner;
    }

    @Override
    public void increment() {
        lock.lock();
        try {
            coutner++;
        } finally {
            lock.unlock();
        }
    }
}
