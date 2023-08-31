package readerWritter;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockReaderWritter implements ReaderWritter {
    private volatile int cnt;
    private final ReadWriteLock lock;

    ReentrantLockReaderWritter(){
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public void write() {
        lock.writeLock().lock();
        cnt++;
        try {
            Thread.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.writeLock().unlock();
    }

    @Override
    public int read() {
        lock.readLock().lock();
        int returnValue = cnt;
        lock.readLock().unlock();
        return returnValue;
    }
}
