package tests;

import com.example.otp1r4.model.Device;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceTest {
    Device device;

    @BeforeEach
    void setUp() {
        device = new Device(1, "TestDevice", "test", false, "test", "test", "test");
    }

    @Test
    void getDeviceName() {
        assertEquals("TestDevice", device.getDeviceName());
    }

    @Test
    void getDeviceId() {
        assertEquals(1, device.getDeviceId());
    }

    @Test
    void getDeviceDesc() {
        assertEquals("test", device.getDeviceDesc());
    }

    @Test
    void isDeviceFavorite() {
        assertFalse(device.isDeviceFavorite().get());
    }

    @Test
    void getDeviceControl() {
        assertEquals("test", device.getDeviceControl());
    }

    @Test
    void getFormat() {
        assertEquals("test", device.getFormat());
    }

    @Test
    void getUnit() {
        assertEquals("test", device.getUnit());
    }

    @Test
    void updateDeviceControl() {
        device.updateDeviceControl("test2");
        assertEquals("test2", device.getDeviceControl());
    }

    @Test
    void updateIsDeviceFavorite() {
        device.updateIsDeviceFavorite(true);
        assertTrue(device.isDeviceFavorite().get());
    }
}