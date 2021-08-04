package dev.sergevas.iot.growlabv1.performance.controller;

import java.util.HashMap;
import java.util.Map;

public class Profiler {
    private static Map<String, Long> points = new HashMap<>();

    public static Long init(String aPointName) {
        return points.put(aPointName, System.currentTimeMillis());
    }

    public static Long current(String aPointName) {
        return System.currentTimeMillis() - points.get(aPointName);
    }

    public static Long stop(String aPointName) {
        return System.currentTimeMillis() - points.remove(aPointName);
    }

    public static String getCurrentMsg(String aStartPointName, String aCurrentPointName) {
        return new StringBuilder("Profile")
                .append(" [")
                .append(aCurrentPointName)
                .append("] ")
                .append(current(aStartPointName))
                .toString();
    }
}
