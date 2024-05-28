package app.controller;

import app.DBaccess.DBAppointments;
import app.DBaccess.DBUsers;
import app.helper.Utilities;
import app.model.Appointment;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controller class for Login.fxml.
 * Manages the login process, language selection, and user authentication.
 * Logs login attempts and displays appropriate messages to the user.
 *
 * @author Elexis Rox
 */

public class LoginController implements Initializable {

    // ComboBox for language selection
    @FXML
    private ComboBox<String> langComboBox;

    // User fields
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    // Labels
    @FXML
    private Label loginErrorLbl;
    @FXML
    private Label timezoneLbl;

    // Translation Strings
    @FXML
    private Label welcomeString;
    @FXML
    private Label loginString;
    @FXML
    private Label usernameString;
    @FXML
    private Label passString;
    @FXML
    private Label langString;
    @FXML
    private Label zoneString;
    @FXML
    private Hyperlink resetString;

    // Buttons
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;

    // Used for translations
    private ResourceBundle rb;

    // Detect the user's time zone
    ZoneId userLocalZone = ZoneId.systemDefault();

    /**
     * Validates username and password upon login.
     * Displays an appropriate error/success message upon login attempt.
     * Logs completed login attempts in login_activity.txt file.
     *
     * @param event the action event triggered by the user
     * @throws IOException if there is an issue writing to the login_activity.txt file
     */
    @FXML
    public void onActionLogin(ActionEvent event) throws IOException {
        String userName = usernameField.getText();
        String userPassword = passwordField.getText();
        boolean loginSuccess;

        // Display error message if both username and password fields are blank or empty.
        if ((userName.isBlank()) && (userPassword.isBlank())) {
            loginErrorLbl.setText(rb.getString("errorUserPass"));
        }
        // Display error message if username field is blank or empty.
        else if (userName.isBlank()) {
            loginErrorLbl.setText(rb.getString("errorUser"));
        }
        // Display error message if password field is blank or empty.
        else if (userPassword.isBlank()) {
            loginErrorLbl.setText(rb.getString("errorPass"));
        }
        // Display error message if the provided username does not exist in the database.
        else if (!DBUsers.userNameValidate(userName)) {
            loginErrorLbl.setText(rb.getString("errorUserNotFound"));
            // Record the login failure.
            recordLoginActivity(false);
        }

        // Attempt to validate that the provided username and password match
        else {
            loginSuccess = DBUsers.userLoginValidate(userName, userPassword);
            // Record the login success or failure.
            recordLoginActivity(loginSuccess);

            // If username and password do not match, display an error message. Record the login attempt.
            if (!loginSuccess) {
                loginErrorLbl.setText(rb.getString("errorNoMatch"));
            }
            // If username and password are successfully validated, load the main application Appointment view. Record the login attempt.
            else {
                // Clear any previous login warnings.
                loginErrorLbl.setText(null);
                // Transition to Appointment Main View
                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Utilities.transitionApptView(stage);
                // Check for upcoming appointments within 15 minutes of logging in
                checkForUpcomingAppts();
            }
        }
    }

    /**
     * Handles the action of exiting the application.
     */
    @FXML
    void onActionExit() {
        System.out.println("Exit button selected.");
        app.helper.JDBC.closeConnection();
        System.out.println("Application terminated.");
        System.exit(0);
    }

    /**
     * Handles the action of resetting the input fields.
     */
     @FXML
    void onActionReset() {
        System.out.println("Reset fields button selected.");
        usernameField.clear();
        passwordField.clear();
    }

    /**
     * Detects the user's locale and sets the appropriate language bundle.
     * Sets text labels based on the detected language.
     */
    @FXML
    void detectUserLocale() {
        Locale userLocale = Locale.getDefault();
        if (userLocale.getLanguage().equals("fr")) {
            rb = ResourceBundle.getBundle("LangBundle", Locale.FRENCH);
        } else {
            rb = ResourceBundle.getBundle("LangBundle", Locale.ENGLISH);
        }
        setTextLabels();
    }

    /**
     * Sets the text labels for the UI components based on the selected language.
     */
    private void setTextLabels() {
        welcomeString.setText(rb.getString("welcomeString"));
        loginString.setText(rb.getString("loginString"));
        usernameString.setText(rb.getString("usernameString"));
        passString.setText(rb.getString("passString"));
        loginButton.setText(rb.getString("loginButton"));
        exitButton.setText(rb.getString("exitButton"));
        resetString.setText(rb.getString("resetString"));
        langString.setText(rb.getString("langString"));
        zoneString.setText(rb.getString("zoneString"));
        // Clear any previous login warnings.
        loginErrorLbl.setText(null);
        // Sets timezone label according to the user's timezone
        timezoneLbl.setText(String.valueOf(userLocalZone));
    }

    /**
     * Initializes the language ComboBox with available languages.
     * Sets the selected language based on the user's locale.
     * LAMBDA EXPRESSION: The lambda expression in this method is used as an event handler for
     * the setOnAction method of the langComboBox. By using a lambda expression here, the code
     * is more concise and readable compared to using an anonymous inner class.
     */
    private void initializeLangComboBox() {
        langComboBox.getItems().addAll("English", "Français");
        Locale userLocale = Locale.getDefault();
        if (userLocale.getLanguage().equals("fr")) {
            langComboBox.setValue("Français");
        } else {
            langComboBox.setValue("English");
        }

        langComboBox.setOnAction(event -> {
            String selectedLanguage = langComboBox.getValue();
            if ("Français".equals(selectedLanguage)) {
                rb = ResourceBundle.getBundle("LangBundle", Locale.FRENCH);
            } else {
                rb = ResourceBundle.getBundle("LangBundle", Locale.ENGLISH);
            }
            setTextLabels();
        });
    }

    /**
     * Checks for upcoming appointments within the next 15 minutes.
     * Displays an alert if there are any upcoming appointments.
     */
    public void checkForUpcomingAppts() {
        // Retrieve any appointments in the next 15 minutes
        ObservableList<Appointment> upcomingAppointments = DBAppointments.readNext15MinAppts();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        // Build and display the alert
        if (!upcomingAppointments.isEmpty()) {
            StringBuilder alertContent = new StringBuilder("You have the following appointments within the next 15 minutes:\n");
            for (Appointment appt : upcomingAppointments) {
                LocalDateTime localStartDateTime = appt.getApptStart();
                alertContent.append("\nAppointment ID: ").append(appt.getApptID())
                        .append(", Date: ").append(localStartDateTime.format(dateFormatter))
                        .append(", Time: ").append(localStartDateTime.format(timeFormatter));
            }
            Utilities.showInfoAlert("Upcoming Appointments", alertContent.toString());
        } else {
            Utilities.showInfoAlert("No Upcoming Appointments", "You have no appointments within the next 15 minutes.");
        }
    }

    /**
     * Records all login activity to login_activity.txt.
     *
     * @param loginSuccess indicates whether the login attempt was successful
     * @throws IOException if there is an issue writing to the login_activity.txt file
     */
    public void recordLoginActivity(boolean loginSuccess) throws IOException {
        // Filename and data formatting
        String filename = "login_activity.txt";
        DateTimeFormatter loginFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss");
        String loginStatus = loginSuccess ? "SUCCESS" : "FAILURE";

        // Detect the user's current time
        LocalDateTime userTimeNow = LocalDateTime.now();

        // Create and open file
        FileWriter loginPrint = new FileWriter(filename, true);

        // Record login activity to file and close
        loginPrint.write("\nLOGIN " + loginStatus + "\n\tUsername:" + usernameField.getText() + ", " + loginFormatter.format(userTimeNow));
        loginPrint.close();
    }

    /**
     * Initializes the Login View Controller.
     * Sets up language detection and initializes the language ComboBox.
     *
     * @param url the location used to resolve relative paths for the root object, or null if
     *           the location is not known
     * @param rb the resources used to localize the root object, or null if the root object
     *           was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        detectUserLocale();
        initializeLangComboBox();
    }
}