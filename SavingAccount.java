public class SavingAccount extends BankAccount {
    private static double interestRate = 2.5;

    // Creates savings account with details
    public SavingAccount(int accountNumber, String accountType, double balance) {
        super(accountNumber, accountType, balance);
    }

    // Applies interest to balance
    public void applyInterest() {
        setBalance(getBalance() + calculateInterest());
    }

    // Calculates interest based on balance
    public double calculateInterest() {
        return getBalance() * (interestRate / 100);
        
    }
}