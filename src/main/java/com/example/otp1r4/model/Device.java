package com.example.otp1r4.model;

public class Device {
    private int deviceId;
    private String deviceName;
    private String deviceType;

    public Device (int id, String name, String type) {
        deviceId = id;
        deviceName = name;
        deviceType = type;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }
}
