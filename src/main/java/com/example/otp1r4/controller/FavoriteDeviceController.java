package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.UserData;
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
    private UserData userData = UserData.getInstance();
    DeviceDAO deviceDAO = new DeviceDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        isFavorite.setOnMouseClicked(mouseEvent -> {
            if (isFavorite.getFill().equals(Color.YELLOW)) {
                isFavorite.setFill(Color.WHITE);
                deviceDAO.removeFavoriteDevice(userData.getUserID(), device.getDeviceId());
            } else {
                isFavorite.setFill(Color.YELLOW);
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
        String controls = device.getDeviceControl();
        System.out.println(controls);
    }


}
