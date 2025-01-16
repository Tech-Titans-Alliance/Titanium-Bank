// Session.java
public class Session {
    private static Session instance;
    private User loggedInUser;

    private Session() {}  // Private constructor to prevent instantiation

    // Get the instance of the Session class (Singleton pattern)
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // Get the logged-in user
    public User getLoggedInUser() {
        return loggedInUser;
    }

    // Set the logged-in user
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
