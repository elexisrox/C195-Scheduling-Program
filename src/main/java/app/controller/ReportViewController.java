package app.controller;

import app.DBaccess.DBContacts;
import app.DBaccess.DBAppointments;
import app.DBaccess.DBCountries;
import app.DBaccess.DBCustomers;
import app.helper.Utilities;
import app.model.Appointment;
import app.model.Contact;
import app.model.Country;
import app.model.Customer;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;

/**
 * Controller class for ReportView.fxml.
 * Manages the report view and handles user interactions for generating various reports.
 *
 * @author Elexis Rox
 */
public class ReportViewController implements Initializable {

    // Top navigation toggle group
    @FXML
    private ToggleGroup topMenuToggle;

    // Detect the user's time zone
    ZoneId userLocalZone = ZoneId.systemDefault();

    // Labels
    @FXML
    private Label choiceBoxLbl;
    @FXML
    private Label reportsResultLbl;
    @FXML
    private Label timezoneLbl;

    // ChoiceBox
    @FXML
    private ChoiceBox<Object> reportsBox;

    // Tabs
    @FXML
    private Tab tabContactReport;
    @FXML
    private Tab tabTypeReport;
    @FXML
    private Tab tabCountryReport;

    // Anchor pane for displaying tables
    @FXML
    private AnchorPane reportsTablePane;

    /**
     * Handles the action of logging out of the application.
     *
     * @param event the action event triggered by the user
     * @throws IOException if there is an issue transitioning to the login view
     */
    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Utilities.logoutButton(stage);
    }

    /**
     * Handles the action of exiting the application.
     */
    @FXML
    void onActionExit() {
        Utilities.exitButton();
    }

    /**
     * Handles the event when a tab is selected.
     * Updates the tab data based on the selected tab.
     *
     * @param event the event triggered by the tab selection
     */
    @FXML
    void onReportsTabChanged(Event event) {
        if (event.getSource() instanceof Tab) {
            updateTabData();
        }
    }

    /**
     * Updates the table, ChoiceBox, and labels based on the selected tab.
     */
    public void updateTabData() {
        if (tabContactReport.isSelected()) {
            setupContactReportTab();
        } else if (tabTypeReport.isSelected()) {
            setupTypeReportTab();
        } else if (tabCountryReport.isSelected()) {
            setupCountryReportTab();
        }
    }

    /**
     * Helper method to add a table to the AnchorPane.
     *
     * @param table the TableView to be added
     */
    private void addTableToPane(TableView<?> table) {
        if (reportsTablePane != null) {
            reportsTablePane.getChildren().clear(); // Clear any existing children
            reportsTablePane.getChildren().add(table); // Add the TableView to the AnchorPane
            AnchorPane.setTopAnchor(table, 0.0);
            AnchorPane.setBottomAnchor(table, 0.0);
            AnchorPane.setLeftAnchor(table, 0.0);
            AnchorPane.setRightAnchor(table, 0.0);
        }
    }

    /**
     * Loads contacts into the ChoiceBox as general Objects.
     *
     * @param choiceBox the ChoiceBox to be populated
     */
    public void loadChoiceBoxContactsGeneral(ChoiceBox<Object> choiceBox) {
        ObservableList<Contact> contacts = DBContacts.readAllContacts();
        // Convert Contact objects to a list of formatted strings
        ObservableList<Object> contactNames = FXCollections.observableArrayList();
        for (Contact contact : contacts) {
            contactNames.add(contact.getContactID() + " - " + contact.getContactName());
        }
        choiceBox.setItems(contactNames);
        // Set the converter for the ChoiceBox
        choiceBox.setConverter(new StringConverter< >() {
            @Override
            public String toString(Object object) {
                return object == null ? null : object.toString();
            }
            @Override
            public Object fromString(String string) {
                return string; // Just return the string representation
            }
        });
    }

    /**
     * Loads countries into the ChoiceBox as general Objects.
     *
     * @param choiceBox the ChoiceBox to be populated
     */
    public void loadChoiceBoxCountriesGeneral(ChoiceBox<Object> choiceBox) {
        ObservableList<Country> countries = DBCountries.readAllCountries();
        // Convert Contact objects to a list of formatted strings
        ObservableList<Object> countryNames = FXCollections.observableArrayList();
        for (Country country : countries) {
            countryNames.add(country.getCountryID() + " - " + country.getCountryName());
        }
        choiceBox.setItems(countryNames);
        // Set the converter for the ChoiceBox
        choiceBox.setConverter(new StringConverter< >() {
            @Override
            public String toString(Object object) {
                return object == null ? null : object.toString();
            }
            @Override
            public Object fromString(String string) {
                return string; // Just return the string representation
            }
        });
    }

    /**
     * Handles contact selection from the ChoiceBox.
     *
     * @param contactString the selected contact as a string
     * @return the list of appointments for the selected contact
     */
    private ObservableList<Appointment> handleContactSelection(String contactString) {
        // Extract Contact ID from the selected string
        int contactID = Integer.parseInt(contactString.split(" - ")[0]);
        // Fetch appointments for the selected contact
        return DBAppointments.readApptsByContactID(contactID);
    }

    /**
     * Handles country selection from the ChoiceBox.
     *
     * @param countryString the selected country as a string
     * @return the list of customers for the selected country
     */
    private ObservableList<Customer> handleCountrySelection(String countryString) {
        // Extract Contact ID from the selected string
        int countryID = Integer.parseInt(countryString.split(" - ")[0]);
        // Fetch appointments for the selected contact
        return DBCustomers.readCustomersByCountryID(countryID);
    }

    /**
     * Sets up the Contact Report Tab.
     * Populates the ChoiceBox with contacts and sets up the table to display appointments.
     * LAMBDA EXPRESSION: The lambda in this method is an event listener for changes in the
     * selectedItemProperty of the reportsBox ChoiceBox. Using a lambda here instead of an
     * anonymous inner method improves the readability of the code.
     */
    private void setupContactReportTab() {
        // Set text labels accordingly
        choiceBoxLbl.setText("Select a Contact:");
        reportsResultLbl.setText("");

        // Create an Appointments table to occupy the reportsTablePane
        if (reportsTablePane != null) {
            TableView<Appointment> apptTable = Utilities.createAppointmentTable();
            addTableToPane(apptTable);

            // Clear and enable the reportsBox
            reportsBox.getItems().clear(); // Clear all items
            reportsBox.getSelectionModel().clearSelection(); // Clear the selected item
            reportsBox.setDisable(false); // Enable the ChoiceBox

            // Populate the ChoiceBox with the contacts list
            loadChoiceBoxContactsGeneral(reportsBox);

            // Add listener to the ChoiceBox for selection changes
            reportsBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    ObservableList<Appointment> displayAppts = handleContactSelection(newValue.toString());
                    // Update the table with the fetched appointments
                    apptTable.setItems(displayAppts);
                    // Set the reportsResultLbl with the correct
                    int resultsCount = displayAppts.size();
                    reportsResultLbl.setText("Total Appointments: " + resultsCount);
                }
            });
        }
    }

    /**
     * Sets up the Type Report Tab.
     * Populates the table with appointments categorized by month and type.
     */
    private void setupTypeReportTab() {
        // Set text labels accordingly
        choiceBoxLbl.setText("");
        reportsResultLbl.setText("");

        // Clear the reportsBox
        reportsBox.getItems().clear(); // Clear all items
        reportsBox.getSelectionModel().clearSelection(); // Clear the selected item
        reportsBox.setDisable(true);

        // Create an Appointments Month and Type table to occupy the reportsTablePane
        if (reportsTablePane != null) {
            TableView<Pair<String, Pair<String, Integer>>> apptMonthTypeTable = Utilities.createAppointmentMonthTypeTable();
            addTableToPane(apptMonthTypeTable);

            // Fetch the data and set it to the table
            ObservableList<Pair<String, Pair<String, Integer>>> data = DBAppointments.readApptsByMonthAndType();
            apptMonthTypeTable.setItems(data);
        }
    }

    /**
     * Sets up the Country Report Tab.
     * Populates the ChoiceBox with countries and sets up the table to display customers.
     * LAMBDA EXPRESSION: The lambda in this method is an event listener for changes in the
     * selectedItemProperty of the reportsBox ChoiceBox. Using a lambda here instead of an
     * anonymous inner method improves the readability of the code.
     */
    private void setupCountryReportTab() {
        // Set text labels accordingly
        choiceBoxLbl.setText("Select a Country:");
        reportsResultLbl.setText("");

        // Clear and enable the reportsBox
        reportsBox.getItems().clear(); // Clear all items
        reportsBox.getSelectionModel().clearSelection(); // Clear the selected item
        reportsBox.setDisable(false); // Enable the ChoiceBox

        //Create an Appointments table to occupy the reportsTablePane
        if (reportsTablePane != null) {
            TableView<Customer> custTable = Utilities.createCustomerTable();
            addTableToPane(custTable);

            //Enable the reportsBox and populate it with contacts
            reportsBox.setDisable(false);
            loadChoiceBoxCountriesGeneral(reportsBox);

            // Add listener to the ChoiceBox for selection changes
            reportsBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    ObservableList<Customer> displayCustomers = handleCountrySelection(newValue.toString());
                    // Update the table with the fetched appointments
                    custTable.setItems(displayCustomers);
                    //Set the reportsResultLbl with the correct
                    int resultsCount = displayCustomers.size();
                    reportsResultLbl.setText("Total Customers: " + resultsCount);
                }
            });
        }
    }

    /**
     * Initializes the Main Reports View.
     * Sets up the toggle navigation menu and default tab.
     * LAMBDA EXPRESSION: The lambda expression is used to filter the result of the
     * showConfirmationAlert method to check if the user clicked the "OK" button.
     *
     * @param url the location used to resolve relative paths for the root object, or null if
     *           the location is not known
     * @param rb the resources used to localize the root object, or null if the root object
     *           was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up the toggle navigation menu
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

        // Sets timezone label according to the user's timezone
        timezoneLbl.setText(String.valueOf(userLocalZone));

        // Set up the default tab
        setupContactReportTab();
    }
}
