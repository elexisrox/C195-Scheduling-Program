package app.controller;

import app.helper.UniversalControls;
import app.helper.Utilities;
import app.model.Contact;
import app.model.Customer;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private Spinner<?> startTimeHoursInput;
    @FXML
    private Spinner<?> startTimeMinutesInput;
    @FXML
    private Spinner<?> endTimeHoursInput;
    @FXML
    private Spinner<?> endTimeMinutesInput;

    //ChoiceBoxes
    @FXML
    private ChoiceBox<Contact> contactIDInput;
    @FXML
    private ChoiceBox<Customer> custIDInput;
    @FXML
    private ChoiceBox<?> userIDInput;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Load Choice boxes
        Utilities.loadChoiceBoxContacts(contactIDInput);

    }

    public void setApptLabels(String topTitleString) {
        topTitleLabel.setText(topTitleString);
    }
}