package counter;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter implements Counter {
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void increment(){
        counter.incrementAndGet();
    }
    @Override
    public int getCounter() {
        return counter.get();
    }
}
