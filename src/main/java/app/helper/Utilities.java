package app.helper;

import app.DBaccess.DBContacts;
import app.DBaccess.DBCustomers;
import app.DBaccess.DBUsers;
import app.controller.ApptDialogController;
import app.controller.ApptViewController;
import app.controller.CustDialogController;
import app.controller.CustViewController;
import app.model.Appointment;
import app.model.Contact;
import app.model.Customer;
import app.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/** Utilities class provides scene transitions, data formatting methods, and other universal functions.
 * @author Elexis Rox
 */

public class Utilities {

    //Scene Transitions
    //Reusable scene transition method
    private static <Parent> void transitionToView(Stage stage, String fxmlPath, String title) throws IOException {
        Parent scene = FXMLLoader.load(Utilities.class.getResource(fxmlPath));
        stage.setScene(new Scene((javafx.scene.Parent) scene));
        stage.centerOnScreen();
        stage.setTitle(title);
        stage.show();
    }

    //Method to transition to the Appointment View in the main application.
    public static void transitionApptView(Stage stage) throws IOException {
        transitionToView(stage, "/app/ApptView.fxml", "View Appointments");
    }

    //Method to transition to the Login screen in the main application.
    public static void transitionLoginView(Stage stage) throws IOException {
        transitionToView(stage, "/app/Login.fxml", "");
    }

    //Toggle group for scene transitions between Main application views
    public static void onRadioButtonSelected(ToggleGroup toggleGroup, Stage stage) throws IOException {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            String toggleGroupValue = selectedRadioButton.getText();
            switch (toggleGroupValue) {
                case "Appointments":
                    transitionToView(stage, "/app/ApptView.fxml", "View Appointments");
                    break;
                case "Customers":
                    transitionToView(stage, "/app/CustView.fxml", "View Customers");
                    break;
                case "Reports":
                    transitionToView(stage, "/app/ReportView.fxml", "View Reports");
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + toggleGroupValue);
            }
        }
    }

    //Dialog Box Transitions
    //Main method to open the Add/Modify Appointments Dialog.
    public static void openApptDialog(Stage ownerStage, boolean isAddMode, ApptViewController apptMainView, Appointment selectedAppt) throws IOException {
        // Initialize and create the dialog pane
        FXMLLoader fxmlLoader = new FXMLLoader(Utilities.class.getResource("/app/ApptDialog.fxml"));
        DialogPane apptPane = fxmlLoader.load();
        ApptDialogController dialogController = fxmlLoader.getController();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(apptPane);

        // Set owner and modality
        dialog.initOwner(ownerStage);
        dialog.initModality(Modality.APPLICATION_MODAL);

        // Set dialog title and labels
        String modeString = isAddMode ? "Add Appointment" : "Modify Appointment";
        dialog.setTitle(modeString);
        dialogController.setApptLabels(modeString);

        // Populate fields if modifying, else retrieve new appointment ID
        if (isAddMode) {
            dialogController.retrieveNewApptID();
        } else if (selectedAppt != null) {
            dialogController.setAppointment(selectedAppt);
        }

        // Create Save/Cancel buttons
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        // Add validation and save handling for the Save button
        dialog.getDialogPane().lookupButton(saveButtonType).addEventFilter(ActionEvent.ACTION, event -> {
            System.out.println("Save button selected.");
            if (!dialogController.validateInputs()) {
                System.out.println("\tValidation failed.");
                event.consume(); // Prevents the dialog from closing
            } else {
                System.out.println("\tValidation succeeded, saving data.");
                dialogController.handleSave();
            }
        });

        // Show the dialog and handle the result
        dialog.showAndWait().ifPresent(result -> {
            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                apptMainView.updateTableData();
            } else {
                System.out.println("Cancel or close button selected.");
            }
        });
    }

    //Main method to open the Add/Modify Customers Dialog.
    public static void openCustDialog(Stage ownerStage, boolean isAddMode, CustViewController custMainView, Customer selectedCust) throws IOException {
        //Initialize and create the dialog pane
        FXMLLoader fxmlLoader = new FXMLLoader(Utilities.class.getResource("/app/CustDialog.fxml"));
        DialogPane custPane = fxmlLoader.load();
        CustDialogController dialogController = fxmlLoader.getController();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(custPane);
        //Set owner and modality
        dialog.initOwner(ownerStage);
        dialog.initModality(Modality.APPLICATION_MODAL);

        // Set dialog title and labels accordingly
        String modeString = isAddMode ? "Add Customer" : "Modify Customer";
        dialog.setTitle(modeString);
        dialogController.setCustLabels(modeString);

        //If adding a customer
        if (isAddMode) {
            //Retrieve the new customer ID
            dialogController.retrieveNewCustID();
            //Block the divisions input
            dialogController.blockDivBox(true);
            //Add event listener for Country input ChoiceBox. When a country is selected, the Division input ChoiceBox will become enabled and populate with the divisions that correlate to the chosen country.
            dialogController.addListeners();
        //If modifying a customer
        } else if (selectedCust != null) {
            //Fetches customer's info and fills in input fields accordingly
            dialogController.setCustomer(selectedCust);
            //Adds the event listener for the Country input ChoiceBox after the customer's data has populated.
            dialogController.addListeners();
        }

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        dialog.getDialogPane().lookupButton(saveButtonType).addEventFilter(ActionEvent.ACTION, event -> {
            if (!dialogController.validateInputs()) {
                //prevents the dialog pane from closing if validation fails.
                event.consume();
            } else {
                //If validation is successful, save the customer to the database.
                dialogController.handleSave();
            }
        });

        dialog.showAndWait().ifPresent(result -> {
            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                //Update the table data after the dialog closes.
                custMainView.updateTableData();
            }
        });
    }

    //Used for LocalDateTime formatting methods
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    //TIME
    //Converts LocalDateTime to a formatted Date string in a specific timezone
    public static String formatDate(LocalDateTime utcDateTime, ZoneId targetZone) {
        if (utcDateTime == null) return "";
        ZonedDateTime zonedDateTime = utcDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(targetZone);
        return DATE_FORMATTER.format(zonedDateTime);
    }

    //Converts UTC LocalDateTime to a formatted Time string in a specific timezone
    public static String formatTime(LocalDateTime utcDateTime, ZoneId targetZone) {
        if (utcDateTime == null) return "";
        ZonedDateTime zonedDateTime = utcDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(targetZone);
        return TIME_FORMATTER.format(zonedDateTime);
    }

    //Converts LocalDateTime from the user's timezone to UTC
    public static LocalDateTime toUTC(LocalDateTime localDateTime, ZoneId userZone) {
        ZonedDateTime localZonedDateTime = localDateTime.atZone(userZone);
        ZonedDateTime utcZoneDateTime = localZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZoneDateTime.toLocalDateTime();
    }

    //Converts LocalDateTime from UTC to the user's timezone
    public static LocalDateTime fromUTC(LocalDateTime utcDateTime, ZoneId userZone) {
        ZonedDateTime utcZonedDateTime = utcDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime localZonedDateTime = utcZonedDateTime.withZoneSameInstant(userZone);
        return localZonedDateTime.toLocalDateTime();
    }

    //ALERTS
    //Displays a confirmation alert with custom title, header, and content text.
    public static Optional<ButtonType> showConfirmationAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    //Displays an information alert with custom title, header, and content text.
    public static void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //CHOICE BOXES
    //Load Contacts into Contacts ChoiceBox
    public static void loadChoiceBoxContacts(ChoiceBox<Contact> choiceBox) {
        ObservableList<Contact> contacts = DBContacts.readAllContacts();
        choiceBox.setItems(contacts);
        choiceBox.setConverter(new StringConverter<Contact>() {
            @Override
            public String toString(Contact contact) {
                return contact == null ? null : contact.getContactID() + " - " + contact.getContactName();
            }

            @Override
            public Contact fromString(String string) {
                return choiceBox.getItems().stream()
                        .filter(contact -> (contact.getContactID() + " - " + contact.getContactName()).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    //Load Contacts into Customers ChoiceBox
    public static void loadChoiceBoxCustomers(ChoiceBox<Customer> choiceBox) {
        ObservableList<Customer> customers = DBCustomers.readAllCustomers();
        choiceBox.setItems(customers);
        choiceBox.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                return customer == null ? null : customer.getCustID() + " - " + customer.getCustName();
            }

            @Override
            public Customer fromString(String string) {
                return choiceBox.getItems().stream()
                        .filter(customer -> (customer.getCustID() + " - " + customer.getCustName()).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    //Load Users into Users ChoiceBox
    public static void loadChoiceBoxUsers(ChoiceBox<User> choiceBox) {
        ObservableList<User> users = DBUsers.readAllUsers();
        choiceBox.setItems(users);
        choiceBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user == null ? null : user.getUserID() + " - " + user.getUserName();
            }

            @Override
            public User fromString(String string) {
                return choiceBox.getItems().stream()
                        .filter(user -> (user.getUserID() + " - " + user.getUserName()).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    //BUTTONS
    //Logout Button
    public static void logoutButton(Stage stage) throws IOException {
        System.out.println("Logout button selected.");

        // Create a confirmation alert
        Optional<ButtonType> result = showConfirmationAlert(
                "Logout Confirmation",
                "Logging Out",
                "Are you sure you want to log out?"
        );

        // Transition to login screen if user clicks OK.
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Logout confirmed.");
            Utilities.transitionLoginView(stage);
        } else {
            System.out.println("Logout canceled.");
        }
    }

    //Exit button
    public static void exitButton() {
        System.out.println("Exit button selected.");

        // Create a confirmation alert
        Optional<ButtonType> result = showConfirmationAlert(
                "Exit Confirmation",
                "Exit Application",
                "Are you sure you want to exit?"
        );

        //Exit if user clicks OK.
        if (result.isPresent() && result.get() == ButtonType.OK) {
            app.helper.JDBC.closeConnection();
            System.out.println("Application terminated.");
            System.exit(0);
        } else {
            System.out.println("Exit canceled.");
        }
    }

    //TABLES
    //Method to create Appointments table and corresponding columns
    public static TableView<Appointment> createAppointmentTable(ZoneId userLocalZone) {
        TableView<Appointment> appointmentTable = new TableView<>();

        TableColumn<Appointment, Integer> apptIDCol = new TableColumn<>("Appt ID");
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptIDCol.setPrefWidth(75);

        TableColumn<Appointment, String> apptTitleCol = new TableColumn<>("Title");
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        apptTitleCol.setPrefWidth(75);

        TableColumn<Appointment, String> apptDescCol = new TableColumn<>("Description");
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("apptDesc"));
        apptDescCol.setPrefWidth(75);

        TableColumn<Appointment, String> apptLocCol = new TableColumn<>("Location");
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        apptLocCol.setPrefWidth(75);

        TableColumn<Appointment, String> apptTypeCol = new TableColumn<>("Type");
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        apptTypeCol.setPrefWidth(75);

        TableColumn<Appointment, String> apptStartDateCol = new TableColumn<>("Start Date");
        apptStartDateCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        Utilities.formatDate(cellData.getValue().getApptStart(), userLocalZone)
                )
        );
        apptStartDateCol.setPrefWidth(75);

        TableColumn<Appointment, String> apptStartTimeCol = new TableColumn<>("Start Time");
        apptStartTimeCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        Utilities.formatTime(cellData.getValue().getApptStart(), userLocalZone)
                )
        );
        apptStartTimeCol.setPrefWidth(75);

        TableColumn<Appointment, String> apptEndDateCol = new TableColumn<>("End Date");
        apptEndDateCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        Utilities.formatDate(cellData.getValue().getApptEnd(), userLocalZone)
                )
        );
        apptEndDateCol.setPrefWidth(75);

        TableColumn<Appointment, String> apptEndTimeCol = new TableColumn<>("End Time");
        apptEndTimeCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        Utilities.formatTime(cellData.getValue().getApptEnd(), userLocalZone)
                )
        );
        apptEndTimeCol.setPrefWidth(75);

        TableColumn<Appointment, Integer> apptContactIDCol = new TableColumn<>("Contact ID");
        apptContactIDCol.setCellValueFactory(new PropertyValueFactory<>("apptContactID"));
        apptContactIDCol.setPrefWidth(75);

        TableColumn<Appointment, Integer> apptCustIDCol = new TableColumn<>("Customer ID");
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("apptCustomerID"));
        apptCustIDCol.setPrefWidth(75);

        TableColumn<Appointment, Integer> apptUserIDCol = new TableColumn<>("User ID");
        apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("apptUserID"));
        apptUserIDCol.setPrefWidth(75);

        Collections.addAll(appointmentTable.getColumns(),
                apptIDCol, apptTitleCol, apptDescCol, apptLocCol,
                apptTypeCol, apptStartDateCol, apptStartTimeCol,
                apptEndDateCol, apptEndTimeCol, apptContactIDCol, apptCustIDCol, apptUserIDCol
        );

        return appointmentTable;
    }

    //Method to create Customers table and corresponding columns
    public static TableView<Customer> createCustomerTable() {
        TableView<Customer> customerTable = new TableView<>();

        TableColumn<Customer, Integer> custIDCol = new TableColumn<>("Customer ID");
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("custID"));
        custIDCol.setPrefWidth(100);

        TableColumn<Customer, String> custNameCol = new TableColumn<>("Name");
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        custNameCol.setPrefWidth(200);

        TableColumn<Customer, String> custAddressCol = new TableColumn<>("Address");
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        custAddressCol.setPrefWidth(250);

        TableColumn<Customer, String> custPostalCol = new TableColumn<>("Postal Code");
        custPostalCol.setCellValueFactory(new PropertyValueFactory<>("custPostalCode"));
        custPostalCol.setPrefWidth(100);

        TableColumn<Customer, String> custPhoneCol = new TableColumn<>("Phone");
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("custPhone"));
        custPhoneCol.setPrefWidth(100);

        TableColumn<Customer, Integer> custDivCol = new TableColumn<>("Division ID");
        custDivCol.setCellValueFactory(new PropertyValueFactory<>("custDivisionID"));
        custDivCol.setPrefWidth(75);

        Collections.addAll(customerTable.getColumns(),
                custIDCol, custNameCol, custAddressCol, custPostalCol, custPhoneCol, custDivCol
        );

        return customerTable;
    }
}
