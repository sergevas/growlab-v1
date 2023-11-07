package dev.sergevas.iot.growlabv1.health.domain;

import java.util.Formatter;
import java.util.Locale;

/**
 * Some chunks of the code is from
 * io\helidon\health\helidon-health-checks\2.6.4\helidon-health-checks-2.6.4-sources.jar!\io\helidon\health\checks\DiskSpaceHealthCheck.java
 */

public abstract class FormattedMemoryVolume {

    private static final long KB = 1024;
    private static final long MB = 1024 * KB;
    private static final long GB = 1024 * MB;
    private static final long TB = 1024 * GB;
    private static final long PB = 1024 * TB;

    public String format(long bytes) {
        //Formatter ensures that returned delimiter will be always the same
        final Formatter formatter = new Formatter(Locale.US);
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
}
