package dev.sergevas.iot.growlabv1.renderer.control.entity;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class SensorReadings {
    private Instant time;
    private Double temperature;
    private Double pressure;
    private Integer pressureMmhg;
    private Double humidity;
    private Double light;
    private String cameraMode;

    public SensorReadings(Instant time, Double temperature, Double pressure, Integer pressureMmhg,
                          Double humidity, Double light, String cameraMode) {
        this.time = time;
        this.temperature = temperature;
        this.pressure = pressure;
        this.pressureMmhg = pressureMmhg;
        this.humidity = humidity;
        this.light = light;
        this.cameraMode = cameraMode;
    }

    public Instant getTime() {
        return time;
    }

    public String getMoscowTime() {
        return DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss", new Locale("ru"))
                .format(time.atOffset(ZoneOffset.of("+03:00")));
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getPressureMmhg() {
        return pressureMmhg;
    }

    public void setPressureMmhg(Integer pressureMmhg) {
        this.pressureMmhg = pressureMmhg;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getLight() {
        return light;
    }

    public void setLight(Double light) {
        this.light = light;
    }

    public String getCameraMode() {
        return cameraMode;
    }

    public void setCameraMode(String cameraMode) {
        this.cameraMode = cameraMode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SensorReadings that = (SensorReadings) o;
        return Objects.equals(time, that.time) && Objects.equals(temperature, that.temperature)
                && Objects.equals(pressure, that.pressure) && Objects.equals(pressureMmhg, that.pressureMmhg)
                && Objects.equals(humidity, that.humidity) && Objects.equals(light, that.light)
                && Objects.equals(cameraMode, that.cameraMode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, temperature, pressure, pressureMmhg, humidity, light, cameraMode);
    }

    @Override
    public String toString() {
        return "SensorReadings{" +
                "time=" + time +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", pressureMmhg=" + pressureMmhg +
                ", humidity=" + humidity +
                ", light=" + light +
                ", cameraMode='" + cameraMode + '\'' +
                '}';
    }
}
