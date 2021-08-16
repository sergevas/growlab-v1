package dev.sergevas.iot.growlabv1.bme280.model;

public class CtrlHumRegister {

    public static final int ADDR = 0xF2;

    private byte osrsH;

    public CtrlHumRegister osrsH(byte osrsH) {
        this.osrsH = osrsH;
        return this;
    }

    public byte getValue() {
        return osrsH;
    }
}
