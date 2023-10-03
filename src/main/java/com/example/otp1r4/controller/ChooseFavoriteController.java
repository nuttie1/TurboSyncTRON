package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.dao.UserDAO;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseFavoriteController implements Controller, Initializable {


    @FXML
    Button favoritesSaveButton;
    @FXML
    Button backButton;
    @FXML
    ListView<Device> devicesList;
    @FXML
    Label devicesWarningLabel;

    ObservableList<Device> selectedDevices = FXCollections.observableArrayList();
    DeviceDAO deviceDAO;
    UserDAO userDAO;
    UserData user;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.deviceDAO = new DeviceDAO();
        this.userDAO = new UserDAO();

        this.user = UserData.getInstance();;
        try {
            showDevices();
            devicesList.setCellFactory(new Callback<ListView<Device>, ListCell<Device>>() {
                @Override
                public ListCell<Device> call(ListView<Device> deviceListView) {
                    return new ListCell<Device>() {
                        @Override
                        protected void updateItem(Device device, boolean empty) {
                            super.updateItem(device, empty);
                            if (device != null && !empty) {
                                setText(device.getDeviceName());
                            } else {
                                setText(null);
                            }
                        }
                    };
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void backButtonClicked (ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    public void saveFavoriteDevices (ActionEvent event) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Virhe");
        if (devicesList.getItems().size() >= 6) {
            alert.setContentText("Liikaa laitteita valittu");
            alert.show();
        }   else if (devicesList.getSelectionModel().isEmpty()) {
            alert.setContentText("Pitää valita jokin laite.");
            alert.show();
        } else {
            for (Device device : selectedDevices) {
                deviceDAO.addFavoriteDevices(user.getUsername(), device.getDeviceId());
            }
        }
    }
    public void showDevices() throws SQLException {
        List<Device> devices;
        devices = deviceDAO.getDevices(user.getUsername());

        if (devices.isEmpty()) {
            devicesWarningLabel.setText("Laitteita ei vielä lisätty.");
        }

        if (!devices.isEmpty()) {
            devicesList.getItems().addAll(devices);
        }

        devicesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedDevices.add(newValue);
            }
        });
    }

}
