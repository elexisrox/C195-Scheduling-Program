package app.controller;

import app.DBaccess.DBAppointments;
import app.helper.Utilities;
import app.model.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

/** Controller class for ApptView.fxml.
 * @author Elexis Rox
 */
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

    //Clear Error Label
    public void clearErrorLbl() {
        errorMsgLbl.setText(" ");
    }

    //BUTTONS
    @FXML
    public void onActionAddAppt(ActionEvent event) throws IOException {
        System.out.println("Add Appointment button selected.");
        clearErrorLbl();
        Stage ownerStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Utilities.openApptDialog(ownerStage, true, this, null);
    }

    @FXML
    public void onActionModAppt(ActionEvent event) throws IOException {
        System.out.println("Modify Appointment button selected.");
        clearErrorLbl();
        Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();
        if (selectedAppt != null) {
            Stage ownerStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Utilities.openApptDialog(ownerStage, false, this, selectedAppt);
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
                    "Are you sure you want to delete this appointment?\n",
                    "\n\tAppointment ID: " + selectedAppt.getApptID() +
                            "\n\tAppointment Type: " + selectedAppt.getApptType() +
                            "\n\tAppointment Title: " + selectedAppt.getApptTitle()
            ).filter(response -> response == ButtonType.OK).isPresent();

            if (confirmed) {
                DBAppointments.deleteAppt(selectedAppt.getApptID());
                System.out.println("Appointment #" + selectedAppt.getApptID() + " deleted.");
                updateTableData();
                Utilities.showInfoAlert(
                        "Delete Successful",
                        "The following appointment has been successfully deleted/canceled:\n" +
                                "\n\tAppointment ID: " + selectedAppt.getApptID() +
                                "\n\tAppointment Type: " + selectedAppt.getApptType() +
                                "\n\tAppointment Title: " + selectedAppt.getApptTitle()
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
    public void loadAllAppts() {
        if (apptTable != null) {
            ObservableList<Appointment> appointments = DBAppointments.readAllAppts();
            apptTable.setItems(appointments);
//            appointments.forEach(appt -> {
//                System.out.println("Appointment ID: " + appt.getApptID());
//                System.out.println("\tStart (UTC): " + appt.getApptStart());
//                System.out.println("\tStart (Local): " + Utilities.fromUTC(appt.getApptStart(), userLocalZone));
//                System.out.println("\tEnd (UTC): " + appt.getApptEnd());
//                System.out.println("\tEnd (Local): " + Utilities.fromUTC(appt.getApptEnd(), userLocalZone));
//            });
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
        ZoneId userLocalZone = ZoneId.systemDefault();

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
        apptTable.getColumns().setAll(Utilities.createAppointmentTable().getColumns());

        //Load data based on the selected tab
        updateTableData();

    }
}