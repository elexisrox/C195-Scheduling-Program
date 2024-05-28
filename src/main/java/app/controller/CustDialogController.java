package app.controller;

import app.DBaccess.*;
import app.model.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

/**
 * Controller class for the Customer Dialog (Add/Modify Customer): CustDialog.fxml.
 * Manages the customer input form and handles validation and data saving.
 *
 * @author Elexis Rox
 */
public class CustDialogController implements Initializable {

    /** The dialog pane for the customer dialog */
    @FXML public DialogPane dialogPane;

    /** The label for the dialog title */
    @FXML private Label topTitleLabel;

    /** Input fields for customer information */
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

    /** Warning labels */
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

    /** Customer object for modifying existing customer details */
    private Customer customer;


    /**
     * Initializes the content in the dialog pane.
     *
     * @param url the location used to resolve relative paths for the root object, or null if
     *           the location is not known
     * @param rb the resources used to localize the root object, or null if the root object
     *           was not localized
     */    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load the Countries ChoiceBox
        loadCountries();
    }

    /**
     * Enables or disables the Division ChoiceBox.
     *
     * @param isBlocked true to disable the Division ChoiceBox, false to enable it
     */
    public void blockDivBox(boolean isBlocked) {
        custDivisionInput.setDisable(isBlocked);
    }

    /**
     * Loads countries into the country choice box.
     * LAMBDA EXPRESSION: The lambdas used in this method converts a country to and from
     * its string interpretation. This quickly displays both the country ID and name to
     * provide more information to the user in an easy-to-read format, while also
     * permitting convenient data retrieval.
     */
    public void loadCountries() {
        custCountryInput.setItems(DBCountries.readAllCountries());
        custCountryInput.setConverter(new StringConverter< >() {
            @Override
            public String toString(Country country) {
                return country == null ? null : country.getCountryID() + " - " + country.getCountryName();
            }

            @Override
            public Country fromString(String string) {
                return custCountryInput.getItems().stream()
                        .filter(country -> (country.getCountryID() + " - " + country.getCountryName()).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    /**
     * Loads divisions into the division choice box based on the selected country.
     * LAMBDA EXPRESSION: The lambdas used in this method converts a division to and from its string interpretation. This quickly displays both the division ID and name to provide more information to the user in an easy-to-read format, while also permitting convenient data retrieval.
     *
     * @param countryID the ID of the selected country
     */
    private void loadDivisions(int countryID) {
        custDivisionInput.setValue(null);
        custDivisionInput.setItems(DBDivisions.returnDivsByCountry(countryID));
        custDivisionInput.setConverter(new StringConverter< >() {
            @Override
            public String toString(Division division) {
                return division == null ? null : division.getDivID() + " - " + division.getDivName();
            }

            @Override
            public Division fromString(String string) {
                return custDivisionInput.getItems().stream()
                        .filter(division -> (division.getDivID() + " - " + division.getDivName()).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    /**
     * Sets labels that may change between Add and Modify modes.
     *
     * @param topTitleString the title string to set
     */
    public void setCustLabels(String topTitleString) {
        topTitleLabel.setText(topTitleString);
    }

    /**
     * Sets the customer object and populates the fields with customer data.
     *
     * @param customer the customer object to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;

        // Fetch and populate the data in the text fields
        custIDInput.setText(String.valueOf(customer.getCustID()));
        custNameInput.setText(customer.getCustName());
        custAddressInput.setText(customer.getCustAddress());
        custPostalInput.setText(customer.getCustPostalCode());
        custPhoneInput.setText(customer.getCustPhone());

        // Fetch data for First Level Division and Country
        int custDivID = customer.getCustDivisionID();
        Division custDiv = DBDivisions.retrieveDiv(custDivID);
        Country custCountry = DBCountries.retrieveCountry(custDiv.getDivCountryID());

        // Set Country Input
        custCountryInput.setValue(custCountry);

        // Load the correlating divisions and set the correct division in the input field
        loadDivisions(customer.getCustCountryID());
        custDivisionInput.setValue(custDiv);
    }

    /**
     * Adds listeners to the input fields after setting initial data.
     * LAMBDA EXPRESSION: The purpose of this lambda expression is to load the divisions
     * corresponding to the selected country and enable or disable the custDivisionInput
     * ChoiceBox based on whether a country is selected. Using a lambda here instead of an
     * anonymous inner method improves the readability of the code.
     */
    public void addListeners() {
        custCountryInput.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadDivisions(newValue.getCountryID());
                blockDivBox(false);
            } else {
                blockDivBox(true);
            }
        });
    }

    /**
     * Clears all error labels.
     */
    public void clearErrorLbls() {
        custNameWarning.setText("");
        custAddressWarning.setText("");
        custPostalWarning.setText("");
        custPhoneWarning.setText("");
        custCountryWarning.setText("");
        custDivisionWarning.setText("");
        failureSaveWarning.setText("");
    }

    /**
     * Validates all data fields before adding or updating a customer.
     *
     * @return true if all inputs are valid, otherwise false
     */
    public boolean validateInputs() {
        System.out.println("\tValidating all inputs.");

        // Clear all Error Labels
        clearErrorLbls();

        // Create a flag for the main warning message at the bottom of the dialog pane. If any errors are present, this value will be assigned as "true".
        boolean errorsPresent = false;

        // Retrieve data fields
        String name = custNameInput.getText();
        String address = custAddressInput.getText();
        String postal = custPostalInput.getText();
        String phone = custPhoneInput.getText();
        Country selectedCountry = custCountryInput.getValue();
        Division selectedDivision = custDivisionInput.getValue();

        // Validate: Make sure that no fields have been left empty/blank.
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

        // Set failureSaveWarning label if errors have been found.
        if (errorsPresent) {
            failureSaveWarning.setText("Unable to save. Please check the warnings above and try again.");
        }
        return !errorsPresent;
    }

    /**
     * Handles saving the customer to the database.
     */
    public void handleSave() {
        // Fetch data input from text fields
        String name = custNameInput.getText();
        String address = custAddressInput.getText();
        String postal = custPostalInput.getText();
        String phone = custPhoneInput.getText();

        // Fetch selected item from the Division ChoiceBox
        Division selectedDivision = custDivisionInput.getValue();
        int divID = selectedDivision.getDivID();

        // If the customer object is null, add a new customer to the database. If it is not null, update the fields and save the changes to the customer.
        if (customer == null) {
            System.out.println("\tAdding new customer to the database.");
            DBCustomers.addCustomer(name, address, postal, phone, divID);
        } else {
            System.out.println("\tUpdating existing customer in the database.");
            //TODO reassess code below
            customer.setCustName(name);
            customer.setCustAddress(address);
            customer.setCustPostalCode(postal);
            customer.setCustPhone(phone);
            customer.setCustDivisionID(divID);

            DBCustomers.updateCustomer(customer.getCustID(), name, address, postal, phone, divID);
        }
    }
}
