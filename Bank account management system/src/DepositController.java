// DepositController.java
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

public class DepositController {
	@FXML 
	private Button backButton;

    @FXML
    private ComboBox<String> accountDropdown;
    @FXML
    private TextField amountField;

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
                .forEach(account -> accountDropdown.getItems().add(
                        account.getId() + " - " + account.getHolderName()
                ));
    }

    @FXML
    private void deposit() {
        String selectedAccount = accountDropdown.getValue();
        if (selectedAccount == null || amountField.getText().isEmpty()) {
            showAlert("Error", "Please select an account and enter an amount.");
            return;
        }

        int accountId = Integer.parseInt(selectedAccount.split(" - ")[0]);
        double amount = Double.parseDouble(amountField.getText());

        boolean success = transactionService.deposit(accountId, amount);

        if (success) {
            showAlert("Success", "Amount deposited successfully.");
            try {
    			goBack();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        } else {
            showAlert("Error", "Deposit failed. Please try again.");
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
        Stage stage = (Stage) amountField.getScene().getWindow();
        
        
        // Load the previous FXML file (e.g., "dashboard.fxml")
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        BorderPane pane = loader.load();
        
        // Set the new scene to the same stage (window)
        stage.setScene(new Scene(pane));
        
        // Show the new scene (this brings the dashboard screen back into view)
        stage.show();
    }
}