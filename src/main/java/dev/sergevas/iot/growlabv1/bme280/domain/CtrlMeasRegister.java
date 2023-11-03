package dev.sergevas.iot.growlabv1.bme280.domain;

public class CtrlMeasRegister {

    public static final int ADDR = 0xF4;

    private byte osrsT;
    private byte osrsP;
    private byte mode;

    private byte value = -1;

    public CtrlMeasRegister osrsT(byte osrsT) {
        this.osrsT = osrsT;
        return this;
    }

    public CtrlMeasRegister osrsP(byte osrsP) {
        this.osrsP = osrsP;
        return this;
    }

    public CtrlMeasRegister mode(byte osrsMode) {
        this.mode = osrsMode;
        return this;
    }

    public byte getValue() {
        if (value == -1) {
            value = (byte) (osrsT << 5 | osrsT << 2 | mode);
        }
        return value;
    }
}
