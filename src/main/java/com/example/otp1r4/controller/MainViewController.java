package com.example.otp1r4.controller;


import com.example.otp1r4.Main;
import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Controller, Initializable {

    @FXML
    TabPane mainViewTabPane;
    @FXML
    Tab favoriteDevicesTab, allDevicesTab, deviceDataTab, addDevicesTab;
    @FXML
    Button profileButton, logoutButton;

    UserData user = UserData.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader tab1Loader = new FXMLLoader(getClass().getResource("/com/example/otp1r4/favoriteDevicesView.fxml"));
        FXMLLoader tab2Loader = new FXMLLoader(getClass().getResource("/com/example/otp1r4/allDevicesView.fxml"));
        FXMLLoader tab3Loader = new FXMLLoader(getClass().getResource("/com/example/otp1r4/deviceDataView.fxml"));
        FXMLLoader tab4Loader = new FXMLLoader(getClass().getResource("/com/example/otp1r4/addDevice.fxml"));
        try {
            Node tab1Content = tab1Loader.load();
            Node tab2Content = tab2Loader.load();
            Node tab3Content = tab3Loader.load();
            Node tab4Content = tab4Loader.load();

            favoriteDevicesTab.setContent(tab1Content);
            allDevicesTab.setContent(tab2Content);
            deviceDataTab.setContent(tab3Content);
            addDevicesTab.setContent(tab4Content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickLogout() throws IOException {
        user = null;
        ResourceBundle bundle = ResourceBundle.getBundle("TextResources");

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        fxmlLoader.setResources(bundle);
        Scene scene = new Scene(fxmlLoader.load());
        Stage window = (Stage) logoutButton.getScene().getWindow();
        window.setScene(scene);
        window.centerOnScreen();
    }

    public void clickProfile() throws IOException {
        this.addSceneOnTop("profileView.fxml", profileButton);
    }
}
