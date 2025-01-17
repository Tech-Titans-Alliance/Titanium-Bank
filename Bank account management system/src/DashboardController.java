// DashboardController.java
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardController {
    @FXML private Label welcomeLabel;

    public void initialize() {
        // Set welcome message on startup
        User loggedInUser = Session.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            welcomeLabel.setText("Welcome, " + loggedInUser.getName() + "!");
        }
    }

    @FXML
    private void viewAccounts(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("viewAccounts.fxml"));
        Parent root = loader.load();

        ViewAccountsController controller = loader.getController();
        // No need to pass loggedInUser, it's already accessible through Session
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void createAccount(ActionEvent event) throws Exception {
        navigateTo("createAccount.fxml", event);
    }

    @FXML
    private void deposit(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deposit.fxml"));
        Parent root = loader.load();

        DepositController controller = loader.getController();
        // No need to pass loggedInUser, it's already accessible through Session
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void withdraw(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("withdraw.fxml"));
        Parent root = loader.load();

        WithdrawController controller = loader.getController();
        // No need to pass loggedInUser, it's already accessible through Session
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void transfer(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transfer.fxml"));
        Parent root = loader.load();

        TransferController controller = loader.getController();
        // No need to pass loggedInUser, it's already accessible through Session
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void viewTransactions(ActionEvent event) throws Exception {
        navigateTo("transactionHistory.fxml", event);
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

