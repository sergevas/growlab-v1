package dev.sergevas.iot.growlabv1.health.adapter.out.os;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SystemInfoAdapterTest {

    @Test
    void getCpuTemp() {
        var cpuTempAdapter = new CpuTempAdapter();
        if (System.getProperty("os.name").startsWith("Lin")) {
            assertTrue(cpuTempAdapter.getCpuTemp() > 0);
        } else {
            assertTrue(true);
        }
    }
}
