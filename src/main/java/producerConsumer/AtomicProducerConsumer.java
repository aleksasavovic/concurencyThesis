package producerConsumer;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicProducerConsumer implements ProducereConsumer {
    private final int bufferSize;
    public final AtomicInteger producerIndex;
    public final AtomicInteger consumerIndex;
    private final int[] buffer;

    public AtomicProducerConsumer() {
        this.bufferSize = 10;
        this.producerIndex = new AtomicInteger(0);
        this.consumerIndex = new AtomicInteger(0);
        this.buffer = new int[bufferSize];
    }

    @Override
    public void produce(int value) throws InterruptedException {
        while (true) {
            int currentProducerIndex = producerIndex.get();
            int currentConsumerIndex = consumerIndex.get();
            int nextProducerIndex = currentProducerIndex + 1;
            if ((currentProducerIndex - currentConsumerIndex) >= bufferSize) {
                continue;
            }
            if (producerIndex.compareAndSet(currentProducerIndex, nextProducerIndex)) {
                int index = currentProducerIndex % bufferSize;
                buffer[index] = value;
                break;
            }
        }
    }
    @Override
    public int consume() throws InterruptedException {
        int value;
        while (true) {
            //System.out.println(consumerIndex.get());
            int currentConsumerIndex = consumerIndex.get();
            int currentProducerIndex = producerIndex.get();
            int nextConsumerIndex = currentConsumerIndex + 1;
            if (currentConsumerIndex >= currentProducerIndex) {
                continue;
            }
            if (consumerIndex.compareAndSet(currentConsumerIndex, nextConsumerIndex)) {
                int index = currentConsumerIndex % bufferSize;
                value = buffer[index];
                break;
            }
        }
        return value;
    }
}

