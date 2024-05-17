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

    }

    //Method to set any labels that may change between Add/Modify modes
    public void setApptLabels(String topTitleString) {
        topTitleLabel.setText(topTitleString);
    }

    //Retrieve auto-generated Appointment ID for new appointments
    public void retrieveNewApptID() {
        String newApptID = DBAppointments.readNextApptID();
        apptIDInput.setText(newApptID);
    }


    //Method for Save button
    public void handleSave(boolean isAddMode) {
        //Create a flag for the main warning message at the bottom of the dialog pane. If any errors are present, this value will be assigned as "true".
        boolean errorsPresent = false;

        //Validate: Check for any empty/blank fields

        //Fetch data input from text fields
        String title = apptTitleInput.getText();
        String desc = apptDescInput.getText();
        String location = apptLocInput.getText();
        String type = apptTypeInput.getText();

        //Fetch and consolidate Dates/Times
        LocalDate startDate = startDateInput.getValue();
        LocalTime startTime = LocalTime.of(startTimeHoursInput.getValue(), startTimeMinutesInput.getValue());
        LocalDate endDate = endDateInput.getValue();
        LocalTime endTime = LocalTime.of(endTimeHoursInput.getValue(), endTimeMinutesInput.getValue());
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        //Validate: Check for time/date errors
        //Validate: Check for conflicts in customer's schedule

        //Fetch selected items from the ChoiceBoxes
        Contact selectedContact = contactIDInput.getValue();
        Customer selectedCustomer = custIDInput.getValue();
        User selectedUser = userIDInput.getValue();
        int userID = selectedUser.getUserID();
        int contactID = selectedContact.getContactID();
        int customerID = selectedCustomer.getCustID();

        //Handle the save operation based on if the dialog pane is in Add or Modify mode.
        if (isAddMode) {
            //Add Appointment: Saves new appointment to the database
            DBAppointments.addAppt(title, desc, location, type, startDateTime, endDateTime, userID, contactID, customerID);
        } else {
            //Modify Appointment: Saves modifications to an existing appointment
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

        //Refresh Appointments table view in the main application


    }

}