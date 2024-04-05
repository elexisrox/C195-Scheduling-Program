package app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller class for ApptView.fxml.
 * @author Elexis Rox
 */

public class ApptViewController implements Initializable {
    /**
     *  Initializes the Appointment View Controller Class.
     */
    Stage stage;
    Parent scene;

    @FXML
    public void onActionDelAppt(ActionEvent event) {
        System.out.println("Delete Appointment button clicked.");
    }

    @FXML
    public void onActionModAppt(ActionEvent event) {
        System.out.println("Modify Appointment button clicked.");
    }

    @FXML
    public void onActionAddAppt(ActionEvent event) throws IOException {
        System.out.println("Add Appointment button clicked.");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/app/ApptDialog.fxml"));
        DialogPane addApptPane = fxmlLoader.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane((DialogPane) addApptPane);
        dialog.setTitle("Add Appointment");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.get() == ButtonType.OK){
            System.out.println("OK button clicked.");
        }
    }

    @FXML
    public void onActionExit(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}