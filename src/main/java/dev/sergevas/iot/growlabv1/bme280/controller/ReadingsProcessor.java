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

    public Double compensateTemperatureReadings() {
        Double temp = null;
        double var1 = (rawR.getAdcT() / 16384.0 - trP.getDigT1() / 1024.0) * trP.getDigT2();
        double var2 = ((rawR.getAdcT() / 131072.0 - trP.getDigT1() / 8192.0) * (rawR.getAdcT() / 131072.0 - trP.getDigT1() / 8192.0)) * trP.getDigT3();
        double varsSum = var1 + var2;
        this.tFine = (int)varsSum;
        temp = varsSum / 5120.0;
        return temp;
    }

    public Double compensateHumidityReadings() {
        Double humid = null;
        return humid;
    }

    public Double compensatePressureReadings() {
        Double press = null;
        double var1 = this.tFine / 2.0 - 64000.0;
        double var2 = var1 * var1 * trP.getDigP6() / 32768.0;
        var2 = var2 + var1 * trP.getDigP5() * 2.0;
        var2 = var2 / 4.0 + trP.getDigP4() * 65536.0;
        return press;
    }
}

/*

// Returns pressure in Pa as double. Output value of “96386.2” equals 96386.2 Pa = 963.862 hPa
double BME280_compensate_P_double(BME280_S32_t adc_P)
{
double var1, var2, p;
var1 = ((double)t_fine/2.0) – 64000.0;
var2 = var1 * var1 * ((double)dig_P6) / 32768.0;
var2 = var2 + var1 * ((double)dig_P5) * 2.0;
var2 = (var2/4.0)+(((double)dig_P4) * 65536.0);
var1 = (((double)dig_P3) * var1 * var1 / 524288.0 + ((double)dig_P2) * var1) / 524288.0;
var1 = (1.0 + var1 / 32768.0)*((double)dig_P1);
if (var1 == 0.0)
{
return 0; // avoid exception caused by division by zero
}
p = 1048576.0 – (double)adc_P;
p = (p – (var2 / 4096.0)) * 6250.0 / var1;
var1 = ((double)dig_P9) * p * p / 2147483648.0;
var2 = p * ((double)dig_P8) / 32768.0;
p = p + (var1 + var2 + ((double)dig_P7)) / 16.0;
return p;
}
// Returns humidity in %rH as as double. Output value of “46.332” represents
46.332 %rH
double bme280_compensate_H_double(BME280_S32_t adc_H);
{
double var_H;
var_H = (((double)t_fine) – 76800.0);
var_H = (adc_H – (((double)dig_H4) * 64.0 + ((double)dig_H5) / 16384.0 *
var_H)) * (((double)dig_H2) / 65536.0 * (1.0 + ((double)dig_H6) /
67108864.0 * var_H *
(1.0 + ((double)dig_H3) / 67108864.0 * var_H)));
var_H = var_H * (1.0 – ((double)dig_H1) * var_H / 524288.0);
if (var_H > 100.0)
var_H = 100.0;
else if (var_H < 0.0)
var_H = 0.0;
return var_H;
}
Modifications reser
*
* */