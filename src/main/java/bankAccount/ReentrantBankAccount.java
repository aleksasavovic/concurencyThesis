package bankAccount;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantBankAccount implements BankAccount{
    private int balance;
    private int creditScore;

    private Lock lock = new ReentrantLock();

    public void withdraw(int amount) {
        lock.lock();
        try {
            if (balance - amount >= 0) {
                balance -= amount;
            } else {
                balance = 0;
                adjustCreditScore();
            }
        } finally {
            lock.unlock();
        }
    }

    public void adjustCreditScore() {
        int currentCreditScore = creditScore;

        if (currentCreditScore > 0) {
            int newCreditScore = Math.max(0, currentCreditScore - 10);
            creditScore = newCreditScore;
            System.out.println("Credit score adjusted to: " + newCreditScore);
        }
    }
}
