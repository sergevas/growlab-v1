package dev.sergevas.iot.growlabv1.shared.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConfigHandlerTest {

    private static Map<String, String> configMap;
    private static ConfigHandler configHandler;

    @BeforeAll
    static void setup() {
        configMap = new HashMap<>();
        configMap.put("integerKey", "125");
        configMap.put("longKey", "127");
        configMap.put("stringKey", "stringVal");
        configMap.put("doubleKey", "125.55");
        configHandler = new ConfigHandler().configMap(configMap);
    }

    @Test
    void getAsInteger() {
        assertEquals(125, configHandler.getAsInteger("integerKey"));
    }

    @Test
    void getAsLong() {
        assertEquals(127L, configHandler.getAsLong("longKey"));
    }

    @Test
    void getAsDouble() {
        assertEquals("stringVal", configHandler.getAsString("stringKey"));
    }

    @Test
    void getAsString() {
        assertEquals(125.55, configHandler.getAsDouble("doubleKey"));
    }
}