package dev.sergevas.iot.growlabv1.bme280.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusRegisterTest {

    static StatusRegister statusRegisterRunning;
    static StatusRegister statusRegisterResultsTransfered;

    @BeforeAll
    static void setup() {
        statusRegisterRunning = new StatusRegister().val((byte)0b01111011);
        statusRegisterResultsTransfered = new StatusRegister().val((byte)0b01110011);
    }

    @Test
    void isConversationRunning() {
        assertTrue(statusRegisterRunning.isConversationRunning());
        assertFalse(statusRegisterRunning.isResultsTransfered());
    }

    @Test
    void isResultsTransfered() {
        assertTrue(statusRegisterResultsTransfered.isResultsTransfered());
        assertFalse(statusRegisterResultsTransfered.isConversationRunning());
    }
}
