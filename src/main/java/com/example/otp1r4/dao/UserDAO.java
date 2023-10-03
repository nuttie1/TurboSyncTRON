package com.example.otp1r4.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserDAO implements DAO {
    PreparedStatement prepStat;

    /** Check if user credentials are found in the database
     *
     * @param name
     * @param password
     * @return bool true if found else false
     * @throws Exception
     */
    public boolean authenticate(String name, String password) throws Exception {
        try {
            String sql = "SELECT `Password`,`salt` FROM `users` WHERE `Name` = ?";
            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,name);

            ResultSet rs = prepStat.executeQuery();
            if(!rs.next()){
                return false;
            }

            byte[] queryPass = rs.getBytes(1);
            byte[] querySalt = rs.getBytes(2);

            byte[] hashedPassword = this.hashString(password,querySalt);

            return Arrays.equals(queryPass,hashedPassword);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Add new User to database with security questions
     *
     * @param name
     * @param password
     * @param securityQuestion1
     * @param securityQuestion2
     * @param securityQuestion3
     */
    public void addUser(String name, String password, String securityQuestion1, String securityQuestion2, String securityQuestion3, String securityAnswer1, String securityAnswer2, String securityAnswer3){
        byte[] salt = salt();
        byte[] hashedPassword = hashString(password,salt);
        try {
            /// TODO: Refractor this mess. Use loops and lists. Easy clean up.
            String sql = "INSERT INTO `users`(`Name`, `Password`,`salt`, `Security1`, `Security2`, `Security3`, `SecurityA1`,`SecASalt1`, `SecurityA2`,`SecASalt2`, `SecurityA3`,`SecASalt3`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,name);
            prepStat.setBytes(2,hashedPassword);
            prepStat.setBytes(3,salt);
            prepStat.setString(4,securityQuestion1);
            prepStat.setString(5,securityQuestion2);
            prepStat.setString(6,securityQuestion3);
            salt = salt();
            prepStat.setBytes(7, hashString(securityAnswer1,salt));
            prepStat.setBytes(8,salt);
            salt = salt();
            prepStat.setBytes(9, hashString(securityAnswer2,salt));
            prepStat.setBytes(10,salt);
            salt = salt();
            prepStat.setBytes(11, hashString(securityAnswer3,salt));
            prepStat.setBytes(12,salt);

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
    private byte[] hashString(String password, byte[] salt) {
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

    public boolean authenticateSecurityQuestions(String name, String securityA1, String securityA2, String securityA3) throws Exception {
        try {

            String sql = "SELECT `SecurityA1`,`SecASalt1`,`SecurityA2`,`SecASalt2`,`SecurityA3`,`SecASalt3` FROM `users` WHERE `Name` = ?";
            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,name);

            ResultSet rs = prepStat.executeQuery();
            if(!rs.next()){
                return false;
            }

            byte[] querySecA1 = rs.getBytes(1);
            byte[] querySecASalt1 = rs.getBytes(2);
            byte[] querySecA2 = rs.getBytes(3);
            byte[] querySecASalt2 = rs.getBytes(4);
            byte[] querySecA3 = rs.getBytes(5);
            byte[] querySecASalt3 = rs.getBytes(6);

            byte[] hashedA1 = this.hashString(securityA1,querySecASalt1);
            byte[] hashedA2 = this.hashString(securityA2,querySecASalt2);
            byte[] hashedA3 = this.hashString(securityA3,querySecASalt3);

            return Arrays.equals(querySecA1,hashedA1) && Arrays.equals(querySecA2,hashedA2) && Arrays.equals(querySecA3,hashedA3);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void changeUsername(String newName, String oldName){
        try {
            String sql = "UPDATE users SET Name = ? WHERE Name = ?";
            prepStat = conn.prepareStatement(sql);
            prepStat.setString(1,newName);
            prepStat.setString(2,oldName);

            prepStat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePassword(String name, String password){
        byte[] salt = salt();
        byte[] hashedPassword = hashString(password,salt);
        try {
            String sql = "UPDATE users SET Password = ?, salt = ? WHERE Name = ?";
            prepStat = conn.prepareStatement(sql);
            prepStat.setBytes(1,hashedPassword);
            prepStat.setBytes(2,salt);
            prepStat.setString(3,name);

            prepStat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getSecurityQuestion(String name, int questionNumber) {
        String question = null;

        try {
            String columnName = null;
            switch (questionNumber) {
                case 1:
                    columnName = "Security1";
                    break;
                case 2:
                    columnName = "Security2";
                    break;
                case 3:
                    columnName = "Security3";
                    break;
                default:
                    break;
            }

            if (columnName != null) {
                String sql = "SELECT " + columnName + " FROM users WHERE Name = ?";
                prepStat = conn.prepareStatement(sql);
                prepStat.setString(1, name);

                ResultSet resultSet = prepStat.executeQuery();

                if (resultSet.next()) {
                    question = resultSet.getString(columnName);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    public int getUserID (String name) throws SQLException {
        String sql = "SELECT UserID FROM users WHERE Name = ?";

        prepStat = conn.prepareStatement(sql);
        prepStat.setString(1, name);

        ResultSet resultSet = prepStat.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("UserID");
        }

        return -1;
    }
}
