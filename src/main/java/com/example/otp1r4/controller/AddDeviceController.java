package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.model.UserData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class AddDeviceController {

    @FXML
    private ComboBox<String> deviceType;
    @FXML
    private TextField deviceName, applianceTimerText, applianceCommandText, formatText, unitText;
    @FXML
    private TextArea deviceDescription;
    @FXML
    private Label deviceDescriptionLabel, deviceLabel, nameErrorLabel, typeErrorLabel, addSucces;
    @FXML
    private Label applianceTimerLabel, applianceCommandLabel, formatLabel, unitLabel;
    @FXML
    private CheckBox applianceCheckBox, lampStatus, favCheck;

    private UserData userData = UserData.getInstance();
    DeviceDAO dDao = new DeviceDAO();

    String deviceControl = "";
    String format = "";
    String unit = "";

    public void initialize() {
        deviceType.setItems(FXCollections.observableArrayList( "Kodinkone", "Valaisin", "Sensori"));
        deviceDescriptionLabel.setText("Laitteen kuvaus:\n(valinnainen)");
    }

    public void typeChanged() {
        hideAll();
        favCheck.setVisible(true);
        if(deviceType.getValue().equals("Kodinkone")) {
            deviceLabel.setText("Kodinkone:");
            applianceCheckBox.setVisible(true);
            applianceTimerLabel.setVisible(true);
            applianceTimerText.setVisible(true);
            applianceCommandLabel.setVisible(true);
            applianceCommandText.setVisible(true);
            formatText.setVisible(true);
            unitText.setVisible(true);
            formatLabel.setVisible(true);
            unitLabel.setVisible(true);
        } else if(deviceType.getValue().equals("Valaisin")) {
            deviceLabel.setText("Valaisin:");
            lampStatus.setVisible(true);
        } else if(deviceType.getValue().equals("Sensori")) {
            deviceLabel.setText("Sensori");
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
            addSucces.setText("Laite lisätty!");
        }
    }

    public void addAppliance() {
        deviceControl = "Appliance;";
        format = formatText.getText();
        unit = unitText.getText();
        if (applianceCheckBox.isSelected()) {
            deviceControl += "On;";
        } else {
            deviceControl += "Off;";
        }
        if (applianceTimerText.getText().isEmpty()) {
            deviceControl += ";";
        } else {
            deviceControl += applianceTimerText.getText() + ";";
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
        lampStatus.setVisible(false);
        applianceCheckBox.setVisible(false);
        applianceTimerLabel.setVisible(false);
        applianceTimerText.setVisible(false);
        applianceCommandLabel.setVisible(false);
        applianceCommandText.setVisible(false);
        formatText.setVisible(false);
        unitText.setVisible(false);
        formatLabel.setVisible(false);
        unitLabel.setVisible(false);
    }

}