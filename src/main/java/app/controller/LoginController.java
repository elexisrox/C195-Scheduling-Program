package app.controller;

//TODO Organize imports for clarity/readability
import app.DBaccess.DBUsers;
import app.MainApplication;
import app.helper.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Controller class for Login.fxml.
 * @author Elexis Rox
 */

public class LoginController implements Initializable {

    static Stage stage;
    static Parent scene;

    @FXML
    private ComboBox<String> langComboBox;

    //User fields
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    //Strings
    @FXML
    private Label loginErrorLbl;
    @FXML
    private Label timezoneLblString;
    @FXML
    private Label langLblString;

    //Validates username and password upon login. Displays an  appropriate error/success message upon login attempt. Logs completed login attempts in login_activity.txt file.
    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        System.out.println("Login button clicked.");

        String userName = usernameField.getText();
        String userPassword = passwordField.getText();
        boolean loginSuccessful = false;

        //Displays error message if both username and password fields are blank or empty.
        if ((userName.isBlank() || userName.isEmpty()) && (userPassword.isBlank() || userPassword.isEmpty())) {
            loginErrorLbl.setText(Utilities.getErrorMsg(1));
        }
        //Displays error message if username field is blank or empty.
        else if (userName.isBlank() || userName.isEmpty()) {
            loginErrorLbl.setText(Utilities.getErrorMsg(2));
        }
        //Displays error message if password field is blank or empty.
        else if (userPassword.isBlank() || userPassword.isEmpty()) {
            loginErrorLbl.setText(Utilities.getErrorMsg(3));
        }
        //Displays error message if the provided username does not exist in the database. Records the login attempt.
        else if (!DBUsers.userNameValidate(userName)) {
            loginErrorLbl.setText(Utilities.getErrorMsg(4));
            //TODO: LOGIN ACTIVITY
            //loginActivity();
        }
        //Attempts to validate that the provided username and password match.
        else {
            loginSuccessful = DBUsers.userLoginValidate(userName, userPassword);

            //If username and password do not match, displays an error message. Records the login attempt.
            if (!loginSuccessful) {
                loginErrorLbl.setText(Utilities.getErrorMsg(5));
                //TODO
                //loginActivity();
            }
            //If username and password are successfully validated, loads the main application Appointment view. Records the login attempt.
            else {
                System.out.println("Login successful.");
                loginErrorLbl.setText(null);
                //TODO
                //loginActivity();
                transitionApptView(event);

                //TODO
                //If login is successful, checks for upcoming appointments within 15 minutes of logging in.
                //TODO
                //Displays a message if there are no upcoming appointments within 15 minutes of logging in.
            }
        }
    }


    @FXML
    void onActionExit(ActionEvent event) {
        System.out.println("Exit button clicked.");
        app.helper.JDBC.closeConnection();
        System.out.println("Application terminated.");
        System.exit(0);
    }

    @FXML
    void onActionReset(ActionEvent event) {
        System.out.println("Reset fields button clicked.");
        usernameField.clear();
        passwordField.clear();
    }

    @FXML
    void onActionLangCombo(ActionEvent event) {
         /*if (Objects.equals(langComboBox.getValue(), "French")) {
             helper.Strings.updateLanguage(french);
         } else {
             helper.Strings.updateLanguage(english);
         }*/
    }



    // Method to update the text of each node based on the selected language
    /*private void updateLanguage() {
        Utilities.updateLanguage(loginErrorWarning, timeZoneLabel, langComboBox.getSelectionModel().getSelectedItem());
    }*/

    /*
    public TableView exampleTable;
    public TableColumn testcolumn1;
    public TableColumn testcolumn2;

    @FXML
    protected void onCustButtonClick() {

        ObservableList<Countries> countryList = DBCountries.getAllCountries();
        for(Countries C : countryList) {
            System.out.println("Country Id : " + C.getID() + " Name: " + C.getName());
        }
    }
    */
    /**
     *  Initializes the Login View Controller Class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //langComboBox.setItems(FXCollections.observableArrayList("English","French"));
        //langComboBox.getSelectionModel().selectFirst();
    }

    //BEGIN SCREEN TRANSITION METHODS
    //Transitions to main Appt View Scene
    public static void transitionApptView(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(LoginController.class.getResource("/app/ApptView.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.setTitle("View Appointments");
        stage.show();
    }

    //BEGIN OPEN DIALOG BOX METHODS
    //Opens AddAppt Dialog Box
//    public static void dialogAddAppt(ActionEvent event) throws IOException {
//    }

    //BEGIN ALERT TYPES
    //TODO move all alert types here
}