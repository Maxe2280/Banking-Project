public abstract class BankAccount {
    private int accountNumber;
    private String accountType;
    private double balance;

    // Creates new account with details
    public BankAccount(int accountNumber, String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }

    // Returns current account balance
    public double getBalance() {
        return balance;
    }

    // Sets new account balance
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Returns formatted account number 
    public String getAccountNumber() {
        return "AC" + accountNumber;
    }

    // Deposits amount if positive, returns status
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    // Withdraws inputted amount
    public boolean withdraw(double amount) {
    if (amount > 0 && getBalance() >= amount) {
        setBalance(getBalance() - amount);
        return true;
    }
    return false;
}

    // Returns the account type
    public String getAccountType() {
        return accountType;
    }
}