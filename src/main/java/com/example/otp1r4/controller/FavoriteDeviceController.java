package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class FavoriteDeviceController implements Controller, Initializable {
    @FXML
    Label deviceName,  deviceType;
    @FXML
    Shape isFavorite;
    @FXML
    TextArea deviceDesc;
    @FXML
    ToggleButton deviceToggleButton;
    @FXML
    TextField deviceTimerField, deviceCommandField;

    Device device;
    private UserData userData = UserData.getInstance();
    DeviceDAO deviceDAO = new DeviceDAO();

    String controls;

    int TYPE = 0, POWER = 1, TIME = 2, COMMAND = 3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        isFavorite.setOnMouseClicked(mouseEvent -> {
            if (isFavorite.getFill().equals(Color.YELLOW)) {
                isFavorite.setFill(Color.WHITE);
                deviceDAO.removeFavoriteDevice(userData.getUserID(), device.getDeviceId());
                device.updateIsDeviceFavorite(false);
            } else {
                isFavorite.setFill(Color.YELLOW);
            }
        });

        deviceToggleButton.setOnMouseClicked(mouseEvent -> {
            if (deviceToggleButton.isSelected()) {
                deviceToggleButton.setText("Päällä");
                deviceToggleButton.setStyle("-fx-background-color: green");
                deviceDAO.updateDeviceControl(device.getDeviceId(), updateControl("On",POWER));
                device.updateDeviceControl(updateControl("On",POWER));
            }   else {
                deviceToggleButton.setText("POIS");
                deviceToggleButton.setStyle("-fx-background-color: red");
                deviceDAO.updateDeviceControl(device.getDeviceId(), updateControl("Off",POWER));
                device.updateDeviceControl(updateControl("Off",POWER));
            }
        });

        deviceTimerField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String time = deviceTimerField.getText();

                if (time.matches("^[0-9]{4}")) {
                    deviceDAO.updateDeviceControl(device.getDeviceId(), updateControl(time,TIME));
                    device.updateDeviceControl(updateControl(time,TIME));
                } else {
                    deviceTimerField.setText(splitControl()[TIME]);
                }
            }
        });

        deviceCommandField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String command = deviceCommandField.getText();

                if (command.matches("^[a-zA-Z ]{3,20}$")) {
                    deviceDAO.updateDeviceControl(device.getDeviceId(), updateControl(command,COMMAND));
                    device.updateDeviceControl(updateControl(command,COMMAND));
                } else {
                    deviceCommandField.setText(splitControl()[COMMAND]);
                }
            }
        });
    }

    public void setDevice(Device device) {
        this.device = device;

        setDeviceName();
        setDeviceDesc();
        setDeviceControl();
        setDeviceType();
    }

    public void setDeviceName() {
        deviceName.setText(device.getDeviceName());
    }

    public void setDeviceType() {
        switch (splitControl()[TYPE]) {
            case "Lighting":
                deviceType.setText("Valaisin");
                break;
            case "Appliance":
                deviceType.setText("Kodinkone");
                break;
            case "Sensor":
                deviceType.setText("Sensori");
                break;
        }
    }

    public void setDeviceDesc() {
        if (!device.getDeviceDesc().isEmpty()) {
            deviceDesc.setText(device.getDeviceDesc());
        }
    }

    public void setDeviceControl() {
        switch (splitControl()[TYPE]) {
            case "Lighting":
                setUpLighting(splitControl());
                break;
            case "Appliance":
                setUpAppliance(splitControl());
                break;
            case "Sensor":
                setUpSensor();
                break;
        }
    }

    public void setUpLighting(String[] controls) {
        setUpButton(controls);
    }

    public void setUpAppliance(String[] controls) {
        setUpButton(controls);

        deviceTimerField.setVisible(true);
        deviceCommandField.setVisible(true);

        if (!splitControl()[TIME].isEmpty()) {
            deviceTimerField.setText(splitControl()[TIME]);
        }

        if (!splitControl()[COMMAND].isEmpty()) {
            deviceCommandField.setText(splitControl()[COMMAND]);
        }

    }

    public void setUpSensor() {
        deviceToggleButton.setVisible(false);
    }

    public void setUpButton(String[] controls) {
        if (controls[1].equals("On")) {
            deviceToggleButton.setSelected(true);
            deviceToggleButton.setText("Päällä");
            deviceToggleButton.setStyle("-fx-background-color: green");
        }   else {
            deviceToggleButton.setSelected(false);
            deviceToggleButton.setText("POIS");
            deviceToggleButton.setStyle("-fx-background-color: red");
        }
    }

    public String updateControl(String s, int i) {
        controls = device.getDeviceControl();
        return controls.replace(splitControl()[i], s);
    }

    public String[] splitControl() {
        controls = device.getDeviceControl();
        return controls.split(";");
    }

}
