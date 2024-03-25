package app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import app.helper.Utilities;
import app.controller.LoginController;


public class MainApplication extends Application {

    //initialize app
    @Override
    public void init() {
        System.out.println("Initializing Application...");
    }
    //app content
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainView.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 1000, 550);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Appointment Scheduler Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    //stop app
    @Override
    public void stop() {
        System.out.println("Application terminated successfully.");
    }

    public static void main(String[] args) throws SQLException {
        //connect to database
        app.helper.JDBC.openConnection();

        //FRUITS EXERCISE
        Utilities.select(3);
        /*int rowsAffected = Utilities.delete(7);

        if(rowsAffected > 0){
            System.out.println("Delete Successful");
        }
        else{
            System.out.println("Delete Failed!");
        }*/
        //END FRUITS EXERCISE

        //launch application
        launch();

        //end connection to database
        app.helper.JDBC.closeConnection();
    }
}