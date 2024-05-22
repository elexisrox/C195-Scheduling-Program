package app.controller;

import app.DBaccess.DBAppointments;
import app.helper.Utilities;
import app.model.Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CustViewController implements Initializable {

    //Top navigation toggle group
    @FXML
    private ToggleGroup topMenuToggle;

    @FXML
    private TableColumn<?, ?> custAddressCol;

    @FXML
    private TableColumn<?, ?> custDivisionCol;

    @FXML
    private TableColumn<?, ?> custIDCol;

    @FXML
    private TableColumn<?, ?> custNameCol;

    @FXML
    private TableColumn<?, ?> custPhoneCol;

    @FXML
    private TableColumn<?, ?> custPostalCol;

    @FXML
    private TableView<?> custTable;

    @FXML
    private Label errorMsgLbl;

    @FXML
    private Label timezoneLbl;

    @FXML
    void onActionAddCust(ActionEvent event) {

    }

    @FXML
    void onActionDelCust(ActionEvent event) {

    }

    @FXML
    void onActionExit(ActionEvent event) {

    }

    @FXML
    void onActionLogout(ActionEvent event) {

    }

    @FXML
    void onActionModCust(ActionEvent event) {

    }

    //Initializes the Main Customer View.
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
