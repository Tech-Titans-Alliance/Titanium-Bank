import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static final double WINDOW_WIDTH = 800; // Constant width
    private static final double WINDOW_HEIGHT = 600; // Constant height
    private static Stage primaryStage; // Store the primary stage

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage; // Save the reference to the primary stage
        primaryStage.setTitle("Bank Account Management System");  
        switchScene("HomePageView.fxml"); // Load the initial scene
        primaryStage.setResizable(false); // Ensure the size stays fixed
        primaryStage.show();
    }

    public static void switchScene(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFileName));
            Scene scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT); // Set size explicitly
            primaryStage.setScene(scene); // Update the stage with the new scene
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileStorageService.initializeFiles(); // Initialize the file storage system
        launch(args);
    }
}
