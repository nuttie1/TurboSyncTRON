package com.example.otp1r4.dao;

public interface DAO {
    public boolean authenticate(String email, String password) throws Exception;
}
