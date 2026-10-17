# Online Banking Interface

A command-line banking application in Java with savings and checking accounts. Accounts are saved
to a CSV file, so balances persist between sessions.

## Features

- Create savings or checking accounts (account numbers `AC1`, `AC2`, ...)
- Deposit and withdraw, with validation (no zero or negative amounts)
- Checking accounts allow an overdraft up to a per-account limit, and block withdrawals once overdrawn
- Apply interest (2.5%) to every savings account
- List all accounts with type and balance

## Design

| Class | Role |
|---|---|
| `BankAccount` | Abstract base class: account number, type, balance, deposit and withdraw |
| `CheckingAccount` | Overrides `withdraw` to allow an overdraft up to its limit |
| `SavingAccount` | Adds interest calculation |
| `AccountDataManager` | Loads and saves accounts in `accounts.csv` with `BufferedReader` / `BufferedWriter` |
| `Bank` | The menu loop and user input |

Each change (new account, deposit, withdrawal, interest) is written back to `accounts.csv`
immediately, and the file is read on startup.

CSV format, one account per line:

```
AC1,CheckingAccount,2000.0,300.0
AC2,SavingAccount,2400.0,
```

The fourth column is the overdraft limit, used only by checking accounts.

## Run it

Requires a JDK (8 or newer).

```bash
javac *.java
java Bank
```

`accounts.csv` ships with three sample accounts.
