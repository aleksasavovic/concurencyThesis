package producerConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockProducerConsumer implements ProducereConsumer {
    private static final int size = 10;
    private final Lock lock;
    private final Condition notFull;
    private final Condition notEmpty;
    private volatile int cnt = 0;
    private final int[] buffer;
    private int consumerIndex, producerIndex;



    public ReentrantLockProducerConsumer() {
        buffer = new int[size];
        producerIndex = consumerIndex = 0;
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    @Override
    public void produce() throws InterruptedException {
        lock.lock();
        if (producerIndex - consumerIndex >= size)
            notFull.await();
        buffer[producerIndex % size] = cnt;
        producerIndex++;
        cnt++;
        notEmpty.signalAll();
        lock.unlock();
    }

    @Override
    public void consume() throws InterruptedException {
        lock.lock();
        if (consumerIndex >= producerIndex)
            notEmpty.await();
        consumerIndex++;
        notFull.signalAll();
        lock.unlock();
    }
}
