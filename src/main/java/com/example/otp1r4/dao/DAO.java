package com.example.otp1r4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public interface DAO {
    Connection conn = JDBCConnection.connection;

}
