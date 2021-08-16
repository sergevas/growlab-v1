package dev.sergevas.iot.growlabv1.bme280.model;

public class ConfigRegister {

    public static final int ADDR = 0xF5;

    private byte tSb;
    private byte filter;
    private byte spi3wEn;

    private byte value = -1;

    public ConfigRegister tSb(byte tSb) {
        this.tSb = tSb;
        return this;
    }

    public ConfigRegister filter(byte filter) {
        this.filter = filter;
        return this;
    }

    public ConfigRegister spi3wEn(byte spi3wEn) {
        this.spi3wEn = spi3wEn;
        return this;
    }

    public byte getValue() {
        if (value == -1) {
            value = (byte)(tSb << 5 | filter << 2 | spi3wEn);
        }
        return value;
    }
}
