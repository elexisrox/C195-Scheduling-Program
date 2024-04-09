package app.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.IOException;
import java.util.Optional;

public class UniversalControls {
    public static void openApptDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(UniversalControls.class.getResource("/app/ApptDialog.fxml"));
        DialogPane addApptPane = fxmlLoader.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane((DialogPane) addApptPane);
        dialog.setTitle("Add Appointment");

        //Set the text of the Save and Cancel buttons
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().setAll(saveButtonType, ButtonType.CANCEL);

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK){
            System.out.println("Save button in dialog box clicked.");
        }
    }

}
