package dev.sergevas.iot.growlabv1.shared.controller;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Optional;

import dev.sergevas.iot.growlabv1.shared.exception.SensorException;
import dev.sergevas.iot.growlabv1.shared.model.SensorType;

public class SensorErrorResponseBuilder {

    /*
    * {
    "event_id": "some text",
    "event_timestamp": "2018-02-10T09:30Z",
    "desc": "some text",
    "uuid": "",
    "event_name": "some text"
}
    * */

    private static final String EVENT_ID = "event_id";
    private static final String EVENT_NAME = "event_name";
    private static final String EVENT_TIMESTAMP = "event_timestamp";
    private static final String EVENT_DESCRIPTION = "desc";
    private static final String S_TYPE = "s_type";

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private SensorException sensorException;
    private String eventId;
    private String eventName;
    private String desc;
    private OffsetDateTime eventTimestamp;

    public OffsetDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public JsonObject buildJsonObject() {
        return JSON.createObjectBuilder()
                .add(EVENT_ID, this.eventId)
                .add(EVENT_NAME, EVENT_NAME)
                .add(S_TYPE, Optional.ofNullable(sensorException.getSensorType())
                        .map(Enum::toString).orElse("UNKNOWN"))
                .add(EVENT_DESCRIPTION, this.desc)
                .add(EVENT_TIMESTAMP, this.eventTimestamp.toString())
                .build();
    }
}
