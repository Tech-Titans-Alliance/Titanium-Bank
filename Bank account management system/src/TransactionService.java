// TransactionService.java
import java.util.*;

public class TransactionService {
    private List<Account> accounts;
    private List<String> transactions;

    public TransactionService() {
        accounts = FileStorageService.readAccounts();
        transactions = FileStorageService.readTransactions();
    }

    public Account createAccount(String holderName, double initialDeposit, int userId) {
        int newId = accounts.isEmpty() ? 1 : accounts.get(accounts.size() - 1).getId() + 1;
        Account account = new Account(newId, holderName, initialDeposit, userId);
        accounts.add(account);
        FileStorageService.writeAccounts(accounts);
        return account;
    }

    public boolean deposit(int accountId, double amount) {
        Account account = findAccount(accountId);
        if (account != null) {
            account.deposit(amount);
            FileStorageService.writeAccounts(accounts);
            transactions.add("Deposit: " + amount + " to account ID " + accountId);
            FileStorageService.writeTransactions(transactions);
            return true;
        }
        return false;
    }

    public boolean withdraw(int accountId, double amount, String pin) {
        Account account = findAccount(accountId);
        if (account != null && verifyPin(account.getUserId(), pin)) {
            if (account.withdraw(amount)) {
                FileStorageService.writeAccounts(accounts);
                transactions.add("Withdraw: " + amount + " from account ID " + accountId);
                FileStorageService.writeTransactions(transactions);
                return true;
            }
        }
        return false;
    }

    public boolean transfer(int fromAccountId, int toAccountId, double amount, String pin) {
        Account fromAccount = findAccount(fromAccountId);
        Account toAccount = findAccount(toAccountId);
        if (fromAccount != null && toAccount != null && verifyPin(fromAccount.getUserId(), pin)) {
            if (fromAccount.withdraw(amount)) {
                toAccount.deposit(amount);
                FileStorageService.writeAccounts(accounts);
                transactions.add("Transfer: " + amount + " from account ID " + fromAccountId + " to account ID " + toAccountId);
                FileStorageService.writeTransactions(transactions);
                return true;
            }
        }
        return false;
    }

    public List<String> getTransactionHistory() {
        return transactions;
    }

    private Account findAccount(int accountId) {
        return accounts.stream().filter(a -> a.getId() == accountId).findFirst().orElse(null);
    }

    private boolean verifyPin(int userId, String pin) {
        User user = FileStorageService.readUsers().stream().filter(u -> u.getId() == userId).findFirst().orElse(null);
        return user != null && user.getPin().equals(pin);
    }
}