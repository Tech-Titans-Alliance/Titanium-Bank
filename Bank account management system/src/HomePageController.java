import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomePageController {

    @FXML
    private VBox headerBox; // Reference to the header VBox that contains the title
    @FXML
    private ImageView bankImage; // Reference to the image node (corrected from Node to ImageView)
    @FXML
    private Button loginButton; // Reference to the login button
    @FXML
    private Button registerButton; // Reference to the register button
    @FXML
    private ImageView animatedIcon; // Reference to the animated icon (image)

    // Chatbot elements
    @FXML
    private VBox chatbotBox; // Chatbot box container
    @FXML
    private TextArea chatArea; // Chat display area
    @FXML
    private TextField userInput; // User input field

    @FXML
    public void initialize() {
        // Fade-in animation for header (slow fade)
        FadeTransition fadeHeader = new FadeTransition(Duration.seconds(2), headerBox);
        fadeHeader.setFromValue(0);
        fadeHeader.setToValue(1);

        // Slide-in animation for the bank image (left to right)
        TranslateTransition slideInImage = new TranslateTransition(Duration.seconds(2), bankImage);
        slideInImage.setFromX(-200); // Start position (off-screen)
        slideInImage.setToX(0); // End position (on-screen)

        // Fade-in animation for the login button
        FadeTransition fadeLoginButton = new FadeTransition(Duration.seconds(2), loginButton);
        fadeLoginButton.setFromValue(0);
        fadeLoginButton.setToValue(1);

        // Fade-in animation for the register button
        FadeTransition fadeRegisterButton = new FadeTransition(Duration.seconds(2), registerButton);
        fadeRegisterButton.setFromValue(0);
        fadeRegisterButton.setToValue(1);

        // Fade-in animation for the animated icon
        FadeTransition fadeIcon = new FadeTransition(Duration.seconds(2), animatedIcon);
        fadeIcon.setFromValue(0);
        fadeIcon.setToValue(1);

        // Slide-up animation for the icon
        TranslateTransition slideUpIcon = new TranslateTransition(Duration.seconds(2), animatedIcon);
        slideUpIcon.setFromY(50); // Start from 50px below its final position
        slideUpIcon.setToY(0); // End at the original position

        // Play all animations
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

    @FXML
    private void handleChatInput() {
        String userMessage = userInput.getText();

        if (userMessage == null || userMessage.trim().isEmpty()) {
            return;
        }

        chatArea.appendText("User: " + userMessage + "\n");
        chatArea.appendText("Titanium Bot: typing...\n");

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            clearTypingStatus();
            String botResponse = getChatbotResponse(userMessage);
            chatArea.appendText("Bot: " + botResponse + "\n");
        });

        pause.play();
        userInput.clear();
    }

    private void clearTypingStatus() {
        String currentText = chatArea.getText();
        if (currentText.endsWith("Titanium Bot: typing...\n")) {
            chatArea.setText(currentText.substring(0, currentText.length() - "Titanium Bot: typing...\n".length()));
        }
    }

    private String getChatbotResponse(String message) {
        if (message.contains("hello")) {
            return "Hello there! How can I assist you today?";
        } else if (message.contains("loan")) {
            return "For loan inquiries, visit the nearest branch or call 0860112429.";
        }
        return "I'm sorry, I didn't understand that. Could you rephrase?";
    }

    @FXML
    private void toggleChatbot() {
        boolean isVisible = chatbotBox.isVisible();
        chatbotBox.setVisible(!isVisible);
        chatbotBox.setManaged(!isVisible);
    }

    @FXML
    private void clearChatArea() {
        chatArea.clear();
    }
}
