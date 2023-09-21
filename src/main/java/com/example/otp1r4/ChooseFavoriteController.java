package com.example.otp1r4;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.dao.SignDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    ListView<Device> devicesList;
    @FXML
    Label devicesWarningLabel;

    ObservableList<Device> selectedDevices = FXCollections.observableArrayList();
    DeviceDAO dao = new DeviceDAO();
    SignDAO signDAO = new SignDAO();
    UserData user = UserData.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                dao.addFavoriteDevices(signDAO.getUserID(user.getUsername()), device.getDeviceId());
            }

            MainViewController controller = new MainViewController();
            controller.addFavoriteDevice();

            Stage stage = (Stage) favoritesSaveButton.getScene().getWindow();
            stage.close();
        }
    }
    public void showDevices() throws SQLException {
        List<Device> devices;
        devices = dao.getDevices(user.getUsername());

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
