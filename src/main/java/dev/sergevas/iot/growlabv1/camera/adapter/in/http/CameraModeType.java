package dev.sergevas.iot.growlabv1.camera.adapter.in.http;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.Objects;

/*
 * The Raspberry Pi IR-CUT camera current mode of operation.
 */
public class CameraModeType {

    @NotNull
    @JsonbProperty("mode")
    private ModeEnum mode;

    @NotNull
    @JsonbProperty("mode_timestamp")
    private OffsetDateTime modeTimestamp;

    /**
     * The Raspberry Pi IR-CUT camera mode.
     **/
    public CameraModeType mode(ModeEnum mode) {
        this.mode = mode;
        return this;
    }

    public ModeEnum getMode() {
        return mode;
    }

    public void setMode(ModeEnum mode) {
        this.mode = mode;
    }

    /**
     * The camera mode fetch timestamp
     **/
    public CameraModeType modeTimestamp(OffsetDateTime modeTimestamp) {
        this.modeTimestamp = modeTimestamp;
        return this;
    }

    public OffsetDateTime getModeTimestamp() {
        return modeTimestamp;
    }

    public void setModeTimestamp(OffsetDateTime modeTimestamp) {
        this.modeTimestamp = modeTimestamp;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CameraModeType cameraModeType = (CameraModeType) o;
        return Objects.equals(mode, cameraModeType.mode) &&
                Objects.equals(modeTimestamp, cameraModeType.modeTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode, modeTimestamp);
    }

    @Override
    public String toString() {
        return "class CameraModeType {\n" +
                "    mode: " + toIndentedString(mode) + "\n" +
                "    modeTimestamp: " + toIndentedString(modeTimestamp) + "\n" +
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
