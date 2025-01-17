// RegisterController.java
import java.util.List;

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

public class RegisterController {  
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField cellphoneField;
    @FXML private TextField emailField;
    @FXML private TextField accountNumberField;
    @FXML private PasswordField pinField;
    private LoginController logincontroller;
    public void handleRegister(ActionEvent event) {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String cellphone = cellphoneField.getText();
        String email = emailField.getText();
        String accountNumber = accountNumberField.getText();
        String pin = pinField.getText();

        // Validate that all fields are filled
        if (name.isEmpty() || surname.isEmpty() || cellphone.isEmpty() || email.isEmpty() || accountNumber.isEmpty() || pin.isEmpty()) {
            showAlert("Error", "All fields are required.");
            return;
        }

        // Create a new user object
        User user = new User(FileStorageService.readUsers().size() + 1, name, surname, cellphone, email, accountNumber, pin);

        // Read existing users from the file
        List<User> existingUsers = FileStorageService.readUsers();

        // Add the new user to the existing list
        existingUsers.add(user);

        // Write the updated list of users back to the file
        FileStorageService.writeUsers(existingUsers);

        // Show success message
        showAlert("Success", "Registration successful! Please log in.");
        
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
        Stage stage = (Stage) emailField.getScene().getWindow();
        
        
        // Load the previous FXML file (e.g., "dashboard.fxml")
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePageView.fxml"));
        BorderPane pane = loader.load();
        
        // Set the new scene to the same stage (window)
        stage.setScene(new Scene(pane));
        
        // Show the new scene (this brings the dashboard screen back into view)
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
