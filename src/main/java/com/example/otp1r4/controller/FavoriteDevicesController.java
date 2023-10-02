package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.dao.UserDAO;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FavoriteDevicesController implements Controller, Initializable {
    @FXML
    Button favoriteDeviceAdd;
    @FXML
    GridPane favDevicesGridPane;
    @FXML
    Label favDevicesWarningLabel;

    UserDAO userDAO;
    DeviceDAO deviceDAO;

    public FavoriteDevicesController() {
        this.userDAO = new UserDAO();
        this.deviceDAO = new DeviceDAO();
    }

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
        this.addSceneOnTop("chooseFavoriteDevices.fxml", favoriteDeviceAdd);
    }

    public void addFavoriteDevice() throws IOException, SQLException {
        List<Device> devices = deviceDAO.getFavoriteDevices(user.getUserID());
        // DeviceController controller;

        if (!devices.isEmpty()) {
            favDevicesGridPane.setVisible(true);
            int column = 0;
            int row = 0;
            for (Device device : devices) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("favoriteDevice.fxml"));
                Parent deviceNode = loader.load();

               /* controller = loader.getController();

                controller.deviceName.setText(device.getDeviceName());
                controller.deviceDataLabel.setText(dao.getDeviceData(device.deviceId));
*/
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
