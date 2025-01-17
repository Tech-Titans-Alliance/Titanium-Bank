// LoginController.java
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class LoginController {
    @FXML 
    private Button backButton;
    @FXML private TextField accountNumberField;
    @FXML private PasswordField pinField;

    public void handleLogin(ActionEvent event) {
        String accountNumber = accountNumberField.getText();
        String pin = pinField.getText();

        // Attempt to find the user based on entered credentials
        User user = FileStorageService.readUsers().stream()
            .filter(u -> u.getAccountNumber().equals(accountNumber) && u.getPin().equals(pin))
            .findFirst()
            .orElse(null);

        if (user == null) {
            // Show an error alert if no matching user is found
            showAlert("Error", "Invalid credentials.");
        } else {
            // Store the logged-in user in the session
            Session.getInstance().setLoggedInUser(user);

            // Navigate to the dashboard
            navigateToDashboard();
        }
    }

    private void navigateToDashboard() {
        try {
            // Load the dashboard view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            Parent root = loader.load();

            // Get the controller for the dashboard
            DashboardController controller = loader.getController();
            // No need to pass the logged-in user, as it's stored in the session

            // Get the current stage and switch scenes
            Stage stage = (Stage) accountNumberField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
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
        Stage stage = (Stage) accountNumberField.getScene().getWindow();

        // Load the previous FXML file (e.g., "HomePageView.fxml")
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePageView.fxml"));
        BorderPane pane = loader.load();
        
        // Set the new scene to the same stage (window)
        stage.setScene(new Scene(pane));
        
        // Show the new scene (this brings the previous page back into view)
        stage.show();
    }
}
