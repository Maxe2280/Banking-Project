import java.util.*;
import java.io.*;

public class AccountDataManager {
    private static final String FILE_NAME = "accounts.csv";

    public static List<BankAccount> loadAccounts() {
        List<BankAccount> accounts = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(FILE_NAME));
            String line;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String accountNumber = fields[0];
                String accountType = fields[1];
                double balance = Double.parseDouble(fields[2]);
                int number = Integer.parseInt(accountNumber.substring(2));
                if (accountType.equals("CheckingAccount")) {
                    double overdraftLimit = Double.parseDouble(fields[3]);
                    accounts.add(new CheckingAccount(number, accountType, balance, overdraftLimit));
                } else if (accountType.equals("SavingAccount")) {
                    accounts.add(new SavingAccount(number, accountType, balance));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return accounts; 
    }
  public static void saveAccounts(List<BankAccount> accounts) {
      BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(FILE_NAME));
            for (BankAccount account : accounts) {
                StringBuilder line = new StringBuilder();
                line.append(account.getAccountNumber()).append(",");
                line.append(account.getAccountType()).append(",");
                line.append(account.getBalance()).append(",");
                
                if (account instanceof CheckingAccount) {
                    line.append(((CheckingAccount) account).getOverDraftLimit());
                } else {
                    line.append("");
                }
                bw.write(line.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) bw.close();
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
  }  
}
