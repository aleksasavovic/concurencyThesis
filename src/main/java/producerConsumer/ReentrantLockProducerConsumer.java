package producerConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockProducerConsumer implements ProducereConsumer {
    private static final int bufferSize = 10;
    private final Lock lock;
    private final Condition notFull;
    private final Condition notEmpty;
    private final int[] buffer;
    private int consumerIndex, producerIndex;


    public ReentrantLockProducerConsumer() {
        buffer = new int[bufferSize];
        producerIndex = consumerIndex = 0;
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    @Override
    public void produce(int value) throws InterruptedException {
        lock.lock();
        try {
            if (producerIndex - consumerIndex >= bufferSize)
                notFull.await();
            buffer[producerIndex % bufferSize] = value;
            producerIndex++;
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int consume() throws InterruptedException {
        lock.lock();
        try {
            if (consumerIndex >= producerIndex)
                notEmpty.await();
            int value = buffer[consumerIndex % bufferSize];
            consumerIndex++;
            notFull.signalAll();
            return value;
        } finally {
            lock.unlock();
        }
    }
}
