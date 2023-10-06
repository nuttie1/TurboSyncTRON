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
    private TextField deviceName;
    @FXML
    private TextArea deviceDescription;
    @FXML
    private Label deviceDescriptionLabel, deviceLabel, nameErrorLabel, typeErrorLabel;
    @FXML
    private Label deviceSubTypeLabel;
    @FXML
    private CheckBox favCheck;

    // SENSORI
    @FXML
    private AnchorPane sensorPane;
    @FXML
    private Label sensorTypeLabel;
    @FXML
    private ComboBox<String> sensorType;
    @FXML
    private  CheckBox sensorStatus;

    // VALAISIN
    @FXML
    private AnchorPane lightingPane;
    @FXML
    private CheckBox lampStatus, motionOn;
    @FXML
    private RadioButton brightness1, brightness2, brightness3, brightness4, colorCold, colorNeutral, colorWarm;

    // PESUKONE
    @FXML
    private AnchorPane washerPane;
    @FXML
    private ComboBox<String> washerMode, washerTemp, washerSpin;
    @FXML
    private CheckBox washerStartNow, washerQuick, washerExtra;
    @FXML
    private Label washerTimerLabel;
    @FXML
    private TextField washerTimerText;

    // ASTIANPESUKONE
    @FXML
    private AnchorPane dishwasherPane;
    @FXML
    private RadioButton radioEco, radioQuick, radioEff;
    @FXML
    private CheckBox dishwasherStartNow;
    @FXML
    private Label dishwasherTimerLabel;
    @FXML
    private TextField dishwasherTimerText;

    private UserData userData = UserData.getInstance();
    DeviceDAO dDao = new DeviceDAO();

    String deviceControl = "";
    String format = "";
    String unit = "";

    public void initialize() {
        deviceDescriptionLabel.setText("Laitteen kuvaus:\n(valinnainen)");
        deviceType.setItems(FXCollections.observableArrayList( "Laite", "Valaisin", "Sensori"));
        deviceSubType.setItems(FXCollections.observableArrayList("Pesukone", "Astianpesukone", "Imuri", "Sauna", "Lukko", "Kamera"));
        sensorType.setItems(FXCollections.observableArrayList("Lämpötila", "Ilmankosteus", "CO2", "Liike"));
        washerMode.setItems(FXCollections.observableArrayList("Puuvilla", "Hienopesu", "Sport"));
        washerTemp.setItems(FXCollections.observableArrayList("30", "40", "60", "90"));
        washerSpin.setItems(FXCollections.observableArrayList("0", "600", "800", "1000", "1200"));
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
            sensorTypeLabel.setVisible(true);
            sensorType.setVisible(true);
            sensorPane.setVisible(true);
        }

    }

    public void subTypeChanged() {
        hideAllSubTEMP();
        if (deviceSubType.getValue().equals("Pesukone")) {
            deviceLabel.setText("Pesukone:");
            washerPane.setVisible(true);
        } else if (deviceSubType.getValue().equals("Astianpesukone")) {
            deviceLabel.setText("Astianpesukone:");
            dishwasherPane.setVisible(true);
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
                generateWasherControl();
            } else if (deviceSubType.getValue().equals("Astianpesukone")) {
                generateDishwasherControl();
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

    public void generateSensorControl() {
        deviceControl = "Sensor;";
        deviceControl += sensorType.getValue() + ";";
        if (sensorStatus.isSelected()) {
            deviceControl += "On";
        } else {
            deviceControl += "Off";
        }
        format = "Sensoriformat";
        unit = null;
    }

    public void generateLightingControl() {
        deviceControl = "Lighting;";
        if (brightness1.isSelected()) {
            deviceControl += "1;";
        } else if (brightness2.isSelected()) {
            deviceControl += "2;";
        } else if (brightness3.isSelected()) {
            deviceControl += "3;";
        } else if (brightness4.isSelected()) {
            deviceControl += "4;";
        }

        if (colorCold.isSelected()) {
            deviceControl += "Cold;";
        } else if (colorNeutral.isSelected()) {
            deviceControl += "Neutral;";
        } else if (colorWarm.isSelected()) {
            deviceControl += "Warm;";
        }

        if (motionOn.isSelected()) {
            deviceControl += "On";
        } else {
            deviceControl += "Off";
        }

        if (lampStatus.isSelected()) {
            deviceControl += "On;";
        } else {
            deviceControl += "Off;";
        }

        format = "Status";
        unit = null;
    }

    public void generateWasherControl() {
        deviceControl = "Laite;" + deviceSubType.getValue() + ";";
        deviceControl += washerMode.getValue() + ";" + washerTemp.getValue() + ";" + washerSpin.getValue() + ";";
        if (washerExtra.isSelected()) {
            deviceControl += "On;";
        } else {
            deviceControl += "Off;";
        }
        if (washerQuick.isSelected()) {
            deviceControl += "On;";
        } else {
            deviceControl += "Off;";
        }
        if (washerStartNow.isSelected()) {
            deviceControl += "On;";
        } else {
            deviceControl += "Off;";
        }
        if (washerTimerText.getText().isEmpty()) {
            deviceControl += "-1";
        } else {
            deviceControl += washerTimerText.getText();
        }
        format = "Laiteformat";
        unit = null;
    }

    public void generateDishwasherControl() {
        deviceControl = "Laite;" + deviceSubType.getValue() + ";";
        if (radioEco.isSelected()) {
            deviceControl += "Eko;";
        } else if (radioQuick.isSelected()) {
            deviceControl += "Pika;";
        } else if (radioEff.isSelected()) {
            deviceControl += "Teho;";
        }
        if (dishwasherStartNow.isSelected()) {
            deviceControl += "On;";
        } else {
            deviceControl += "Off;";
        }
        if (dishwasherTimerText.getText().isEmpty()) {
            deviceControl += "-1";
        } else {
            deviceControl += dishwasherTimerText.getText();
        }
        format = "Laiteformat";
        unit = null;
    }

    public void hideAll() {
        deviceLabel.setText("");
        sensorPane.setVisible(false);
        lightingPane.setVisible(false);
        washerPane.setVisible(false);
        dishwasherPane.setVisible(false);
        deviceSubTypeLabel.setVisible(false);
        deviceSubType.setVisible(false);
        sensorTypeLabel.setVisible(false);
        sensorType.setVisible(false);
    }

    public void hideAllSubTEMP() {
        deviceLabel.setText("");
        sensorPane.setVisible(false);
        lightingPane.setVisible(false);
        washerPane.setVisible(false);
        dishwasherPane.setVisible(false);
        sensorTypeLabel.setVisible(false);
        sensorType.setVisible(false);
    }

    public void washerStartNowClicked() {
        if (washerStartNow.isSelected()) {
            washerTimerLabel.setDisable(true);
            washerTimerText.setDisable(true);
        } else {
            washerTimerLabel.setDisable(false);
            washerTimerText.setDisable(false);
        }
    }

    public void dishwasherStartNowClicked() {
        if (dishwasherStartNow.isSelected()) {
            dishwasherTimerLabel.setDisable(true);
            dishwasherTimerText.setDisable(true);
        } else {
            dishwasherTimerLabel.setDisable(false);
            dishwasherTimerText.setDisable(false);
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