package app.controller;

import app.DBaccess.DBAppointments;
import app.helper.UniversalControls;
import app.helper.Utilities;
import app.model.Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
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
    private Label userTimeLbl;
    @FXML
    private Label timezoneLbl;

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
    private TableColumn<Appointment, String> apptStartDateCol;
    @FXML
    private TableColumn<Appointment, String> apptStartTimeCol;
    @FXML
    private TableColumn<Appointment, String> apptEndDateCol;
    @FXML
    private TableColumn<Appointment, String> apptEndTimeCol;
    @FXML
    private TableColumn<Appointment, Integer> apptCustIDCol;
    @FXML
    private TableColumn<Appointment, Integer> apptUserIDCol;

    //Label Setters
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
    public void onActionLogout(ActionEvent event) {
        System.out.println("Logout button clicked.");
    }

    @FXML
    public void onActionExit(ActionEvent event) {
        System.out.println("Exit button clicked.");
    }

    private void loadApptTableData() {
        setApptColumns();
        ObservableList<Appointment> appointments = DBAppointments.readAllAppts();
        apptTable.setItems(appointments);
    }

    private void setApptColumns() {
        //Detect the user's timezone and set "timezoneLbl" and "userTimeLbl" accordingly
        ZoneId localZone = ZoneId.systemDefault();

        //Set Time/Date Columns
        apptStartDateCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        Utilities.formatDate(cellData.getValue().getApptStart(), localZone)
                )
        );

        apptStartTimeCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        Utilities.formatTime(cellData.getValue().getApptStart(), localZone)
                )
        );
        apptEndDateCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        Utilities.formatDate(cellData.getValue().getApptEnd(), localZone)
                )
        );

        apptEndTimeCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        Utilities.formatTime(cellData.getValue().getApptEnd(), localZone)
                )
        );

        //Set all remaining columns
        // Initialize each column to use the property from the Appointment model
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("apptDesc"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("apptUserID"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("apptContactID"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("apptCustomerID"));
    }

    //Initializes the appointment table data.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadApptTableData();
    }

    //Tab Selection Filtering
    @FXML
    void tabAllSelected(Event event) {

    }

    @FXML
    void tabMonthSelected(Event event) {

    }

    @FXML
    void tabWeekSelected(Event event) {

    }
}