package producerConsumer;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicProducerConsumer implements ProducereConsumer {
    private final int bufferSize = 10;
    private final AtomicInteger producerIndex = new AtomicInteger(0);
    private final AtomicInteger consumerIndex = new AtomicInteger(0);
    private final AtomicInteger counter = new AtomicInteger(0);
    private final int[] buffer = new int[bufferSize];

    @Override
    public void produce() throws InterruptedException {
        while (true) {
            int currentProducerIndex = producerIndex.get();
            int currentConsumerIndex = consumerIndex.get();

            if ((currentProducerIndex - currentConsumerIndex) >= bufferSize) {
                continue; // Busy wait
            }

            if (producerIndex.compareAndSet(currentProducerIndex, currentProducerIndex + 1)) {
                int index = currentProducerIndex % bufferSize;
                int item = counter.getAndIncrement();
                buffer[index] = item;
                break;
            }
        }
    }

    @Override
    public void consume() throws InterruptedException {
        while (true) {
            int currentConsumerIndex = consumerIndex.get();
            int currentProducerIndex = producerIndex.get();

            if (currentConsumerIndex >= currentProducerIndex) {
                continue; // Busy wait
            }
            if (consumerIndex.compareAndSet(currentConsumerIndex, currentConsumerIndex + 1)) {
                int index = currentConsumerIndex % bufferSize;
                int item = buffer[index];
                break;
            }
        }
    }
}
