package app.controller;

import app.DBaccess.DBAppointments;
import app.helper.Utilities;
import app.model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * Controller class for the Appointment View: ApptView.fxml.
 * Manages the appointment view and handles user interactions with appointments.
 *
 * @author Elexis Rox
 */
public class ApptViewController implements Initializable {

    /** Top navigation toggle group */
    @FXML
    private ToggleGroup topMenuToggle;

    /** Main table view for displaying appointments */
    @FXML
    private TableView<Appointment> apptTable;

    /** Tabs for filtering appointments */
    @FXML
    private Tab tabAll;
    @FXML
    private Tab tabMonth;
    @FXML
    private Tab tabWeek;

    /** Label for displaying error messages */
    @FXML
    private Label errorMsgLbl;

    /** Label for displaying the user's timezone */

    @FXML
    private Label timezoneLbl;

    /**
     * Clears the error message label.
     */
    public void clearErrorLbl() {
        errorMsgLbl.setText(" ");
    }

    /**
     * Handles the action of adding a new appointment.
     *
     * @param event the action event triggered by the user
     * @throws IOException if there is an issue opening the appointment dialog
     */
    @FXML
    public void onActionAddAppt(ActionEvent event) throws IOException {
        clearErrorLbl();
        Stage ownerStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Utilities.openApptDialog(ownerStage, true, this, null);
    }

    /**
     * Handles the action of modifying an existing appointment.
     *
     * @param event the action event triggered by the user
     * @throws IOException if there is an issue opening the appointment dialog
     */
    @FXML
    public void onActionModAppt(ActionEvent event) throws IOException {
        clearErrorLbl();
        Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();
        if (selectedAppt != null) {
            Stage ownerStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Utilities.openApptDialog(ownerStage, false, this, selectedAppt);
        } else {
            errorMsgLbl.setText("Please select an appointment to modify.");
        }
    }

    /**
     * Handles the action of deleting an existing appointment.
     * LAMBDA EXPRESSION: The lambda expression is used to filter the result of the
     * showConfirmationAlert method to check if the user clicked the "OK" button. Using a
     * lambda here instead of an anonymous inner method improves the readability of the code.
     */
    @FXML
    public void onActionDelAppt() {
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
            errorMsgLbl.setText("Please select an appointment to delete.");
        }

    }

    /**
     * Handles the action of logging out of the application.
     *
     * @param event the action event triggered by the user
     * @throws IOException if there is an issue transitioning to the login view
     */
    @FXML
    public void onActionLogout(ActionEvent event) throws IOException {
        clearErrorLbl();
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Utilities.logoutButton(stage);
    }

    /**
     * Handles the action of exiting the application.
     */
    @FXML
    public void onActionExit() {
        clearErrorLbl();
        Utilities.exitButton();
    }

    /**
     * Handles the event when a tab is selected.
     * This event is fired when a tab is selected. "View All" tab is selected by default on
     * application load.
     *
     * @param event the event triggered by the tab selection
     */
    @FXML
    void onTabChanged(Event event) {
        if (event.getSource() instanceof Tab) {
            updateTableData();
        }
    }

    /**
     * Updates the table data based on the selected tab.
     */
    public void updateTableData() {
        if (tabAll.isSelected()) {
            loadAllAppts();
        } else if (tabMonth.isSelected()) {
            loadMonthAppts();
        } else if (tabWeek.isSelected()) {
            loadWeekAppts();
        }
    }

    /**
     * Loads all appointments and displays them in the table.
     */
    @FXML
    public void loadAllAppts() {
        if (apptTable != null) {
            ObservableList<Appointment> appointments = DBAppointments.readAllAppts();
            apptTable.setItems(appointments);
        }
    }

    /**
     * Loads all appointments within the current month and displays them in the table.
     */
    @FXML
    void loadMonthAppts() {
        if (apptTable != null) {
            ObservableList<Appointment> appointments = DBAppointments.readMonthAppts();
            apptTable.setItems(appointments);
        }
    }

    /**
     * Loads appointments within the current week (Monday-Sunday) and displays them in
     * the table.
     */
    @FXML
    void loadWeekAppts() {
        if (apptTable != null) {
            ObservableList<Appointment> appointments = DBAppointments.readWeekAppts();
            apptTable.setItems(appointments);
        }
    }

    /**
     * Initializes the Main Appointment View. Detects and displays the user's time zone.
     * Builds and displays the appointments table. Updates table data based on tab filtering.
     * LAMBDA EXPRESSION: The lambda expression in this method is used as a change
     * listener for the selectedToggleProperty of the topMenuToggle ToggleGroup.
     *
     * @param url the location used to resolve relative paths for the root object, or null
     *            if the location is not known
     * @param rb the resources used to localize the root object, or null if the root object
     *           was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Detect user's time zone
        ZoneId userLocalZone = ZoneId.systemDefault();
        // Sets timezone label according to the user's timezone
        timezoneLbl.setText(String.valueOf(userLocalZone));

        // Set up the toggle navigation menu
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

        // Initialize the Appointments table and set up the columns
        apptTable.getColumns().setAll(Utilities.createAppointmentTable().getColumns());

        // Load data based on the selected tab
        updateTableData();
    }
}