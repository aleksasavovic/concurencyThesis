package producerConsumer;

import java.util.concurrent.Semaphore;

public class SemaphoreProducerConsumer implements ProducereConsumer {
    private static final int bufferSize = 10;
    private int[] buffer;
    private Semaphore mutex;
    private Semaphore empty;
    private Semaphore full;
    public int consumerIndex;
    public int producerIndex;

    public SemaphoreProducerConsumer() {
        buffer = new int[bufferSize];
        mutex = new Semaphore(1);
        empty = new Semaphore(bufferSize);
        full = new Semaphore(0);
        consumerIndex = producerIndex = 0;
    }

    public void produce(int value) throws InterruptedException {
        empty.acquire();
        mutex.acquire();
        try {
            buffer[producerIndex % bufferSize] = value;
            producerIndex++;
        } finally {
            mutex.release();
            full.release();
        }
    }

    public int consume() throws InterruptedException {
        full.acquire();
        mutex.acquire();
        try {
            int value = buffer[consumerIndex % bufferSize];
            consumerIndex++;
            return value;
        } finally {
            mutex.release();
            empty.release();
        }
    }
}