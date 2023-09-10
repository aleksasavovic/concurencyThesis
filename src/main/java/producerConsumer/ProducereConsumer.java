package producerConsumer;

public interface ProducereConsumer {
    public void produce(int value) throws InterruptedException;

    public int consume() throws InterruptedException;
}
