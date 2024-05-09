package app.controller;

import app.helper.UniversalControls;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Controller class for ApptView.fxml.
 * @author Elexis Rox
 */

//TODO Possibly rename MainViewController depending on how I handle the other views
public class ApptViewController implements Initializable {
    /**
     *  Initializes the Appointment View Controller Class.
     */
    Stage stage;
    Parent scene;

    @FXML
    public void onActionAddAppt(ActionEvent event) throws IOException {
        System.out.println("Add Appointment button clicked.");
        ApptDialogController.openAddApptDialog();
    }

    @FXML
    public void onActionModAppt(ActionEvent event) throws IOException {
        System.out.println("Modify Appointment button clicked.");
        ApptDialogController.openModApptDialog();
    }

    @FXML
    public void onActionDelAppt(ActionEvent event) {
        System.out.println("Delete Appointment button clicked.");
    }

    @FXML
    public void onActionExit(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}