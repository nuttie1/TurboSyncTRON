package com.example.otp1r4;

public class Device {
    String deviceId;
    String deviceName;
    String deviceType;

    public Device (String id, String name, String type) {
        deviceId = id;
        deviceName = name;
        deviceType = type;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }
}
