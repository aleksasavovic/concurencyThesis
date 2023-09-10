package producerConsumer.instrict;

import producerConsumer.ProducereConsumer;

public class SynchronizedProducerConsumer implements ProducereConsumer {
    private static final int bufferSize = 10;
    private int[] buffer;
    private int consumerIndex, producerIndex;

    public SynchronizedProducerConsumer() {
        buffer = new int[bufferSize];
        producerIndex = consumerIndex = 0;
    }

    @Override
    public synchronized void produce(int value) throws InterruptedException {
        if (producerIndex - consumerIndex >= bufferSize)
            wait();
        buffer[producerIndex % bufferSize] = value;
        producerIndex++;
        notifyAll();
    }

    @Override
    public synchronized int consume() throws InterruptedException {
        if (consumerIndex >= producerIndex)
            wait();
        int value = buffer[consumerIndex % bufferSize];
        consumerIndex++;
        notifyAll();
        return value;
    }
}
