package app.controller;

import app.DBaccess.DBAppointments;
import app.DBaccess.DBCustomers;
import app.helper.Utilities;
import app.model.Appointment;
import app.model.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CustViewController implements Initializable {

    //Top navigation toggle group
    @FXML
    private ToggleGroup topMenuToggle;

    //Detect the user's time zone
    ZoneId userLocalZone = ZoneId.systemDefault();

    @FXML
    private TableView<Customer> custTable;

    @FXML
    private Label errorMsgLbl;

    @FXML
    private Label timezoneLbl;

    //Clear Error Label
    public void clearErrorLbl() {
        errorMsgLbl.setText(" ");
    }

    //BUTTONS
    @FXML
    void onActionAddCust(ActionEvent event) throws IOException {
        System.out.println("Add Customer button selected.");
        clearErrorLbl();
        Stage ownerStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Utilities.openCustDialog(ownerStage, true, this, null);
    }

    @FXML
    void onActionModCust(ActionEvent event) throws IOException {
        System.out.println("Modify Customer button selected.");
        clearErrorLbl();
        Customer selectedCust = custTable.getSelectionModel().getSelectedItem();
        if (selectedCust != null) {
            Stage ownerStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Utilities.openCustDialog(ownerStage, false, this, selectedCust);
        } else {
            System.out.println("No customer selected.");
            errorMsgLbl.setText("Please select a customer to modify.");
        }
    }

    @FXML
    void onActionDelCust(ActionEvent event) {
        System.out.println("Delete Appointment button selected.");
        clearErrorLbl();
        Customer selectedCust = custTable.getSelectionModel().getSelectedItem();
        if (selectedCust != null) {
            boolean confirmed = Utilities.showConfirmationAlert(
                    "Delete Confirmation",
                    "Are you sure you want to delete this customer?",
                    "Customer ID: " + selectedCust.getCustID() +
                            "\nCustomer Name: " + selectedCust.getCustName()
            ).filter(response -> response == ButtonType.OK).isPresent();

            if (confirmed) {
                DBAppointments.deleteAppt(selectedCust.getCustID());
                System.out.println("Customer #" + selectedCust.getCustID() + " deleted.");
                updateTableData();
                Utilities.showInfoAlert(
                        "Delete Successful",
                        "The following customer has been successfully deleted/canceled:\n" +
                                "Customer ID: " + selectedCust.getCustID() +
                                "\nCustomer Name: " + selectedCust.getCustName()
                );
            }
        } else {
            System.out.println("No customer selected.");
            errorMsgLbl.setText("Please select a customer to delete.");
        }
    }

    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Utilities.logoutButton(stage);
    }

    @FXML
    void onActionExit(ActionEvent event) {
        Utilities.exitButton();
    }

    //Update the table data based on the selected tab
    public void updateTableData() {
        if (custTable != null) {
            ObservableList<Customer> customers = DBCustomers.readAllCustomers();
            custTable.setItems(customers);
        }
    }

    //Initializes the Main Customer View
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set up the toggle navigation menu
        topMenuToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    RadioButton selectedRadioButton = (RadioButton) newValue;
                    Stage stage = (Stage) selectedRadioButton.getScene().getWindow();
                    Utilities.onRadioButtonSelected(topMenuToggle, stage);
                } catch (Exception e) {
                    System.out.println("Error (Navigation Radio Buttons): " + e.getMessage());
                }
            }
        });

        //Sets timezone label according to the user's timezone
        timezoneLbl.setText(String.valueOf(userLocalZone));

        //Initialize the Appointments table and set up the columns
        custTable.getColumns().setAll(Utilities.createCustomerTable().getColumns());

        //Load data based on the selected tab
        updateTableData();
    }
}
