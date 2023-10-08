package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.ObservableDevices;
import com.example.otp1r4.model.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FavoriteDeviceController implements Controller {
    @FXML
    Label deviceName,sensorType, deviceName1, deviceName2, deviceName3, deviceName4, deviceName5, deviceName6, deviceName7;
    @FXML
    Shape isFavorite, isFavorite1, isFavorite2, isFavorite3, isFavorite4, isFavorite5, isFavorite6, isFavorite7;
    @FXML
    TextArea deviceDesc, deviceDesc1, deviceDesc2, deviceDesc3, deviceDesc4, deviceDesc5, deviceDesc6, deviceDesc7;
    @FXML
    ToggleButton deviceToggleButton;
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

    Shape[] isFavoriteShapes;
    TextField[] timerFields;
    RadioButton[] lightBrightnessButtons;
    RadioButton[] lightTempButtons;
    RadioButton[] dishWasherButtons;
    ComboBox<String>[] washerComboBoxes;
    CheckBox[] washerCheckBoxes;
    CheckBox[] vacuumDays;
    RadioButton[] vacuumPrograms;
    TextArea[] deviceDescs;

    Device device;
    private UserData userData = UserData.getInstance();
    DeviceDAO deviceDAO = new DeviceDAO();

    String controls;

    final int TYPE = 0, SUBTYPE = 1;
    final int lightBRIGHT = 1, lightTEMP = 2, lightSENSOR = 3, lightPOWER = 4;
    final int sensorTYPE = 1;
    final int washerPROG = 2, washerTEMP = 3, washerSPEED = 4, washerEXTRA = 5, washerQUICK = 6, washerNOW = 7, washerTIMER = 8;
    final int dishPROG = 2, dishNOW = 3, dishTIMER = 4;
    final int vacuumPROG = 2, vacuumNOW = 3, vacuumDAY = 4, vacuumDO1 = 5, vacuumDO2 = 6, vacuumCMD = 7;
    final int saunaTEMP = 2, saunaTIME = 3;
    final int LOCK = 2;
    final int CAMERA = 2;

    private ObservableDevices observableDevices = ObservableDevices.getInstance();

    public void initialize() {
        initializeArrays();

        for (Shape isFavoriteNode : isFavoriteShapes) {
            isFavoriteNode.setOnMouseClicked(mouseEvent -> {
                if (isFavoriteNode.getFill().equals(Color.YELLOW)) {
                    isFavoriteNode.setFill(Color.WHITE);
                    deviceDAO.removeFavoriteDevice(userData.getUserID(), device.getDeviceId());
                    device.updateIsDeviceFavorite(false);
                    observableDevices.updateDevice(device);
                }
            });
        }

        for (TextField field : timerFields) {
            field.focusedProperty().addListener((observable, oldValue, newValue) -> {
                String old = "";
                if (!newValue) {
                    String time = field.getText();
                    if (time.matches("^[0-9]{4}")) {
                        if (field == washingTimer) {
                            dataToDatabase(time, washerTIMER);
                        }
                        if (field == dishWashTimer) {
                            dataToDatabase(time, dishTIMER);
                        }
                        if (field == vacuumTimer1) {
                            String[] times = splitControl()[vacuumDO1].split("-");
                            times[0] = field.getText();
                            String timer = times[0] + "-" + times[1];
                            dataToDatabase(timer, vacuumDO1);
                        }
                        if (field == vacuumTimer2) {
                            String[] times = splitControl()[vacuumDO1].split("-");
                            times[1] = field.getText();
                            String timer = times[0] + "-" + times[1];
                            dataToDatabase(timer, vacuumDO1);
                        }
                        if (field == vacuumTimer3) {
                            String[] times = splitControl()[vacuumDO2].split("-");
                            times[0] = field.getText();
                            String timer = times[0] + "-" + times[1];
                            dataToDatabase(timer, vacuumDO2);
                        }
                        if (field == vacuumTimer4) {
                            String[] times = splitControl()[vacuumDO2].split("-");
                            times[1] = field.getText();
                            String timer = times[0] + "-" + times[1];
                            dataToDatabase(timer, vacuumDO2);
                        }
                        if (field == saunaTimer1) {
                            String[] times = splitControl()[saunaTIME].split("\\+");
                            times[0] = field.getText();
                            String timer = times[0] + "+" + times[1];
                            dataToDatabase(timer, saunaTIME);
                        }
                        if (field == saunaTimer2) {
                            String[] times = splitControl()[saunaTIME].split("\\+");
                            times[1] = field.getText();
                            String timer = times[0] + "+" + times[1];
                            dataToDatabase(timer, saunaTIME);
                        }
                    } else {
                        field.setText(old);
                    }
                }
            });

            for (RadioButton button: lightBrightnessButtons) {
                button.setOnAction(event -> {
                    dataToDatabase(button.getText(), lightBRIGHT);
                });
            }

            for (RadioButton button: lightTempButtons) {
                button.setOnAction(event -> {
                    dataToDatabase(button.getText(), lightTEMP);
                });
            }

            for (RadioButton button: dishWasherButtons) {
                button.setOnAction(event -> {
                    dataToDatabase(button.getText(), dishPROG);
                });
            }

            for (ComboBox<String> box : washerComboBoxes) {
                box.setOnAction(event -> {
                    if (box == washingProgram) {
                        dataToDatabase(box.getValue(), washerPROG);
                    }
                    if (box == washingTemp) {
                        dataToDatabase(box.getValue(), washerTEMP);
                    }
                    if (box == washingSpeed) {
                        dataToDatabase(box.getValue(), washerSPEED);
                    }
                });
            }

            for (CheckBox box : washerCheckBoxes) {
                box.setOnAction(event -> {
                    if (box == quickWash) {
                        if (box.isSelected()) {
                            dataToDatabase("On", washerQUICK);
                        }   else {
                            dataToDatabase("Off", washerQUICK);
                        }
                    }
                    if (box == extraWash) {
                        if (box.isSelected()) {
                            dataToDatabase("On", washerEXTRA);
                        }   else {
                            dataToDatabase("Off", washerEXTRA);
                        }
                    }
                    if (box == washNow) {
                        if (box.isSelected()) {
                            dataToDatabase("On", washerNOW);
                        }   else {
                            dataToDatabase("Off", washerNOW);
                        }
                    }
                });
            }


            for (CheckBox box : vacuumDays) {
                box.setOnAction(event -> {
                    String[] week = new String[7];

                    String[] days = splitControl()[vacuumDAY].split("\\+");

                    if (box.isSelected()) {

                    }
                });
            }

            saunaTemp.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    String time = saunaTemp.getText();
                    if (time.matches("^[0-9]{1,3}")) {
                        dataToDatabase(time, saunaTEMP);
                    } else {
                        saunaTemp.setText(splitControl()[saunaTEMP]);
                    }
                }
            });
        }

        deviceToggleButton.setOnAction(event -> {
            if (deviceToggleButton.isSelected()) {
                deviceToggleButton.setText("Päällä");
                deviceToggleButton.setStyle("-fx-background-color: green");
                dataToDatabase("On", lightPOWER);
            }   else {
                deviceToggleButton.setText("POIS");
                deviceToggleButton.setStyle("-fx-background-color: red");
                dataToDatabase("Off", lightPOWER);
            }
        });

        vacuumCommand.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String command = vacuumCommand.getText();
                if (command.matches("^[a-zA-Z ]{3,20}$")) {
                    dataToDatabase(command, vacuumCMD);
                } else {
                    vacuumCommand.setText(splitControl()[vacuumCMD]);
                }
            }
        });

        dishWashNow.setOnAction(event -> {
            if (dishWashNow.isSelected()) {
                dataToDatabase("On", dishNOW);
            } else {
                dataToDatabase("Off", dishNOW);
            }
        });

        lockLocked.setOnAction(event -> {
            if (lockLocked.isSelected()) {
                dataToDatabase("On", LOCK);
            } else {
                dataToDatabase("Off", LOCK);
            }
        });

        cameraOn.setOnAction(event -> {
            if (cameraOn.isSelected()) {
                dataToDatabase("On", CAMERA);
            } else {
                dataToDatabase("Off", CAMERA);
            }
        });

        mvmntSensor.setOnAction(event -> {
            if (mvmntSensor.isSelected()) {
                dataToDatabase("On", lightSENSOR);
            } else {
                dataToDatabase("Off", lightSENSOR);
            }
        });

        vacuumNow.setOnAction(event -> {
            if (vacuumNow.isSelected()) {
                dataToDatabase("On", vacuumNOW);
            } else {
                dataToDatabase("Off", vacuumNOW);
            }
        });

        for (RadioButton button : vacuumPrograms) {
            button.setOnAction(event -> {
                if (button == vacuumProgram) {
                    dataToDatabase("Imurointi", vacuumPROG);
                }
                else {
                    dataToDatabase("Moppaus", vacuumPROG);
                }
            });
        }
    }

    public void dataToDatabase(String data, int where) {
        deviceDAO.updateDeviceControl(device.getDeviceId(), updateControl(data, where));
        device.updateDeviceControl(updateControl(data, where));
    }

    public void initializeArrays() {
        washingProgram.setItems(FXCollections.observableArrayList("Puuvilla", "Hienopesu", "Sport"));
        washingTemp.setItems(FXCollections.observableArrayList("30", "40", "60", "90"));
        washingSpeed.setItems(FXCollections.observableArrayList("0", "600", "800", "1000", "1200"));

        isFavoriteShapes = new Shape[]{isFavorite, isFavorite1, isFavorite2, isFavorite3, isFavorite4, isFavorite5, isFavorite6, isFavorite7};
        timerFields = new TextField[]{washingTimer, dishWashTimer, vacuumTimer1, vacuumTimer2, vacuumTimer3, vacuumTimer4, saunaTimer1, saunaTimer2};
        lightBrightnessButtons = new RadioButton[]{lightBrightness1, lightBrightness2, lightBrightness3, lightBrightness4};
        lightTempButtons = new RadioButton[]{lightColorTemp1, lightColorTemp2, lightColorTemp3};
        dishWasherButtons = new RadioButton[]{dishProgram1, dishProgram2, dishProgram3};
        washerComboBoxes = new ComboBox[]{washingProgram, washingTemp, washingSpeed};
        washerCheckBoxes = new CheckBox[]{quickWash, extraWash, washNow};
        vacuumDays = new CheckBox[]{vacuumDay1, vacuumDay2, vacuumDay3, vacuumDay4, vacuumDay5, vacuumDay6, vacuumDay7};
        deviceDescs = new TextArea[]{deviceDesc, deviceDesc1, deviceDesc2, deviceDesc3, deviceDesc4, deviceDesc5, deviceDesc6, deviceDesc7};
        vacuumPrograms = new RadioButton[]{vacuumProgram, vacuumProgram1};
    }

    public void setDevice(Device device) {
        this.device = device;
        setUp();
    }

    public void setUp() {
        setDeviceType();
        setDeviceName();
        setDeviceDesc();
        setDeviceControl();
    }

    public void setDeviceName() {
        deviceName.setText(device.getDeviceName());
        deviceName1.setText(device.getDeviceName());
        deviceName2.setText(device.getDeviceName());
        deviceName3.setText(device.getDeviceName());
        deviceName4.setText(device.getDeviceName());
        deviceName5.setText(device.getDeviceName());
        deviceName6.setText(device.getDeviceName());
        deviceName7.setText(device.getDeviceName());
    }

    public void setDeviceType() {
        String[] control = splitControl();

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
            case "Valaisin":
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
            case "Sensori":
                deviceSensor.setVisible(true);
                deviceSensor.setDisable(false);
                break;
        }
    }

    public void setDeviceDesc() {
        for (TextArea area : deviceDescs) {
            area.setText(device.getDeviceDesc());
        }
    }

    public void setDeviceControl() {
        switch (splitControl()[TYPE]) {
            case "Valaisin":
                setUpLighting(splitControl());
                break;
            case "Sensori":
                setUpSensor(splitControl());
                break;
            case "Laite":
                setUpAppliance(splitControl());
                break;
        }
    }

    public void setUpLighting(String[] controls) {
        if (controls[lightPOWER].equals("On")) {
            deviceToggleButton.setSelected(true);
            deviceToggleButton.setText("Päällä");
            deviceToggleButton.setStyle("-fx-background-color: green");
        }   else {
            deviceToggleButton.setSelected(false);
            deviceToggleButton.setText("POIS");
            deviceToggleButton.setStyle("-fx-background-color: red");
        }

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
                    String[] time = controls[vacuumDO1].split("-");
                    vacuumTimer1.setText(time[0]);
                    vacuumTimer2.setText(time[1]);
                }
                if (!controls[vacuumDO2].equals("-1")) {
                    String[] time = controls[vacuumDO2].split("-");
                    vacuumTimer3.setText(time[0]);
                    vacuumTimer4.setText(time[1]);
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
        sensorType.setText(controls[sensorTYPE]);
    }

    public String updateControl(String s, int i) {
        controls = device.getDeviceControl();
        String[] splitControls = splitControl();
        if (i >= 0 && i < splitControls.length) {
            splitControls[i] = s;
            String updatedControls = String.join(";", splitControls);
            return updatedControls;
        } else {
            return controls;
        }
    }

    public String[] splitControl() {
        controls = device.getDeviceControl();
        return controls.split(";");
    }
}
