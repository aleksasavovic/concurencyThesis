package explicitLocking;

public class TestRentrantLockDeadLockExample {
    public static void main(String[] args){
        ReentrantLockDeadLockExample reentrantLockDeadLockExample = new ReentrantLockDeadLockExample();
        reentrantLockDeadLockExample.m2();
    }
}
