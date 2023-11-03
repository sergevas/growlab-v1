package dev.sergevas.iot.growlabv1.shared.controller;

import dev.sergevas.iot.growlabv1.shared.domain.SensorType;

import javax.json.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SensorResponseBuilder {

    private static final String S_READINGS = "s_readings";
    private static final String S_ID = "s_id";
    private static final String S_DATA = "s_data";
    private static final String S_TYPE = "s_type";
    private static final String S_TIMESTAMP = "s_timestamp";

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private List<Item> items = new ArrayList<>();

    public SensorResponseBuilder item(Item item) {
        this.items.add(item);
        return this;
    }

    public JsonObjectBuilder createReadingsItem(Item item) {
        return JSON.createObjectBuilder()
                .add(S_ID, item.sId != null ? Json.createValue(item.sId) : JsonValue.NULL)
                .add(S_TYPE, item.sType.toString())
                .add(S_DATA, item.sData)
                .add(S_TIMESTAMP, item.sTimestamp.toString());
    }

    public JsonObject buildSensorReadingsItem() {
        return this.createReadingsItem(this.items.get(0)).build();
    }

    public JsonObject buildSensorReadings() {
        JsonArrayBuilder jab = JSON.createArrayBuilder();
        this.items.forEach(i -> jab.add(this.createReadingsItem(i)));
        return JSON.createObjectBuilder().add(S_READINGS, jab).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorResponseBuilder that = (SensorResponseBuilder) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        return "SensorResponseBuilder{" +
                "items=" + items +
                '}';
    }

    public static class Item {
        private String sId;
        private String sData;
        private SensorType sType;
        private OffsetDateTime sTimestamp;

        public String getsId() {
            return sId;
        }

        public Item sId(String sId) {
            this.sId = sId;
            return this;
        }

        public Item sData(Object sData) {
            this.sData = String.valueOf(sData);
            return this;
        }

        public Item sType(SensorType sType) {
            this.sType = sType;
            return this;
        }

        public Item sTimestamp(OffsetDateTime sTimestamp) {
            this.sTimestamp = sTimestamp;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return Objects.equals(sId, item.sId) && Objects.equals(sData, item.sData) && sType == item.sType && Objects.equals(sTimestamp, item.sTimestamp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sId, sData, sType, sTimestamp);
        }

        @Override
        public String toString() {
            return "Item{" +
                    "sId='" + sId + '\'' +
                    ", sData='" + sData + '\'' +
                    ", sType=" + sType +
                    ", sTimestamp=" + sTimestamp +
                    '}';
        }
    }
}
