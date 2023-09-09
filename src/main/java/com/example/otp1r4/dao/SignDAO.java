package com.example.otp1r4.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

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
            // TODO: poista nää ohje kommentit ennen julkasuu LOL
            // find using username passwordhash and salt
            String sql = "SELECT `Password`,`salt` FROM `users` WHERE `Name` = ?";
            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,name);

            ResultSet rs = prepStat.executeQuery();
            // if nothing is found return
            if(!rs.next()){
                return false;
            }

            byte[] queryPass = rs.getBytes(1);
            byte[] querySalt = rs.getBytes(2);

            byte[] hashedPassword = this.hashPassword(password,querySalt);

            // compare hashed password with new hashpassword that has been salted using database SALT
            return Arrays.equals(queryPass,hashedPassword);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Add new user to database w/o security questions
     *
     * @param name
     * @param password
     */
    public void addUser(String name, String password){
        byte[] salt = salt();
        byte[] hashedPassword = hashPassword(password,salt);
        try {
            String sql = "INSERT INTO `users`(`Name`, `Password`,`salt`) VALUES (?,?,?)";
            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,name);
            prepStat.setBytes(2,hashedPassword);
            prepStat.setBytes(3,salt);

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
        byte[] salt = salt();
        byte[] hashedPassword = hashPassword(password,salt);
        try {
            String sql = "INSERT INTO `users`(`Name`, `Password`,`salt`, `Security1`, `Security2`, `Security3`) VALUES (?,?,?,?,?,?)";
            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,name);
            prepStat.setBytes(2,hashedPassword);
            prepStat.setBytes(3,salt);
            prepStat.setString(4,securityQuestion1);
            prepStat.setString(5,securityQuestion2);
            prepStat.setString(6,securityQuestion3);

            prepStat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param name
     * @return bool true if name found
     */
    public boolean checkUsername(String name){
        try {
            String sql = "SELECT COUNT(*) FROM `users` WHERE `Name` = ?";
            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,name);

            ResultSet rs = prepStat.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Create salt for the password hash
     *
     * @return byte[]
     */
    private byte[] salt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;
    }

    /** Hash the password using salt
     *
     * @param password
     * @param salt
     * @return hashed password
     */
    private byte[] hashPassword(String password, byte[] salt) {
        byte[] hashedPassword;

        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return hashedPassword;
    }
}
