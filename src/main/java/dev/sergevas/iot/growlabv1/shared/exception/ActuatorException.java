package dev.sergevas.iot.growlabv1.shared.exception;

import dev.sergevas.iot.growlabv1.shared.model.SensorType;

public class ActuatorException extends RuntimeException {

    private String eventId;

    public ActuatorException() {
    }

    public ActuatorException(String message) {
        super(message);
    }

    public ActuatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActuatorException(Throwable cause) {
        super(cause);
    }

    public ActuatorException(String eventId, String message, Throwable cause) {
        super(message, cause);
        this.eventId = eventId;
    }

    public ActuatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getEventId() {
        return eventId;
    }
}
