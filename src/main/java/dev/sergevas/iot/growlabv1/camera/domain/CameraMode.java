package dev.sergevas.iot.growlabv1.camera.domain;

import java.time.OffsetDateTime;
import java.util.Objects;

public class CameraMode {

    public enum Mode {
        NORM, NIGHT, UNDEFINED;
    }

    private final Mode mode;
    private final OffsetDateTime modeTimestamp;

    public CameraMode(Mode mode, OffsetDateTime modeTimestamp) {
        this.mode = mode;
        this.modeTimestamp = modeTimestamp;
    }

    public CameraMode(Mode mode) {
        this(mode, null);
    }

    public Mode mode() {
        return mode;
    }

    public OffsetDateTime modeTimestamp() {
        return modeTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CameraMode that = (CameraMode) o;
        return mode == that.mode && Objects.equals(modeTimestamp, that.modeTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode, modeTimestamp);
    }

    @Override
    public String toString() {
        return "CameraMode{" +
                "mode=" + mode +
                ", modeTimestamp=" + modeTimestamp +
                '}';
    }
}
