package dev.sergevas.iot.growlabv1.bme280.domain;

public enum Spi3Wire {

    OFF((byte) 0b0),
    ON((byte) 0b1);

    private final byte val;

    Spi3Wire(byte val) {
        this.val = val;
    }

    public byte val() {
        return val;
    }
}
