package app.controller;

import app.DBaccess.DBAppointments;
import app.helper.UniversalControls;
import app.helper.Utilities;
import app.model.Appointment;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Optional;
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

    //Detect the user's time zone
    ZoneId userLocalZone = ZoneId.systemDefault();

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
    public void onActionLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Utilities.logoutButton(stage);
    }

    @FXML
    public void onActionExit(ActionEvent event) {
        Utilities.exitButton();
    }

    //Sets all columns in the Appointments table
    private void setApptColumns() {
        //Set Time/Date Columns
        apptStartDateCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        Utilities.formatDate(cellData.getValue().getApptStart(), userLocalZone)
                )
        );
        apptStartTimeCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        Utilities.formatTime(cellData.getValue().getApptStart(), userLocalZone)
                )
        );
        apptEndDateCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        Utilities.formatDate(cellData.getValue().getApptEnd(), userLocalZone)
                )
        );
        apptEndTimeCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        Utilities.formatTime(cellData.getValue().getApptEnd(), userLocalZone)
                )
        );

        //Set all remaining columns
        //Initialize each column to use the property from the Appointment model
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
        //Sets timezone label according to the user's timezone
        timezoneLbl.setText(String.valueOf(userLocalZone));

        //Initialize the table columns first without loading data
        setApptColumns();

        //Load data based on the selected tab
        updateTableData();
    }

    //Tab Selection Filtering
    //Event fires when a tab is selected. "View All" tab is selected by default on application load
    @FXML
    void onTabChanged(Event event) {
        if (event.getSource() instanceof Tab) {
            updateTableData();
        }
    }

    //Update the table data based on the selected tab
    private void updateTableData() {
        if (tabAll.isSelected()) {
            loadAllAppts();
        } else if (tabMonth.isSelected()) {
            loadMonthAppts();
        } else if (tabWeek.isSelected()) {
            loadWeekAppts();
        }
    }

    //Loads all appointments
    @FXML
    void loadAllAppts() {
        if (apptTable != null) {
            ObservableList<Appointment> appointments = DBAppointments.readAllAppts();
            apptTable.setItems(appointments);
        }
    }

    //Loads all appointments within the current month
    @FXML
    void loadMonthAppts() {
        if (apptTable != null) {
            ObservableList<Appointment> appointments = DBAppointments.readMonthAppts();
            apptTable.setItems(appointments);
        }
    }

    //Loads appointments within the current week (Monday-Sunday)
    @FXML
    void loadWeekAppts() {
        if (apptTable != null) {
            ObservableList<Appointment> appointments = DBAppointments.readWeekAppts();
            apptTable.setItems(appointments);
        }
    }
}