package tests;

import com.example.otp1r4.model.Device;
import com.example.otp1r4.model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {
    UserData userData;
    List<Device> devices = new ArrayList<>();
    Device device;
    @BeforeEach
    void setUp() {
        userData.setUserID(1);
        userData.setUsername("test");
        device = new Device(1, "TestDevice", "test", false, "test", "test", "test");
        devices.add(device);
        userData.setFavoriteDevices(devices);
    }

    @Test
    void getUsername() {
        assertEquals("test", userData.getUsername());
    }

    @Test
    void getUserID() {
        assertEquals(1, userData.getUserID());
    }

    @Test
    void setUsername() {
        userData.setUsername("test2");
        assertEquals("test2", userData.getUsername());
    }

    @Test
    void setUserID() {
        userData.setUserID(2);
        assertEquals(2, userData.getUserID());
    }

    @Test
    void getFavoriteDevices() {
        assertEquals(devices, userData.getFavoriteDevices());
        assertEquals(device, userData.getFavoriteDevices().get(0));
    }

    @Test
    void setFavoriteDevices() {
        List<Device> devices2 = new ArrayList<>();
        Device device2 = new Device(2, "TestDevice2", "test2", false, "test2", "test2", "test2");
        devices2.add(device2);
        userData.setFavoriteDevices(devices2);
        assertEquals(devices2, userData.getFavoriteDevices());
        assertEquals(device2, userData.getFavoriteDevices().get(0));
    }

    @Test
    void addFavoriteDevice() {
        Device device2 = new Device(2, "TestDevice2", "test2", false, "test2", "test2", "test2");
        userData.addFavoriteDevice(device2);
        assertEquals(device2, userData.getFavoriteDevices().get(1));
    }
}