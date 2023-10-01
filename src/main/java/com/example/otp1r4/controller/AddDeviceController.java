package com.example.otp1r4.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddDeviceController {

    @FXML
    private ComboBox<String> deviceType;
    @FXML
    private TextField deviceName;
    @FXML
    private TextArea deviceDescription;
    @FXML
    private Label deviceDescriptionLabel, deviceLabel;
    @FXML
    private RadioButton lampOn, lampOff;

    public void initialize() {
        deviceType.setItems(FXCollections.observableArrayList( "Kodinkone", "Lamppu", "x-sensori", "y-sensori"));
        deviceDescriptionLabel.setText("Laitteen kuvaus:\n(valinnainen)");
        ToggleGroup toggleGroup = new ToggleGroup();
        lampOn.setToggleGroup(toggleGroup);
        lampOff.setToggleGroup(toggleGroup);
    }

    public void typeChanged() {
        lampOn.setVisible(false);
        lampOff.setVisible(false);
        if(deviceType.getValue().equals("Kodinkone")) {
            deviceLabel.setText("Kodinkone:");
            deviceLabel.setVisible(true);
        } else if(deviceType.getValue().equals("Lamppu")) {
            deviceLabel.setText("Lamppu:");
            deviceLabel.setVisible(true);
            lampOn.setVisible(true);
            lampOff.setVisible(true);
        } else if(deviceType.getValue().equals("x-sensori")) {
            deviceLabel.setText("x-sensori:");
            deviceLabel.setVisible(true);
        } else if(deviceType.getValue().equals("y-sensori")) {
            deviceLabel.setText("y-sensori:");
            deviceLabel.setVisible(true);
        }

    }

    public void addDeviceButton() {
        deviceName.getText();
        deviceType.getValue();
        deviceDescription.getText();

        // SQL LAUSE
    }

}