package connections;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockConnectionPool implements ConnectionPool{
    private int maxConnections=5;
    private int availableConnections;
    private Lock lock;
    private Condition noConnections;

    ReentrantLockConnectionPool(){
        lock = new ReentrantLock();
        noConnections = lock.newCondition();
        availableConnections=0;
    }
    @Override
    public void acquireConnection() throws InterruptedException {
        lock.lock();
        if(availableConnections==maxConnections){
            noConnections.await();
        }
        availableConnections++;
        lock.unlock();
    }

    @Override
    public void releaseConnection() {
        lock.lock();
        availableConnections--;
        noConnections.signalAll();
        lock.unlock();
    }
}
