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
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/** Controller class for ApptView.fxml.
 * @author Elexis Rox
 */

//TODO Possibly rename MainViewController depending on how I handle the other views
public class ApptViewController implements Initializable {

    //Top navigation toggle group
    @FXML
    private ToggleGroup topMenuToggle;

    //Main table view
    @FXML
    private TableView<Appointment> apptTable;

    //Tabs
    @FXML
    private Tab tabAll;
    @FXML
    private Tab tabMonth;
    @FXML
    private Tab tabWeek;

    //Labels
    @FXML
    private Label errorMsgLbl;
    @FXML
    private Label timezoneLbl;

    //Detect the user's time zone
    ZoneId userLocalZone = ZoneId.systemDefault();

    //Label Setters
    @FXML
    public void onActionAddAppt(ActionEvent event) throws IOException {
        System.out.println("Add Appointment button selected.");
        clearErrorLbl();
        Stage ownerStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Utilities.openAddApptDialog(ownerStage, this);
    }

    //Clear Error Label
    public void clearErrorLbl() {
        errorMsgLbl.setText(" ");
    }
    @FXML
    public void onActionModAppt(ActionEvent event) throws IOException {
        System.out.println("Modify Appointment button selected.");
        clearErrorLbl();
        Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();
        if (selectedAppt != null) {
            Stage ownerStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Utilities.openModApptDialog(ownerStage, this, selectedAppt);
        } else {
            System.out.println("No appointment selected.");
            errorMsgLbl.setText("Please select an appointment to modify.");
        }
    }

    @FXML
    public void onActionDelAppt(ActionEvent event) {
        System.out.println("Delete Appointment button selected.");
        clearErrorLbl();
        Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();
        if (selectedAppt != null) {
            boolean confirmed = Utilities.showConfirmationAlert(
                    "Delete Confirmation",
                    "Are you sure you want to delete this appointment?",
                    "Appointment ID: " + selectedAppt.getApptID() +
                            "\nAppointment Type: " + selectedAppt.getApptType() +
                            "\nAppointment Title: " + selectedAppt.getApptTitle()
            ).filter(response -> response == ButtonType.OK).isPresent();

            if (confirmed) {
                DBAppointments.deleteAppt(selectedAppt.getApptID());
                System.out.println("Appointment #" + selectedAppt.getApptID() + " deleted.");
                updateTableData();
                Utilities.showInfoAlert(
                        "Delete Successful",
                        "The following appointment has been successfully deleted/canceled:\n" +
                                "Appointment ID: " + selectedAppt.getApptID() + "\n" +
                                "Appointment Type: " + selectedAppt.getApptType() + "\n" +
                                "Appointment Title: " + selectedAppt.getApptTitle()
                );
            }
        } else {
            System.out.println("No appointment selected.");
            errorMsgLbl.setText("Please select an appointment to delete.");
        }

    }

    @FXML
    public void onActionLogout(ActionEvent event) throws IOException {
        clearErrorLbl();
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Utilities.logoutButton(stage);
    }

    @FXML
    public void onActionExit(ActionEvent event) {
        clearErrorLbl();
        Utilities.exitButton();
    }

    //Tab Selection Filtering
    //Event fires when a tab is selected. "View All" tab is selected by default on application load
    @FXML
    void onTabChanged(Event event) {
        if (event.getSource() instanceof Tab) {
            updateTableData();
        }
    }

    //Update the table data based on the selected tab
    public void updateTableData() {
        if (tabAll.isSelected()) {
            loadAllAppts();
        } else if (tabMonth.isSelected()) {
            loadMonthAppts();
        } else if (tabWeek.isSelected()) {
            loadWeekAppts();
        }
    }

    //Loads all appointments
    @FXML
    void loadAllAppts() {
        if (apptTable != null) {
            ObservableList<Appointment> appointments = DBAppointments.readAllAppts();
            apptTable.setItems(appointments);
        }
    }

    //Loads all appointments within the current month
    @FXML
    void loadMonthAppts() {
        if (apptTable != null) {
            ObservableList<Appointment> appointments = DBAppointments.readMonthAppts();
            apptTable.setItems(appointments);
        }
    }

    //Loads appointments within the current week (Monday-Sunday)
    @FXML
    void loadWeekAppts() {
        if (apptTable != null) {
            ObservableList<Appointment> appointments = DBAppointments.readWeekAppts();
            apptTable.setItems(appointments);
        }
    }

    //Initializes the Main Appointment View
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

        //Initialize the Appointments table and set up the columns
        apptTable.getColumns().setAll(Utilities.createAppointmentTable(userLocalZone).getColumns());

        //Load data based on the selected tab
        updateTableData();

    }
}