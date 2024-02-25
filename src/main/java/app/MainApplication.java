package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    //initialize app
    @Override
    public void init() {
        System.out.println("Initializing Application...");
    }
    //app content
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 550);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        stage.setMinWidth(400);
        stage.setMinHeight(400);
    }
    //stop app
    @Override
    public void stop() {
        System.out.println("Application terminated successfully.");
    }

    public static void main(String[] args) {
        //connect to database
        app.helper.JDBC.openConnection();

        //launch application
        launch();

        //end connection to database
        app.helper.JDBC.closeConnection();
    }
}