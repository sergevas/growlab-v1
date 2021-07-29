package dev.sergevas.iot.bh1750.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("growlab/api/v1")
public class Bh1750Resource {

    @GET
    @Path("/sensors/light")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLightIntensity() {
        return new Bh1750Adapter().getLightIntensity();
    }
}
