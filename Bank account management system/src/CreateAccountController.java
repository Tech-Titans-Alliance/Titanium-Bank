import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CreateAccountController {
    @FXML
    private TextField holderNameField;
    @FXML
    private TextField depositField;
    @FXML
    private TextField pinField; // Add a field for PIN input

    private TransactionService transactionService = new TransactionService();

    @FXML
    private void createAccount() {
        String holderName = holderNameField.getText();
        String depositText = depositField.getText();
        String pin = pinField.getText(); // Get the entered PIN

        // Validation: Check if fields are empty
        if (holderName == null || holderName.trim().isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Holder name is required.");
            return;
        }

        if (depositText == null || depositText.trim().isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Initial deposit is required.");
            return;
        }

        if (pin == null || pin.trim().isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "PIN is required.");
            return;
        }

        // Validation: Check if deposit is a valid number
        double initialDeposit;
        try {
            initialDeposit = Double.parseDouble(depositText);
            if (initialDeposit < 0) {
                showAlert(AlertType.ERROR, "Validation Error", "Deposit amount must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Validation Error", "Deposit must be a valid number.");
            return;
        }

        // Get the logged-in user ID
        int userId = Session.getInstance().getLoggedInUser().getId();

        // Verify the PIN
        if (!verifyPin(userId, pin)) {
            showAlert(AlertType.ERROR, "Authentication Error", "Incorrect PIN. Please try again.");
            return;
        }

        // Create the account
        Account newAccount = transactionService.createAccount(holderName, initialDeposit, userId);

        // Show success alert
        showAlert(AlertType.INFORMATION, "Account Created", 
                  "Account created successfully!\nAccount ID: " + newAccount.getId());
        try {
			goBack();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    private void goBack() throws Exception {
        // Get the current stage (the window)
        Stage stage = (Stage) depositField.getScene().getWindow();

        // Load the previous FXML file (e.g., "dashboard.fxml")
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        BorderPane pane = loader.load();

        // Set the new scene to the same stage (window)
        stage.setScene(new Scene(pane));

        // Show the new scene (this brings the dashboard screen back into view)
        stage.show();
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean verifyPin(int userId, String pin) {
        User user = FileStorageService.readUsers()
                .stream()
                .filter(u -> u.getId() == userId)
                .findFirst()
                .orElse(null);
        return user != null && user.getPin().equals(pin);
    }
}
