package dev.sergevas.iot.growlabv1.shared.controller;

import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.Json;
import java.time.OffsetDateTime;
import java.util.Collections;

public class SensorResponse {

    enum SensorType {
        TEMP, HUMID, PRESS, LIGH, CO2;
    }
/*
{
    "s_type": "some text",
    "s_id": "some text",
    "s_data": "some text",
    "s_timestamp": "2018-02-10T09:30Z"
*/
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

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsData() {
        return sData;
    }

    public void setsData(String sData) {
        this.sData = sData;
    }

    public SensorType getsType() {
        return sType;
    }

    public void setsType(SensorType sType) {
        this.sType = sType;
    }

    public OffsetDateTime getsTimestamp() {
        return sTimestamp;
    }

    public void setsTimestamp(OffsetDateTime sTimestamp) {
        this.sTimestamp = sTimestamp;
    }

    JsonObject returnObject = JSON.createObjectBuilder()
            .add("message", msg)
            .build();

}
