package tests;

import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.ObservableDevices;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObservableDevicesTest {

    ObservableDevices observableDevices;
    ObservableList<Device> observableDevicesList;
    Device device;

    @BeforeEach
    void setUp() {
        device = new Device(1, "TestDevice", "test", false, "test", "test", "test");
        observableDevicesList.add(device);
        observableDevices.setObservableList(observableDevicesList);
    }

    @Test
    void getObservableDevices() {
        assertEquals(observableDevicesList, observableDevices.getObservableDevices());
    }

    @Test
    void setObservableList() {
        Device device2 = new Device(2, "TestDevice2", "test2", false, "test2", "test2", "test2");
        observableDevicesList.add(device2);
        observableDevices.setObservableList(observableDevicesList);
        assertEquals(observableDevicesList, ObservableDevices.getInstance().getObservableDevices());
    }

    @Test
    void updateDevice() {
        device.updateDeviceControl("test2");
        observableDevices.updateDevice(device);
        assertEquals(device, observableDevices.getObservableDevices().get(0));
    }

    @Test
    void addDevice() {
        Device device3 = new Device(3, "TestDevice3", "test3", false, "test3", "test3", "test3");
        observableDevices.addDevice(device3);
        assertEquals(device3, observableDevices.getObservableDevices().get(1));
    }
}