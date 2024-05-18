package app.helper;

import app.controller.ApptDialogController;
import app.controller.ApptViewController;
import app.controller.LoginController;
import app.model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/** Universal Controls class provides methods for scene transitions, data conversions, and other functions that are used across the application.
 * @author Elexis Rox
 */

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

    //Method to transition to the Login screen in the main application.
    public static <Parent> void transitionLoginView(Stage stage) throws IOException {
        Parent scene = FXMLLoader.load(UniversalControls.class.getResource("/app/Login.fxml"));
        stage.setScene(new Scene((javafx.scene.Parent) scene));
        stage.centerOnScreen();
        stage.setTitle("Appointment Scheduler Login");
        stage.show();
    }

    //Dialog Box Transitions
    //Method to specifically open the Add Appointment dialog box. Sets isAddMode to true.
    public static void openAddApptDialog(Stage ownerStage, ApptViewController apptMainView) throws IOException {
        openApptDialog(ownerStage, true, apptMainView);
    }

    //Method to specifically open the Modify Appointment dialog box. Sets isAddMode to false.
    public static void openModApptDialog(Stage ownerStage, ApptViewController apptMainView) throws IOException {
        openApptDialog(ownerStage, false, apptMainView);
    }

    //Main method to open the Add/Modify Appointments Dialog, which is referenced by more specific methods below.
    public static void openApptDialog(Stage ownerStage, boolean isAddMode, ApptViewController apptMainView) throws IOException {
        //Initializes and creates the dialog pane
        FXMLLoader fxmlLoader = new FXMLLoader(UniversalControls.class.getResource("/app/ApptDialog.fxml"));
        DialogPane apptPane = fxmlLoader.load();
        ApptDialogController dialogController = fxmlLoader.getController();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(apptPane);

        //Sets the owner to ensure modality and proper event dispatch
        dialog.initOwner(ownerStage);
        dialog.initModality(Modality.APPLICATION_MODAL);

        //Determines if dialog pane is in Add or Modify mode and set the labels accordingly.
        String modeString = isAddMode ? "Add Appointment" : "Modify Appointment";
        dialog.setTitle(modeString);
        dialogController.setApptLabels(modeString);

        //For new appointments, retrieves auto-generated AppointmentID from the database.
        if (isAddMode) {
            dialogController.retrieveNewApptID();
        }

        //Creates Save/Cancel buttons
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        //Gets the save button and adds a manual event handler.
        Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        if (saveButton != null) {
            saveButton.addEventFilter(ActionEvent.ACTION, event -> {
                System.out.println("Save button clicked");
                if (!dialogController.validateInputs()) {
                    System.out.println("Validation failed.");
                    event.consume(); // Prevents the dialog from closing
                } else {
                    System.out.println("Validation succeeded, saving data.");
                    dialogController.handleSave();
                }
            });
        } else {
            System.out.println("Save button is null, unable to add event filter.");
        }

        //Shows the dialog and handles the buttons being selected.
        dialog.showAndWait().ifPresent(result -> {
            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                System.out.println("Save button selected.");
                apptMainView.updateTableData();
            } else {
                System.out.println("Cancel or close button selected.");
            }
        });

    }
}


