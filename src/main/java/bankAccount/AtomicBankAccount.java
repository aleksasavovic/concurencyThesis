package bankAccount;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicBankAccount implements BankAccount {
    private AtomicInteger balance = new AtomicInteger(0);
    private AtomicInteger creditScore = new AtomicInteger(0);

    public void withdraw(int amount) {
        int currentBalance;
        do {
            currentBalance = balance.get();
            if (currentBalance >= amount) {
                if (balance.compareAndSet(currentBalance, currentBalance - amount)) {
                    break;
                }
            } else {
                if (balance.compareAndSet(currentBalance, 0)) {
                    adjustCreditScore();
                    break;
                }
            }
        } while (true);
    }

    private void adjustCreditScore() {
        int currentCreditScore = creditScore.get();

        if (currentCreditScore > 0) {
            // Decrease credit score by a certain amount
            int newCreditScore = Math.max(0, currentCreditScore - 10);
            creditScore.set(newCreditScore);
            System.out.println("Credit score adjusted to: " + newCreditScore);
        }
    }
}
