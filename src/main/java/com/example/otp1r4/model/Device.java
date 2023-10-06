package com.example.otp1r4.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Device implements Model{
    int deviceId;
    String deviceName;
    String deviceDesc;
    BooleanProperty deviceFavorite = new SimpleBooleanProperty();
    String deviceControl;
    String format;
    String unit;

    public Device(int deviceId, String deviceName, String deviceDesc, boolean deviceFavorite, String deviceControl, String format, String unit) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceDesc = deviceDesc;
        this.deviceFavorite.set(deviceFavorite);
        this.deviceControl = deviceControl;
        this.format = format;
        this.unit = unit;
    }
    public String getDeviceName() {
        return deviceName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public BooleanProperty isDeviceFavorite() {
        return deviceFavorite;
    }

    public String getDeviceControl() {
        return deviceControl;
    }

    public String getFormat() {
        return format;
    }

    public String getUnit() {
        return unit;
    }

    public void updateDeviceControl(String deviceControl) {
        this.deviceControl = deviceControl;
    }

    public void updateIsDeviceFavorite(boolean deviceFavorite) {
        this.deviceFavorite.set(deviceFavorite);
    }
}
