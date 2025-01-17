// Account.java
public class Account {
    private int id;
    private String holderName;
    private double balance;
    private int userId;

    public Account(int id, String holderName, double balance, int userId) {
        this.id = id;
        this.holderName = holderName;
        this.balance = balance;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public int getUserId() {
        return userId;
    }

    public String toCsv() {
        return id + "," + holderName + "," + balance + "," + userId;
    }

    public static Account fromCsv(String csv) {
        String[] parts = csv.split(",");
        return new Account(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]));
    }
}
