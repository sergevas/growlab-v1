package dev.sergevas.iot.growlabv1.hardware.boundary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemInfoAdapterTest {

    @Test
    void getCpuTemp() {
        if (System.getProperty("os.name").startsWith("Lin")) {
            assertNotNull(SystemInfoAdapter.create().getCpuTemp());
        } else {
            assertTrue(true);
        }
    }
}