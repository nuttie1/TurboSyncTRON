package com.example.otp1r4;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.dao.JDBCConnection;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.Language;
import com.example.otp1r4.model.UserData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application{


    @Override
    public void start(Stage stage) throws IOException {
        JDBCConnection jdbc = new JDBCConnection();
        jdbc.start();

        ResourceBundle bundle = ResourceBundle.getBundle("TextResources");

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        fxmlLoader.setResources(bundle);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TurboSync Tron!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

