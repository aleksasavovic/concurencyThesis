package connections;

import java.util.concurrent.Semaphore;

public class SemaphoreConnectionPool implements  ConnectionPool{
    private int maxConnections=5;
    private Semaphore semaphore;

    public SemaphoreConnectionPool(){
        semaphore = new Semaphore(maxConnections);
    }
    @Override
    public void acquireConnection() throws InterruptedException {
        semaphore.acquire();
    }

    @Override
    public void releaseConnection() {
        semaphore.release();
    }
}
