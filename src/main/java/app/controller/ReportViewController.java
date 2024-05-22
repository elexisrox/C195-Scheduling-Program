package app.controller;

import app.DBaccess.DBAppointments;
import app.DBaccess.DBContacts;
import app.DBaccess.DBCustomers;
import app.DBaccess.DBUsers;
import app.helper.Utilities;
import app.model.Appointment;
import app.model.Contact;
import app.model.Customer;
import app.model.User;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.stage.Stage;

public class ReportViewController implements Initializable {

    //Top navigation toggle group
    @FXML
    private ToggleGroup topMenuToggle;

    //Initializes the Main Reports View.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set up the toggle navigation menu
        topMenuToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    RadioButton selectedRadioButton = (RadioButton) newValue;
                    Stage stage = (Stage) selectedRadioButton.getScene().getWindow();
                    Utilities.onRadioButtonSelected(topMenuToggle, stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
