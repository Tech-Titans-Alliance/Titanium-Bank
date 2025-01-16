// WithdrawController.java
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

public class WithdrawController {
	@FXML 
	private Button backButton;
    @FXML
    private ComboBox<String> accountDropdown;
    @FXML
    private TextField amountField;
    @FXML
    private TextField pinField;

    private TransactionService transactionService = new TransactionService();
    private User loggedInUser;
    
    // Called when the controller is initialized
    public void initialize() {
        setLoggedInUser();
    }
    public void setLoggedInUser() {
    	
        this.loggedInUser = Session.getInstance().getLoggedInUser();
        populateAccounts();
        System.out.println(loggedInUser.getId());
    }

//    private void populateAccounts() {
//    	accountDropdown.getItems().clear();
//
//        List<Account> accounts = FileStorageService.readAccounts();
//        accounts.stream()
//                .filter(account -> account.getUserId() == loggedInUser.getId())
//                .forEach(account -> accountDropdown.getItems().add(
//                        account.getId() + " - " + account.getHolderName()
//                ));
//        System.out.println(loggedInUser.getEmail());
//    }
    
    
    private void populateAccounts() {
        accountDropdown.getItems().clear(); // Clear previous items
        List<Account> accounts = FileStorageService.readAccounts();
        System.out.println("Logged in user ID: " + loggedInUser.getId());
        
        accounts.stream()
            .filter(account -> account.getUserId() == loggedInUser.getId())
            .forEach(account -> {
            	System.out.println("account user ID: " + account.getUserId());
                System.out.println("Adding Account: " + account.getId() + " - " + account.getHolderName());
                accountDropdown.getItems().add(
                        account.getId() + " - " + account.getHolderName()
                );
            });

        if (accountDropdown.getItems().isEmpty()) {
            System.out.println("No accounts found for this user.");
        }
    }


    @FXML
    private void withdraw() {
        String selectedAccount = accountDropdown.getValue();
        if (selectedAccount == null || amountField.getText().isEmpty() || pinField.getText().isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        int accountId = Integer.parseInt(selectedAccount.split(" - ")[0]);
        double amount = Double.parseDouble(amountField.getText());
        String pin = pinField.getText();

        boolean success = transactionService.withdraw(accountId, amount, pin);

        if (success) {
            showAlert("Success", "Amount withdrawn successfully.");
            try {
    			goBack();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        } else {
            showAlert("Error", "Withdrawal failed. Please check your details and try again.");
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
