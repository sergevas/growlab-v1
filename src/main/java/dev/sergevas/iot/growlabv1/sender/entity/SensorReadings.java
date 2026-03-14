package dev.sergevas.iot.growlabv1.sender.entity;

import dev.sergevas.iot.growlabv1.camera.model.CameraMode;

import java.time.Instant;
import java.util.Objects;

public class SensorReadings {

    private final Double temperature;
    private final Double humidity;
    private final Double pressure;
    private final Double light;
    private final Instant timestamp;
    private final CameraMode cameraMode;

    public SensorReadings(Double temperature, Double humidity, Double pressure, Double light, Instant timestamp, CameraMode cameraMode) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.light = light;
        this.timestamp = timestamp;
        this.cameraMode = cameraMode;
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

    public Double getLight() {
        return light;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public CameraMode getCameraMode() {
        return cameraMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorReadings that = (SensorReadings) o;
        return Objects.equals(temperature, that.temperature) && Objects.equals(humidity, that.humidity)
                && Objects.equals(pressure, that.pressure) && Objects.equals(light, that.light)
                && Objects.equals(timestamp, that.timestamp) && cameraMode == that.cameraMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, humidity, pressure, light, timestamp, cameraMode);
    }

    @Override
    public String toString() {
        return "SensorReadings{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", light=" + light +
                ", timestamp=" + timestamp +
                ", cameraMode=" + cameraMode +
                '}';
    }
}
