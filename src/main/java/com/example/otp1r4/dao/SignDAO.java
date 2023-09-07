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

    /** Add new user to database w/o security questions
     *
     * @param name
     * @param password
     */
    public void addUser(String name, String password){
        try {
            String sql = "INSERT INTO `users`(`Name`, `Password`) VALUES (?,?)";
            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,name);
            prepStat.setString(2,password);

            prepStat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Add new User to database with security questions
     *
     * @param name
     * @param password
     * @param securityQuestion1
     * @param securityQuestion2
     * @param securityQuestion3
     */
    public void addUser(String name, String password, String securityQuestion1, String securityQuestion2, String securityQuestion3){
        try {
            String sql = "INSERT INTO `users`(`Name`, `Password`, `Security1`, `Security2`, `Security3`) VALUES (?,?,?,?,?)";
            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,name);
            prepStat.setString(2,password);
            prepStat.setString(3,securityQuestion1);
            prepStat.setString(4,securityQuestion2);
            prepStat.setString(5,securityQuestion3);

            prepStat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
