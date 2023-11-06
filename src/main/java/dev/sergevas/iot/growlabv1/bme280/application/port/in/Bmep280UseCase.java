package dev.sergevas.iot.growlabv1.bme280.application.port.in;

import dev.sergevas.iot.growlabv1.bme280.domain.Bmep280Readings;

public interface Bmep280UseCase {
    Bmep280Readings getThpReadings();
}
