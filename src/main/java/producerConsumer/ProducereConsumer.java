package producerConsumer;

public interface ProducereConsumer {
    public void produce() throws InterruptedException;

    public void consume() throws InterruptedException;
}
