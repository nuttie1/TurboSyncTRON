package com.example.otp1r4;

import com.example.otp1r4.dao.DeviceDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseFavoriteController implements Initializable {
    Utils u = new Utils();

    @FXML
    Button favoritesSaveButton;
    @FXML
    Button backButton;
    @FXML
    ListView<String> devicesList;
    @FXML
    Label devicesWarning;
    DeviceDAO dao = new DeviceDAO();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showDevices();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void backButtonClicked (ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    public void saveFavoriteDevices (ActionEvent event) throws IOException {
        Stage stage = (Stage) favoritesSaveButton.getScene().getWindow();
        stage.close();

        // Tallenna laitteet..
    }
    public void showDevices() throws SQLException {
        List<Device> devices;
        devices = dao.getDevices("OnniP");

        if (devices.isEmpty()) {
            devicesWarning.setText("Laitteita ei vielä lisätty.");
        }

        List<String> deviceNames = new ArrayList<>();
        if (!devices.isEmpty()) {
            for (Device device : devices) {
                deviceNames.add(device.getDeviceName());
            }
            devicesList.getItems().addAll(deviceNames);
        }
    }

}
