package com.example.otp1r4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignDAO implements DAO {
    Connection conn = JDBCConnection.connection;
    PreparedStatement prepStat = JDBCConnection.preparedStatement;

    /** Check if user credentials are found in the database
     *
     * @param name
     * @param password
     * @return bool true if found else false
     * @throws Exception
     */
    @Override
    public boolean authenticate(String name, String password) throws Exception {
        try {
            String sql = "SELECT * FROM `users` WHERE `Name` = ? AND `Password` = ?";
            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,name);
            prepStat.setString(2,password);

            ResultSet rs = prepStat.executeQuery();

            if(rs.next()){
                System.out.println("The user password combination was found.");
                return true;
            } else return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new Exception("DB authentication went wrong");
    }
}
