package dev.sergevas.iot.growlabv1.shared.domain;

public enum ErrorEventId {
    E_BH1750_0001("E-BH1750-0001", "BH1750 data read error"),
    E_CAMERA_0001("E-CAMERA-0001", "Camera init error"),
    E_CAMERA_0002("E-CAMERA-0002", "Camera image take error"),
    E_CAMERA_0003("E-CAMERA-0003", "Camera mode read error"),
    E_CAMERA_0004("E-CAMERA-0004", "Camera mode update error"),

    E_BMEP280_0001("E-BMEP280-0001", "BMEP280 THP data read error"),
    E_BMEP280_0002("E-BMEP280-0002", "BMEP280 THP raw data burst read timeout"),

    E_SYSTEM_0001("E_SYSTEM_0001", "CPU temperature read error"),
    E_SYSTEM_0002("E_SYSTEM_0002", "Disk space fetch error");

    private final String id;
    private final String name;

    private ErrorEventId(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
