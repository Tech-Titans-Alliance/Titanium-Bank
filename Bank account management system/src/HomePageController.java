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
import javafx.scene.control.Label;
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
    private ImageView bankImage; // Reference to the image node
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
            return "Hello there, how can I assist you?";
        } else if (message.contains("transfer money")) {
            return "Navigate to the payments menu, select or add a beneficiary, input the payment details, and confirm the immediate payment.";
        } else if (message.contains("recurring payments")) {
            return "Yes, you can arrange recurring payments to be automatically deducted from your account payday each month.";
        } else if (message.contains("utility bills")) {
            return "Yes, you can set up utility bill payments.";
        } else if (message.contains("bill payment history")) {
            return "Bill payment can be viewed on the transaction history.";
        } else if (message.contains("apply for loan")) {
            return "You can visit our nearest branch or call us on 0860112429. You must bring the following: your original SA ID document (must be over 18 years), latest payslip, and a bank statement showing your last 3 salary deposits.";
        } else if (message.contains("loan interest")) {
            return "13%.";
        } else if (message.contains("savings account")) {
            return "Opening an account is easy. Download the Titanium Bank app, enter your SA ID number, take a few selfies, and provide your personal details.";
        } else if (message.contains("savings interest")) {
            return "2.5%.";
        } else if (message.contains("fraud")) {
            return "To report fraud for personal banking, call us on 0860112429, or WhatsApp 0614261759.";
        } else if (message.contains("customer support")) {
            return "For personal banking, call us on 0860112429, or WhatsApp 0614261759.";
        } else if (message.contains("exit")) {
            return "Thank you for using our service. Goodbye!";
        } else {
            return "I'm sorry, I didn't understand that. Could you try rephrasing?";
        }
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
