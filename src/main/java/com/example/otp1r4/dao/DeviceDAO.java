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

        String sql = "SELECT Devices.DeviceID, Devices.DeviceName, Devices.DeviceDesc, Devices.IsFavorite, Devices.DeviceControl, Devices.Format, Devices.Unit " +
                "FROM Devices " +
                "left JOIN users on users.UserID = Devices.UserID " +
                "WHERE users.Name = ?";

        prepStat = conn.prepareStatement(sql);
        prepStat.setString(1,name);

        ResultSet rs = prepStat.executeQuery();

        while (rs.next()) {
            int deviceId = rs.getInt("DeviceID");
            String deviceName = rs.getString("DeviceName");
            String deviceDesc = rs.getString("DeviceDesc");
            boolean deviceFav = rs.getBoolean("IsFavorite");
            String deviceControl = rs.getString("DeviceControl");
            String deviceFormat = rs.getString("Format");
            String deviceUnit = rs.getString("Unit");

            Device device = new Device(deviceId, deviceName, deviceDesc,deviceFav,deviceControl,deviceFormat,deviceUnit);
            deviceList.add(device);
        }

        return deviceList;
    }
    public void addFavoriteDevices (String userID, String deviceID) {
        try {
            String sql = "UPDATE Devices SET Devices.IsFavorite = 1 WHERE Devices.UserID = ? AND Devices.DeviceID = ?";

            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,userID);
            prepStat.setString(2,deviceID);

            prepStat.executeUpdate();
        }   catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFavoriteDevice (int userID, int deviceID) {
        try {
            String sql = "UPDATE Devices SET Devices.IsFavorite = 0 WHERE Devices.UserID = ? AND Devices.DeviceID = ?";

            prepStat = conn.prepareStatement(sql);
            prepStat.setInt(1,userID);
            prepStat.setInt(2,deviceID);

            prepStat.executeUpdate();
        }   catch (SQLException e) {
            e.printStackTrace();
        }
    }
   public List<Device> getFavoriteDevices(int userID) {
       List<Device> favDevices = new ArrayList<>();

       String sql = "SELECT Devices.DeviceID, Devices.DeviceName, Devices.DeviceDesc, Devices.IsFavorite, Devices.DeviceControl, Devices.Format, Devices.Unit " +
               "FROM Devices " +
               "WHERE Devices.UserID = ? and Devices.IsFavorite = 1";
       try {
            prepStat = conn.prepareStatement(sql);
            prepStat.setInt(1, userID);

            ResultSet rs = prepStat.executeQuery();

            while (rs.next()) {
                int deviceId = rs.getInt("DeviceID");
                String deviceName = rs.getString("DeviceName");
                String deviceDesc = rs.getString("DeviceDesc");
                boolean deviceFav = rs.getBoolean("IsFavorite");
                String deviceControl = rs.getString("DeviceControl");
                String deviceFormat = rs.getString("Format");
                String deviceUnit = rs.getString("Unit");

                Device device = new Device(deviceId, deviceName, deviceDesc, deviceFav, deviceControl, deviceFormat, deviceUnit);
                favDevices.add(device);
            }

       }catch(SQLException e){
            throw new RuntimeException(e);
       }
       return favDevices;
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

    public void updateDeviceControl(int deviceID, String newControl) {
        try {
            String sql = "UPDATE Devices SET Devices.DeviceControl = ? WHERE Devices.DeviceID = ?";
            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,newControl);
            prepStat.setInt(2,deviceID);

            prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
