package app.controller;

import app.DBaccess.DBAppointments;
import app.helper.UniversalControls;
import app.helper.Utilities;
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

    @FXML
    private DialogPane dialogPane;
    @FXML
    private Label topTitleLabel;

    //Text fields
    @FXML
    private TextField apptIDInput;
    @FXML
    private TextField apptTitleInput;
    @FXML
    private TextField apptDescInput;
    @FXML
    private TextField apptLocInput;
    @FXML
    private TextField apptTypeInput;

    //Error/Warning Labels
    @FXML
    private Label apptTitleWarning;
    @FXML
    private Label apptDescWarning;
    @FXML
    private Label apptLocWarning;
    @FXML
    private Label apptTypeWarning;
    @FXML
    private Label apptContactWarning;
    @FXML
    private Label apptCustomerWarning;
    @FXML
    private Label apptUserWarning;
    @FXML
    private Label apptTimeWarning;
    @FXML
    private Label failureSaveWarning;

    //Date Pickers
    @FXML
    private DatePicker startDateInput;
    @FXML
    private DatePicker endDateInput;

    //Spinners
    @FXML
    private Spinner<Integer> startTimeHoursInput, startTimeMinutesInput, endTimeHoursInput, endTimeMinutesInput;

    //ChoiceBoxes
    @FXML
    private ChoiceBox<Contact> contactIDInput;
    @FXML
    private ChoiceBox<Customer> custIDInput;
    @FXML
    private ChoiceBox<User> userIDInput;

    //Mode
    private boolean isAddMode;

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

    //Method to change isAddMode. If set to true, dialog pane is set for "Add Appointment". If set to false, dialog pane is set for "Modify Appointment".
    public void setMode(boolean isAddMode) {
        this.isAddMode = isAddMode;
    }

    //Method to validate text fields and provide warnings if necessary
    private boolean validateInputs() {
        String title = apptTitleInput.getText();
        String description = apptDescInput.getText();
        String location = apptLocInput.getText();
        String type = apptTypeInput.getText();
        LocalDate startDate = startDateInput.getValue();
        LocalTime startTime = LocalTime.of(startTimeHoursInput.getValue(), startTimeMinutesInput.getValue());
        LocalDate endDate = endDateInput.getValue();
        LocalTime endTime = LocalTime.of(endTimeHoursInput.getValue(), endTimeMinutesInput.getValue());
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        int contactId = contactIDInput.getValue().getContactID();
        int customerId = custIDInput.getValue().getCustID();
        int userId = userIDInput.getValue().getUserID();
//
//        // Validate input here
//        if (title.isEmpty() || description.isEmpty()) {
//            failureSaveWarning.setText("Title and description are required.");
//            return false;
//        }
//
//        if (startDateTime.isAfter(endDateTime)) {
//            failureSaveWarning.setText("Start time must be before end time.");
//            return false;
//        }
//
        return true;
    }

    //Method for "Add Appointment" to save new appointment to database
    private void saveAddAppt() {
        // Add appointment to the database
    }

    //Method for "Modify Appointment" to update appointment in database
    private void saveModifyAppt() {
        // Modify appointment in the database
    }

}