package com.example.otp1r4;

import com.example.otp1r4.controller.RegisterController;
import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.dao.JDBCConnection;
import com.example.otp1r4.dao.SignDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{


    @Override
    public void start(Stage stage) throws IOException {
        JDBCConnection jdbc = new JDBCConnection();
        jdbc.start();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TurboSync Tron!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
