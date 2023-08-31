package producerConsumer.concurentDataStructures;

import producerConsumer.ProducereConsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingProducerConsumer implements ProducereConsumer {
    private final static int size = 10;
    private AtomicInteger cnt = new AtomicInteger(0);
    private BlockingQueue<Integer> queue;

    public BlockingProducerConsumer() {
        queue = new ArrayBlockingQueue<>(size);
    }

    @Override
    public void produce() throws InterruptedException {
        queue.put(cnt.getAndIncrement());
    }

    @Override
    public void consume() throws InterruptedException {
        queue.take();
    }
}
