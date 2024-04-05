package app.controller;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/** Controller class for ApptDialog.fxml.
 * @author Elexis Rox
 */

public class ApptDialogController implements Initializable {
    /**
     *  Initializes the Appointment Dialog Box Controller Class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private DialogPane dialogPane;

    @FXML
    private Label apptContactWarning;

    @FXML
    private Label apptCustomerWarning;

    @FXML
    private TextField apptDescInput;

    @FXML
    private Label apptDescWarning;

    @FXML
    private TextField apptIDInput;

    @FXML
    private TextField apptLocInput;

    @FXML
    private Label apptLocWarning;

    @FXML
    private Label apptTimeWarning;

    @FXML
    private TextField apptTitleInput;

    @FXML
    private Label apptTitleWarning;

    @FXML
    private TextField apptTypeInput;

    @FXML
    private Label apptTypeWarning;

    @FXML
    private Label apptUserWarning;

    @FXML
    private ChoiceBox<?> contactIDInput;

    @FXML
    private ChoiceBox<?> custIDInput;

    @FXML
    private DatePicker endDateInput;

    @FXML
    private Spinner<?> endTimeHoursInput;

    @FXML
    private Spinner<?> endTimeMinutesInput;

    @FXML
    private Label failureSaveWarning;

    @FXML
    private DatePicker startDateInput;

    @FXML
    private Spinner<?> startTimeHoursInput;

    @FXML
    private Spinner<?> startTimeMinutesInput;

    @FXML
    private Label titleLabel;

    @FXML
    private Label topMsgLabel;

    @FXML
    private ChoiceBox<?> userIDInput;

}