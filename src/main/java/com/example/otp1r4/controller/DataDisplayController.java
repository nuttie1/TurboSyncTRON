package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.UserData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class DataDisplayController implements Controller {
    @FXML
    private ComboBox comboBox;
    @FXML
    private LineChart numberLineChart;
    @FXML
    private BarChart barChart;
    @FXML
    private ScrollPane applianceDataView;
    @FXML
    private ScrollPane arbView;
    private final DeviceDAO deviceDao = new DeviceDAO();
    private final UserData userData = UserData.getInstance();
    private List<Device> deviceList;
    private ArrayList<Device.DeviceData> dataList;
    private Device selectedDevice;

    enum chartMode {
        LIGHTING,
        SENSOR,
        APPLIANCE,
        NONE
    }
    public void initialize() {
        createCombobox();
        onComboboxChanged();
    }
    private void createCombobox(){
        deviceList = deviceDao.getFavoriteDevices(userData.getUserID());

        comboBox.setItems(FXCollections.observableArrayList(deviceList));
        comboBox.getSelectionModel().selectFirst();
    }

    public void onComboboxChanged(){
        selectedDevice = (Device) comboBox.getValue();

        switch (parseControl()) {
            case LIGHTING -> {
                dataList = selectedDevice.getDeviceDataList(20);
                modifyNumberChart();
            }
            case SENSOR -> {
                dataList = selectedDevice.getDeviceDataList(50);
                modifyBarChart();
            }
            case APPLIANCE -> {
                dataList = selectedDevice.getDeviceDataList(75);
                modifyApplianceGrid();
            }
        }
        modifyArbView();
    }

    private chartMode parseControl(){
        String[] split = selectedDevice.getDeviceControl().split(";");
        return switch (split[0]) {
            case "Valaisin" -> chartMode.LIGHTING;
            case "Sensori" -> chartMode.SENSOR;
            case "Laite" -> chartMode.APPLIANCE;
            default -> throw new IllegalStateException("Unexpected value: " + split[0]);
        };
    }

    private void modifyBarChart(){
        applianceDataView.setVisible(false);
        numberLineChart.setVisible(false);
        barChart.setVisible(true);

        barChart.getData().clear();
        barChart.setAnimated(false);

        XYChart.Series series = new XYChart.Series();

        if(dataList.isEmpty()){
            return;
        }
        barChart.getYAxis().setLabel(selectedDevice.getFormat());
        barChart.setTitle(selectedDevice.getDeviceName());

        for(Device.DeviceData deviceData : dataList){
            double data = Double.parseDouble(deviceData.devData);
            series.getData().add(new XYChart.Data(deviceData.timeStamp.toString(), data));
        }

        barChart.getData().add(series);
    }
    private void modifyApplianceGrid(){
        numberLineChart.setVisible(false);
        barChart.setVisible(false);
        applianceDataView.setVisible(true);

        applianceDataView.setContent(new GridPane());
        if(dataList.isEmpty()){
            return;
        }
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);


        for(int i = 0; i < dataList.size();i++){
            Label ts = new Label(dataList.get(i).timeStamp.toString() + " --- ");
            ts.setStyle("-fx-font-size: 18;");
            Label data = new Label(dataList.get(i).devData);
            data.setStyle("-fx-font-size: 18; -fx-text-fill: #2196F3;-fx-border-color: #646464; -fx-border-width: 2px; -fx-border-radius: 5px");

            grid.add(ts,0,i);
            grid.add(data,1,i);
        }

        applianceDataView.setContent(grid);
        applianceDataView.setPannable(true);
    }
    private void modifyNumberChart(){
        applianceDataView.setVisible(false);
        barChart.setVisible(false);
        numberLineChart.setVisible(true);

        numberLineChart.getData().clear();
        numberLineChart.setAnimated(false);
        XYChart.Series series = new XYChart.Series();

        if(dataList.isEmpty()){
            return;
        }
        numberLineChart.getYAxis().setLabel(selectedDevice.getFormat());
        numberLineChart.setTitle(selectedDevice.getDeviceName());


        for(Device.DeviceData deviceData : dataList){
            double data = Double.parseDouble(deviceData.devData);
            series.getData().add(new XYChart.Data(deviceData.timeStamp.toString(),  data));
        }

        numberLineChart.getData().add(series);

    }
    private void modifyArbView(){
        arbView.setContent(new GridPane());
        if(dataList.isEmpty()){
            return;
        }
        GridPane grid = new GridPane();

        for(int i = 0; i < dataList.size();i++){
            Label ts = new Label(dataList.get(i).timeStamp.toString() + " --- ");
            Label arb = new Label(dataList.get(i).dataArb);
            grid.add(ts,0,i);
            grid.add(arb,1,i);
        }

        grid.setPadding(new Insets(10));

        arbView.setContent(grid);
        arbView.setPannable(true);
    }
}
