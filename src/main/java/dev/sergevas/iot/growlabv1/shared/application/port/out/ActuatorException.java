package dev.sergevas.iot.growlabv1.shared.application.port.out;

public class ActuatorException extends RuntimeException {

    private final String eventId;

    public ActuatorException(String eventId, String message, Throwable cause) {
        super(message, cause);
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }
}
