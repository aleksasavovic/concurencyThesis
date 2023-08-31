package readerWritter;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicReaderWritter implements ReaderWritter {
    private AtomicInteger counter = new AtomicInteger(0);


    @Override
    public void write() {
        counter.incrementAndGet();
        try {
            Thread.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int read() {
        return counter.get();
    }
}
