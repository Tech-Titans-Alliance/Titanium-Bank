import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViewAccountsController {

    @FXML 
    private Button backButton; // The "Back to Dashboard" button in the FXML file
    @FXML
    private VBox accountsVBox; // VBox to hold account information dynamically
    @FXML
    private Text dateText; // Text for displaying the date
    @FXML
    private Text timeText; // Text for displaying the time

    private User loggedInUser;

    public void initialize() {
        // Ensures that the FXML elements are properly loaded
        if (backButton == null || accountsVBox == null || dateText == null || timeText == null) {
            System.err.println("FXML elements not properly initialized");
        }
        setLoggedInUser();
        loadAccounts();
        displayDateAndTime();
    }

    public void setLoggedInUser() {
        this.loggedInUser = Session.getInstance().getLoggedInUser();
    }

    private void loadAccounts() {
        List<Account> accounts = FileStorageService.readAccounts();
        accounts.stream()
                .filter(account -> account.getUserId() == loggedInUser.getId())
                .forEach(account -> {
                    Text accountInfo = new Text(" Holder: " + account.getHolderName() + ", Balance: R" + account.getBalance());
                    accountInfo.setStyle("-fx-font-size: 16px; -fx-fill: black;");
                    accountsVBox.getChildren().add(accountInfo);
                });
    }

    private void displayDateAndTime() {
        // Get current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        
        // Set the text for date and time
        dateText.setText("Date: " + dateFormat.format(date));
        timeText.setText("Time: " + timeFormat.format(date));
    }

    @FXML
    private void goBack() throws Exception {
        if (backButton == null) {
            System.err.println("Back button is null, unable to navigate.");
            return;
        }

        // Get the current stage (the window)
        Stage stage = (Stage) backButton.getScene().getWindow();
        
        // Load the previous FXML file (e.g., "MainView.fxml")
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        BorderPane pane = loader.load();
        
        // Set the new scene to the same stage (window)
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        
        // Show the new scene (this brings the dashboard screen back into view)
        stage.show();
    }
}

