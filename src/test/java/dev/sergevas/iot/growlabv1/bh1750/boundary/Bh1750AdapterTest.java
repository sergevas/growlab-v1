package dev.sergevas.iot.growlabv1.bh1750.boundary;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class Bh1750AdapterTest {

    @Test
    void fromRawReadingsToLightIntensityTest() {
        assertEquals(51030, Bh1750Adapter.create(new HashMap<>())
                .fromRawReadingsToLightIntensity(new byte[]{(byte)0xef, (byte)0x34}));
        assertEquals(1000, Bh1750Adapter.create(new HashMap<>())
                .fromRawReadingsToLightIntensity(new byte[]{(byte)0x04, (byte)0xb0}));
    }
}
