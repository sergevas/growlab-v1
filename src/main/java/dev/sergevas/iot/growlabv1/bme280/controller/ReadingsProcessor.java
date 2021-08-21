package dev.sergevas.iot.growlabv1.bme280.controller;

import dev.sergevas.iot.growlabv1.bme280.model.Bme280RawReadings;
import dev.sergevas.iot.growlabv1.bme280.model.Bme280Readings;
import dev.sergevas.iot.growlabv1.bme280.model.TrimmingParameters;

public class ReadingsProcessor {

    private Bme280RawReadings bme280RawReadings;
    private TrimmingParameters trimmingParameters;

    public ReadingsProcessor bme280RawReadings(Bme280RawReadings bme280RawReadings) {
        this.bme280RawReadings = bme280RawReadings;
        return this;
    }

    public ReadingsProcessor trimmingParameters(TrimmingParameters trimmingParameters) {
        this.trimmingParameters = trimmingParameters;
        return this;
    }

    public Bme280Readings compensateReadings() {
        Bme280Readings bme280Readings = new Bme280Readings();
        return bme280Readings;
    }
}
