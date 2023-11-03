package dev.sergevas.iot.growlabv1.bme280.domain;

public class Bme280RawReadings {

    public static final int ADDR = 0xF7;
    public static final int READINGS_LENGTH = 8;

    public int adcTemperature;
    public int adcHumidity;
    public int adcPressure;

    private byte[] readings = new byte[READINGS_LENGTH];

    public byte[] getReadings() {
        return readings;
    }

    public Bme280RawReadings computeAdcValues() {
        this.computeTemperature();
        this.computeHumidity();
        this.computePressure();
        return this;

    }

    public int getAdcT() {
        return this.adcTemperature;
    }

    public int getAdcH() {
        return this.adcHumidity;
    }

    public int getAdcP() {
        return this.adcPressure;
    }

    public void computeTemperature() {
        this.adcTemperature = Byte.toUnsignedInt(readings[3]) << 12
                | Byte.toUnsignedInt(readings[4]) << 4
                | Byte.toUnsignedInt(readings[5]) >> 4;
    }

    public void computeHumidity() {
        this.adcHumidity = Byte.toUnsignedInt(readings[6]) << 8 | Byte.toUnsignedInt(readings[7]);
    }

    public void computePressure() {
        this.adcPressure = Byte.toUnsignedInt(readings[0]) << 12
                | Byte.toUnsignedInt(readings[1]) << 4
                | Byte.toUnsignedInt(readings[2]) >> 4;
    }
}
