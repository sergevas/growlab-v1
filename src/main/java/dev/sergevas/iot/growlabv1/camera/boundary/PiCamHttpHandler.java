package dev.sergevas.iot.growlabv1.camera.boundary;

import io.helidon.common.http.MediaType;
import io.helidon.config.Config;
import io.helidon.webserver.Handler;
import io.helidon.webserver.ResponseHeaders;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

import java.util.logging.Logger;

public class PiCamHttpHandler implements Handler {

    private static final Logger LOG = Logger.getLogger(PiCamHttpHandler.class.getName());
    private PiCamAdapter piCamAdapter;

    public PiCamHttpHandler(Config config) {
        this.piCamAdapter = PiCamAdapter.getInstance();
        this.piCamAdapter.installNativeLib();
        this.piCamAdapter.createCameraConfiguration(config);
    }

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        LOG.info("Handle request... " + req);
        byte[] rawPicture = this.piCamAdapter.takePictureWithCamRecover();
        ResponseHeaders headers = res.headers();
        headers.contentType(MediaType.APPLICATION_OCTET_STREAM);
        res.send(rawPicture);
    }
}
