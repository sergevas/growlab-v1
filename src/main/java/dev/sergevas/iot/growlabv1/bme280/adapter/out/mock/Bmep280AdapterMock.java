package dev.sergevas.iot.growlabv1.bme280.adapter.out.mock;

import dev.sergevas.iot.growlabv1.bme280.application.port.out.ThpReader;
import dev.sergevas.iot.growlabv1.bme280.domain.Bmep280Readings;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultBean
public class Bmep280AdapterMock implements ThpReader {
    @Override
    public Bmep280Readings readThp() {
        return new Bmep280Readings()
                .temperature(20.35)
                .humidity(78.75)
                .pressure(97549.49)
                .id("0x60");
    }
}
