package app.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/** Controller class for Login.fxml.
 * @author Elexis Rox
 */

public class LoginController implements Initializable {
    @FXML
    private ComboBox<String> langComboBox;

    @FXML
    private Label loginErrorLbl;

    //User fields
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    //Strings
    @FXML
    private Label timezoneLblString;
    @FXML
    private Label langLblString;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        langComboBox.setItems(FXCollections.observableArrayList("English","French"));
        langComboBox.getSelectionModel().selectFirst();


    }

    @FXML
    void onActionLogin(ActionEvent event) {

    }

    @FXML
    void onActionExit(ActionEvent event) {

    }

    @FXML
    void onActionReset(ActionEvent event) {

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
}