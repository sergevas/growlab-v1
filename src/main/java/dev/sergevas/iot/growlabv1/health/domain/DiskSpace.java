package dev.sergevas.iot.growlabv1.health.domain;

import java.util.Formatter;
import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Some chunks of the code is from
 * io\helidon\health\helidon-health-checks\2.6.4\helidon-health-checks-2.6.4-sources.jar!\io\helidon\health\checks\DiskSpaceHealthCheck.java
 */

public class DiskSpace {

    private static final long KB = 1024;
    private static final long MB = 1024 * KB;
    private static final long GB = 1024 * MB;
    private static final long TB = 1024 * GB;
    private static final long PB = 1024 * TB;

    private final long diskFreeInBytes;
    private final long totalInBytes;
    private final Formatter formatter;

    public DiskSpace(long diskFreeInBytes, long totalInBytes) {
        this.diskFreeInBytes = diskFreeInBytes;
        this.totalInBytes = totalInBytes;
        formatter = new Formatter(Locale.US);
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
        return formatter.format("%.2f%%", 100 * ((double) diskFreeInBytes / totalInBytes)).toString();
    }

    public String free() {
        return format(diskFreeInBytes);
    }

    public String total() {
        return format(totalInBytes);
    }

    public String format(long bytes) {
        //Formatter ensures that returned delimiter will be always the same
        Formatter formatter = new Formatter(Locale.US);
        if (bytes >= PB) {
            return formatter.format("%.2f PB", bytes / (double) PB).toString();
        } else if (bytes >= TB) {
            return formatter.format("%.2f TB", bytes / (double) TB).toString();
        } else if (bytes >= GB) {
            return formatter.format("%.2f GB", bytes / (double) GB).toString();
        } else if (bytes >= MB) {
            return formatter.format("%.2f MB", bytes / (double) MB).toString();
        } else if (bytes >= KB) {
            return formatter.format("%.2f KB", bytes / (double) KB).toString();
        } else {
            return bytes + " bytes";
        }
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
