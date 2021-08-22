package dev.sergevas.iot.growlabv1.hardware.boundary;

public class SystemInfoAdapter {

    private static SystemInfoAdapter instance;

    private SystemInfoAdapter() {
        super();
    }

    public static SystemInfoAdapter getInstance() {
        if (instance == null) {
            instance = new SystemInfoAdapter();
        }
        return instance;
    }

    public double getCpuTemp() {
        return 0;
    }
}
