package dev.sergevas.iot.growlabv1.shared.model;

public enum ErrorEventId {
    E_BH1750_0001("E-BH1750-0001", "BH1750 data read error"),
    E_CAMERA_0001("E-CAMERA-0001", "Camera mode read error"),
    E_CAMERA_0002("E-CAMERA-0002", "Camera mode update error");

    private String id;
    private String name;

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
