package com.example.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    //initialize application
    @Override
    public void init() {
        System.out.println("Initializing Application...");
    }
    //application content
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    //stop application
    @Override
    public void stop() {
        System.out.println("Application terminated successfully.");
    }
    public static void main(String[] args) {
        launch();
    }
}