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


    public List<Device> getDevices(String name) throws SQLException {
        List<Device> deviceList = new ArrayList<>();

        String sql = "SELECT Devices.DeviceID, Devices.DeviceName, Devices.DeviceType " +
                "FROM Devices " +
                "left JOIN users on users.UserID = Devices.UserID " +
                "WHERE users.Name = ?";

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
    public void addFavoriteDevices (String userID, String deviceID) {
        try {
            String sql = "UPDATE Ownership SET Favorite = 1 WHERE UserID = ? AND DeviceID = ?";

            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,userID);
            prepStat.setString(2,deviceID);

            prepStat.executeUpdate();
        }   catch (SQLException e) {
            e.printStackTrace();
        }
    }
   public List<Device> getFavoriteDevices(String name) throws SQLException {
       List<Device> favDevices = new ArrayList<>();

       String sql = "SELECT Devices.DeviceID, Devices.DeviceName, Devices.DeviceType " +
               "FROM Devices " +
               "left JOIN users on users.UserID = Devices.UserID " +
               "WHERE users.Name = ? and Devices.IsFavorite = 1";

       prepStat = conn.prepareStatement(sql);
       prepStat.setString(1,name);

       ResultSet rs = prepStat.executeQuery();

       while (rs.next()) {
           String deviceId = rs.getString("DeviceID");
           String deviceName = rs.getString("Name");
           String deviceType = rs.getString("typename");

           Device device = new Device(deviceId, deviceName, deviceType);
           favDevices.add(device);
       }

       return favDevices;
   }

   public String getDeviceData(String deviceID) throws SQLException {
        // TODO: fix the query
        String sql = "SELECT Data FROM Devicedata WHERE DeviceID = ?";

        prepStat = conn.prepareStatement(sql);
        prepStat.setString(1,deviceID);

        ResultSet resultSet = prepStat.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("Data");
        }
        return null;
   }

    public void addDevice(String dName, String dDesc, boolean isFav, int userId, String dControl, String dFormat, String dUnit) throws SQLException {
        try {
        String sql = "INSERT INTO `Devices`(`DeviceName`,`DeviceDesc`, `IsFavorite`, `UserID`, `DeviceControl`, `Format`, `Unit`) VALUES (?,?,?,?,?,?,?)";
        prepStat = conn.prepareStatement(sql);
        prepStat.setString(1,dName);
        prepStat.setString(2,dDesc);
        prepStat.setBoolean(3,isFav);
        prepStat.setInt(4,userId);
        prepStat.setString(5, dControl);
        prepStat.setString(6, dFormat);
        prepStat.setString(7, dUnit);

        prepStat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
