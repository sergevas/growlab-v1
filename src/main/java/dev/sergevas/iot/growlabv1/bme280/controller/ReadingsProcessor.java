package dev.sergevas.iot.growlabv1.bme280.controller;

import dev.sergevas.iot.growlabv1.bme280.model.Bme280RawReadings;
import dev.sergevas.iot.growlabv1.bme280.model.Bme280Readings;
import dev.sergevas.iot.growlabv1.bme280.model.TrimmingParameters;

public class ReadingsProcessor {

    private Bme280RawReadings rawR;
    private TrimmingParameters trP;

    private int tFine;

    public ReadingsProcessor bme280RawReadings(Bme280RawReadings bme280RawReadings) {
        this.rawR = bme280RawReadings;
        return this;
    }

    public ReadingsProcessor trimmingParameters(TrimmingParameters trimmingParameters) {
        this.trP = trimmingParameters;
        return this;
    }

    public Bme280Readings compensateReadings() {
        Bme280Readings bme280Readings = new Bme280Readings()
                .temperature(this.compensateTemperatureReadings())
                .humidity(this.compensateHumidityReadings())
                .pressure(this.compensatePressureReadings());
        return bme280Readings;
    }

    // Returns temperature in DegC, double precision. Output value of “51.23” equals 51.23 DegC.
    public double compensateTemperatureReadings() {
        double var1 = (rawR.getAdcT() / 16384.0 - trP.getDigT1() / 1024.0) * trP.getDigT2();
        double var2 = ((rawR.getAdcT() / 131072.0 - trP.getDigT1() / 8192.0)
                * (rawR.getAdcT() / 131072.0 - trP.getDigT1() / 8192.0)) * trP.getDigT3();
        double varsSum = var1 + var2;
        this.tFine = (int)varsSum;
        double temp = varsSum / 5120.0;
        return temp;
    }

    // Returns humidity in %rH as double. Output value of “46.332” represents 46.332 %rH
    public Double compensateHumidityReadings() {
        double humid = this.tFine - 76800.0;
        humid = (rawR.getAdcH() - (trP.getDigH4() * 64.0 + trP.getDigH5() / 16384.0 * humid))
                * (trP.getDigH2() / 65536.0 * (1.0 + trP.getDigH6() / 67108864.0 * humid
                * (1.0 + trP.getDigH3() / 67108864.0 * humid)));
        humid = humid * (1.0 - trP.getDigH1() * humid / 524288.0);
        if (humid > 100.0) {
            humid = 100.0;
        } else if (humid < 0.0) {
            humid = 0.0;
        }
        return humid;
    }

    // Returns pressure in Pa as double. Output value of “96386.2” equals 96386.2 Pa = 963.862 hPa
    public double compensatePressureReadings() {
        double var1 = this.tFine / 2.0 - 64000.0;
        double var2 = var1 * var1 * trP.getDigP6() / 32768.0;
        var2 = var2 + var1 * trP.getDigP5() * 2.0;
        var2 = var2 / 4.0 + trP.getDigP4() * 65536.0;
        var1 = (trP.getDigP3() * var1 * var1 / 524288.0 + trP.getDigP2() * var1) / 524288.0;
        var1 = (1.0 + var1 / 32768.0) * trP.getDigP1();
        if (var1 == 0.0) {
            return 0.0; // avoid exception caused by division by zero
        }
        double press = 1048576.0 - rawR.getAdcP();
        press = (press - var2 / 4096.0) * 6250.0 / var1;
        var1 = trP.getDigP9() * press * press / 2147483648.0;
        var2 = press * trP.getDigP8() / 32768.0;
        press = press + (var1 + var2 + trP.getDigP7()) / 16.0;
        return press;
    }
}
