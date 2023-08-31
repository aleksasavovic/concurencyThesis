package counter;

public class SynchronizedCounter implements Counter{
    private int counter = 0;

    @Override
    public synchronized void increment(){
        counter++;
    }
    @Override
    public int getCounter(){
        return counter;
    }
}
