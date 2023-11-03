package dev.sergevas.iot.growlabv1.bh1750.domain;

import java.util.Objects;
import java.util.StringJoiner;

public class Bh1750Readings {

    private double lightIntensity;

    public Bh1750Readings lightIntensity(double lightIntensity) {
        this.lightIntensity = lightIntensity;
        return this;
    }

    public double getLightIntensity() {
        return lightIntensity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bh1750Readings that = (Bh1750Readings) o;
        return Double.compare(that.lightIntensity, lightIntensity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lightIntensity);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Bh1750Readings.class.getSimpleName() + "[", "]")
                .add("lightIntensity=" + lightIntensity)
                .toString();
    }
}
