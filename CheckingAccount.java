public class CheckingAccount extends BankAccount {
    private double overDraftLimit;  

    public CheckingAccount(int accountNumber, String accountType, double balance, double overDraftLimit) {
        // Creates checking account and sets overdraft
        super(accountNumber, accountType, balance);
        this.overDraftLimit = overDraftLimit;
    }

    public double getOverDraftLimit() {  
        // Returns overdraft limit 
        return overDraftLimit;
    }

    @Override
    public boolean withdraw(double amount) {  
        if (getBalance() < 0) {
            return false;  // Reject withdrawals if already overdrawn
        }
        if (amount > 0 && (getBalance() + overDraftLimit) >= amount) {
            setBalance(getBalance() - amount);
            return true;
        }
        return false;
    }
}