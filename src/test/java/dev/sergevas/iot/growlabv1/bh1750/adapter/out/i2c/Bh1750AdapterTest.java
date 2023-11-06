package dev.sergevas.iot.growlabv1.bh1750.adapter.out.i2c;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Bh1750AdapterTest {

    @Test
    void fromRawReadingsToLightIntensityTest() {
        var bh1750Adapter = new Bh1750Adapter();
        assertEquals(51030, bh1750Adapter.fromRawReadingsToLightIntensity(new byte[]{(byte) 0xef, (byte) 0x34}));
        assertEquals(1000, bh1750Adapter.fromRawReadingsToLightIntensity(new byte[]{(byte) 0x04, (byte) 0xb0}));
    }
}
