package dev.sergevas.iot.growlabv1.bme280.model;

public class StatusRegister {

    public static final int ADDR = 0xF3;

    private byte measuring;
    private byte imUpdate;

    private byte value = -1;

    public StatusRegister osrsT(byte osrsT) {
        this.osrsT = osrsT;
        return this;
    }

    public StatusRegister osrsP(byte osrsP) {
        this.osrsP = osrsP;
        return this;
    }

    public StatusRegister mode(byte osrsMode) {
        this.mode = osrsMode;
        return this;
    }

    public byte getValue() {
        if (value == -1) {
            value = (byte)(osrsT << 5 | osrsT << 2 | mode);
        }
        return value;
    }
}
