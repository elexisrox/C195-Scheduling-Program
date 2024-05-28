package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * MainApplication class extends the JavaFX Application class and serves as the entry
 * point for the JavaFX application. It handles the initialization, starting, and
 * stopping of the application, as well as managing the database connection.
 * @author Elexis Rox
 */
public class MainApplication extends Application {

    /**
     * Initializes the application.
     */
    @Override
    public void init() {
        System.out.println("Initializing Application...");
    }

    /**
     * Starts the application. This method is called after the init method has been
     * executed. It sets up the primary stage and loads the login screen.
     * @param stage The primary stage for this application, onto which the application
     *              scene can be set.
     * @throws IOException if the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoaderDialog = new FXMLLoader(MainApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoaderDialog.load());
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Stops the application. This method is called to end the program.
     */
    @Override
    public void stop() {
        System.out.println("Application terminated.");
    }

    /**
     * The main method is the entry point for the application. It connects to the
     * database, launches the JavaFX application, and closes the database connection
     * when the application ends.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // Connect to database
        app.helper.JDBC.openConnection();

        // Launch application
        launch();

        // End connection to database
        app.helper.JDBC.closeConnection();
    }
}