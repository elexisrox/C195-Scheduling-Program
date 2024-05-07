package app;

import app.DBaccess.DBAppointments;
import app.DBaccess.DBCountries;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class MainApplication extends Application {

    //initialize app
    @Override
    public void init() {
        System.out.println("Initializing Application...");
    }
    //app content
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoaderDialog = new FXMLLoader(MainApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoaderDialog.load());
        stage.setTitle("Appointment Scheduler Login");
        stage.centerOnScreen();
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
        /*Utilities.select(3);
        int rowsAffected = Utilities.delete(7);

        if(rowsAffected > 0){s
            System.out.println("Delete Successful");
        }
        else{
            System.out.println("Delete Failed!");
        }*/

        //APPOINTMENT ADD/DELETE TEST
        /*DBAppointments.addAppt("Test Appt", "TestDesc", "test location", "test type", LocalDateTime.of(2019, 3, 28, 14, 33, 48, 640000), LocalDateTime.of(2019, 3, 28, 16, 33, 48, 640000), 1, 1, 1);

        DBAppointments.deleteAppt(3);*/

        //launch application
        launch();

        //end connection to database
        app.helper.JDBC.closeConnection();
    }
}