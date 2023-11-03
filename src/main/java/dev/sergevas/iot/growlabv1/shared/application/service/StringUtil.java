package dev.sergevas.iot.growlabv1.shared.application.service;

public class StringUtil {
    public static void appendHexString(StringBuilder builder, byte[] bytes) {
        for (byte b : bytes) {
            builder.append(String.format("%02X ", b));
        }
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        appendHexString(sb, bytes);
        return sb.toString().trim();
    }
}
