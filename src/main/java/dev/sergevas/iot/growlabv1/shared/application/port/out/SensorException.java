package dev.sergevas.iot.growlabv1.shared.application.port.out;

import dev.sergevas.iot.growlabv1.shared.domain.SensorType;

import java.util.StringJoiner;

public class SensorException extends RuntimeException {

    private String eventId;
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

    public SensorException(String eventId, SensorType sensorType, String message, Throwable cause) {
        super(message, cause);
        this.eventId = eventId;
        this.sensorType = sensorType;
    }

    public SensorException(String eventId, SensorType sensorType, String message) {
        super(message);
        this.eventId = eventId;
        this.sensorType = sensorType;
    }


    public SensorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getEventId() {
        return eventId;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SensorException.class.getSimpleName() + "[", "]")
                .add(super.toString())
                .add("eventId='" + eventId + "'")
                .add("sensorType=" + sensorType)
                .toString();
    }
}
