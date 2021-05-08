package dev.sergevas.iot.growlab.camera.boundary;

import uk.co.caprica.picam.CameraException;

public class PiCamException extends RuntimeException {

    public PiCamException() {
    }

    public PiCamException(String message) {
        super(message);
    }

    public PiCamException(String message, Throwable cause) {
        super(message, cause);
    }

    public PiCamException(Throwable cause) {
        super(cause);
    }
}
