/*package producerConsumer.instrict;

import producerConsumer.ProducereConsumer;

public class ImprovedSynchronizedProducerConsumer implements ProducereConsumer {
    private volatile int cnt = 0;
    private static final int size = 10;
    private int[] buffer;
    private int consumerIndex, producerIndex;
    private Object consumerLock = new Object();
    private Object producerLock = new Object();
    private boolean notifyProducer = false;
    private boolean notifyConsumer = false;

    public ImprovedSynchronizedProducerConsumer() {
        buffer = new int[size];
        producerIndex = consumerIndex = 0;
    }

    @Override
    public void produce() throws InterruptedException {
        synchronized (producerLock) {
            while (producerIndex - consumerIndex >= size) {
                producerLock.wait();
            }
            buffer[producerIndex % size] = cnt;
            producerIndex++;
            cnt++;
            notifyConsumer = true;
        }
        if (notifyConsumer) {
            synchronized (consumerLock) {
                notifyConsumer = false;
                consumerLock.notify();
            }
        }
    }

    @Override
    public void consume() throws InterruptedException {
        synchronized (consumerLock) {
            while (consumerIndex >= producerIndex) {
                consumerLock.wait();
            }
            consumerIndex++;
            notifyProducer = true;
        }
        if (notifyProducer) {
            synchronized (producerLock) {
                notifyProducer = false;
                producerLock.notify();
            }
        }
    }
}
*/