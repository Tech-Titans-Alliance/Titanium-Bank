// HomePageController.java
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class HomePageController {

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
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
}
