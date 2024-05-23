package app.controller;

import app.DBaccess.DBAppointments;
import app.DBaccess.DBCustomers;
import app.DBaccess.DBUsers;
import app.helper.Utilities;
import app.model.*;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CustDialogController implements Initializable {

    @FXML public DialogPane dialogPane;
    @FXML private Label topTitleLabel;

    //Input Fields
    @FXML
    private TextField custIDInput;
    @FXML
    private TextField custNameInput;
    @FXML
    private TextField custAddressInput;
    @FXML
    private TextField custPostalInput;
    @FXML
    private TextField custPhoneInput;
    @FXML
    private ChoiceBox<Country> custCountryInput;
    @FXML
    private ChoiceBox<Division> custDivisionInput;

    //Warning Labels
    @FXML
    private Label custNameWarning;
    @FXML
    private Label custAddressWarning;
    @FXML
    private Label custPostalWarning;
    @FXML
    private Label custPhoneWarning;
    @FXML
    private Label custCountryWarning;
    @FXML
    private Label custDivisionWarning;
    @FXML
    private Label failureSaveWarning;

    //Customer object
    private Customer customer;

    //Initialize the content in the dialog pane
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Load Choice boxes
        //Utilities.loadChoiceBoxCountries(custCountryInput);
        //Utilities.loadChoiceBoxDivisions(custDivisionInput);
    }

    //Sets any labels that may change between Add/Modify modes
    public void setCustLabels(String topTitleString) {
        topTitleLabel.setText(topTitleString);
    }

    //Retrieves auto-generated Customer ID for new customers
    public void retrieveNewCustID() {
        String newCustID = DBCustomers.readNextCustID();
        custIDInput.setText(newCustID);
    }

    // Method to set the customer object and populate the fields
    public void setCustomer(Customer customer) {
        this.customer = customer;
        custIDInput.setText(String.valueOf(customer.getCustID()));
        custNameInput.setText(customer.getCustName());
        custAddressInput.setText(customer.getCustAddress());
        custPostalInput.setText(customer.getCustPostalCode());
        custPhoneInput.setText(customer.getCustPhone());

        //TODO: Set choice boxes with appointment data
        //custCountryInput.setValue();
        //custDivisionInput.setValue();
    }

    //Clears all error labels
    public void clearErrorLbls() {
        custNameWarning.setText("");
        custAddressWarning.setText("");
        custPostalWarning.setText("");
        custPhoneWarning.setText("");
        custCountryWarning.setText("");
        custDivisionWarning.setText("");
        failureSaveWarning.setText("");
    }

    //Validates all data fields before adding/updating an appointment
    public boolean validateInputs() {
        System.out.println("Validating all inputs.");

        //Clear all Error Labels
        clearErrorLbls();

        //Create a flag for the main warning message at the bottom of the dialog pane. If any errors are present, this value will be assigned as "true".
        boolean errorsPresent = false;

        //Retrieve data fields
        String name = custNameInput.getText();
        String address = custAddressInput.getText();
        String postal = custPostalInput.getText();
        String phone = custPhoneInput.getText();
        Country selectedCountry = custCountryInput.getValue();
        Division selectedDivision = custDivisionInput.getValue();

        //Validate: Make sure that no fields have been left empty/blank.
        if (name.isBlank()) {
            custNameWarning.setText("Please enter a name.");
            errorsPresent = true;
        }
        if (address.isBlank()) {
            custAddressWarning.setText("Please enter an address.");
            errorsPresent = true;
        }
        if (postal.isBlank()) {
            custPostalWarning.setText("Please enter a postal code.");
            errorsPresent = true;
        }
        if (phone.isBlank()) {
            custPhoneWarning.setText("Please enter a phone number.");
            errorsPresent = true;
        }
        if (selectedCountry == null) {
            custCountryWarning.setText("Please select a Country.");
            errorsPresent = true;
        }
        if (selectedDivision == null) {
            custDivisionWarning.setText("Please select a Division.");
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
        //Fetch data input from text fields
        String name = custNameInput.getText();
        String address = custAddressInput.getText();
        String postal = custPostalInput.getText();
        String phone = custPhoneInput.getText();

        //Fetch selected item from the Division ChoiceBox
        Division selectedDivision = custDivisionInput.getValue();
        int divID = selectedDivision.getDivID();

        //If the customer object is null, add a new customer to the database. If it is not null, update the fields and save the changes to the customer.
        if (customer == null) {
            System.out.println("\tAdding new customer to the database.");
            DBCustomers.addCustomer(name, address, postal, phone, divID);
        } else {
            System.out.println("\tUpdating existing customer in the database.");
            //TODO reassess code below
            customer.setCustName(name);
            customer.setCustAddress(address);
            customer.getCustPostalCodeCode(postal);
            customer.setCustPhone(phone);
            customer.setCustDivisionID(divID);

            DBCustomers.updateCustomer(customer.getCustID(), name, address, postal, phone, divID);
        }
    }
}
