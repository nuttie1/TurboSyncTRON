package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.model.UserData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private CheckBox applianceStartNow, washerQuickCheck, lampStatus, favCheck;

    private UserData userData = UserData.getInstance();
    DeviceDAO dDao = new DeviceDAO();

    String deviceControl = "";
    String format = "";
    String unit = "";

    public void initialize() {
        deviceType.setItems(FXCollections.observableArrayList( "Kodinkone", "Valaisin", "Sensori"));
        deviceSubType.setItems(FXCollections.observableArrayList("Pesukone", "Kiuas"));
        deviceDescriptionLabel.setText("Laitteen kuvaus:\n(valinnainen)");
    }

    public void typeChanged() {
        hideAll();
        favCheck.setVisible(true);
        if(deviceType.getValue().equals("Kodinkone")) {
            deviceSubTypeLabel.setVisible(true);
            deviceSubType.setVisible(true);
        } else if(deviceType.getValue().equals("Valaisin")) {
            deviceLabel.setText("Valaisin:");
            lampStatus.setVisible(true);
            deviceSubTypeLabel.setVisible(false);
            deviceSubType.setVisible(false);
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

        if (deviceType.getValue().equals("Kodinkone")) {
            addAppliance();
        } else if (deviceType.getValue().equals("Valaisin")) {
            addLighting();
        } else if (deviceType.getValue().equals("Sensori")) {
            addSensor();
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

    public void addLighting() {
        deviceControl = "Lighting;";
        format = "Status";
        unit = null;
        if (lampStatus.isSelected()) {
            deviceControl += "On";
        } else {
            deviceControl += "Off";
        }
    }

    public void addSensor() {
        deviceControl = "Sensor";
    }

    public void hideAll() {
        washerQuickCheck.setVisible(false);
        lampStatus.setVisible(false);
        applianceStartNow.setVisible(false);
        applianceTimerLabel.setVisible(false);
        applianceTimerText.setVisible(false);
        applianceCommandLabel.setVisible(false);
        applianceCommandText.setVisible(false);
        formatText.setVisible(false);
        unitText.setVisible(false);
        formatLabel.setVisible(false);
        unitLabel.setVisible(false);
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