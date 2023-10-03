package com.example.otp1r4.model;

public class Device implements Model{
    String deviceId;
    String deviceName;
    String deviceDesc;
    boolean deviceFavorite;
    String deviceControl;
    String format;
    String unit;

    public Device(String deviceId, String deviceName, String deviceDesc, boolean deviceFavorite, String deviceControl, String format, String unit) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceDesc = deviceDesc;
        this.deviceFavorite = deviceFavorite;
        this.deviceControl = deviceControl;
        this.format = format;
        this.unit = unit;
    }
    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public boolean isDeviceFavorite() {
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
}
