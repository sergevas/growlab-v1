package dev.sergevas.iot.growlabv1.shared.controller;

import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.Json;
import javax.json.JsonValue;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Objects;
import dev.sergevas.iot.growlabv1.shared.model.SensorType;

public class SensorResponseBuilder {

    private static final String S_ID = "s_id";
    private static final String S_DATA = "s_data";
    private static final String S_TYPE = "s_type";
    private static final String S_TIMESTAMP = "s_timestamp";

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private String sId;
    private String sData;
    private SensorType sType;
    private OffsetDateTime sTimestamp;

    public String getsId() {
        return sId;
    }

    public SensorResponseBuilder sId(String sId) {
        this.sId = sId;
        return this;
    }

    public String getsData() {
        return sData;
    }

    public SensorResponseBuilder sData(Object sData) {
        this.sData = String.valueOf(sData);
        return this;
    }

    public SensorType getsType() {
        return sType;
    }

    public SensorResponseBuilder sType(SensorType sType) {
        this.sType = sType;
        return this;
    }

    public OffsetDateTime getsTimestamp() {
        return sTimestamp;
    }

    public SensorResponseBuilder sTimestamp(OffsetDateTime sTimestamp) {
        this.sTimestamp = sTimestamp;
        return this;
    }

    public JsonObject buildJsonObject() {
        return JSON.createObjectBuilder()
                .add(S_ID, this.sId != null ? Json.createValue(this.sId) : JsonValue.NULL)
                .add(S_TYPE, this.sType.toString())
                .add(S_DATA, this.sData)
                .add(S_TIMESTAMP, this.sTimestamp.toString())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorResponseBuilder that = (SensorResponseBuilder) o;
        return Objects.equals(sId, that.sId) && Objects.equals(sData, that.sData) && sType == that.sType && Objects.equals(sTimestamp, that.sTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sId, sData, sType, sTimestamp);
    }

    @Override
    public String toString() {
        return "SensorResponseBuilder{" +
                "sId='" + sId + '\'' +
                ", sData='" + sData + '\'' +
                ", sType=" + sType +
                ", sTimestamp=" + sTimestamp +
                '}';
    }
}
