package com.example.otp1r4.controller;

import com.example.otp1r4.Main;
import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.dao.UserDAO;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.ObservableDevices;
import com.example.otp1r4.model.UserData;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FavoriteDevicesController implements Controller, Initializable {
    @FXML
    Label favDevicesWarningLabel;
    @FXML
    AnchorPane favDevicesAnchorPane;
    @FXML
    GridPane favoriteDevicesGridPane;
    UserDAO userDAO;
    DeviceDAO deviceDAO;
    ObservableDevices observableDevice = ObservableDevices.getInstance();

    public FavoriteDevicesController() {
        this.userDAO = new UserDAO();
        this.deviceDAO = new DeviceDAO();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableDevice.getObservableDevices().addListener((ListChangeListener<Device>) change -> {
            while (change.next()) {
                if (change.wasAdded()){
                    try {
                        addFavoriteDevice();
                    } catch (IOException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        observableDevice.getObservableDevices().forEach(device -> {
            device.isDeviceFavorite().addListener((observable, oldValue, newValue) -> {
                try {
                    if (newValue) {
                        addFavoriteDevice();
                    } else {
                        addFavoriteDevice();
                    }
                } catch (IOException | SQLException e) {
                        throw new RuntimeException(e);
                }
            });
        });
        try {
            addFavoriteDevice();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFavoriteDevice() throws IOException, SQLException {
        favoriteDevicesGridPane.getChildren().clear();
        List<Device> devices = new ArrayList<>();
        for (Device device : observableDevice.getObservableDevices()) {
            if (device.isDeviceFavorite().get()) {
                devices.add(device);
            }
        }
        FavoriteDeviceController controller;
        Parent deviceNode;

        int column = 0;
        int row = 0;
        if (!devices.isEmpty()) {
            for (Device device : devices) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("favoriteDevice.fxml"));
                deviceNode = loader.load();
                controller = loader.getController();
                controller.setDevice(device);

                favoriteDevicesGridPane.add(deviceNode, column, row);

                column++;
                if (column == 3) {
                    favoriteDevicesGridPane.getRowConstraints().add(new RowConstraints());
                    row++;
                    column = 0;
                }
            }
        }
    }
}
