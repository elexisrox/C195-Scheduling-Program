package app.helper;

import app.DBaccess.DBContacts;
import app.DBaccess.DBCustomers;
import app.DBaccess.DBUsers;
import app.controller.ApptDialogController;
import app.model.Contact;
import app.model.Customer;
import app.model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Optional;

import javafx.stage.Stage;
import javafx.util.StringConverter;

/** Utilities class provides CRUD queries and other utility functions.
 * @author Elexis Rox
 */

public class Utilities {

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

    //Displays a confirmation alert with custom title, header, and content text.
    public static Optional<ButtonType> showConfirmationAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
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
            UniversalControls.transitionLoginView(stage);
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

}
