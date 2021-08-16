package dev.sergevas.iot.growlabv1.bme280.model;

public enum Filter {

    OFF((byte)0b000),
    F_2((byte)0b001),
    F_4((byte)0b010),
    F_8((byte)0b011),
    F_16((byte)0b100);

    private byte val;

    Filter(byte val) {
        this.val = val;
    }

    public byte val() {
        return val;
    }
}
