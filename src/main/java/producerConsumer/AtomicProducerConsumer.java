package producerConsumer;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicProducerConsumer implements ProducereConsumer {
    private final int bufferSize;
    private final AtomicInteger producerIndex;
    private final AtomicInteger consumerIndex;
    private final AtomicInteger counter;
    private final int[] buffer;

    public AtomicProducerConsumer() {
        this.bufferSize = 10;
        this.producerIndex = new AtomicInteger(0);
        this.consumerIndex = new AtomicInteger(0);
        this.counter = new AtomicInteger(0);
        this.buffer = new int[bufferSize];
    }

    @Override
    public void produce(int value) throws InterruptedException {
        while (true) {
            int currentProducerIndex = producerIndex.get();
            int currentConsumerIndex = consumerIndex.get();
            if ((currentProducerIndex - currentConsumerIndex) >= bufferSize) {
                continue;
            }
            if (producerIndex.compareAndSet(currentProducerIndex, currentProducerIndex + 1)) {
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
            int currentConsumerIndex = consumerIndex.get();
            int currentProducerIndex = producerIndex.get();
            if (currentConsumerIndex >= currentProducerIndex) {
                continue;
            }
            if (consumerIndex.compareAndSet(currentConsumerIndex, currentConsumerIndex + 1)) {
                int index = currentConsumerIndex % bufferSize;
                value = buffer[index];
                break;
            }
        }
        return value;
    }
}
