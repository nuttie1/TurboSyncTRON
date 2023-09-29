package com.example.otp1r4.dao;

import com.example.otp1r4.model.Device;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeviceDAO implements DAO{
    PreparedStatement prepStat = JDBCConnection.preparedStatement;


    public List<Device> getDevices(int userID) throws SQLException {
        List<Device> deviceList = new ArrayList<>();

        String sql = "SELECT Devices.Name FROM users INNER JOIN Ownership ON users.UserID = Ownership.UserID " +
                "INNER JOIN Devices ON Ownership.DeviceID = Devices.DeviceID " +
                "INNER JOIN Devicetype ON Devices.devicetypeID = Devicetype.devicetypeid " +
                "WHERE users.UserID = ? ";

        prepStat = conn.prepareStatement(sql);
        prepStat.setInt(1,userID);

        ResultSet rs = prepStat.executeQuery();

        while (rs.next()) {
            int deviceId = rs.getInt("DeviceID");
            String deviceName = rs.getString("Name");
            String deviceType = rs.getString("typename");

            Device device = new Device(deviceId, deviceName, deviceType);
            deviceList.add(device);
        }

        return deviceList;
    }
    public void addFavoriteDevices (int userID, int deviceID) {
        try {
            String sql = "UPDATE Ownership SET Favorite = 1 WHERE UserID = ? AND DeviceID = ?";

            prepStat = conn.prepareStatement(sql);
            prepStat.setInt(1,userID);
            prepStat.setInt(2,deviceID);

            prepStat.executeUpdate();
        }   catch (SQLException e) {
            e.printStackTrace();
        }
    }
   public List<Device> getFavoriteDevices(int userID) throws SQLException {
       List<Device> favDevices = new ArrayList<>();

       String sql = "SELECT Devices.Name " +
                    "FROM Ownership " +
                    "INNER JOIN Devices ON Ownership.DeviceID = Devices.DeviceID " +
                    "INNER JOIN Devicetype ON Devices.devicetypeID = Devicetype.devicetypeid " +
                    "WHERE Ownership.UserID = ? " +
                    "AND Ownership.Favorite = 1";

       prepStat = conn.prepareStatement(sql);
       prepStat.setInt(1,userID);

       ResultSet rs = prepStat.executeQuery();

       while (rs.next()) {
           int deviceId = rs.getInt("DeviceID");
           String deviceName = rs.getString("Name");
           String deviceType = rs.getString("typename");

           Device device = new Device(deviceId, deviceName, deviceType);
           favDevices.add(device);
       }

       return favDevices;
   }

   public String getDeviceData(int deviceID) throws SQLException {
        String sql = "SELECT Data FROM Devicedata WHERE DeviceID = ?";

        prepStat = conn.prepareStatement(sql);
        prepStat.setInt(1,deviceID);

        ResultSet resultSet = prepStat.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("Data");
        }
        return null;
   }
}
