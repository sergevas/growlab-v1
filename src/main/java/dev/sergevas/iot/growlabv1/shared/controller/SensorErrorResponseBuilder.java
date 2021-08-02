package dev.sergevas.iot.growlabv1.shared.controller;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import dev.sergevas.iot.growlabv1.shared.exception.SensorException;

public class SensorErrorResponseBuilder {

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

    public SensorException getSensorException() {
        return sensorException;
    }

    public SensorErrorResponseBuilder sensorException(SensorException sensorException) {
        this.sensorException = sensorException;
        return this;
    }

    public String getEventId() {
        return eventId;
    }

    public SensorErrorResponseBuilder eventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public String getEventName() {
        return eventName;
    }

    public SensorErrorResponseBuilder eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public SensorErrorResponseBuilder desc(String desc) {
        this.desc = desc;
        return this;
    }

    public OffsetDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public SensorErrorResponseBuilder eventTimestamp(OffsetDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
        return this;
    }

    public JsonObject buildJsonObject() {
        return JSON.createObjectBuilder()
                .add(EVENT_ID, Optional.ofNullable(this.eventId)
                        .orElse(sensorException.getEventId()))
                .add(EVENT_NAME, Optional.ofNullable(this.eventName)
                        .orElse(sensorException.getMessage()))
                .add(S_TYPE, Optional.ofNullable(sensorException.getSensorType())
                        .map(Enum::toString).orElse("UNKNOWN"))
                .add(EVENT_DESCRIPTION, this.desc != null
                        ? Json.createValue(this.desc) : ExceptionUtils.getStackTrace(sensorException) != null
                            ? Json.createValue(ExceptionUtils.getStackTrace(sensorException)) : JsonValue.NULL)
                .add(EVENT_TIMESTAMP, this.eventTimestamp.toString())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorErrorResponseBuilder that = (SensorErrorResponseBuilder) o;
        return Objects.equals(sensorException, that.sensorException) && Objects.equals(eventId, that.eventId)
                && Objects.equals(eventName, that.eventName) && Objects.equals(desc, that.desc)
                && Objects.equals(eventTimestamp, that.eventTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorException, eventId, eventName, desc, eventTimestamp);
    }

    @Override
    public String toString() {
        return "SensorErrorResponseBuilder{" +
                "sensorException=" + sensorException +
                ", eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", desc='" + desc + '\'' +
                ", eventTimestamp=" + eventTimestamp +
                '}';
    }
}
