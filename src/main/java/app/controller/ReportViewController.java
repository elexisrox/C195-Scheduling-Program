package app.controller;

import app.DBaccess.DBContacts;
import app.DBaccess.DBAppointments;
import app.helper.Utilities;
import app.model.Appointment;
import app.model.Contact;
import javafx.collections.FXCollections;
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

    //Handle changing tabs
    @FXML
    void onReportsTabChanged(Event event) {
        if (event.getSource() instanceof Tab) {
            updateTabData();
        }
    }

    //Update the table, ChoiceBox and labels based on the selected tab
    public void updateTabData() {
        if (tabContactReport.isSelected()) {
            setupContactReportTab();
        } else if (tabTypeReport.isSelected()) {
            setupTypeReportTab();
        } else if (tabMonthReport.isSelected()) {
            setupMonthReportTab();
        } else if (tabCountryReport.isSelected()) {
            setupCountryReportTab();
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

        //Set up the default tab
        setupContactReportTab();
    }

    // Helper method to add a table to the AnchorPane
    private void addTableToPane(TableView<?> table) {
        if (reportsTablePane != null) {
            reportsTablePane.getChildren().clear(); // Clear any existing children
            reportsTablePane.getChildren().add(table); // Add the TableView to the AnchorPane
            AnchorPane.setTopAnchor(table, 0.0);
            AnchorPane.setBottomAnchor(table, 0.0);
            AnchorPane.setLeftAnchor(table, 0.0);
            AnchorPane.setRightAnchor(table, 0.0);
        }
    }

    //CHOICE BOXES FOR REPORTS
    //Load Contacts into ChoiceBox as general Objects
    public void loadReportsContactsCBox(ChoiceBox<Object> choiceBox) {
        ObservableList<Contact> contacts = DBContacts.readAllContacts();

        // Convert Contact objects to a list of formatted strings
        ObservableList<Object> contactNames = FXCollections.observableArrayList();
        for (Contact contact : contacts) {
            contactNames.add(contact.getContactID() + " - " + contact.getContactName());
        }

        choiceBox.setItems(contactNames);

        // Set the converter for the ChoiceBox
        choiceBox.setConverter(new StringConverter<Object>() {
            @Override
            public String toString(Object object) {
                return object == null ? null : object.toString();
            }

            @Override
            public Object fromString(String string) {
                return string; // Just return the string representation
            }
        });
    }

    //Handle Contact selection from ChoiceBox
    private ObservableList<Appointment> handleContactSelection(String contactString) {
        // Extract Contact ID from the selected string
        int contactID = Integer.parseInt(contactString.split(" - ")[0]);

        // Fetch appointments for the selected contact

        return DBAppointments.readApptsByContactID(contactID);
    }

    //Load Appointment Types into ChoiceBox as general Objects
    //Load Months into ChoiceBox as general Objects
    //Load Countries into ChoiceBox as general Objects

    private void setupContactReportTab() {
        //Set text labels accordingly
        choiceBoxLbl.setText("Select a Contact:");

        //Create an Appointments table to occupy the reportsTablePane
        if (reportsTablePane != null) {
            TableView<Appointment> apptTable = Utilities.createAppointmentTable(userLocalZone);
            addTableToPane(apptTable);

            //Enable the reportsBox and populate it with contacts
            reportsBox.setDisable(false);
            loadReportsContactsCBox(reportsBox);

            // Add listener to the ChoiceBox for selection changes
            reportsBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    ObservableList<Appointment> displayAppts = handleContactSelection(newValue.toString());
                    // Update the table with the fetched appointments
                    apptTable.setItems(displayAppts);
                    //Set the reportsResultLbl with the correct
                    int resultsCount = displayAppts.size();
                    reportsResultLbl.setText("Total Appointments: " + resultsCount);
                }
            });

        }

    }

    private void setupTypeReportTab() {

    }

    private void setupMonthReportTab() {

    }

    private void setupCountryReportTab() {

    }



}
