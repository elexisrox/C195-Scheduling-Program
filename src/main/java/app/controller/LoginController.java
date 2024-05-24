package app.controller;

//TODO Organize imports for clarity/readability
import app.DBaccess.DBAppointments;
import app.DBaccess.DBUsers;
import app.helper.Utilities;
import app.model.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller class for Login.fxml.
 * @author Elexis Rox
 */

public class LoginController implements Initializable {

    static Stage stage;
    static Parent scene;

    //ComboBox
    @FXML
    private ComboBox<String> langComboBox;

    //User fields
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    //Labels
    @FXML
    private Label loginErrorLbl;
    @FXML
    private Label timezoneLbl;

    //Translation Strings
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
    private Button loginButton;
    @FXML
    private Button exitButton;
    @FXML
    private Hyperlink resetString;

    //Used for translations
    private ResourceBundle rb;

    //Detect the user's time zone
    ZoneId userLocalZone = ZoneId.systemDefault();

    //Detect users' default language
    Locale userLocale = Locale.getDefault();

    //Validates username and password upon login. Displays an appropriate error/success message upon login attempt. Logs completed login attempts in login_activity.txt file.
    @FXML
    public void onActionLogin(ActionEvent event) throws IOException {
        System.out.println("Login button selected.");

        String userName = usernameField.getText();
        String userPassword = passwordField.getText();
        boolean loginSuccessful = false;

        //Displays error message if both username and password fields are blank or empty.
        if ((userName.isBlank()) && (userPassword.isBlank())) {
            loginErrorLbl.setText(rb.getString("errorUserPass"));
        }
        //Displays error message if username field is blank or empty.
        else if (userName.isBlank()) {
            loginErrorLbl.setText(rb.getString("errorUser"));
        }
        //Displays error message if password field is blank or empty.
        else if (userPassword.isBlank()) {
            loginErrorLbl.setText(rb.getString("errorPass"));
        }
        //Displays error message if the provided username does not exist in the database. Records the login attempt.
        else if (!DBUsers.userNameValidate(userName)) {
            loginErrorLbl.setText(rb.getString("errorUserNotFound"));
            //TODO: LOGIN ACTIVITY
            //loginActivity();
        }

        //Attempts to validate that the provided username and password match.
        else {
            loginSuccessful = DBUsers.userLoginValidate(userName, userPassword);

            //If username and password do not match, displays an error message. Records the login attempt.
            if (!loginSuccessful) {
                loginErrorLbl.setText(rb.getString("errorNoMatch"));
                //TODO
                //loginActivity();
            }
            //If username and password are successfully validated, loads the main application Appointment view. Records the login attempt.
            else {
                System.out.println("Login successful.");
                //Clear any previous login warnings.
                loginErrorLbl.setText(null);
                //TODO
                //loginActivity();
                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Utilities.transitionApptView(stage);

                //Check for upcoming appointments within 15 minutes of logging in.
                checkForUpcomingAppts();
            }
        }
    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.out.println("Exit button selected.");
        app.helper.JDBC.closeConnection();
        System.out.println("Application terminated.");
        System.exit(0);
    }

    @FXML
    void onActionReset(ActionEvent event) {
        System.out.println("Reset fields button selected.");
        usernameField.clear();
        passwordField.clear();
    }

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
        //Clear any previous login warnings.
        loginErrorLbl.setText(null);
        //Sets timezone label according to the user's timezone
        timezoneLbl.setText(String.valueOf(userLocalZone));
    }

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

    //Initializes the Login View Controller Class.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        detectUserLocale();
        initializeLangComboBox();
    }

    //Checks for appointments within the next 15 minutes.
    public void checkForUpcomingAppts() {
        ObservableList<Appointment> upcomingAppointments = DBAppointments.readNext15MinAppts();
        if (!upcomingAppointments.isEmpty()) {
            StringBuilder alertContent = new StringBuilder("You have the following appointments within the next 15 minutes:\n");
            for (Appointment appt : upcomingAppointments) {
                alertContent.append("\nAppointment ID: ").append(appt.getApptID())
                        .append(", Date: ").append(appt.getApptStart().toLocalDate())
                        //TODO make sure this displays in user timezone
                        .append(", Time: ").append(appt.getApptStart().toLocalTime());
            }
            Utilities.showInfoAlert("Upcoming Appointments", alertContent.toString());
        } else {
            Utilities.showInfoAlert("No Upcoming Appointments", "You have no appointments within the next 15 minutes.");
        }
    }

}