package app.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/** Controller class for Login.fxml.
 * @author Elexis Rox
 */

public class LoginController implements Initializable {
    @FXML
    private ComboBox<String> langComboBox;

    @FXML
    private Label loginErrorWarning;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label timeZoneLabel;

    @FXML
    private TextField usernameField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        langComboBox.setItems(FXCollections.observableArrayList("English","French"));
    }

    @FXML
    void onActionExit(ActionEvent event) {

    }

    @FXML
    void onActionLogin(ActionEvent event) {

    }

    @FXML
    void onActionReset(ActionEvent event) {

    }


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