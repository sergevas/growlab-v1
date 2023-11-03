package dev.sergevas.iot.growlabv1.bme280.domain;

public enum StandbyTime {

    T_SB_0_5((byte) 0b000),
    T_SB_62_5((byte) 0b001),
    T_SB_125((byte) 0b010),
    T_SB_250((byte) 0b011),
    T_SB_500((byte) 0b100),
    T_SB_1000((byte) 0b101),
    T_SB_10((byte) 0b110),
    T_SB_20((byte) 0b111);

    private final byte val;

    StandbyTime(byte val) {
        this.val = val;
    }

    public byte val() {
        return val;
    }
}
