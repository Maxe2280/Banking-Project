import java.util.Scanner;
import java.util.List;

public class Bank {
    private static int nextAccountNumber = 1;
    
    public static void main(String[] args) {
        List<BankAccount> accounts = AccountDataManager.loadAccounts();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Menu start
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Display All Accounts");
            System.out.println("5. Apply Interest to Savings");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: 
                    addAccount(accounts, scanner);
                    break;
                case 2:
                    deposit(accounts, scanner);
                    break;
                case 3:
                    withdraw(accounts, scanner);
                    break;
                case 4:
                    displayAllAccounts(accounts);
                    break;
                case 5:
                    applyInterest(accounts);
                    break;
                case 6:
                    exitProgram(scanner);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Account creation
    private static void addAccount(List<BankAccount> accounts, Scanner scanner) {
        System.out.print("Enter 'S' for Saving or 'C' for Checking: ");
        String type = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        
        BankAccount newAccount;
        if (type.equalsIgnoreCase("C")) {
            System.out.print("Enter overdraft limit: ");
            double limit = scanner.nextDouble();
            newAccount = new CheckingAccount(nextAccountNumber, "CheckingAccount", balance, limit);
        } else {
            newAccount = new SavingAccount(nextAccountNumber, "SavingAccount", balance);
        }
        
        accounts.add(newAccount);
        System.out.println("Account created. Account Number: AC" + nextAccountNumber + ". Account Balance: " + balance);
        nextAccountNumber++;
        AccountDataManager.saveAccounts(accounts);
    }

    // Depositing money
    private static void deposit(List<BankAccount> accounts, Scanner scanner) {
        System.out.print("Enter account number: ");
        String acNum = scanner.nextLine();
        boolean found = false;
        
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(acNum)) {
                System.out.print("Enter deposit amount: ");
                double amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("Failed Deposit - Must Deposit More than $0, Account Balance: " + account.getBalance());
                } else {
                    account.deposit(amount);
                    System.out.println("Successful Deposit, Account Balance: " + account.getBalance());
                    AccountDataManager.saveAccounts(accounts);
                }
                found = true;
                break;
            }
        }
        if (!found) { System.out.println("Account not found."); }
    }

    // Withdrawing money
    private static void withdraw(List<BankAccount> accounts, Scanner scanner) {
        System.out.print("Enter account number: ");
        String acNum = scanner.nextLine();
        boolean found = false;
        
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(acNum)) {
                System.out.print("Enter withdraw amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();
                
                if (account.withdraw(amount)) {
                    System.out.println("Successful Withdrawal, Account Balance: " + account.getBalance());
                    AccountDataManager.saveAccounts(accounts);
                } else {
                    if (account.getAccountType().equals("CheckingAccount") && account.getBalance() < 0) {
                        System.out.println("Failed Withdrawal, Account Balance Below Zero, Account Balance: " + account.getBalance());
                    } else if (account.getAccountType().equals("SavingAccount")) {
                        System.out.println("Failed Withdrawal, Insufficient Saving Account Balance, Account Balance: " + account.getBalance());
                    }
                }
                found = true;
                break;
            }
        }
        if (!found) { System.out.println("Account not found."); }
    }

    // Displaying all account details
    private static void displayAllAccounts(List<BankAccount> accounts) {
        for (BankAccount account : accounts) {
            System.out.println("Account Number: " + account.getAccountNumber() + 
                             ", Type: " + account.getAccountType() + 
                             ", Balance: " + account.getBalance());
        }
    }

    // Applying interest to savings accounts
    private static void applyInterest(List<BankAccount> accounts) {
        boolean anySavings = false;
        for (BankAccount account : accounts) {
            if (account.getAccountType().equals("SavingAccount")) {
                ((SavingAccount) account).applyInterest();
                anySavings = true;
            }
        }
        if (anySavings) { 
            System.out.println("Successful Process, Interest Rate was applied to all Saving Accounts."); 
            AccountDataManager.saveAccounts(accounts);
        }
    }

    // Exit program
    private static void exitProgram(Scanner scanner) {
        scanner.close();
        System.out.println("Exiting...");
        System.exit(0);
    }

    // Checks if account number is unique 
    private static boolean isAccountNumberUnique(List<BankAccount> accounts, int accountNumber) {
        String targetNum = "AC" + accountNumber;
        for (BankAccount account : accounts) {
            if (account != null && account.getAccountNumber().equals(targetNum)) {
                return false;
            }
        }
        return true;
    }
}
