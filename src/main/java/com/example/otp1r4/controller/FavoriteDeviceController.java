package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.ObservableDevices;
import com.example.otp1r4.model.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class FavoriteDeviceController implements Controller {
    @FXML
    Label deviceName,sensorType, deviceName1, deviceName2, deviceName3, deviceName4, deviceName5, deviceName6, deviceName7;
    @FXML
    Shape isFavorite, isFavorite1, isFavorite2, isFavorite3, isFavorite4, isFavorite5, isFavorite6, isFavorite7;
    @FXML
    TextArea deviceDesc, deviceDesc1, deviceDesc2, deviceDesc3, deviceDesc4, deviceDesc5, deviceDesc6, deviceDesc7;
    @FXML
    ToggleButton deviceToggleButton, deviceToggleButton1;
    @FXML
    TextField washingTimer, dishWashTimer, vacuumTimer1, vacuumTimer2, vacuumTimer3, vacuumTimer4, vacuumCommand, saunaTimer1, saunaTimer2, saunaTemp;

    @FXML
    RadioButton lightBrightness1, lightBrightness2, lightBrightness3, lightBrightness4, vacuumProgram1, vacuumProgram;
    @FXML
    RadioButton lightColorTemp1, lightColorTemp2, lightColorTemp3, dishProgram1, dishProgram2, dishProgram3;
    @FXML
    CheckBox mvmntSensor, quickWash, extraWash, washNow, dishWashNow, vacuumNow, vacuumDay1, vacuumDay2, vacuumDay3, vacuumDay4, vacuumDay5, vacuumDay6, vacuumDay7, lockLocked, cameraOn;

    @FXML
    AnchorPane deviceLight, deviceSensor, deviceWasher, deviceDishwasher, deviceVacuum, deviceSauna, deviceLock, deviceCamera;
    @FXML
    ComboBox<String> washingProgram, washingTemp, washingSpeed;


    Device device;
    private UserData userData = UserData.getInstance();
    DeviceDAO deviceDAO = new DeviceDAO();

    String controls;

    final int TYPE = 0, POWER = 1, SUBTYPE = 1;
    final int lightBRIGHT = 1, lightTEMP = 2, lightSENSOR = 3, lightPOWER = 4;
    final int sensorTYPE = 1, sensorPOWER = 2;
    final int washerPROG = 2, washerTEMP = 3, washerSPEED = 4, washerEXTRA = 5, washerQUICK = 6, washerNOW = 7, washerTIMER = 8;
    final int dishPROG = 2, dishNOW = 3, dishTIMER = 4;
    final int vacuumPROG = 2, vacuumNOW = 3, vacuumDAY = 4, vacuumDO1 = 5, vacuumDO2 = 6, vacuumCMD = 7;
    final int saunaTEMP = 2, saunaTIME = 3;
    final int LOCK = 2;
    final int CAMERA = 2;

    private ObservableDevices observableDevices = ObservableDevices.getInstance();


    public void initialize() {
        washingProgram.setItems(FXCollections.observableArrayList("Puuvilla", "Hienopesu", "Sport"));
        washingTemp.setItems(FXCollections.observableArrayList("30", "40", "60", "90"));
        washingSpeed.setItems(FXCollections.observableArrayList("0", "600", "800", "1000", "1200"));



        isFavorite.setOnMouseClicked(mouseEvent -> {
            if (isFavorite.getFill().equals(Color.YELLOW)) {
                isFavorite.setFill(Color.WHITE);
                //deviceDAO.removeFavoriteDevice(userData.getUserID(), device.getDeviceId());
                device.updateIsDeviceFavorite(false);
                observableDevices.updateDevice(device);
            } else {
                isFavorite.setFill(Color.YELLOW);
            }
        });

        deviceToggleButton.setOnMouseClicked(mouseEvent -> {
            if (deviceToggleButton.isSelected()) {
                deviceToggleButton.setText("Päällä");
                deviceToggleButton.setStyle("-fx-background-color: green");
                deviceDAO.updateDeviceControl(device.getDeviceId(), updateControl("On",POWER));
                device.updateDeviceControl(updateControl("On",POWER));
                observableDevices.addDevice(device);
            }   else {
                deviceToggleButton.setText("POIS");
                deviceToggleButton.setStyle("-fx-background-color: red");
                deviceDAO.updateDeviceControl(device.getDeviceId(), updateControl("Off",POWER));
                device.updateDeviceControl(updateControl("Off",POWER));
            }
        });
    }

    public void setDevice(Device device) {
        this.device = device;

        setDeviceType();
        setDeviceName();
        setDeviceDesc();
        setDeviceControl();
    }

    public void setDeviceName() {
        deviceName.setText(device.getDeviceName());
        deviceName1.setText(device.getDeviceName());
        deviceName2.setText(device.getDeviceName());
    }

    public void setDeviceType() {
        // Assuming splitControl() returns an array and TYPE and SUBTYPE are constants or variables representing indices
        String[] control = splitControl();

        // First, set all controls to be invisible and disabled
        deviceLight.setVisible(false);
        deviceLight.setDisable(true);
        deviceWasher.setVisible(false);
        deviceWasher.setDisable(true);
        deviceDishwasher.setVisible(false);
        deviceDishwasher.setDisable(true);
        deviceVacuum.setVisible(false);
        deviceVacuum.setDisable(true);
        deviceSauna.setVisible(false);
        deviceSauna.setDisable(true);
        deviceLock.setVisible(false);
        deviceLock.setDisable(true);
        deviceCamera.setVisible(false);
        deviceCamera.setDisable(true);
        deviceSensor.setVisible(false);
        deviceSensor.setDisable(true);

        switch (control[TYPE]) {
            case "Lighting":
                deviceLight.setVisible(true);
                deviceLight.setDisable(false);
                break;
            case "Laite":
                switch (control[SUBTYPE]) {
                    case "Pesukone":
                        deviceWasher.setVisible(true);
                        deviceWasher.setDisable(false);
                        break;
                    case "Astianpesukone":
                        deviceDishwasher.setVisible(true);
                        deviceDishwasher.setDisable(false);
                        break;
                    case "Imuri":
                        deviceVacuum.setVisible(true);
                        deviceVacuum.setDisable(false);
                        break;
                    case "Sauna":
                        deviceSauna.setVisible(true);
                        deviceSauna.setDisable(false);
                        break;
                    case "Lukko":
                        deviceLock.setVisible(true);
                        deviceLock.setDisable(false);
                        break;
                    case "Kamera":
                        deviceCamera.setVisible(true);
                        deviceCamera.setDisable(false);
                        break;
                }
                break;
            case "Sensor":
                deviceSensor.setVisible(true);
                deviceSensor.setDisable(false);
                break;
        }
    }

    public void setDeviceDesc() {
        if (!device.getDeviceDesc().isEmpty()) {
            deviceDesc.setText(device.getDeviceDesc());
        }
    }

    public void setDeviceControl() {
        switch (splitControl()[TYPE]) {
            case "Lighting":
                setUpLighting(splitControl());
                break;
            case "Sensor":
                setUpSensor(splitControl());
                break;
            case "Laite":
                setUpAppliance(splitControl());
                break;
        }
    }

    public void setUpLighting(String[] controls) {
        setUpButton(controls, controls[TYPE]);

        mvmntSensor.setSelected(controls[lightSENSOR].equals("On"));

        switch (controls[lightTEMP]) {
            case "Cold":
                lightColorTemp1.setSelected(true);
                break;
            case "Neutral":
                lightColorTemp2.setSelected(true);
                break;
            case "Warm":
                lightColorTemp3.setSelected(true);
                break;
        }

        switch (controls[lightBRIGHT]) {
            case "1":
                lightBrightness1.setSelected(true);
                break;
            case "2":
                lightBrightness2.setSelected(true);
                break;
            case "3":
                lightBrightness3.setSelected(true);
                break;
            case "4":
                lightBrightness4.setSelected(true);
                break;
        }
    }

    public void setUpAppliance(String[] controls) {
        switch (controls[SUBTYPE]) {
            case "Pesukone":
                washingProgram.setPromptText(controls[washerPROG]);
                washingTemp.setPromptText(controls[washerTEMP]);
                washingSpeed.setPromptText(controls[washerSPEED]);
                if (controls[washerEXTRA].equals("On")) {
                    extraWash.setSelected(true);
                }
                if (controls[washerQUICK].equals("On")) {
                    quickWash.setSelected(true);
                }
                if (controls[washerNOW].equals("On")) {
                    washNow.setSelected(true);
                }
                if (!controls[washerTIMER].equals("-1")) {
                    washingTimer.setText(controls[washerTIMER]);
                }
                break;
            case "Astianpesukone":
                if (controls[dishPROG].equals("Eko")) {
                    dishProgram1.setSelected(true);
                }
                if (controls[dishPROG].equals("Pika")) {
                    dishProgram2.setSelected(true);
                }
                if (controls[dishPROG].equals("Teho")) {
                    dishProgram3.setSelected(true);
                }
                if (controls[dishNOW].equals("On")) {
                    dishWashNow.setSelected(true);
                }
                if (!controls[dishTIMER].equals("-1")) {
                    dishWashTimer.setText(controls[dishTIMER]);
                }
                break;
            case "Imuri":
                if (controls[vacuumPROG].equals("Imurointi")) {
                    vacuumProgram.setSelected(true);
                }   else {
                    vacuumProgram1.setSelected(true);
                }
                if (controls[vacuumNOW].equals("On")) {
                    vacuumNow.setSelected(true);
                }
                String[] days = controls[vacuumDAY].split("\\+");
                for (String day : days) {
                    switch (day) {
                        case "MA":
                            vacuumDay1.setSelected(true);
                            break;
                        case "TI":
                            vacuumDay2.setSelected(true);
                            break;
                        case "KE":
                            vacuumDay3.setSelected(true);
                            break;
                        case "TO":
                            vacuumDay4.setSelected(true);
                            break;
                        case "PE":
                            vacuumDay5.setSelected(true);
                            break;
                        case "LA":
                            vacuumDay6.setSelected(true);
                            break;
                        case "SU":
                            vacuumDay7.setSelected(true);
                            break;
                    }
                }
                if (!controls[vacuumDO1].equals("-1")) {
                    vacuumTimer1.setText(controls[vacuumDO1]);
                    //vacuumTimer2.setText(time[1]);
                }
                if (!controls[vacuumDO2].equals("-1")) {
                    vacuumTimer3.setText(controls[vacuumDO2]);
                    //vacuumTimer4.setText(time[1]);
                }
                if (!controls[vacuumCMD].isEmpty()) {
                    vacuumCommand.setText(controls[vacuumCMD]);
                }
                break;
            case "Sauna":
                if (!controls[saunaTEMP].isEmpty()) {
                    saunaTemp.setText(controls[saunaTEMP]);
                }   else {
                    saunaTemp.setText("0");
                }
                if (!controls[saunaTIME].isEmpty()) {
                    String[] time = controls[saunaTIME].split("\\+");
                    saunaTimer1.setText(time[0]);
                    saunaTimer2.setText(time[1]);
                }
                break;
            case "Lukko":
                if (controls[LOCK].equals("On")) {
                    lockLocked.setSelected(true);
                } else {
                    lockLocked.setSelected(false);
                }
                break;
            case "Kamera":
                if (controls[CAMERA].equals("On")) {
                    cameraOn.setSelected(true);
                } else {
                    cameraOn.setSelected(false);
                }
        }
    }

    public void setUpSensor(String[] controls) {
        setUpButton(controls, controls[TYPE]);
        sensorType.setText(controls[sensorTYPE]);

    }

    public void setUpButton(String[] controls, String type) {
        if (type.equals("Lighting")) {
            if (controls[lightPOWER].equals("On")) {
                deviceToggleButton.setSelected(true);
                deviceToggleButton.setText("Päällä");
                deviceToggleButton.setStyle("-fx-background-color: green");
            }   else {
                deviceToggleButton.setSelected(false);
                deviceToggleButton.setText("POIS");
                deviceToggleButton.setStyle("-fx-background-color: red");
            }
        }
        if (type.equals("Sensor")) {
            if (controls[sensorPOWER].equals("On")) {
                deviceToggleButton.setSelected(true);
                deviceToggleButton.setText("Päällä");
                deviceToggleButton.setStyle("-fx-background-color: green");
            }   else {
                deviceToggleButton.setSelected(false);
                deviceToggleButton.setText("POIS");
                deviceToggleButton.setStyle("-fx-background-color: red");
            }
        }
    }

    public String updateControl(String s, int i) {
        controls = device.getDeviceControl();
        return controls.replace(splitControl()[i], s);
    }

    public String[] splitControl() {
        controls = device.getDeviceControl();
        return controls.split(";");
    }
}
