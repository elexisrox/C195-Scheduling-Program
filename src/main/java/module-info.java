module com.example.c195_scheduling_program_v4_rox {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens app to javafx.fxml;
    exports app;
    exports app.controller;
    opens app.controller to javafx.fxml;
}