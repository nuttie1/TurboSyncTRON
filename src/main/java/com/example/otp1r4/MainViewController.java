package com.example.otp1r4;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.dao.SignDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    Button favoriteDeviceAdd;
    @FXML
    GridPane favDevicesGridPane;
    @FXML
    Label favDevicesWarningLabel;

    Utils u = new Utils();

    UserData user = UserData.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addFavoriteDevice();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickFavoriteAdd(ActionEvent event) throws IOException {
        u.addSceneOnTop("chooseFavoriteDevices.fxml", favoriteDeviceAdd);
    }

    public void addFavoriteDevice() throws IOException, SQLException {
        DeviceDAO dao = new DeviceDAO();
        List<Device> devices = dao.getFavoriteDevices(user.getUsername());
        DeviceController controller;

        if (!devices.isEmpty()) {
            favDevicesGridPane.setVisible(true);
            int column = 0;
            int row = 0;
            for (Device device : devices) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("favoriteDevice.fxml"));
                Parent deviceNode = loader.load();

                controller = loader.getController();

                controller.deviceName.setText(device.getDeviceName());
                controller.deviceDataLabel.setText(dao.getDeviceData(device.deviceId));

                favDevicesGridPane.add(deviceNode, column, row);
                column++;
                if (column == 2) {
                    column = 0;
                    row++;
                }
            }
        } else {
            favDevicesGridPane.setVisible(false);
        }
    }
}
