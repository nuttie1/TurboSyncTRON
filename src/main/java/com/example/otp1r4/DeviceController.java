package com.example.otp1r4;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DeviceController {
    @FXML
    Label deviceName;
    @FXML
    Label deviceDataLabel;

    public Label getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String name) {
        deviceName.setText(name);
    }
}
