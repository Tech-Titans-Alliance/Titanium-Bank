// TransferController.java
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TransferController {
	@FXML 
	private Button backButton;
    @FXML
    private ComboBox<String> fromAccountDropdown;
    @FXML
    private ComboBox<String> toAccountDropdown;
    @FXML
    private TextField amountField;
    @FXML
    private TextField pinField;

    private TransactionService transactionService = new TransactionService();
    private User loggedInUser;

    public void initialize() {
        setLoggedInUser();
    }
    public void setLoggedInUser() {
    	
        this.loggedInUser = Session.getInstance().getLoggedInUser();
        populateAccounts();
        System.out.println(loggedInUser.getId());
    }

    private void populateAccounts() {
        List<Account> accounts = FileStorageService.readAccounts();
        accounts.stream()
                .filter(account -> account.getUserId() == loggedInUser.getId())
                .forEach(account -> {
                    String display = account.getId() + " - " + account.getHolderName();
                    fromAccountDropdown.getItems().add(display);
                    toAccountDropdown.getItems().add(display);
                });
    }

    @FXML
    private void transfer() {
        String fromAccount = fromAccountDropdown.getValue();
        String toAccount = toAccountDropdown.getValue();
        if (fromAccount == null || toAccount == null || amountField.getText().isEmpty() || pinField.getText().isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        int fromAccountId = Integer.parseInt(fromAccount.split(" - ")[0]);
        int toAccountId = Integer.parseInt(toAccount.split(" - ")[0]);
        double amount = Double.parseDouble(amountField.getText());
        String pin = pinField.getText();

        boolean success = transactionService.transfer(fromAccountId, toAccountId, amount, pin);

        if (success) {
            showAlert("Success", "Amount transferred successfully.");
            try {
    			goBack();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        } else {
            showAlert("Error", "Transfer failed. Please check your details and try again.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void goBack() throws Exception {
        // Get the current stage (the window)
        Stage stage = (Stage) pinField.getScene().getWindow();
        
        
        // Load the previous FXML file (e.g., "dashboard.fxml")
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        BorderPane pane = loader.load();
        
        // Set the new scene to the same stage (window)
        stage.setScene(new Scene(pane));
        
        // Show the new scene (this brings the dashboard screen back into view)
        stage.show();
    }
}
