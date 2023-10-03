package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AllDevicesController implements Controller, Initializable {

    @FXML
    AnchorPane allDevicesPane;

    List<Device> allDevices = new ArrayList<>();

    DeviceDAO deviceDAO = new DeviceDAO();
    UserData userData = UserData.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAllDevices();

        try {
            allDevices = deviceDAO.getDevices(userData.getUsername());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAllDevices() {
        GridPane pane = new GridPane();

        int column = 0;
        int row = 0;
        for (Device device : allDevices) {
            Label deviceName = new Label();
            Label deviceType = new Label();
            deviceName.setText(device.getDeviceName());
            deviceType.setText(device.getDeviceType());
            ImageView deviceImage = new ImageView();
            Image image;

            GridPane labelPane = new GridPane();
            labelPane.add(deviceName, 0, 0);
            labelPane.add(deviceType, 0, 1);

            labelPane.setAlignment(Pos.CENTER_LEFT);

            pane.add(labelPane, column, row);

            column++;

            switch (device.getDeviceType()) {
                case "kodinkone":
                    image = new Image(String.valueOf(getClass().getResource("/images/homeAppliances.png")));
                    deviceImage.setImage(image);
                    break;
                case "sensori":
                    image = new Image(String.valueOf(getClass().getResource("/images/sensor.png")));
                    deviceImage.setImage(image);
                    break;
                case "lamppu":
                    image = new Image(String.valueOf(getClass().getResource("/images/light.png")));
                    deviceImage.setImage(image);
                    break;
            }

            pane.add(deviceImage, column, row);

            column++;
            if (column == 4) {
                column = 0;
                row++;
            }
        }

        pane.setHgap(20);
        pane.setPadding(new Insets(10));

        allDevicesPane.getChildren().add(pane);
    }


}
