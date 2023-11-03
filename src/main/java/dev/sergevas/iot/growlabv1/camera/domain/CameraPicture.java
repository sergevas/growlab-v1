package dev.sergevas.iot.growlabv1.camera.domain;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

public class CameraPicture {

    private final byte[] content;

    public CameraPicture(byte[] content) {
        this.content = content;
    }

    public byte[] content() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CameraPicture that = (CameraPicture) o;
        return Arrays.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(content);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CameraPicture.class.getSimpleName() + "[", "]")
                .add("content length=" + Optional.ofNullable(content).map(c -> c.length).orElse(0))
                .toString();
    }
}
