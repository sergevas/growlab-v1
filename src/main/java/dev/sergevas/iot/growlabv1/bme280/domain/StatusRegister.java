package dev.sergevas.iot.growlabv1.bme280.domain;

public class StatusRegister {

    public static final int ADDR = 0xF3;

    private int val;

    public StatusRegister val(int val) {
        this.val = val;
        return this;
    }

    public boolean isConversationRunning() {
        return (0x0008 & val) == 0x0008;
    }

    public boolean isResultsTransferred() {
        return (0x0008 & val) == 0;
    }
}
