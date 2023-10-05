package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    UserData userData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userData = UserData.getInstance();;

        allDevices = deviceDAO.getDevices(userData.getUsername());
        showAllDevices();
    }

    public void showAllDevices() {
        GridPane pane = new GridPane();

        Node[] headers = new Node[] {
                createHeaderNode("Nimi"),
                createHeaderNode("Kuvaus"),
                createHeaderNode("Lempi")
        };
        pane.addRow(0,headers);

        int row = 1;
        for (Device device : allDevices) {
            final GridPane labelPane = getGridPane(device);

            labelPane.setAlignment(Pos.CENTER_LEFT);

            pane.add(labelPane, 0, row);
            row++;
        }

        pane.setHgap(20);
        pane.setPadding(new Insets(10));

        allDevicesPane.getChildren().add(pane);
    }

    private static GridPane getGridPane(Device device) {
        Label deviceName = new Label(device.getDeviceName());
        Label deviceDesc = new Label(device.getDeviceDesc());
        Label deviceFavorite = new Label();

        deviceName.setPadding(new Insets(1));
        deviceDesc.setPadding(new Insets(1));
        deviceFavorite.setPadding(new Insets(1));

        if(device.isDeviceFavorite()){
            deviceFavorite.setText("true");
        }else {
            deviceFavorite.setText("false");
        }

        GridPane labelPane = new GridPane();
        labelPane.add(deviceName, 0, 0);
        labelPane.add(deviceDesc, 1, 0);
        labelPane.add(deviceFavorite,2,0);
        return labelPane;
    }
    private static Label createHeaderNode(String text) {
        Label label = new Label(text);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setStyle("-fx-border-style: solid; -fx-background-color: #62efc3;");
        return label;
    }
}
