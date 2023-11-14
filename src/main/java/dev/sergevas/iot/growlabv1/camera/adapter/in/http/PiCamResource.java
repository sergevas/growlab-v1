package dev.sergevas.iot.growlabv1.camera.adapter.in.http;

import dev.sergevas.iot.growlabv1.camera.appication.port.in.CameraUseCase;
import dev.sergevas.iot.growlabv1.camera.domain.CameraMode;
import dev.sergevas.iot.growlabv1.camera.domain.CameraMode.Mode;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;

@Path("/actuators/camera")
public class PiCamResource {

    @Inject
    CameraUseCase cameraUseCase;

    @GET
    @Path("/image")
    @Produces({APPLICATION_OCTET_STREAM, APPLICATION_JSON})
    public Response getImage() {
        return Response.ok(cameraUseCase.takePicture().content()).build();
    }

    @GET
    @Path("/mode")
    @Produces(APPLICATION_JSON)
    public Response getCameraMode() {
        var cameraMode = cameraUseCase.getMode();
        var cameraModeType = new CameraModeType()
                .mode(ModeEnum.valueOf(cameraMode.mode().name()))
                .modeTimestamp(cameraMode.modeTimestamp());
        return Response.ok(cameraModeType).build();
    }

    @PUT
    @Path("/mode")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response updateCameraMode(@Valid CameraModeSetType cameraModeSetType) {
        cameraUseCase.updateMode(new CameraMode(Mode.valueOf(cameraModeSetType.getMode().name())));
        return Response.ok().build();
    }
}
