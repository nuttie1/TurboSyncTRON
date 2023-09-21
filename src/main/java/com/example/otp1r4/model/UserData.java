package com.example.otp1r4.model;

import java.util.List;

public class UserData {
    private String username;
    private int userID;
    private List<Device> favoriteDevices;

    private static UserData instance;




    public static void setInstance(UserData instance) {
        UserData.instance = instance;
    }

    private UserData() {}

    public static synchronized UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<Device> getFavoriteDevices() {
        return favoriteDevices;
    }

    public void setFavoriteDevices(List<Device> favoriteDevices) {
        this.favoriteDevices = favoriteDevices;
    }
    public void addFavoriteDevice(Device device){
        this.favoriteDevices.add(device);
    }
}
