package intrinsic;

public class IntrinsicLocks {
    Object lock1;
    Object lock2;
    IntrinsicLocks() {
        lock1 = new Object();
        lock2 = new Object();
    }
    public void m1() {
        synchronized (lock1) {
            //uradi nešto u kritičnoj sekciji 1
        }
    }
    public void m2() {
        synchronized (lock2) {
            //uradi nešto nešto u kritičnoj sekciji 2
        }
    }
}

