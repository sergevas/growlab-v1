package dev.sergevas.iot.growlabv1.shared.domain;


import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * This type contains an actuator command request error description elements of the API
 */
public class ActuatorErrorType {

    @NotNull
    private String eventId;
    @NotNull
    private String eventName;
    private String desc;
    @NotNull
    private OffsetDateTime eventTimestamp;

    /**
     * An error event type id
     **/
    public ActuatorErrorType eventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    @JsonbProperty("event_id")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * An error event descriptive name
     **/
    public ActuatorErrorType eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    @JsonbProperty("event_name")
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * An error event detailed info
     **/
    public ActuatorErrorType desc(String desc) {
        this.desc = desc;
        return this;
    }

    @JsonbProperty("desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * An error event occurrence timestamp
     **/
    public ActuatorErrorType eventTimestamp(OffsetDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
        return this;
    }

    @JsonbProperty("event_timestamp")
    public OffsetDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(OffsetDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActuatorErrorType actuatorErrorType = (ActuatorErrorType) o;
        return Objects.equals(eventId, actuatorErrorType.eventId) &&
                Objects.equals(eventName, actuatorErrorType.eventName) &&
                Objects.equals(desc, actuatorErrorType.desc) &&
                Objects.equals(eventTimestamp, actuatorErrorType.eventTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, desc, eventTimestamp);
    }

    @Override
    public String toString() {

        return "class ActuatorErrorType {\n" +
                "    eventId: " + toIndentedString(eventId) + "\n" +
                "    eventName: " + toIndentedString(eventName) + "\n" +
                "    desc: " + toIndentedString(desc) + "\n" +
                "    eventTimestamp: " + toIndentedString(eventTimestamp) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
