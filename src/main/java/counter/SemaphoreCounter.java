package counter;

import java.util.concurrent.Semaphore;

public class SemaphoreCounter implements Counter {
    Semaphore semaphore = new Semaphore(1);
    private int counter = 0;

    @Override
    public void increment() {
        try {
            semaphore.acquire();
            counter++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    @Override
    public int getCounter() {
        try {
            semaphore.acquire();
            return counter;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;//nelegalna vrednost
        } finally {
            semaphore.release();
        }
    }
}
