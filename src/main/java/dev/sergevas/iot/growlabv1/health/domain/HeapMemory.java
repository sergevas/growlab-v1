package dev.sergevas.iot.growlabv1.health.domain;

import java.util.Formatter;
import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;

public class HeapMemory extends FormattedMemoryVolume {

    private final long freeMemory;
    private final long totalMemory;
    private final long maxMemory;

    public HeapMemory(long freeMemory, long totalMemory, long maxMemory) {
        this.freeMemory = freeMemory;
        this.totalMemory = totalMemory;
        this.maxMemory = maxMemory;
    }

    public long freeMemory() {
        return freeMemory;
    }

    public long totalMemory() {
        return totalMemory;
    }

    public long maxMemory() {
        return maxMemory;
    }

    public long usedMemory() {
        return totalMemory - freeMemory;
    }

    public String percentFree() {
        Formatter formatter = new Formatter(Locale.US);
        return formatter.format("%.2f%%", 100 * ((double) (maxMemory - usedMemory()) / maxMemory)).toString();
    }

    public String free() {
        return format(freeMemory);
    }

    public String max() {
        return format(maxMemory);
    }

    public String total() {
        return format(totalMemory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeapMemory that = (HeapMemory) o;
        return freeMemory == that.freeMemory && totalMemory == that.totalMemory && maxMemory == that.maxMemory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(freeMemory, totalMemory, maxMemory);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HeapMemory.class.getSimpleName() + "[", "]")
                .add("freeMemory=" + freeMemory)
                .add("totalMemory=" + totalMemory)
                .add("maxMemory=" + maxMemory)
                .add("usedMemory=" + usedMemory())
                .toString();
    }
}
