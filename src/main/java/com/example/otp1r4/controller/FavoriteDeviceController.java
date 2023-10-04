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
                deviceDAO.updateDeviceControl(device.getDeviceId(), updateControl("On",1));
                device.updateDeviceControl(updateControl("On",1));
            }   else {
                deviceToggleButton.setText("POIS");
                deviceToggleButton.setStyle("-fx-background-color: red");
                deviceDAO.updateDeviceControl(device.getDeviceId(), updateControl("Off",1));
                device.updateDeviceControl(updateControl("Off",1));
            }
        });
    }

    public void setDevice(Device device) {
        this.device = device;

        setDeviceName();
        setDeviceDesc();
        setDeviceControl();
    }

    public void setDeviceName() {
        deviceName.setText(device.getDeviceName());
    }

    public void setDeviceType() {

    }

    public void setDeviceDesc() {
        deviceDesc.setText(device.getDeviceDesc());
    }

    public void setDeviceControl() {
        controls = device.getDeviceControl();
        String[] splitControls = controls.split(";");
        System.out.println(Arrays.toString(splitControls));
        switch (splitControls[0]) {
            case "Lighting":
                setUpLighting(splitControls);
                break;
            case "Appliance":
                setUpAppliance(splitControls);
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
        String[] splitControls = controls.split(";");

        return controls.replace(splitControls[i], s);
    }

}
