package com.example.otp1r4.dao;

import com.example.otp1r4.Device;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeviceDAO implements DAO{
    Connection conn = JDBCConnection.connection;
    PreparedStatement prepStat = JDBCConnection.preparedStatement;
    @Override
    public boolean authenticate(String email, String password) throws Exception {
        return false;
    }

    public List<Device> getDevices(String name) throws SQLException {
        List<Device> deviceList = new ArrayList<>();

        String sql = "SELECT Devices.Name FROM users INNER JOIN Ownership ON users.UserID = Ownership.UserID " +
                "INNER JOIN Devices ON Ownership.DeviceID = Devices.DeviceID " +
                "INNER JOIN Devicetype ON Devices.devicetypeID = Devicetype.devicetypeid " +
                "WHERE users.Name = ? ";

        prepStat = conn.prepareStatement(sql);
        prepStat.setString(1,name);

        ResultSet rs = prepStat.executeQuery();

        while (rs.next()) {
            String deviceId = rs.getString("DeviceID");
            String deviceName = rs.getString("Name");
            String deviceType = rs.getString("typename");

            Device device = new Device(deviceId, deviceName, deviceType);
            deviceList.add(device);
        }

        return deviceList;
    }

   public List<String> getFavoriteDevice(String name) throws SQLException {
       List<String> deviceNames = new ArrayList<>();

        String sql = "SELECT Devices.Name FROM users INNER JOIN Ownership ON users.UserID = Ownership.UserID " +
                "INNER JOIN Devices ON Ownership.DeviceID = Devices.DeviceID " +
                "WHERE users.Name = ? " +
                "AND Ownership.Favorite = 1";

        prepStat = conn.prepareStatement(sql);
        prepStat.setString(1,name);

        ResultSet rs = prepStat.executeQuery();

        while (rs.next()) {
            String deviceName = rs.getString("Name");
            deviceNames.add(deviceName);
        }

        return deviceNames;
   }

}
