package com.example.otp1r4;

import com.example.otp1r4.dao.DeviceDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    Button favoriteDeviceAdd;
    @FXML
    VBox favoriteDevicesContainer1;
    @FXML
    VBox favoriteDevicesContainer2;

    Utils u = new Utils();

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


        DeviceController controller;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("favoriteDevice.fxml"));
        Parent deviceNode = loader.load();

        controller = loader.getController();

        if (!dao.getFavoriteDevice("OnniP").isEmpty()) {
            controller.deviceName.setText(dao.getFavoriteDevice("OnniP").get(0));
        }

        favoriteDevicesContainer1.getChildren().add(deviceNode);
        /*
        for (int i = 0; i < 3; i++){
            DeviceController controller;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("favoriteDevice.fxml"));
            Parent deviceNode = loader.load();

            controller = loader.getController();

            controller.deviceName.setText(devicesName[i]);

            favoriteDevicesContainer1.getChildren().add(deviceNode);

        }

        for (int i = 3; i < 6; i++){
            DeviceController controller;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("favoriteDevice.fxml"));
            Parent deviceNode = loader.load();

            controller = loader.getController();

            controller.deviceName.setText(devicesName[i]);

            favoriteDevicesContainer2.getChildren().add(deviceNode);

        }

         */
    }
}
