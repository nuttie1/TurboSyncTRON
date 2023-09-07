package com.example.otp1r4;

import com.example.otp1r4.dao.JDBCConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        JDBCConnection jdbcConnection = new JDBCConnection();
        jdbcConnection.start();

        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}