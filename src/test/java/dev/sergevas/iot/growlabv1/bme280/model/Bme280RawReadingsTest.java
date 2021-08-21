package dev.sergevas.iot.growlabv1.bme280.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Bme280RawReadingsTest {

    private static Bme280RawReadings bme280RawReadings;

    @BeforeAll
    static void setup() {
        bme280RawReadings = new Bme280RawReadings();
        byte[] rawReadings = bme280RawReadings.getReadings();
        rawReadings[0] = (byte)0x53;
        rawReadings[1] = (byte)0x0E;
        rawReadings[2] = (byte)0x00;
        rawReadings[3] = (byte)0x82;
        rawReadings[4] = (byte)0x80;
        rawReadings[5] = (byte)0x00;
        rawReadings[6] = (byte)0x6F;
        rawReadings[7] = (byte)0xF7;
        bme280RawReadings.computeAdcValues();
    }

    @Test
    void computeAdcValues() {
        assertEquals(0x82800, bme280RawReadings.getAdcT());
        assertEquals(0x6FF7, bme280RawReadings.getAdcHumidity());
        assertEquals(0x530E0, bme280RawReadings.getAdcPressure());
    }
}
