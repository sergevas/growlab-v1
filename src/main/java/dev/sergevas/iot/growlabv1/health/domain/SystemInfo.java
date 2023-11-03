package dev.sergevas.iot.growlabv1.health.domain;

import java.util.Objects;
import java.util.StringJoiner;

public class SystemInfo {
    private final double cpuTemp;

    public SystemInfo(double cpuTemp) {
        this.cpuTemp = cpuTemp;
    }

    public double cpuTemp() {
        return cpuTemp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemInfo that = (SystemInfo) o;
        return Double.compare(cpuTemp, that.cpuTemp) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpuTemp);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SystemInfo.class.getSimpleName() + "[", "]")
                .add("cpuTemp=" + cpuTemp)
                .toString();
    }
}
