package dev.sergevas.iot.growlabv1.health.application.port.in;

import dev.sergevas.iot.growlabv1.health.domain.HeapMemory;

public interface HeapMemoryUseCase {

    HeapMemory getHeapMemory();

    boolean isThresholdReached(HeapMemory heapMemory);
}
