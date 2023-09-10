package explicitLocking;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockMechanism {
    Lock lock = new ReentrantLock();
    public void m1() {
        if (lock.tryLock()) {
            try {
                //lock je bio slobodan pa je uspešno zazet
                //uradi nešto u kritičnoj sekciji 1
            } finally {
                lock.unlock();
            }
        } else {
            //uradi nešto u situaciji kada nit nije uspela da zaume lock
        }
    }
    public void m2() {
        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    //lock je uspešno zauzet u vremenskom intervalu od 1000ms
                    //uradi nešto u kritičnoj sekciji 2
                } finally {
                    lock.unlock();
                }
            } else {
                //uradi nešto u situaciji kada nit nije uspela da zaume lock
            }
        } catch (InterruptedException e) {
            //rukuj izuzetkom, nit je prekinuta dok je čekala 1000ms
        }
    }
}


