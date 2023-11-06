package dev.sergevas.iot.growlabv1.bme280.application.port.out;

import dev.sergevas.iot.growlabv1.bme280.domain.Bmep280Readings;

public interface ThpReader {

    Bmep280Readings readThp();
}
