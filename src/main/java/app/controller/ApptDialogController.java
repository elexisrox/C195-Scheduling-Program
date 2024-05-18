package app.controller;

import app.DBaccess.DBAppointments;
import app.helper.UniversalControls;
import app.helper.Utilities;
import app.model.Appointment;
import app.model.Contact;
import app.model.Customer;
import app.model.User;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.stage.Stage;

/** Controller class for ApptDialog.fxml. Applies to both the Add Appointment and Modify Appointment dialog boxes.
 * @author Elexis Rox
 */

public class ApptDialogController implements Initializable {
    /**
     *  Initializes the Appointment Dialog Box Controller Class.
     */

    @FXML public DialogPane dialogPane;
    @FXML private Label topTitleLabel;
    @FXML private TextField apptIDInput;
    @FXML private TextField apptTitleInput;
    @FXML private TextField apptDescInput;
    @FXML private TextField apptLocInput;
    @FXML private TextField apptTypeInput;
    @FXML private Label apptTitleWarning;
    @FXML private Label apptDescWarning;
    @FXML private Label apptLocWarning;
    @FXML private Label apptTypeWarning;
    @FXML private Label apptContactWarning;
    @FXML private Label apptCustomerWarning;
    @FXML private Label apptUserWarning;
    @FXML private Label apptTimeWarning;
    @FXML private Label failureSaveWarning;
    @FXML private DatePicker startDateInput;
    @FXML private DatePicker endDateInput;
    @FXML private Spinner<Integer> startTimeHoursInput, startTimeMinutesInput, endTimeHoursInput, endTimeMinutesInput;
    @FXML private ChoiceBox<Contact> contactIDInput;
    @FXML private ChoiceBox<Customer> custIDInput;
    @FXML private ChoiceBox<User> userIDInput;

    //Appointment object
    private Appointment appointment;
    //Detect the user's time zone
    ZoneId userLocalZone = ZoneId.systemDefault();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Load Choice boxes
        Utilities.loadChoiceBoxContacts(contactIDInput);
        Utilities.loadChoiceBoxCustomers(custIDInput);
        Utilities.loadChoiceBoxUsers(userIDInput);

        //Initialize spinners
        startTimeHoursInput.setValueFactory(new IntegerSpinnerValueFactory(0, 23, 12));
        startTimeMinutesInput.setValueFactory(new IntegerSpinnerValueFactory(0, 59, 0, 5));
        endTimeHoursInput.setValueFactory(new IntegerSpinnerValueFactory(0, 23, 12));
        endTimeMinutesInput.setValueFactory(new IntegerSpinnerValueFactory(0, 59, 0, 5));

        //Set DatePicker default values to today's date
        LocalDate today = LocalDate.now();
        startDateInput.setValue(today);
        endDateInput.setValue(today);
    }

    //Sets any labels that may change between Add/Modify modes
    public void setApptLabels(String topTitleString) {
        topTitleLabel.setText(topTitleString);
    }

    //Retrieves auto-generated Appointment ID for new appointments
    public void retrieveNewApptID() {
        String newApptID = DBAppointments.readNextApptID();
        apptIDInput.setText(newApptID);
    }

    //Clears all error labels
    public void clearErrorLbls() {
        apptTitleWarning.setText("");
        apptDescWarning.setText("");
        apptLocWarning.setText("");
        apptTypeWarning.setText("");
        apptContactWarning.setText("");
        apptCustomerWarning.setText("");
        apptUserWarning.setText("");
        apptTimeWarning.setText("");
        failureSaveWarning.setText("");
    }
    //Validates all data fields before adding/updating an appointment
    public boolean validateInputs() {
        System.out.println("validateInputs called");

        //Clear all Error Labels
        clearErrorLbls();

        //Create a flag for the main warning message at the bottom of the dialog pane. If any errors are present, this value will be assigned as "true".
        boolean errorsPresent = false;

        //Retrieve data fields
        String title = apptTitleInput.getText();
        String desc = apptDescInput.getText();
        String location = apptLocInput.getText();
        String type = apptTypeInput.getText();
        LocalDate startDate = startDateInput.getValue();
        LocalTime startTime = LocalTime.of(startTimeHoursInput.getValue(), startTimeMinutesInput.getValue());
        LocalDate endDate = endDateInput.getValue();
        LocalTime endTime = LocalTime.of(endTimeHoursInput.getValue(), endTimeMinutesInput.getValue());
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        Contact selectedContact = contactIDInput.getValue();
        Customer selectedCustomer = custIDInput.getValue();
        User selectedUser = userIDInput.getValue();

        //Validate: Make sure that no fields have been left empty/blank.
        if (title.isBlank()) {
            apptTitleWarning.setText("Please enter a descriptive title.");
            errorsPresent = true;
        }
        if (desc.isBlank()) {
            apptDescWarning.setText("Please enter a brief description.");
            errorsPresent = true;
        }
        if (location.isBlank()) {
            apptLocWarning.setText("Please enter a location.");
            errorsPresent = true;
        }
        if (type.isBlank()) {
            apptTypeWarning.setText("Please specify the appointment type.");
            errorsPresent = true;
        }
        if (selectedContact == null) {
            apptContactWarning.setText("Please select a Contact ID.");
            errorsPresent = true;
        }
        if (selectedCustomer == null) {
            apptCustomerWarning.setText("Please select a Customer ID.");
            errorsPresent = true;
        }
        if (selectedUser == null) {
            apptUserWarning.setText("Please select a User ID.");
            errorsPresent = true;
        }

        //Validate: Check for time/date errors
        if (startDate.isAfter(endDate)) {
            apptTimeWarning.setText("End Date must occur after Start Date.");
            errorsPresent = true;
        }
        if (startDateTime.isAfter(endDateTime)) {
            apptTimeWarning.setText("End Time and Date must occur after Start Time and Date.");
            errorsPresent = true;
        }

        //TODO Validate: Check for conflicts in customer's schedule
        //TODO Validate: Check to make sure times are within business hours.

        //Set failureSaveWarning label if errors have been found.
        if (errorsPresent) {
            failureSaveWarning.setText("Unable to save. Please check the warnings above and try again.");
        }
        return !errorsPresent;
    }

    //Method for Save button
    public void handleSave() {
        System.out.println("handleSave called");
        //Fetch data input from text fields
        String title = apptTitleInput.getText();
        String desc = apptDescInput.getText();
        String location = apptLocInput.getText();
        String type = apptTypeInput.getText();

        //Fetch Dates/Times
        LocalDate startDate = startDateInput.getValue();
        LocalTime startTime = LocalTime.of(startTimeHoursInput.getValue(), startTimeMinutesInput.getValue());
        LocalDate endDate = endDateInput.getValue();
        LocalTime endTime = LocalTime.of(endTimeHoursInput.getValue(), endTimeMinutesInput.getValue());
        //Consolidate Dates/Times
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        //Convert Dates/Times to UTC
        LocalDateTime startDateTimeUTC = Utilities.toUTC(startDateTime, userLocalZone);
        LocalDateTime endDateTimeUTC = Utilities.toUTC(endDateTime, userLocalZone);

        //Fetch selected items from the ChoiceBoxes
        Contact selectedContact = contactIDInput.getValue();
        Customer selectedCustomer = custIDInput.getValue();
        User selectedUser = userIDInput.getValue();
        int userID = selectedUser.getUserID();
        int contactID = selectedContact.getContactID();
        int customerID = selectedCustomer.getCustID();

        //If the appointment object is null, add a new appointment to the database. If it is not null, update the fields and save the changes to the appointment.
        if (appointment == null) {
            System.out.println("Adding new appointment to the database.");
            DBAppointments.addAppt(title, desc, location, type, startDateTimeUTC, endDateTimeUTC, userID, contactID, customerID);
        } else {
            System.out.println("Updating existing appointment in the database.");
            //TODO reassess code below
            appointment.setApptTitle(title);
            appointment.setApptDesc(desc);
            appointment.setApptLocation(location);
            appointment.setApptType(type);
            appointment.setApptStart(startDateTimeUTC);
            appointment.setApptEnd(endDateTimeUTC);
            appointment.setApptUserID(userID);
            appointment.setApptContactID(contactID);
            appointment.setApptCustomerID(customerID);
            DBAppointments.updateAppt(appointment.getApptID(), title, desc, location, type, startDateTimeUTC, endDateTimeUTC, userID, contactID, customerID);
        }
    }

}