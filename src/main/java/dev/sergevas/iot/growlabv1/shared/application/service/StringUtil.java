package dev.sergevas.iot.growlabv1.shared.application.service;

public class StringUtil {
    public static void appendHexString(final StringBuilder builder, final byte[] bytes) {
        for (byte b : bytes) {
            builder.append(String.format("%X", b));
        }
    }

    public static String toHexString(final byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        appendHexString(sb, bytes);
        return sb.toString().trim();
    }

    public static String toHexString(final int val) {
        return String.format("%X", val);
    }
}
