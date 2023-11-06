package dev.sergevas.iot.growlabv1.shared.domain;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.Objects;

/*
 * This type contains a sensor readings request error description elements of the API
 */
public class SensorErrorType {

    @Valid
    @NotNull
    private String eventId;
    @Valid
    @NotNull
    private String eventName;
    @Valid
    private String sType;
    @Valid
    private String desc;
    @Valid
    @NotNull
    private OffsetDateTime eventTimestamp;

    /**
     * An error event type id
     **/
    public SensorErrorType eventId(String eventId) {
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
    public SensorErrorType eventName(String eventName) {
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
     * A sensor type, e.g. TEMP (temperature)
     **/
    public SensorErrorType sType(String sType) {
        this.sType = sType;
        return this;
    }

    @JsonbProperty("s_type")
    public String getSType() {
        return sType;
    }

    public void setSType(String sType) {
        this.sType = sType;
    }

    /**
     * An error event detailed info
     **/
    public SensorErrorType desc(String desc) {
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
    public SensorErrorType eventTimestamp(OffsetDateTime eventTimestamp) {
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
        SensorErrorType sensorErrorType = (SensorErrorType) o;
        return Objects.equals(eventId, sensorErrorType.eventId) &&
                Objects.equals(eventName, sensorErrorType.eventName) &&
                Objects.equals(sType, sensorErrorType.sType) &&
                Objects.equals(desc, sensorErrorType.desc) &&
                Objects.equals(eventTimestamp, sensorErrorType.eventTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, sType, desc, eventTimestamp);
    }

    @Override
    public String toString() {

        return "class SensorErrorType {\n" +
                "    eventId: " + toIndentedString(eventId) + "\n" +
                "    eventName: " + toIndentedString(eventName) + "\n" +
                "    sType: " + toIndentedString(sType) + "\n" +
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
