package dev.sergevas.iot.growlabv1.health.domain;

import java.util.Formatter;
import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Some chunks of the code is from
 * io\helidon\health\helidon-health-checks\2.6.4\helidon-health-checks-2.6.4-sources.jar!\io\helidon\health\checks\DiskSpaceHealthCheck.java
 */

public class DiskSpace extends FormattedMemoryVolume {

    private final long diskFreeInBytes;
    private final long totalInBytes;

    public DiskSpace(long diskFreeInBytes, long totalInBytes) {
        this.diskFreeInBytes = diskFreeInBytes;
        this.totalInBytes = totalInBytes;
    }

    public long diskFreeInBytes() {
        return diskFreeInBytes;
    }

    public long totalInBytes() {
        return totalInBytes;
    }

    public long usedInBytes() {
        return totalInBytes - diskFreeInBytes;
    }

    public String percentFree() {
        final Formatter formatter = new Formatter(Locale.US);
        return formatter.format("%.2f%%", 100 * ((double) diskFreeInBytes / totalInBytes)).toString();
    }

    public String free() {
        return format(diskFreeInBytes);
    }

    public String total() {
        return format(totalInBytes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiskSpace diskSpace = (DiskSpace) o;
        return Double.compare(diskFreeInBytes, diskSpace.diskFreeInBytes) == 0 && Double.compare(totalInBytes, diskSpace.totalInBytes) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(diskFreeInBytes, totalInBytes);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DiskSpace.class.getSimpleName() + "[", "]")
                .add("diskFreeInBytes=" + diskFreeInBytes)
                .add("totalInBytes=" + totalInBytes)
                .add("usedInBytes='" + usedInBytes() + "'")
                .add("percentFree='" + percentFree() + "'")
                .add("free='" + free() + "'")
                .add("total='" + total() + "'")
                .toString();
    }
}
