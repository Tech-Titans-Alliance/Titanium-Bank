// FileStorageService.java
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileStorageService {
    private static final String USERS_FILE = "users.txt";
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    public static void initializeFiles() {
        try {
            new File(USERS_FILE).createNewFile();
            new File(ACCOUNTS_FILE).createNewFile();
            new File(TRANSACTIONS_FILE).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> readUsers() {
        return readFromFile(USERS_FILE, User::fromCsv);
    }

    public static void writeUsers(List<User> users) {
        synchronized (USERS_FILE) {
            writeToFile(USERS_FILE, users.stream().map(User::toCsv).collect(Collectors.toList()));
        }
    }

    public static List<Account> readAccounts() {
        return readFromFile(ACCOUNTS_FILE, Account::fromCsv);
    }

    public static void writeAccounts(List<Account> accounts) {
        synchronized (ACCOUNTS_FILE) {
            writeToFile(ACCOUNTS_FILE, accounts.stream().map(Account::toCsv).collect(Collectors.toList()));
        }
    }

    public static List<String> readTransactions() {
        return readFromFile(TRANSACTIONS_FILE, line -> line);
    }

    public static void writeTransactions(List<String> transactions) {
        synchronized (TRANSACTIONS_FILE) {
            writeToFile(TRANSACTIONS_FILE, transactions);
        }
    }

    private static <T> List<T> readFromFile(String fileName, CsvParser<T> parser) {
        List<T> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    list.add(parser.parse(line));
                } catch (Exception e) {
                    System.err.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void writeToFile(String fileName, List<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    private interface CsvParser<T> {
        T parse(String csv);
    }
}