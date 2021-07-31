package dev.sergevas.iot.growlabv1.shared.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;


class SensorResponseBuilderTest {

    private static  JsonObject expected;
    private static OffsetDateTime sTimestamp = OffsetDateTime.of(2021, 7, 31, 23,
            07, 00, 00, ZoneOffset.UTC);

    @BeforeAll
    static void setup() {
        expected =Json
                .createBuilderFactory(Collections.emptyMap())
                .createObjectBuilder()
                .add("s_id", JsonValue.NULL)
                .add("s_type", "LIGHT")
                .add("s_data", "25.7")
                .add("s_timestamp", sTimestamp.toString())
                .build();
    }

    @Test
    void buildJsonObject() {
        JsonObject actual = new SensorResponseBuilder()
                .sType(SensorResponseBuilder.SensorType.LIGHT)
                .sData("25.7")
                .sTimestamp(sTimestamp)
                .buildJsonObject();
        assertEquals(expected, actual);
    }
}
