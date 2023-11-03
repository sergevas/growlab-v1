package dev.sergevas.iot.growlabv1.camera.adapter.in.http;

import dev.sergevas.iot.growlabv1.camera.appication.port.in.CameraUseCase;
import dev.sergevas.iot.growlabv1.camera.domain.CameraMode;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Path("/camera")
public class PiCamResource {

    @Inject
    CameraUseCase cameraUseCase;

    @GET
    @Path("/image")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getImage() {
        return Response.ok(cameraUseCase.takePicture().content()).build();
    }

    @GET
    @Path("/mode")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCameraMode() {
        var cameraMode = cameraUseCase.getMode();
        var cameraModeType = new CameraModeType()
                .mode(ModeEnum.valueOf(cameraMode.name()))
                .modeTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
        return Response.ok(cameraModeType).build();
    }

    @PUT
    @Path("/mode")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCameraMode(@Valid CameraModeSetType cameraModeSetType) {
        cameraUseCase.updateMode(CameraMode.valueOf(cameraModeSetType.getMode().name()));
        return Response.ok().build();
    }
}
