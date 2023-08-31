package connections;

public interface ConnectionPool {
    public void acquireConnection() throws InterruptedException;
    public void releaseConnection();
}
