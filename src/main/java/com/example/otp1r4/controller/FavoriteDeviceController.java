package com.example.otp1r4.controller;

import com.example.otp1r4.model.Device;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class FavoriteDeviceController implements Controller, Initializable {
    @FXML
    Label deviceName,  deviceType;
    @FXML
    Shape isFavorite;
    @FXML
    TextArea deviceDesc;

    Device device;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        isFavorite.setOnMouseClicked(mouseEvent -> {
            if (isFavorite.getFill().equals(Color.YELLOW)) {
                isFavorite.setFill(Color.WHITE);
            } else {
                isFavorite.setFill(Color.YELLOW);
            }
        });
    }

    public void setDevice(Device device) {
        this.device = device;

        setDeviceName();
        setDeviceDesc();
    }

    public void setDeviceName() {
        deviceName.setText(device.getDeviceName());
    }

    public void setDeviceType() {

    }

    public void setDeviceDesc() {
        deviceDesc.setText(device.getDeviceDesc());
    }


}
