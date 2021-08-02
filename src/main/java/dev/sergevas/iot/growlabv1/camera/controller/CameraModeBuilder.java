package dev.sergevas.iot.growlabv1.camera.controller;

import dev.sergevas.iot.growlabv1.camera.model.CameraMode;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Objects;

public class CameraModeBuilder {
    private static final String MODE = "mode";
    private static final String MODE_TIMESTAMP = "mode_timestamp";

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private CameraMode mode;
    private OffsetDateTime modeTimestamp;

    public CameraMode getMode() {
        return mode;
    }

    public CameraModeBuilder mode(CameraMode mode) {
        this.mode = mode;
        return this;
    }

    public OffsetDateTime getModeTimestamp() {
        return modeTimestamp;
    }

    public CameraModeBuilder modeTimestamp(OffsetDateTime modeTimestamp) {
        this.modeTimestamp = modeTimestamp;
        return this;
    }

    public JsonObject buildJsonObject() {
        return JSON.createObjectBuilder()
                .add(MODE, mode.toString())
                .add(MODE_TIMESTAMP, this.modeTimestamp.toString())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CameraModeBuilder that = (CameraModeBuilder) o;
        return mode == that.mode && Objects.equals(modeTimestamp, that.modeTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode, modeTimestamp);
    }

    @Override
    public String toString() {
        return "CameraModeBuilder{" +
                "mode=" + mode +
                ", modeTimestamp=" + modeTimestamp +
                '}';
    }
}
