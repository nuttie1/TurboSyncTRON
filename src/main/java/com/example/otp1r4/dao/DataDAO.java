package com.example.otp1r4.dao;

import com.example.otp1r4.model.Device;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataDAO implements DAO{
    PreparedStatement prepStat;

    public void getDeviceData(Device device,int amount){

        ResultSet rs;
        String sql = "SELECT * FROM(SELECT `Timestamp`,`Data`, DataArb FROM Devicedata WHERE DeviceID = ? ORDER BY `Timestamp` ASC LIMIT ?) AS sub ORDER BY Timestamp ASC";
        try {
            prepStat = conn.prepareStatement(sql);
            prepStat.setInt(1,device.getDeviceId());
            prepStat.setInt(2,amount);

            rs = prepStat.executeQuery();

            while(rs.next()){
                device.setDeviceData(rs.getTimestamp("Timestamp"),rs.getString("Data"),rs.getString("DataArb"));
            }
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
