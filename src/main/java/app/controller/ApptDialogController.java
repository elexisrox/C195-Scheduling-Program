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
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

/**
 * Controller class for the Appointment Dialog (Add/Modify Appointment): ApptDialog.fxml.
 * Manages the appointment input form and handles validation and data saving.
 *
 * @author Elexis Rox
 */

public class ApptDialogController implements Initializable {
    // Dialog Pane components
    @FXML public DialogPane dialogPane;
    @FXML private Label topTitleLabel;

    // Input fields
    @FXML private TextField apptIDInput, apptTitleInput, apptDescInput, apptLocInput, apptTypeInput;
    @FXML private DatePicker startDateInput, endDateInput;
    @FXML private Spinner<Integer> startTimeHoursInput, startTimeMinutesInput, endTimeHoursInput, endTimeMinutesInput;
    @FXML private ChoiceBox<Contact> contactIDInput;
    @FXML private ChoiceBox<Customer> custIDInput;
    @FXML private ChoiceBox<User> userIDInput;

    // Warning labels
    @FXML private Label apptTitleWarning, apptDescWarning, apptLocWarning, apptTypeWarning;
    @FXML private Label apptContactWarning, apptCustomerWarning, apptUserWarning, apptTimeWarning, failureSaveWarning;

    // Appointment object
    private Appointment appointment;

    // Define business hours in ET
    ZoneId businessZone = ZoneId.of("America/New_York");
    // Business start and end time in ET
    LocalTime businessStart = LocalTime.of(8, 0);
    LocalTime businessEnd = LocalTime.of(22, 0);

    /**
     * Initializes the dialog pane, calling methods to set up the ChoiceBoxes, Spinners, and
     * DatePickers.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeChoiceBoxes();
        initializeSpinners();
        initializeDatePickers();
    }

    /**
     * Initializes the choice boxes with data from the database.
     */
    private void initializeChoiceBoxes() {
        Utilities.loadChoiceBoxContacts(contactIDInput);
        Utilities.loadChoiceBoxCustomers(custIDInput);
        Utilities.loadChoiceBoxUsers(userIDInput);
    }

    /**
     * Initializes the spinners for time inputs.
     */
    private void initializeSpinners() {
        startTimeHoursInput.setValueFactory(new IntegerSpinnerValueFactory(0, 23, 12));
        startTimeMinutesInput.setValueFactory(new IntegerSpinnerValueFactory(0, 59, 0, 1));
        endTimeHoursInput.setValueFactory(new IntegerSpinnerValueFactory(0, 23, 12));
        endTimeMinutesInput.setValueFactory(new IntegerSpinnerValueFactory(0, 59, 0, 1));
    }

    /**
     * Initializes the date pickers with the current date.
     */
    private void initializeDatePickers() {
        LocalDate today = LocalDate.now();
        startDateInput.setValue(today);
        endDateInput.setValue(today);
    }

    /**
     * Sets the labels for the dialog based on the mode (Add/Modify).
     *
     * @param topTitleString The title string to set.
     */
    public void setApptLabels(String topTitleString) {
        topTitleLabel.setText(topTitleString);
    }

    /**
     * Sets the appointment data in the input fields for editing.
     *
     * @param appointment The appointment to edit.
     */
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
        apptIDInput.setText(String.valueOf(appointment.getApptID()));
        apptTitleInput.setText(appointment.getApptTitle());
        apptDescInput.setText(appointment.getApptDesc());
        apptLocInput.setText(appointment.getApptLocation());
        apptTypeInput.setText(appointment.getApptType());

        // Set date and time pickers with appointment data
        LocalDateTime startDateTime = appointment.getApptStart();
        LocalDateTime endDateTime = appointment.getApptEnd();
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

    /**
     * Clears all warning labels.
     */
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

    /**
     * Checks if the inputted times are within business hours.
     *
     * @param startDateTime The start date and time.
     * @param endDateTime The end date and time.
     * @return True if within business hours, otherwise false.
     */
    public boolean isWithinBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        // Get user's local time zone
        ZoneId userTimeZone = ZoneId.systemDefault();

        // Convert business hours to user's local time zone
        // Create ZonedDateTime for business hours in ET
        ZonedDateTime businessStartTimeET = ZonedDateTime.of(LocalDate.now(), businessStart, businessZone);
        ZonedDateTime businessEndTimeET = ZonedDateTime.of(LocalDate.now(), businessEnd, businessZone);

        // Convert to user's local time zone
        LocalTime businessStartTimeLocal = businessStartTimeET.withZoneSameInstant(userTimeZone).toLocalTime();
        LocalTime businessEndTimeLocal = businessEndTimeET.withZoneSameInstant(userTimeZone).toLocalTime();

        // Extract the LocalTime components from the appointment times
        LocalTime apptStartTime = startDateTime.toLocalTime();
        LocalTime apptEndTime = endDateTime.toLocalTime();

        // Check if appointment times are within business hours
        boolean startsWithinBusinessHours = !apptStartTime.isBefore(businessStartTimeLocal) && !apptStartTime.isAfter(businessEndTimeLocal);
        boolean endsWithinBusinessHours = !apptEndTime.isBefore(businessStartTimeLocal) && !apptEndTime.isAfter(businessEndTimeLocal);

        return startsWithinBusinessHours && endsWithinBusinessHours;
    }

    /**
     * Checks if the given time range conflicts with any of the existing appointments for
     * the customer.
     *
     * @param customerID The customer ID.
     * @param newStart The new start date and time.
     * @param newEnd The new end date and time.
     * @return True if there are conflicting appointments, otherwise false.
     */
    private boolean hasConflictingAppointments(int customerID, LocalDateTime newStart, LocalDateTime newEnd) {
        // Pass along current appointment ID if modifying an existing appointment
        // Use -1 if there is no appointment to exclude
        int excludeApptID = (appointment != null) ? appointment.getApptID() : -1;

        ObservableList<Appointment> conflictingAppointments = DBAppointments.readOverlappingApptsByCustID(customerID, newStart, newEnd, excludeApptID);

        return !conflictingAppointments.isEmpty();
    }

    /**
     * Validates all input fields before adding or updating an appointment.
     *
     * @return True if inputs are valid, otherwise false.
     */
    public boolean validateInputs() {
        // Clear all Error Labels
        clearErrorLbls();

        // Create a flag for the main warning message at the bottom of the dialog pane. If any errors are present, this value will be assigned as "true".
        boolean errorsPresent = false;

        // Retrieve data fields
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

        // Validate: Make sure that no fields have been left empty/blank.
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

        // Validate: Check for time/date errors
        if (startDate.isAfter(endDate)) {
            apptTimeWarning.setText("End Date must occur after Start Date.");
            errorsPresent = true;
        }
        if (startDateTime.isAfter(endDateTime)) {
            apptTimeWarning.setText("End Time and Date must occur after Start Time and Date.");
            errorsPresent = true;
        }

        // Validate: Check to make sure times are within business hours
        if (!isWithinBusinessHours(startDateTime, endDateTime)) {
            apptTimeWarning.setText("Appointment times must be within business hours: \n"
                    + businessStart + " to " + businessEnd + " " + businessZone);
            errorsPresent = true;
        }

        // Validate: Check for conflicts in customer's schedule
        if (selectedCustomer != null && hasConflictingAppointments(selectedCustomer.getCustID(), startDateTime, endDateTime)) {
            apptTimeWarning.setText("This customer already has an appointment during the selected time.");
            errorsPresent = true;
        }

        // Set failureSaveWarning label if errors have been found.
        if (errorsPresent) {
            failureSaveWarning.setText("Unable to save. Please check the warnings above and try again.");
        }
        return !errorsPresent;
    }

    /**
     * Handles saving the appointment to the database.
     */
    public void handleSave() {
        // Fetch data input from text fields
        String title = apptTitleInput.getText();
        String desc = apptDescInput.getText();
        String location = apptLocInput.getText();
        String type = apptTypeInput.getText();
        // Fetch Dates/Times
        LocalDate startDate = startDateInput.getValue();
        LocalTime startTime = LocalTime.of(startTimeHoursInput.getValue(), startTimeMinutesInput.getValue());
        LocalDate endDate = endDateInput.getValue();
        LocalTime endTime = LocalTime.of(endTimeHoursInput.getValue(), endTimeMinutesInput.getValue());
        // Consolidate Dates/Times
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        // Fetch selected items from the ChoiceBoxes
        Contact selectedContact = contactIDInput.getValue();
        Customer selectedCustomer = custIDInput.getValue();
        User selectedUser = userIDInput.getValue();
        int userID = selectedUser.getUserID();
        int contactID = selectedContact.getContactID();
        int customerID = selectedCustomer.getCustID();

        // If the appointment object is null, add a new appointment to the database. If it is not null, update the fields and save the changes to the appointment.
        if (appointment == null) {
            DBAppointments.addAppt(title, desc, location, type, startDateTime, endDateTime, userID, contactID, customerID);
        } else {
            appointment.setApptTitle(title);
            appointment.setApptDesc(desc);
            appointment.setApptLocation(location);
            appointment.setApptType(type);
            appointment.setApptStart(startDateTime);
            appointment.setApptEnd(endDateTime);
            appointment.setApptUserID(userID);
            appointment.setApptContactID(contactID);
            appointment.setApptCustomerID(customerID);
            DBAppointments.updateAppt(appointment.getApptID(), title, desc, location, type, startDateTime, endDateTime, userID, contactID, customerID);
        }
    }
}