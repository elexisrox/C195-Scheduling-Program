package app.helper;

import app.controller.ApptDialogController;
import app.controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
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

    //Method to transition to the Login screen in the main application.
    public static <Parent> void transitionLoginView(Stage stage) throws IOException {
        Parent scene = FXMLLoader.load(UniversalControls.class.getResource("/app/Login.fxml"));
        stage.setScene(new Scene((javafx.scene.Parent) scene));
        stage.centerOnScreen();
        stage.setTitle("Appointment Scheduler Login");
        stage.show();
    }

    //Dialog Box Transitions
    //Main method to open the Add/Modify Appointments Dialog, which is referenced by more specific methods below.
    public static void openApptDialog(Stage ownerStage, boolean isAddMode) throws IOException {
        //Initialize the dialog pane.
        FXMLLoader fxmlLoader = new FXMLLoader(UniversalControls.class.getResource("/app/ApptDialog.fxml"));
        DialogPane apptPane = fxmlLoader.load();
        ApptDialogController controller = fxmlLoader.getController();
        Dialog<Void> dialog = new Dialog<>();
        dialog.setDialogPane(apptPane);
        dialog.initOwner(ownerStage); // Set the owner to ensure modality and proper event dispatch
        dialog.initModality(Modality.APPLICATION_MODAL);

        //Determine if dialog pane is in Add or Modify mode and set the labels accordingly.
        String modeString = isAddMode ? "Add Appointment" : "Modify Appointment";
        dialog.setTitle(modeString);
        controller.setApptLabels(modeString);

        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(cancelButtonType);

        dialog.showAndWait();

    }

    //Method to specifically open the Add Appointment dialog box. Sets isAddMode to true.
    public static void openAddApptDialog(Stage ownerStage) throws IOException {
        openApptDialog(ownerStage, true);
    }

    //Method to specifically open the Modify Appointment dialog box. Sets isAddMode to false.
    public static void openModApptDialog(Stage ownerStage) throws IOException {
        openApptDialog(ownerStage, false);
    }
}


