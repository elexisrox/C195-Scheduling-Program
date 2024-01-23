module com.example.c195_scheduling_program_v4_rox {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.main to javafx.fxml;
    exports com.example.main;
    exports com.example.main.controller;
    opens com.example.main.controller to javafx.fxml;
}