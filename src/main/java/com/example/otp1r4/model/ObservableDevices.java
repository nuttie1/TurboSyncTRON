package com.example.otp1r4.model;

import com.example.otp1r4.dao.DeviceDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ObservableDevices implements Model{
    private static ObservableDevices instance;
    private ObservableList<Device> observableDevices;

    private ObservableDevices() {
    }

    public static void setInstance(ObservableDevices instance) {
        ObservableDevices.instance = instance;
    }

    public static synchronized ObservableDevices getInstance() {
        if (instance == null) {
            instance = new ObservableDevices();
        }
        return instance;
    }

    public void setObservableList(List<Device> devices) {
        observableDevices = FXCollections.observableArrayList(devices);
    }

    public ObservableList<Device> getObservableDevices() {
        return observableDevices;
    }

    public void updateDevice(Device device) {
        int index = observableDevices.indexOf(device);
        if (index >= 0) {
            observableDevices.set(index, device);
        }
    }

    public void addDevice(Device device) {
        observableDevices.add(device);
    }
}
