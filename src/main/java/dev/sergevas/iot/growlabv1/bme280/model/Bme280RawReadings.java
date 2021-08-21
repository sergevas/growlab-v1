package dev.sergevas.iot.growlabv1.bme280.model;

public class Bme280RawReadings {

    public static final int ADDR = 0xF7;
    public static final int READINGS_LENGTH = 8;

    public int abcTemperature;
    public int abcHumidity;
    public int abcPressure;

    private byte[] readings = new byte[READINGS_LENGTH];

    public byte[] getReadings() {
        return readings;
    }

    public Bme280RawReadings init() {
        return this;

    }

    public int getAbcTemperature() {
        return this.abcTemperature;
    }

    public int getAbcHumidity() {
        return this.abcHumidity;
    }

    public int getAbcPressure() {
        return this.abcPressure;
    }

    public void computeTemperature() {
        this.abcTemperature = Byte.toUnsignedInt(readings[3]) << 12
                | Byte.toUnsignedInt(readings[4]) << 4
                | Byte.toUnsignedInt(readings[5]) >> 4;
    }

    public void computeHumidity() {
        this.abcHumidity = Byte.toUnsignedInt(readings[6]) << 8 | Byte.toUnsignedInt(readings[7]);
    }

    public void computePressure() {
        this.abcPressure = Byte.toUnsignedInt(readings[0]) << 12
                | Byte.toUnsignedInt(readings[1]) << 4
                | Byte.toUnsignedInt(readings[2]) >> 4;
    }
}
