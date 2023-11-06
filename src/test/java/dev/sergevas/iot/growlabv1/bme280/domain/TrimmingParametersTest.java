package dev.sergevas.iot.growlabv1.bme280.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrimmingParametersTest {

    @Test
    void toDigH4() {
        // -78 FFB2 1111 1111 1011 0010
        assertEquals(-78, TrimmingParameters.toDigH4((byte) 0b1111_0010, (byte) 0b1111_1011));
    }

    @Test
    void toDigH5() {
        // -78 FFB2 1111 1111 1011 0010
        assertEquals(-78, TrimmingParameters.toDigH5((byte) 0b0010_1111, (byte) 0b1111_1011));
    }

    @Test
    void toUnsigned() {
        assertEquals(65534, TrimmingParameters.toUnsigned(0xFFFE));
        assertEquals(65534, TrimmingParameters.toUnsigned(0x00FFFFFE));
        assertEquals(255, TrimmingParameters.toUnsigned(0x00FF00FF));
    }
}
