package dev.sergevas.iot.growlabv1.shared.controller;

import dev.sergevas.iot.growlabv1.shared.application.port.out.SensorException;
import dev.sergevas.iot.growlabv1.shared.domain.SensorType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SensorErrorResponseBuilderTest {

    private static OffsetDateTime eventTimestamp;
    private static SensorException sensorException;
    private static JsonObject expected;

    @BeforeAll
    static void setup() {
        eventTimestamp = OffsetDateTime.of(2021, 7, 31, 23,
                07, 00, 00, ZoneOffset.UTC);
        sensorException = new SensorException("E-BH1750-0001",
                SensorType.LIGHT, "BH1750 data read error", new NullPointerException());
        expected = Json
                .createBuilderFactory(Collections.emptyMap())
                .createObjectBuilder()
                .add("event_id", "E-BH1750-0001")
                .add("event_name", "BH1750 data read error")
                .add("s_type", "LIGHT")
                .add("desc", JsonValue.NULL)
                .add("event_timestamp", eventTimestamp.toString())
                .build();
    }

    @Test
    void buildJsonObject() {
        JsonObject actual = new SensorsErrorResponseBuilder()
                .sensorException(sensorException)
                .eventTimestamp(eventTimestamp)
                .buildJsonObject();
        assertEquals("E-BH1750-0001", actual.getString("event_id"));
        assertEquals("BH1750 data read error", actual.getString("event_name"));
        assertEquals("LIGHT", actual.getString("s_type"));
        assertNotNull(actual.getString("desc"));
        assertEquals("2021-07-31T23:07Z", actual.getString("event_timestamp"));
    }
}
