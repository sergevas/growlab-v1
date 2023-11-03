package dev.sergevas.iot.growlabv1.camera.adapter.in.http;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class CameraModeSetType {

    @NotNull
    @JsonbProperty("mode")
    private ModeEnum mode;

    /**
     * The Raspberry Pi IR-CUT camera operation mode.
     * Possible values:
     *   - NORM - normal mode
     *   - NIGHT - night mode
     **/
    public CameraModeSetType mode(ModeEnum mode) {
        this.mode = mode;
        return this;
    }

    public ModeEnum getMode() {
        return mode;
    }

    public void setMode(ModeEnum mode) {
        this.mode = mode;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CameraModeSetType cameraModeSetType = (CameraModeSetType) o;
        return Objects.equals(mode, cameraModeSetType.mode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode);
    }

    @Override
    public String toString() {
        return "class CameraModeSetType {\n" +
                "    mode: " + toIndentedString(mode) + "\n" +
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
