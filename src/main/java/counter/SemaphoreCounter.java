package counter;

import java.util.concurrent.Semaphore;

public class SemaphoreCounter implements Counter{
    Semaphore semaphore = new Semaphore(1);
    private int counter = 0;
    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public void increment() {
        try {
            semaphore.acquire();
            counter++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            semaphore.release();
        }

    }
}
