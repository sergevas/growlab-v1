package dev.sergevas.iot.growlabv1.bme280.application.service;

import dev.sergevas.iot.growlabv1.bme280.application.port.in.Bmep280UseCase;
import dev.sergevas.iot.growlabv1.bme280.application.port.out.ThpReader;
import dev.sergevas.iot.growlabv1.bme280.domain.Bmep280Readings;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Bmep280Service implements Bmep280UseCase {

    @Inject
    ThpReader thpReader;

    @Override
    public Bmep280Readings getThpReadings() {
        return thpReader.readThp();
    }
}
