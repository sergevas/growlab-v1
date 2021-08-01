package dev.sergevas.iot.growlabv1.shared.exception;

import dev.sergevas.iot.growlabv1.shared.model.SensorType;

public class SensorException extends RuntimeException {

    private SensorType sensorType;

    public SensorException() {
    }

    public SensorException(String message) {
        super(message);
    }

    public SensorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SensorException(Throwable cause) {
        super(cause);
    }

    public SensorException(SensorType sensorType, Throwable cause) {
        super(cause);
        this.sensorType = sensorType;
    }

    public SensorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SensorType getSensorType() {
        return sensorType;
    }
}
