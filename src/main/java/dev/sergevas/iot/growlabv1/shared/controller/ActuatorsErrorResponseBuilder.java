package dev.sergevas.iot.growlabv1.shared.controller;

import dev.sergevas.iot.growlabv1.shared.exception.ActuatorException;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

public class ActuatorsErrorResponseBuilder {

    private static final String EVENT_ID = "event_id";
    private static final String EVENT_NAME = "event_name";
    private static final String EVENT_TIMESTAMP = "event_timestamp";
    private static final String EVENT_DESCRIPTION = "desc";

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private ActuatorException actuatorException;
    private String eventId;
    private String eventName;
    private String desc;
    private OffsetDateTime eventTimestamp;

    public ActuatorsErrorResponseBuilder actuatorException(ActuatorException actuatorException) {
        this.actuatorException = actuatorException;
        return this;
    }

    public ActuatorsErrorResponseBuilder eventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public ActuatorsErrorResponseBuilder eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public ActuatorsErrorResponseBuilder desc(String desc) {
        this.desc = desc;
        return this;
    }

    public ActuatorsErrorResponseBuilder eventTimestamp(OffsetDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
        return this;
    }

    public JsonObject buildJsonObject() {
        return JSON.createObjectBuilder()
                .add(EVENT_ID, Optional.ofNullable(this.eventId)
                        .orElse(actuatorException.getEventId()))
                .add(EVENT_NAME, Optional.ofNullable(this.eventName)
                        .orElse(actuatorException.getMessage()))
                .add(EVENT_DESCRIPTION, this.desc != null
                        ? Json.createValue(this.desc) : ExceptionUtils.getStackTrace(actuatorException) != null
                            ? Json.createValue(ExceptionUtils.getStackTrace(actuatorException)) : JsonValue.NULL)
                .add(EVENT_TIMESTAMP, this.eventTimestamp.toString())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActuatorsErrorResponseBuilder that = (ActuatorsErrorResponseBuilder) o;
        return Objects.equals(actuatorException, that.actuatorException) && Objects.equals(eventId, that.eventId)
                && Objects.equals(eventName, that.eventName)
                && Objects.equals(desc, that.desc) && Objects.equals(eventTimestamp, that.eventTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actuatorException, eventId, eventName, desc, eventTimestamp);
    }

    @Override
    public String toString() {
        return "ActuatorsErrorResponseBuilder{" +
                "actuatorException=" + actuatorException +
                ", eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", desc='" + desc + '\'' +
                ", eventTimestamp=" + eventTimestamp +
                '}';
    }
}
