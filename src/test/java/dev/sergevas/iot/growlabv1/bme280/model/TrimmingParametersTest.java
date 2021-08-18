package dev.sergevas.iot.growlabv1.bme280.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrimmingParametersTest {

    private static TrimmingParameters trimmingParameters;
    private static byte digsReadings[];

    @BeforeAll
    static void setup() {
        trimmingParameters = new TrimmingParameters();
        byte[] digs = trimmingParameters.getDigs();
        digs[0] = 0b01011001;
        digs[1] = 0b01111110;
        digs[2] = (byte) 0b10100111;
        digs[3] = (byte) 0b10000001;
        trimmingParameters.init();
    }

    @Test
    void getDigT1() {
        assertEquals(32345, trimmingParameters.getDigT1());
    }

    @Test
    void getDigT2() {
        assertEquals(-32345, trimmingParameters.getDigT2());
    }
}
