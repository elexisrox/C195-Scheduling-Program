package app.helper;

import app.controller.ApptDialogController;
import app.controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class UniversalControls {
    //Scene Transitions
    //Method to transition to the Appointment View in the main application.
    public static <Parent> void transitionApptView(Stage stage) throws IOException {
        Parent scene = FXMLLoader.load(UniversalControls.class.getResource("/app/ApptView.fxml"));
        stage.setScene(new Scene((javafx.scene.Parent) scene));
        stage.centerOnScreen();
        stage.setTitle("View Appointments");
        stage.show();
    }

    //Dialog Box Transitions
    //Main method to open the Add/Modify Appointments Dialog, which is referenced by more specific methods below.
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

    //Method to specifically open the Add Appointment dialog box.
    public static void openAddApptDialog() throws IOException {
        openApptDialog("Add Appointment", "Add Appointment");
    }

    //Method to specifically open the Modify Appointment dialog box.
    public static void openModApptDialog() throws IOException {
        openApptDialog("Modify Appointment", "Modify Appointment");
    }
}


