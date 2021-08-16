package dev.sergevas.iot.growlabv1.bme280.model;

public enum Mode {

    SLEEP((byte)0b00),
    // FORCED_1 and FORCED_2 have an identical behaviour
    FORCED_1((byte)0b01),
    FORCED_2((byte)0b10),
    NORMAL((byte)0b11);

    private final byte val;

    Mode(byte val) {
        this.val = val;
    }

    public byte val() {
        return val;
    }
}
