// User.java
public class User {
    private int id;
    private String name;
    private String surname;
    private String cellphone;
    private String email;
    private String accountNumber;
    private String pin;

    public User(int id, String name, String surname, String cellphone, String email, String accountNumber, String pin) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.cellphone = cellphone;
        this.email = email;
        this.accountNumber = accountNumber;
        this.pin = pin;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public String toCsv() {
        return id + "," + name + "," + surname + "," + cellphone + "," + email + "," + accountNumber + "," + pin;
    }

    public static User fromCsv(String csv) {
        String[] parts = csv.split(",");
        return new User(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
    }
}