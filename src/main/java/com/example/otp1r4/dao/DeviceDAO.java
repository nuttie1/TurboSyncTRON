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
   public List<Device> getFavoriteDevices(String name) throws SQLException {
       List<Device> favDevices = new ArrayList<>();

       String sql = "SELECT Devices.DeviceID, Devices.DeviceName, Devices.DeviceDesc, Devices.IsFavorite, Devices.DeviceControl, Devices.Format, Devices.Unit " +
               "FROM Devices " +
               "left JOIN users on users.UserID = Devices.UserID " +
               "WHERE users.Name = ? and Devices.IsFavorite = 1";

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

    //HUONO
    public Device getDevice(String deviceName, int userID) {
        String sql = "SELECT DeviceID, DeviceDesc, IsFavorite, DeviceControl, Format, Unit FROM Devices " +
                 "WHERE DeviceName = ? AND UserID = ?";
        try {
            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,deviceName);
            prepStat.setInt(2, userID);

            int deviceID = 0;
            String deviceDesc = null, deviceControl = null, format = null, unit = null;
            boolean deviceFavorite = false;

            ResultSet resultSet = prepStat.executeQuery();

            while (resultSet.next()) {
                deviceID = resultSet.getInt(1);
                deviceDesc = resultSet.getString(3);
                deviceFavorite = resultSet.getBoolean(4);
                deviceControl = resultSet.getString(6);
                format = resultSet.getString(7);
                unit = resultSet.getString(8);
            }

            return new Device(deviceID,deviceName,deviceDesc,deviceFavorite,deviceControl,format,unit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public String getControl (int deviceID) {
        try {
            String sql = "SELECT Devices.DeviceControl FROM Devices WHERE Devices.DeviceID = ?";
            prepStat = conn.prepareStatement(sql);
            prepStat.setInt(1, deviceID);

            ResultSet resultSet = prepStat.executeQuery();

            if (resultSet.next()) {
                String control = resultSet.getString("DeviceControl");
                return control;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
