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
    //Defined business hours in ET
    private static final ZoneId businessTimeZone = ZoneId.of("America/New_York");
    private static final LocalTime businessStartTime = LocalTime.of(8, 0);  // 8:00 AM ET
    private static final LocalTime businessEndTime = LocalTime.of(22, 0);   // 10:00 PM ET

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Load Choice boxes
        Utilities.loadChoiceBoxContacts(contactIDInput);
        Utilities.loadChoiceBoxCustomers(custIDInput);
        Utilities.loadChoiceBoxUsers(userIDInput);

        //Initialize spinners
        startTimeHoursInput.setValueFactory(new IntegerSpinnerValueFactory(0, 23, 12));
        startTimeMinutesInput.setValueFactory(new IntegerSpinnerValueFactory(0, 59, 0, 1));
        endTimeHoursInput.setValueFactory(new IntegerSpinnerValueFactory(0, 23, 12));
        endTimeMinutesInput.setValueFactory(new IntegerSpinnerValueFactory(0, 59, 0, 1));

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

    // Method to set the appointment object and populate the fields
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
        apptIDInput.setText(String.valueOf(appointment.getApptID()));
        apptTitleInput.setText(appointment.getApptTitle());
        apptDescInput.setText(appointment.getApptDesc());
        apptLocInput.setText(appointment.getApptLocation());
        apptTypeInput.setText(appointment.getApptType());

        // Set date and time pickers with appointment data
        LocalDateTime startDateTime = Utilities.fromUTC(appointment.getApptStart(), userLocalZone);
        LocalDateTime endDateTime = Utilities.fromUTC(appointment.getApptEnd(), userLocalZone);

        startDateInput.setValue(startDateTime.toLocalDate());
        startTimeHoursInput.getValueFactory().setValue(startDateTime.getHour());
        startTimeMinutesInput.getValueFactory().setValue(startDateTime.getMinute());

        endDateInput.setValue(endDateTime.toLocalDate());
        endTimeHoursInput.getValueFactory().setValue(endDateTime.getHour());
        endTimeMinutesInput.getValueFactory().setValue(endDateTime.getMinute());

        // Set choice boxes with appointment data
        contactIDInput.setValue(DBContacts.readContact(appointment.getApptContactID()));
        custIDInput.setValue(DBCustomers.readCustomer(appointment.getApptCustomerID()));
        userIDInput.setValue(DBUsers.readUser(appointment.getApptUserID()));
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

    //Checks if inputted times are within set business hours
    private boolean isWithinBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        ZonedDateTime startZonedDateTime = startDateTime.atZone(userLocalZone).withZoneSameInstant(businessTimeZone);
        ZonedDateTime endZonedDateTime = endDateTime.atZone(userLocalZone).withZoneSameInstant(businessTimeZone);

        LocalTime startLocalTime = startZonedDateTime.toLocalTime();
        LocalTime endLocalTime = endZonedDateTime.toLocalTime();

        return !startLocalTime.isBefore(businessStartTime) && !endLocalTime.isAfter(businessEndTime);
    }

    // Checks if the given time range conflicts with any of the existing appointments for the customer.
    private boolean hasConflictingAppointments(int customerID, LocalDateTime newStart, LocalDateTime newEnd) {
        ObservableList<Appointment> conflictingAppointments = DBAppointments.readOverlappingApptsByCustID(customerID, newStart, newEnd);
        if (!conflictingAppointments.isEmpty()) {
            System.out.println("Conflicting appointments found:");
            for (Appointment appt : conflictingAppointments) {
                System.out.println("Appointment ID: " + appt.getApptID());
                System.out.println("Start: " + appt.getApptStart());
                System.out.println("End: " + appt.getApptEnd());
            }
            return true;
        }
        return false;
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

        //Validate: Check to make sure times are within business hours
        if (!isWithinBusinessHours(startDateTime, endDateTime)) {
            apptTimeWarning.setText("Appointment times must be within business hours (8:00 AM to 10:00 PM ET).");
            errorsPresent = true;
        }

        //Validate: Check for conflicts in customer's schedule
        if (selectedCustomer != null && hasConflictingAppointments(selectedCustomer.getCustID(), startDateTime, endDateTime)) {
            apptTimeWarning.setText("This customer already has an appointment during the selected time.");
            errorsPresent = true;
        }

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