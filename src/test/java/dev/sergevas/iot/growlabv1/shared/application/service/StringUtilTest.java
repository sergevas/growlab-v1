package dev.sergevas.iot.growlabv1.shared.application.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilTest {

    @Test
    void toHexStringFromByteArray() {
        assertEquals("FFFE", StringUtil.toHexString(new byte[]{(byte) 255, (byte) 254}));
    }

    @Test
    void toHexStringFromInt() {
        assertEquals("FFFF", StringUtil.toHexString(65535));
    }
}
