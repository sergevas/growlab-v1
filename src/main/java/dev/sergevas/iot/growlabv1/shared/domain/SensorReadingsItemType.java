package dev.sergevas.iot.growlabv1.shared.domain;


import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.Objects;

/*
 * A structure, containing sensor data readings, e.g. temperature, humidity, pressure, etc.
 */
public class SensorReadingsItemType {

    @Valid
    @JsonbProperty("s_type")
    private String sType;
    @Valid
    @JsonbProperty("s_id")
    private String sId;
    @Valid
    @NotNull
    @JsonbProperty("s_data")
    private String sData;
    @Valid
    @NotNull
    @JsonbProperty("s_timestamp")
    private OffsetDateTime sTimestamp;

    /**
     * A sensor type, e.g. TEMP (temperature)
     **/
    public SensorReadingsItemType sType(String sType) {
        this.sType = sType;
        return this;
    }

    public String getSType() {
        return sType;
    }

    public void setSType(String sType) {
        this.sType = sType;
    }

    /**
     * Sensor id
     **/
    public SensorReadingsItemType sId(String sId) {
        this.sId = sId;
        return this;
    }

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    /**
     * A sensor readings value
     **/
    public SensorReadingsItemType sData(String sData) {
        this.sData = sData;
        return this;
    }

    public String getSData() {
        return sData;
    }

    public void setSData(String sData) {
        this.sData = sData;
    }

    /**
     * Readings fetch timestamp
     **/
    public SensorReadingsItemType sTimestamp(OffsetDateTime sTimestamp) {
        this.sTimestamp = sTimestamp;
        return this;
    }

    public OffsetDateTime getSTimestamp() {
        return sTimestamp;
    }

    public void setSTimestamp(OffsetDateTime sTimestamp) {
        this.sTimestamp = sTimestamp;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SensorReadingsItemType sensorReadingsItemType = (SensorReadingsItemType) o;
        return Objects.equals(sType, sensorReadingsItemType.sType) &&
                Objects.equals(sId, sensorReadingsItemType.sId) &&
                Objects.equals(sData, sensorReadingsItemType.sData) &&
                Objects.equals(sTimestamp, sensorReadingsItemType.sTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sType, sId, sData, sTimestamp);
    }

    @Override
    public String toString() {

        return "class SensorReadingsItemType {\n" +
                "    sType: " + toIndentedString(sType) + "\n" +
                "    sId: " + toIndentedString(sId) + "\n" +
                "    sData: " + toIndentedString(sData) + "\n" +
                "    sTimestamp: " + toIndentedString(sTimestamp) + "\n" +
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
