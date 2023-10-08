package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.ObservableDevices;
import com.example.otp1r4.model.UserData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.List;

public class AddDeviceController implements Controller {

    @FXML
    private ComboBox<String> deviceType, deviceSubType;
    @FXML
    private TextField deviceName;
    @FXML
    private TextArea deviceDescription;
    @FXML
    private Label deviceDescriptionLabel, deviceLabel, nameErrorLabel, typeErrorLabel, subTypeErrorLabel, sensorTypeErrorLabel;
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

    // IMURI
    @FXML
    private AnchorPane vacuumPane;
    @FXML
    private RadioButton vacuumVacuum, vacuumMop;
    @FXML
    private CheckBox vacuumStartNow, monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    @FXML
    private TextField vacuumActiveTime1, vacuumActiveTime2, vacuumActiveTime3, vacuumActiveTime4, vacuumCommand;

    // SAUNA
    @FXML
    private AnchorPane saunaPane;
    @FXML
    private TextField saunaTemp, heatingTime1, heatingTime2;

    // LUKKO
    @FXML
    private AnchorPane lockPane;
    @FXML
    private CheckBox lockStatus;

    // KAMERA
    @FXML
    private AnchorPane cameraPane;
    @FXML
    private CheckBox cameraStatus;

    private UserData userData = UserData.getInstance();
    ObservableDevices observableDevices = ObservableDevices.getInstance();
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
        hideAll(false);
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
        hideAll(true);
        if (deviceSubType.getValue().equals("Pesukone")) {
            deviceLabel.setText("Pesukone:");
            washerPane.setVisible(true);
        } else if (deviceSubType.getValue().equals("Astianpesukone")) {
            deviceLabel.setText("Astianpesukone:");
            dishwasherPane.setVisible(true);
        } else if (deviceSubType.getValue().equals("Imuri")) {
            deviceLabel.setText("Imuri:");
            vacuumPane.setVisible(true);
        } else if (deviceSubType.getValue().equals("Sauna")) {
            deviceLabel.setText("Sauna:");
            saunaPane.setVisible(true);
        } else if (deviceSubType.getValue().equals("Lukko")) {
            deviceLabel.setText("Lukko:");
            lockPane.setVisible(true);
        } else if (deviceSubType.getValue().equals("Kamera")) {
            deviceLabel.setText("Kamera:");
            cameraPane.setVisible(true);
        }
    }

    public void addDeviceButton() throws SQLException {
        boolean isValid = true;
        nameErrorLabel.setText("");
        typeErrorLabel.setText("");
        subTypeErrorLabel.setText("");
        sensorTypeErrorLabel.setText("");
        if (deviceName.getText().isEmpty()) {
            nameErrorLabel.setText("Syötä laitteelle nimi!");
            isValid = false;
        }
        if (deviceType.getValue() == null) {
            typeErrorLabel.setText("Valitse laitteen tyyppi!");
            isValid = false;
        }
        if (deviceSubType.getValue() == null && deviceType.getValue().equals("Laite")) {
            subTypeErrorLabel.setText("Valitse laitteen alityyppi!");
            isValid = false;
        }
        if (sensorType.getValue() == null && deviceType.getValue().equals("Sensori")) {
            sensorTypeErrorLabel.setText("Valitse sensorin tyyppi!");
            isValid = false;
        }

        if (deviceType.getValue().equals("Laite")) {
            if (deviceSubType.getValue().equals("Pesukone")) {
                generateWasherControl();
            } else if (deviceSubType.getValue().equals("Astianpesukone")) {
                generateDishwasherControl();
            } else if (deviceSubType.getValue().equals("Imuri")) {
                generateVacuumControl();
            } else if (deviceSubType.getValue().equals("Sauna")) {
                generateSaunaControl();
            } else if (deviceSubType.getValue().equals("Lukko")) {
                generateLockControl();
            } else if (deviceSubType.getValue().equals("Kamera")) {
                generateCameraControl();
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
            deviceDescription.setText("");
            hideAll(false);

            List<Device> deviceList = dDao.getDevices(userData.getUsername());
            int last = deviceList.size() -1;
            observableDevices.addDevice(deviceList.get(last));
            Stage stage = (Stage) deviceName.getScene().getWindow();
            showSuccessMessage(stage, "Laitteen lisäys onnistui!", "Laite lisätty onnistuneesti!", 3);
        }
    }

    public void generateSensorControl() {
        deviceControl = "Sensori;";
        deviceControl += sensorType.getValue() + ";";
        if (sensorStatus.isSelected()) {
            deviceControl += "On";
        } else {
            deviceControl += "Off";
        }
        if (sensorType.getValue().equals("Lämpötila")) {
            format = "Lämpötila";
            unit = "°C";
        } else if (sensorType.getValue().equals("Ilmankosteus")) {
            format = "Ilmankosteus";
            unit = "%";
        } else if (sensorType.getValue().equals("CO2")) {
            format = "CO2-pitoisuus";
            unit = "ppm";
        } else if (sensorType.getValue().equals("Liike")) {
            format = "Liike";
            unit = null;
        }
    }

    public void generateLightingControl() {
        deviceControl = "Valaisin;";
        if (brightness1.isSelected()) {
            deviceControl += "1";
        } else if (brightness2.isSelected()) {
            deviceControl += "2";
        } else if (brightness3.isSelected()) {
            deviceControl += "3";
        } else if (brightness4.isSelected()) {
            deviceControl += "4";
        }
        deviceControl += ";";
        if (colorCold.isSelected()) {
            deviceControl += "Kylmä";
        } else if (colorNeutral.isSelected()) {
            deviceControl += "Neutraali";
        } else if (colorWarm.isSelected()) {
            deviceControl += "Lämmin";
        }
        deviceControl += ";";
        if (motionOn.isSelected()) {
            deviceControl += "On;";
        } else {
            deviceControl += "Off;";
        }
        if (lampStatus.isSelected()) {
            deviceControl += "On";
        } else {
            deviceControl += "Off";
        }
        format = "Kirkkaus";
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
        format = "Toiminto";
        unit = null;
    }

    public void generateDishwasherControl() {
        deviceControl = "Laite;" + deviceSubType.getValue() + ";";
        if (radioEco.isSelected()) {
            deviceControl += "Eko";
        } else if (radioQuick.isSelected()) {
            deviceControl += "Pika";
        } else if (radioEff.isSelected()) {
            deviceControl += "Teho";
        }
        deviceControl += ";";
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
        format = "Toiminto";
        unit = null;
    }

    public void generateVacuumControl() {
        StringBuilder selectedCheckBoxes = new StringBuilder();
        appendIfSelectedForVacuum(selectedCheckBoxes, monday);
        appendIfSelectedForVacuum(selectedCheckBoxes, tuesday);
        appendIfSelectedForVacuum(selectedCheckBoxes, wednesday);
        appendIfSelectedForVacuum(selectedCheckBoxes, thursday);
        appendIfSelectedForVacuum(selectedCheckBoxes, friday);
        appendIfSelectedForVacuum(selectedCheckBoxes, saturday);
        appendIfSelectedForVacuum(selectedCheckBoxes, sunday);

        deviceControl = "Laite;" + deviceSubType.getValue() + ";";
        if (vacuumVacuum.isSelected()) {
            deviceControl += "Imurointi";
        } else if (vacuumMop.isSelected()) {
            deviceControl += "Moppaus";
        }
        deviceControl += ";";
        if (vacuumStartNow.isSelected()) {
            deviceControl += "On;";
        } else {
            deviceControl += "Off;";
        }
        String result = selectedCheckBoxes.toString();
        deviceControl += result + ";" + vacuumActiveTime1.getText() + "-" + vacuumActiveTime2.getText() + ";"
                + vacuumActiveTime3.getText() + "-" + vacuumActiveTime4.getText() + ";" + vacuumCommand.getText();

        format = "Toiminto";
        unit = null;
    }

    public void generateSaunaControl() {
        deviceControl = "Laite;" + deviceSubType.getValue() + ";" + saunaTemp.getText() + ";" + heatingTime1.getText() + "-" + heatingTime2.getText();
        format = "Lämpötila";
        unit = "°C";
    }

    public void generateLockControl() {
        deviceControl = "Laite;" + deviceSubType.getValue() + ";";
        if (lockStatus.isSelected()) {
            deviceControl += "On";
        } else {
            deviceControl += "Off";
        }
        format = "Lukitus";
        unit = null;
    }

    public void generateCameraControl() {
        deviceControl = "Laite;" + deviceSubType.getValue() + ";";
        if (cameraStatus.isSelected()) {
            deviceControl += "On";
        } else {
            deviceControl += "Off";
        }
        format = "Tallennus";
        unit = null;
    }

    public void hideAll(boolean sub) {
        deviceLabel.setText("");
        nameErrorLabel.setText("");
        typeErrorLabel.setText("");
        subTypeErrorLabel.setText("");
        sensorTypeErrorLabel.setText("");
        sensorPane.setVisible(false);
        lightingPane.setVisible(false);
        washerPane.setVisible(false);
        dishwasherPane.setVisible(false);
        vacuumPane.setVisible(false);
        saunaPane.setVisible(false);
        lockPane.setVisible(false);
        cameraPane.setVisible(false);
        if (!sub) {
            deviceSubTypeLabel.setVisible(false);
            deviceSubType.setVisible(false);
            sensorTypeLabel.setVisible(false);
            sensorType.setVisible(false);
        }
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

    private void appendIfSelectedForVacuum(StringBuilder stringBuilder, CheckBox checkBox) {
        if (checkBox.isSelected()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("+");
            }
            stringBuilder.append(checkBox.getText());
        }
    }

}