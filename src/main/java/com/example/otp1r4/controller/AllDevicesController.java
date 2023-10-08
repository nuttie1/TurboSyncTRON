package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.ObservableDevices;
import com.example.otp1r4.model.UserData;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AllDevicesController implements Controller, Initializable {

    @FXML
    AnchorPane allDevicesPane;

    List<Device> allDevices = new ArrayList<>();


    DeviceDAO deviceDAO = new DeviceDAO();
    UserData userData;

    ArrayList<StarDevice> allStarsDevices= new ArrayList<>();
    private ObservableDevices observableDevices = ObservableDevices.getInstance();

    private class StarDevice {
        Polygon star;
        Device device;

        public StarDevice(Polygon star, Device device) {
            this.star = star;
            this.device = device;

            star.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Polygon Clicked!");
                    if(device.isDeviceFavorite().getValue()){
                        deviceDAO.removeFavoriteDevice(userData.getUserID(), device.getDeviceId());
                        device.updateIsDeviceFavorite(false);
                        star.setFill(Color.GRAY);
                    } else {
                        deviceDAO.addFavoriteDevices(userData.getUserID(), device.getDeviceId());
                        device.updateIsDeviceFavorite(true);
                        star.setFill(Color.YELLOW);
                    }

                    observableDevices.updateDevice(device);
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userData = UserData.getInstance();

        observableDevices.getObservableDevices().addListener((ListChangeListener<Device>) change -> {
            allDevices = ObservableDevices.getInstance().getObservableDevices();
            showAllDevices();
        });

        allDevices = ObservableDevices.getInstance().getObservableDevices();


        showAllDevices();
    }

    public void showAllDevices() {
        final GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        Label headerName = new Label("Nimi");
        Label headerDesc = new Label("Kuvaus");

        headerName.setStyle("-fx-font-size: 25px;-fx-border-style: solid;-fx-border-color: #A3E1C9FF;");
        headerDesc.setStyle("-fx-font-size: 25px;-fx-border-style: solid;-fx-border-color: #A3E1C9FF;");

        grid.add(headerName,0,0);
        grid.add(headerDesc,1,0);

        for (int i = 0; i<allDevices.size();i++) {
            Device device = allDevices.get(i);

            Label deviceName = new Label(device.getDeviceName());
            Label deviceDesc = new Label(device.getDeviceDesc());


            deviceName.setStyle("-fx-font-size: 25px;-fx-border-style: solid;-fx-border-color: #646464;");
            deviceDesc.setStyle("-fx-font-size: 25px;-fx-border-style: solid;-fx-border-color: #646464;");

            grid.add(deviceName, 0, i+1);
            grid.add(deviceDesc, 1, i+1);
            grid.add(createStar(device),2,i+1);

        }

        allDevicesPane.getChildren().add(grid);
    }
    private Polygon createStar(Device device){
        double[] points = {-23.0, -12.0, -67.0, -11.0, -29.0, 7.0, -41.0, 50.0, -12.0, 20.0, 15.0, 50.0, 5.0, 7.0, 37.0, -12.0, -3.0, -12.0, -12.0, -48.0};
        Polygon starPolygon = new Polygon(points);
        starPolygon.setScaleX(0.5);
        starPolygon.setScaleY(0.5);

        StarDevice sd = new StarDevice(starPolygon, device);

        allStarsDevices.add(sd);

        if(device.isDeviceFavorite().getValue()){
            starPolygon.setFill(Color.YELLOW);
        } else{
            starPolygon.setFill(Color.GRAY);
        }

        return starPolygon;
    }

}
