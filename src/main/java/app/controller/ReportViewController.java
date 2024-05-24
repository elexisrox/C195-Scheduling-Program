package app.controller;

import app.DBaccess.DBContacts;
import app.DBaccess.DBAppointments;
import app.helper.Utilities;
import app.model.Appointment;
import app.model.Contact;
import app.model.Customer;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ReportViewController implements Initializable {

    //Top navigation toggle group
    @FXML
    private ToggleGroup topMenuToggle;

    //Detect the user's time zone
    ZoneId userLocalZone = ZoneId.systemDefault();

    //Labels
    @FXML
    private Label choiceBoxLbl;
    @FXML
    private Label reportsResultLbl;
    @FXML
    private Label timezoneLbl;

    //ChoiceBox
    @FXML
    private ChoiceBox<Object> reportsBox;

    //Tabs
    @FXML
    private Tab tabContactReport;
    @FXML
    private Tab tabTypeReport;
    @FXML
    private Tab tabMonthReport;
    @FXML
    private Tab tabCountryReport;

    //Anchor pane for displaying tables
    @FXML
    private AnchorPane reportsTablePane;

    //Buttons
    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Utilities.logoutButton(stage);
    }

    @FXML
    void onActionExit(ActionEvent event) {
        Utilities.exitButton();
    }

    //Changing Tabs
    @FXML
    void onReportsTabChanged(Event event) {
        System.out.println("onReportsTabChanged Fired");
        Tab selectedTab = (Tab) event.getSource();
        if (selectedTab.isSelected()) {
            if (selectedTab == tabContactReport) {
                setupContactSchedulesTab();
            } else if (selectedTab == tabTypeReport) {

            }
            // Handle other tabs similarly
        }
    }

    //Initializes the Main Reports View
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set up the toggle navigation menu
        topMenuToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    RadioButton selectedRadioButton = (RadioButton) newValue;
                    Stage stage = (Stage) selectedRadioButton.getScene().getWindow();
                    Utilities.onRadioButtonSelected(topMenuToggle, stage);
                } catch (Exception e) {
                    System.out.println("Error (Navigation Radio Buttons): " + e.getMessage());
                }
            }
        });

        //Sets timezone label according to the user's timezone
        timezoneLbl.setText(String.valueOf(userLocalZone));
        setupContactSchedulesTab();
    }

    private void setupContactSchedulesTab() {
        // TODO reminder to wrap functions inside this if condition to make sure that the reportsTablePane exists before modifying it
        if (reportsTablePane != null) {
            TableView<Appointment> appointmentTable = Utilities.createAppointmentTable(userLocalZone);
            addTableToPane(appointmentTable);
        }
    }

    // Helper method to add a table to the AnchorPane
    private void addTableToPane(TableView<?> table) {
        if (reportsTablePane != null) {
            reportsTablePane.getChildren().clear();}
        System.out.println(reportsTablePane);
        System.out.println(table);
        AnchorPane.setTopAnchor(table, 0.0);
        AnchorPane.setBottomAnchor(table, 0.0);
        AnchorPane.setLeftAnchor(table, 0.0);
        AnchorPane.setRightAnchor(table, 0.0);
    }

}
