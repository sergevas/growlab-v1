package dev.sergevas.iot.growlabv1.bme280.domain;

import java.util.Objects;

public class Bme280Readings {

    private String id;
    private Double temperature;
    private Double humidity;
    private Double pressure;

    public Bme280Readings() {
        super();
    }

    public Bme280Readings id(String id) {
        this.id = id;
        return this;
    }

    public Bme280Readings temperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public Bme280Readings humidity(Double humidity) {
        this.humidity = humidity;
        return this;
    }

    public Bme280Readings pressure(Double pressure) {
        this.pressure = pressure;
        return this;
    }

    public String getId() {
        return id;
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
        return Objects.equals(id, that.id) && Objects.equals(temperature, that.temperature) && Objects.equals(humidity, that.humidity) && Objects.equals(pressure, that.pressure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, temperature, humidity, pressure);
    }

    @Override
    public String toString() {
        return "Bme280Readings{" +
                "id='" + id + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                '}';
    }
}
