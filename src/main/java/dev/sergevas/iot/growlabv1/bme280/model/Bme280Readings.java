package dev.sergevas.iot.growlabv1.bme280.model;

import java.util.Objects;

public class Bme280Readings {

    private Double temperature;
    private Double humidity;
    private Double pressure;

    public Bme280Readings(Double temperature, Double humidity, Double pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bme280Readings that = (Bme280Readings) o;
        return Objects.equals(temperature, that.temperature) && Objects.equals(humidity, that.humidity) && Objects.equals(pressure, that.pressure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, humidity, pressure);
    }

    @Override
    public String toString() {
        return "Bme280Readings{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                '}';
    }
}
