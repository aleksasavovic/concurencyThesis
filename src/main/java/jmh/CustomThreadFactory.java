package jmh;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class CustomThreadFactory implements ThreadFactory {
    private final AtomicLong threadIdCounter = new AtomicLong(25);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("CustomThread-" + threadIdCounter.getAndIncrement());
        return thread;
    }
}