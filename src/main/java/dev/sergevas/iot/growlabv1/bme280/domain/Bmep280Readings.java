package dev.sergevas.iot.growlabv1.bme280.domain;

import java.time.OffsetDateTime;
import java.util.Objects;

public class Bmep280Readings {

    private String id;
    private Double temperature;
    private Double humidity;
    private Double pressure;
    private OffsetDateTime thpTimestamp;

    public Bmep280Readings() {
    }

    public Bmep280Readings(String id, Double temperature, Double humidity, Double pressure, OffsetDateTime thpIntensityTimestamp) {
        this.id = id;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.thpTimestamp = thpIntensityTimestamp;
    }

    public String getId() {
        return id;
    }

    public Bmep280Readings id(String id) {
        this.id = id;
        return this;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Bmep280Readings temperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Bmep280Readings humidity(Double humidity) {
        this.humidity = humidity;
        return this;
    }

    public Double getPressure() {
        return pressure;
    }

    public Bmep280Readings pressure(Double pressure) {
        this.pressure = pressure;
        return this;
    }

    public OffsetDateTime getThpTimestamp() {
        return thpTimestamp;
    }

    public Bmep280Readings thpTimestamp(OffsetDateTime thpTimestamp) {
        this.thpTimestamp = thpTimestamp;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bmep280Readings that = (Bmep280Readings) o;
        return Objects.equals(id, that.id) && Objects.equals(temperature, that.temperature)
                && Objects.equals(humidity, that.humidity) && Objects.equals(pressure, that.pressure)
                && Objects.equals(thpTimestamp, that.thpTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, temperature, humidity, pressure, thpTimestamp);
    }

    @Override
    public String toString() {
        return "Bmep280Readings{" +
                "id='" + id + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", thpTimestamp=" + thpTimestamp +
                '}';
    }
}
