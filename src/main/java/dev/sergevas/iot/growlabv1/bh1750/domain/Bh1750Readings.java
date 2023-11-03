package dev.sergevas.iot.growlabv1.bh1750.domain;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class Bh1750Readings {

    private final double lightIntensity;
    private final OffsetDateTime lightIntensityTimestamp;

    public Bh1750Readings(double lightIntensity, OffsetDateTime lightIntensityTimestamp) {
        this.lightIntensity = lightIntensity;
        this.lightIntensityTimestamp = lightIntensityTimestamp;
    }

    public double lightIntensity() {
        return lightIntensity;
    }

    public OffsetDateTime lightIntensityTimestamp() {
        return lightIntensityTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bh1750Readings that = (Bh1750Readings) o;
        return Double.compare(lightIntensity, that.lightIntensity) == 0 && Objects.equals(lightIntensityTimestamp, that.lightIntensityTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lightIntensity, lightIntensityTimestamp);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Bh1750Readings.class.getSimpleName() + "[", "]")
                .add("lightIntensity=" + lightIntensity)
                .add("lightIntensityTimestamp=" + lightIntensityTimestamp)
                .toString();
    }
}
