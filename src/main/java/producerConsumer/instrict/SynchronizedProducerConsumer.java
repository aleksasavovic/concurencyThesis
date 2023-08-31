package producerConsumer.instrict;

import producerConsumer.ProducereConsumer;

public class SynchronizedProducerConsumer implements ProducereConsumer {
    private volatile int cnt = 0;
    private static final int size = 10;
    private int[] buffer;
    private int consumerIndex, producerIndex;

    public SynchronizedProducerConsumer() {
        buffer = new int[size];
        producerIndex = consumerIndex = 0;
    }

    @Override
    public synchronized void produce() throws InterruptedException {
        if (producerIndex - consumerIndex >= size)
            wait();
        buffer[producerIndex % size] = cnt;
        System.out.println("produced " + cnt);
        producerIndex++;
        cnt++;
        notifyAll();
    }

    @Override
    public synchronized void consume() throws InterruptedException {
        if (consumerIndex >= producerIndex)
            wait();
        System.out.println("Consumed " + buffer[consumerIndex % size]);
        consumerIndex++;
        notifyAll();
    }
}
