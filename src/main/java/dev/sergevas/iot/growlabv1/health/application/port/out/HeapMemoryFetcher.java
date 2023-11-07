package dev.sergevas.iot.growlabv1.health.application.port.out;

import dev.sergevas.iot.growlabv1.health.domain.HeapMemory;

public interface HeapMemoryFetcher {

    HeapMemory getHeapMemory();
}
