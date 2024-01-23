module com.example.c195_scheduling_program_v4_rox {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens application to javafx.fxml;
    exports application;
    exports application.controller;
    opens application.controller to javafx.fxml;
}