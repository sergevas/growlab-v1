package dev.sergevas.iot.growlabv1.camera.controller;

import dev.sergevas.iot.growlabv1.camera.model.CameraMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CameraModeBuilderTest {

    private static OffsetDateTime modeTimestamp;
    private static JsonObject expected;

    @BeforeAll
    static void setup() {
        modeTimestamp = OffsetDateTime.of(2021, 7, 31, 23,
                07, 00, 00, ZoneOffset.UTC);
        expected = Json
                .createBuilderFactory(Collections.emptyMap())
                .createObjectBuilder()
                .add("mode", "NORM")
                .add("mode_timestamp", modeTimestamp.toString())
                .build();
    }

    @Test
    void buildJsonObject() {
        JsonObject actual = new CameraModeBuilder()
                .mode(CameraMode.NORM)
                .modeTimestamp(modeTimestamp)
                .buildJsonObject();
        assertEquals(expected, actual);
    }
}
