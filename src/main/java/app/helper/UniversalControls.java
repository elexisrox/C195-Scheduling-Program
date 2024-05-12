package app.helper;

import app.controller.ApptDialogController;
import app.controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class UniversalControls {
    //Scene Transitions

    //Dialog Box Transitions
    //Main method to open the Add/Modify Appointments Dialog
    public static void openApptDialog(String dialogBoxTitle, String label) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UniversalControls.class.getResource("/app/ApptDialog.fxml"));
        DialogPane apptPane = fxmlLoader.load();

        ApptDialogController controller = fxmlLoader.getController();
        controller.setApptLabels(label); // Set label text here

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(apptPane);
        dialog.setTitle(dialogBoxTitle);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().setAll(saveButtonType, ButtonType.CANCEL);

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK) {
            System.out.println("Save button in dialog box clicked.");
        }
    }

    public static void openAddApptDialog() throws IOException {
        openApptDialog("Add Appointment", "Add Appointment");
    }

    public static void openModApptDialog() throws IOException {
        openApptDialog("Modify Appointment", "Modify Appointment");
    }
}


