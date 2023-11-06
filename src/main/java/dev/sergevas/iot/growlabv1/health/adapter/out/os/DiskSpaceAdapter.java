package dev.sergevas.iot.growlabv1.health.adapter.out.os;

import dev.sergevas.iot.growlabv1.health.application.port.out.DiskSpaceFetcher;
import dev.sergevas.iot.growlabv1.health.domain.DiskSpace;
import dev.sergevas.iot.growlabv1.shared.application.port.out.HardwareException;
import dev.sergevas.iot.growlabv1.shared.application.port.out.SensorException;
import dev.sergevas.iot.growlabv1.shared.domain.SensorType;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

import static dev.sergevas.iot.growlabv1.shared.domain.ErrorEventId.E_SYSTEM_0002;

/**
 * Some chunks of the code is from
 * io\helidon\health\helidon-health-checks\2.6.4\helidon-health-checks-2.6.4-sources.jar!\io\helidon\health\checks\DiskSpaceHealthCheck.java
 */

@ApplicationScoped
public class DiskSpaceAdapter implements DiskSpaceFetcher {

    @ConfigProperty(name = "growlabv1.healthcheck.diskSpace.path", defaultValue = ".")
    String diskSpacePath;
    private FileStore fileStore;

    @PostConstruct
    public void init() {
        try {
            this.fileStore = Files.getFileStore(Paths.get(diskSpacePath));
        } catch (IOException ioe) {
            String errorMsgFormatted = "Unable to get FileStore located at %s";
            Log.errorf(ioe, errorMsgFormatted, diskSpacePath);
            throw new HardwareException(String.format(errorMsgFormatted, diskSpacePath), ioe);
        }
    }

    @Override
    public DiskSpace getDiskSpace() {
        try {
            return new DiskSpace(fileStore.getUsableSpace(), fileStore.getTotalSpace());
        } catch (IOException ioe) {
            Log.error(ioe);
            Log.error("Unable to get DiskSpace ", ioe);
            throw new SensorException(E_SYSTEM_0002.getId(), SensorType.DISK_SPACE, E_SYSTEM_0002.getName(), ioe);
        }
    }
}
