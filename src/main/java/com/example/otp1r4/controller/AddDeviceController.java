package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.model.UserData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLException;

public class AddDeviceController {

    @FXML
    private ComboBox<String> deviceType, deviceSubType;
    @FXML
    private TextField deviceName, applianceTimerText, applianceCommandText, formatText, unitText;
    @FXML
    private TextArea deviceDescription;
    @FXML
    private Label deviceDescriptionLabel, deviceLabel, nameErrorLabel, typeErrorLabel;
    @FXML
    private Label deviceSubTypeLabel, applianceTimerLabel, applianceCommandLabel, formatLabel, unitLabel;
    @FXML
    private CheckBox applianceStartNow, washerQuickCheck, favCheck;
    @FXML
    private AnchorPane washerPane;

    // SENSORI


    // VALAISIN
    @FXML
    private AnchorPane lightingPane;
    @FXML
    private CheckBox lampStatus, motionOn;
    @FXML
    ToggleGroup brightness, color;
    @FXML
    private RadioButton brightness1, brightness2, brightness3, brightness4, colorCold, colorNeutral, colorWarm;

    // LAITE

    private UserData userData = UserData.getInstance();
    DeviceDAO dDao = new DeviceDAO();

    String deviceControl = "";
    String format = "";
    String unit = "";

    public void initialize() {
        deviceType.setItems(FXCollections.observableArrayList( "Laite", "Valaisin", "Sensori"));
        deviceSubType.setItems(FXCollections.observableArrayList("Pesukone", "Astianpesukone", "Imuri", "Sauna", "Lukko", "Kamera"));
        deviceDescriptionLabel.setText("Laitteen kuvaus:\n(valinnainen)");
    }

    public void typeChanged() {
        hideAll();
        favCheck.setVisible(true);
        if(deviceType.getValue().equals("Laite")) {
            deviceSubTypeLabel.setVisible(true);
            deviceSubType.setVisible(true);
        } else if(deviceType.getValue().equals("Valaisin")) {
            deviceLabel.setText("Valaisin:");
            lightingPane.setVisible(true);
        } else if(deviceType.getValue().equals("Sensori")) {
            deviceLabel.setText("Sensori");
            deviceSubTypeLabel.setVisible(false);
            deviceSubType.setVisible(false);
        }

    }

    public void subTypeChanged() {
        hideAll();
        if (deviceSubType.getValue().equals("Pesukone")) {
            deviceLabel.setText("Pesukone:");
            applianceStartNow.setVisible(true);
            washerQuickCheck.setVisible(true);
            applianceTimerLabel.setVisible(true);
            applianceTimerText.setVisible(true);
            applianceCommandLabel.setVisible(true);
            applianceCommandText.setVisible(true);
            formatText.setVisible(true);
            unitText.setVisible(true);
            formatLabel.setVisible(true);
            unitLabel.setVisible(true);
        } else if (deviceSubType.getValue().equals("Kiuas")) {
            deviceLabel.setText("Kiuas:");
            applianceStartNow.setVisible(true);
            applianceTimerLabel.setVisible(true);
            applianceTimerText.setVisible(true);
            formatText.setVisible(true);
            unitText.setVisible(true);
            formatLabel.setVisible(true);
            unitLabel.setVisible(true);
        }
    }

    public void addDeviceButton() throws SQLException {
        boolean isValid = true;
        nameErrorLabel.setText("");
        typeErrorLabel.setText("");
        if (deviceName.getText().isEmpty()) {
            nameErrorLabel.setText("Syötä laitteelle nimi!");
            isValid = false;
        }
        if (deviceType.getValue() == null) {
            typeErrorLabel.setText("Valitse laitteen tyyppi!");
            isValid = false;
        }

        if (deviceType.getValue().equals("Laite")) {
            if (deviceSubType.getValue().equals("Pesukone")) {
                addAppliance();
            }
        } else if (deviceType.getValue().equals("Valaisin")) {
            generateLightingControl();
        } else if (deviceType.getValue().equals("Sensori")) {
            generateSensorControl();
        }

        if (isValid) {
            dDao.addDevice(deviceName.getText(), deviceDescription.getText(),
                    favCheck.isSelected(), userData.getUserID(), deviceControl, format, unit);
            deviceName.setText("");
            deviceType.setValue("Laitteen tyyppi");
            deviceDescription.setText("");

            Stage stage = (Stage) deviceName.getScene().getWindow();
            showSuccessMessage(stage);
        }
    }

    public void addAppliance() {
        deviceControl = "Appliance;" + deviceSubType.getValue() + ";";
        format = formatText.getText();
        unit = unitText.getText();
        if (applianceStartNow.isSelected()) {
            deviceControl += "On;";
        } else {
            deviceControl += "Off;";
        }
        if (applianceTimerText.getText().isEmpty()) {
            deviceControl += ";";
        } else {
            deviceControl += applianceTimerText.getText() + ";";
        }
        if (washerQuickCheck.isSelected()) {
            deviceControl += "Quick;";
        } else {
            deviceControl += "Normal;";
        }
        deviceControl += applianceCommandText.getText();
    }

    public void generateLightingControl() {
        deviceControl = "Lighting;";
        if (lampStatus.isSelected()) {
            deviceControl += "On";
        } else {
            deviceControl += "Off";
        }

        switch (brightness.getSelectedToggle().toString()) {
            case "brightness1":
                deviceControl += "1;";
                break;
            case "brightness2":
                deviceControl += "2;";
                break;
            case "brightness3":
                deviceControl += "3;";
                break;
            case "brightness4":
                deviceControl += "4;";
                break;
        }

        switch (color.getSelectedToggle().toString()) {
            case "colorCold":
                deviceControl += "Cold;";
                break;
            case "colorNeutral":
                deviceControl += "Neutral;";
                break;
            case "colorWarm":
                deviceControl += "Warm;";
                break;
        }

        if (motionOn.isSelected()) {
            deviceControl += "On";
        } else {
            deviceControl += "Off";
        }

        format = "Status";
        unit = null;
    }

    public void generateSensorControl() {
        deviceControl = "Sensor";
    }

    public void hideAll() {
        lightingPane.setVisible(false);
        washerQuickCheck.setVisible(false);
        applianceStartNow.setVisible(false);
        applianceTimerLabel.setVisible(false);
        applianceTimerText.setVisible(false);
        applianceCommandLabel.setVisible(false);
        applianceCommandText.setVisible(false);
        formatText.setVisible(false);
        unitText.setVisible(false);
        formatLabel.setVisible(false);
        unitLabel.setVisible(false);
        deviceSubTypeLabel.setVisible(false);
        deviceSubType.setVisible(false);
    }

    public void startNowClicked() {
        if (applianceStartNow.isSelected()) {
            applianceTimerLabel.setDisable(true);
            applianceTimerText.setDisable(true);
        } else {
            applianceTimerLabel.setDisable(false);
            applianceTimerText.setDisable(false);
        }
    }

    private void showSuccessMessage(Stage ownerStage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Laite lisätty");
        alert.setHeaderText(null);
        alert.setContentText("Laite lisätty onnistuneesti!");

        alert.initOwner(ownerStage);
        alert.show();

        Duration duration = Duration.seconds(5);
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(duration);
        pause.setOnFinished(event -> alert.close());
        pause.play();
    }

}