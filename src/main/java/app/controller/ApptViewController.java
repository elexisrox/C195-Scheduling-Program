package app.controller;

import app.DBaccess.DBAppointments;
import app.helper.UniversalControls;
import app.model.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/** Controller class for ApptView.fxml.
 * @author Elexis Rox
 */

//TODO Possibly rename MainViewController depending on how I handle the other views
public class ApptViewController implements Initializable {
    /**
     *  Initializes the Appointment View Controller Class.
     */
    @FXML
    private ToggleGroup topMenuToggle;

    @FXML
    private Label errorMsgLbl;

    @FXML
    private ChoiceBox<?> filterApptDropdown;

    @FXML
    private TableView<Appointment> apptTable;

    //Appointment Table Columns
    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;
    @FXML
    private TableColumn<Appointment, String> apptTitleCol;
    @FXML
    private TableColumn<Appointment, String> apptDescCol;
    @FXML
    private TableColumn<Appointment, String> apptLocCol;
    @FXML
    private TableColumn<Appointment, Integer> apptContactCol;
    @FXML
    private TableColumn<Appointment, String> apptTypeCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> apptStartDateCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> apptStartTimeCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> apptEndDateCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> apptEndTimeCol;
    @FXML
    private TableColumn<Appointment, Integer> apptCustIDCol;
    @FXML
    private TableColumn<Appointment, Integer> apptUserIDCol;

    @FXML
    public void onActionAddAppt(ActionEvent event) throws IOException {
        System.out.println("Add Appointment button clicked.");
        UniversalControls.openAddApptDialog();
    }

    @FXML
    public void onActionModAppt(ActionEvent event) throws IOException {
        System.out.println("Modify Appointment button clicked.");
       UniversalControls.openModApptDialog();
    }

    @FXML
    public void onActionDelAppt(ActionEvent event) {
        System.out.println("Delete Appointment button clicked.");
    }

    @FXML
    public void onActionExit(ActionEvent event) {

    }

    //Initializes the appointment table data.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize each column to use the property from the Appointment model
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("apptDesc"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        apptStartDateCol.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        apptStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        apptEndDateCol.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
        apptEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
        apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("apptUserID"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("apptContactID"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("apptCustomerID"));


        // Load the data into the table
        loadTableData();
    }

    private void loadTableData() {
        ObservableList<Appointment> appointments = DBAppointments.readAllAppts();
        apptTable.setItems(appointments);
    }

}