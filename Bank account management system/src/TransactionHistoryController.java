import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionHistoryController {

    @FXML
    private Button backButton;
    @FXML
    private VBox transactionVBox;
    @FXML
    private Text dateText;
    @FXML
    private Text timeText;

    private TransactionService transactionService;

    public TransactionHistoryController() {
        this.transactionService = new TransactionService();
    }

    @FXML
    private void initialize() {
        User loggedInUser = Session.getInstance().getLoggedInUser();

        // Get all accounts for the logged-in user
        List<Account> userAccounts = FileStorageService.readAccounts().stream()
                .filter(account -> account.getUserId() == loggedInUser.getId())
                .collect(Collectors.toList());

        // Map account IDs to account names
        Map<Integer, String> accountIdToName = userAccounts.stream()
                .collect(Collectors.toMap(Account::getId, Account::getHolderName));

        // Get all transactions
        List<String> allTransactions = FileStorageService.readTransactions();

        // Filter and format transactions for the logged-in user
        List<String> userTransactions = allTransactions.stream()
                .filter(transaction -> isTransactionRelatedToUser(transaction, accountIdToName.keySet()))
                .map(transaction -> replaceAccountIdsWithNames(transaction, accountIdToName))
                .collect(Collectors.toList());

        // Set the date and time
        setDateTime();

        // Add transactions to the VBox
        userTransactions.forEach(transaction -> {
            Text transactionText = new Text(transaction);
            transactionVBox.getChildren().add(transactionText);
        });
    }

    private boolean isTransactionRelatedToUser(String transaction, Iterable<Integer> userAccountIds) {
        for (int accountId : userAccountIds) {
            if (transaction.contains("account ID " + accountId)) {
                return true;
            }
        }
        return false;
    }

    private String replaceAccountIdsWithNames(String transaction, Map<Integer, String> accountIdToName) {
        for (Map.Entry<Integer, String> entry : accountIdToName.entrySet()) {
            String accountIdString = "account ID " + entry.getKey();
            if (transaction.contains(accountIdString)) {
                transaction = transaction.replace(accountIdString, entry.getValue() + "'s account");
            }
        }
        return transaction;
    }

    private void setDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        Date date = new Date();
        dateText.setText(dateFormat.format(date));
        timeText.setText(timeFormat.format(date));
    }

    @FXML
    private void goBack() throws Exception {
        if (backButton != null) {
            // Get the current stage (the window)
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Load the MainView (Dashboard) FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            BorderPane pane = loader.load();

            // Set the new scene to the same stage (window)
            stage.setScene(new Scene(pane));

            // Show the new scene (this brings the dashboard screen back into view)
            stage.show();
        } else {
            System.err.println("Back Button not initialized.");
        }
    }
}


