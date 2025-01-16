import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;

public class HomePageController {

    @FXML
    private VBox headerBox;  // Reference to the header VBox that contains the title
    @FXML
    private ImageView bankImage;  // Reference to the image node (corrected from Node to ImageView)
    @FXML
    private Button loginButton;  // Reference to the login button
    @FXML
    private Button registerButton;  // Reference to the register button
    @FXML
    private ImageView animatedIcon;  // Reference to the animated icon (image)

    @FXML
    public void initialize() {
        // Fade-in animation for header (slow fade)
        FadeTransition fadeHeader = new FadeTransition(Duration.seconds(2), headerBox);
        fadeHeader.setFromValue(0);
        fadeHeader.setToValue(1);

        // Slide-in animation for the bank image (left to right)
        TranslateTransition slideInImage = new TranslateTransition(Duration.seconds(2), bankImage);
        slideInImage.setFromX(-200);  // Start position (off-screen)
        slideInImage.setToX(0);  // End position (on-screen)

        // Fade-in animation for the login button
        FadeTransition fadeLoginButton = new FadeTransition(Duration.seconds(2), loginButton);
        fadeLoginButton.setFromValue(0);
        fadeLoginButton.setToValue(1);

        // Fade-in animation for the register button
        FadeTransition fadeRegisterButton = new FadeTransition(Duration.seconds(2), registerButton);
        fadeRegisterButton.setFromValue(0);
        fadeRegisterButton.setToValue(1);

        // Fade-in animation for the animated icon (new addition)
        FadeTransition fadeIcon = new FadeTransition(Duration.seconds(2), animatedIcon);
        fadeIcon.setFromValue(0);
        fadeIcon.setToValue(1);

        // Slide-up animation for the icon to give a subtle movement
        TranslateTransition slideUpIcon = new TranslateTransition(Duration.seconds(2), animatedIcon);
        slideUpIcon.setFromY(50);  // Start from 50px below its final position
        slideUpIcon.setToY(0);  // End at the original position

        // Play the animations together for smooth transition
        fadeHeader.play();
        slideInImage.play();
        fadeLoginButton.play();
        fadeRegisterButton.play();
        fadeIcon.play();
        slideUpIcon.play();
    }

    @FXML
    public void navigateToLogin(ActionEvent event) {
        navigateTo("LoginView.fxml", event);
    }

    @FXML
    public void navigateToRegister(ActionEvent event) {
        navigateTo("RegistrationPageView.fxml", event);
    }

    private void navigateTo(String fxml, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

