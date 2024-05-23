package app.controller;

import app.helper.Utilities;
import javafx.event.Event;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ReportViewController implements Initializable {

    //Top navigation toggle group
    @FXML
    private ToggleGroup topMenuToggle;

    //Detect the user's time zone
    ZoneId userLocalZone = ZoneId.systemDefault();

    @FXML
    private Label choiceBoxLbl;

    @FXML
    private ChoiceBox<?> reportsBox;

    @FXML
    private Label reportsResultLbl;

    @FXML
    private TableView<?> reportsTable;

    @FXML
    private Tab tabContactReport;

    @FXML
    private Tab tabCountryReport;

    @FXML
    private Tab tabMonthReport;

    @FXML
    private Tab tabTypeReport;

    @FXML
    private Label timezoneLbl;

    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Utilities.logoutButton(stage);
    }

    @FXML
    void onActionExit(ActionEvent event) {
        Utilities.exitButton();
    }

    @FXML
    void onReportsTabChanged(Event event) {

    }

    //Initializes the Main Reports View.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set up the toggle navigation menu
        topMenuToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    RadioButton selectedRadioButton = (RadioButton) newValue;
                    Stage stage = (Stage) selectedRadioButton.getScene().getWindow();
                    Utilities.onRadioButtonSelected(topMenuToggle, stage);
                } catch (Exception e) {
                    System.out.println("Error (Navigation Radio Buttons): " + e.getMessage());
                }
            }
        });

        //Sets timezone label according to the user's timezone
        timezoneLbl.setText(String.valueOf(userLocalZone));
    }
}
