package dev.sergevas.iot.growlabv1.shared.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonValue;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import dev.sergevas.iot.growlabv1.shared.model.SensorType;
import static org.junit.jupiter.api.Assertions.*;


class SensorResponseBuilderTest {

    private static JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());
    private static  JsonObject expectedReadingsItem;
    private static  JsonObject expectedReadings;
    private static OffsetDateTime sTimestamp;

    @BeforeAll
    static void setup() {
        sTimestamp = OffsetDateTime.of(2021, 7, 31, 23,
                07, 00, 00, ZoneOffset.UTC);

        expectedReadingsItem =JSON.createObjectBuilder()
                .add("s_id", JsonValue.NULL)
                .add("s_type", "LIGHT")
                .add("s_data", "25.7")
                .add("s_timestamp", sTimestamp.toString())
                .build();

        expectedReadings = JSON.createObjectBuilder()
                .add("s_readings",JSON.createArrayBuilder()
                .add(JSON.createObjectBuilder()
                    .add("s_id", "0xEF")
                    .add("s_type", "TEMP")
                    .add("s_data", "25.7")
                    .add("s_timestamp", sTimestamp.toString()))
                .add(JSON.createObjectBuilder()
                    .add("s_id", "0xEF")
                    .add("s_type", "HUMID")
                    .add("s_data", "60.7")
                    .add("s_timestamp", sTimestamp.toString()))
                .add(JSON.createObjectBuilder()
                    .add("s_id", "0xEF")
                    .add("s_type", "PRESS")
                    .add("s_data", "1000.78")
                    .add("s_timestamp", sTimestamp.toString()))
                ).build();
    }

    @Test
    void buildSensorReadingsItem() {
        JsonObject actual = new SensorResponseBuilder()
                .item(new SensorResponseBuilder.Item()
                .sType(SensorType.LIGHT)
                .sData("25.7")
                .sTimestamp(sTimestamp))
                .buildSensorReadingsItem();
        assertEquals(expectedReadingsItem, actual);
    }

    @Test
    void buildSensorReadings() {
        JsonObject actual = new SensorResponseBuilder()
                .item(new SensorResponseBuilder.Item()
                        .sId("0xEF")
                        .sType(SensorType.TEMP)
                        .sData("25.7")
                        .sTimestamp(sTimestamp))
                .item(new SensorResponseBuilder.Item()
                        .sId("0xEF")
                        .sType(SensorType.HUMID)
                        .sData("60.7")
                        .sTimestamp(sTimestamp))
                .item(new SensorResponseBuilder.Item()
                        .sId("0xEF")
                        .sType(SensorType.PRESS)
                        .sData("1000.78")
                        .sTimestamp(sTimestamp))
                .buildSensorReadings();
        assertEquals(expectedReadings, actual);
    }
}
