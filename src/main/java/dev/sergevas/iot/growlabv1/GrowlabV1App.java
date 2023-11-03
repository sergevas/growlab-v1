
package dev.sergevas.iot.growlabv1;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
//import io.helidon.common.LogConfig;
//import io.helidon.config.Config;
//import io.helidon.health.HealthSupport;
//import io.helidon.health.checks.HealthChecks;
//import io.helidon.media.jsonp.JsonpSupport;
//import io.helidon.openapi.OpenAPISupport;
//import io.helidon.webserver.Routing;
//import io.helidon.webserver.WebServer;

@ApplicationPath("/growlab/api/v1")
public class GrowlabV1App extends Application {

//    /**
//     * Application main entry point.
//     * @param args command line arguments.
//     */
//    public static void main(final String[] args) {
//        startServer();
//    }
//
//    /**
//     * Start the server.
//     * @return the created {@link WebServer} instance
//     */
//    static WebServer startServer() {
//        LogConfig.configureRuntime();
//        Config config = Config.create();
//
//        WebServer server = WebServer.builder(createRouting(config))
//                .config(config.get("server"))
//                .addMediaSupport(JsonpSupport.create())
//                .build();
//
//        server.start()
//                .thenAccept(ws -> {
//                    System.out.println("GrowLab server is up! http://0.0.0.0:" + ws.port() + "/growlab/api/v1");
//                    ws.whenShutdown().thenRun(() -> {
//                        Pi4JContextFactory.shutdown();
//                        PiCamAdapter.create().closeCamera();
//                        System.out.println("GrowLab server is down.");
//                    });
//                })
//                .exceptionally(t -> {
//                    Pi4JContextFactory.shutdown();
//                    System.err.println("Startup failed: " + t.getMessage());
//                    t.printStackTrace(System.err);
//                    return null;
//                });
//        return server;
//    }
//
//    private static Routing createRouting(Config config) {
//        HealthSupport health = HealthSupport.builder()
//                .addLiveness(HealthChecks.healthChecks())
//                .addLiveness(SystemInfoHealthCheck.create())
//                .build();
//
//        return Routing.builder()
//                .register("/growlab/api/v1", health)
//                .register("/growlab/api/v1", OpenAPISupport.create(config.get(OpenAPISupport.Builder.CONFIG_KEY)))
//                .register("/growlab/api/v1/sensors", new SensorsHttpService(config))
//                .register("/growlab/api/v1/actuators", new ActuatorsHttpService(config))
//                .error(SensorException.class, new SensorsErrorHandler())
//                .error(ActuatorException.class, new ActuatorsErrorHandler())
//                .build();
//    }
}
