package app.routes;

import app.security.rest.ISecurityController;
import app.security.rest.SecurityController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.config.JavalinConfig;

import static io.javalin.apibuilder.ApiBuilder.path;

public class ApplicationConfig
{
    private static ApplicationConfig instance;
    private static Javalin app;
    private static JavalinConfig javalinConfig;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ISecurityController securityController = new SecurityController();

    private ApplicationConfig() {}

    public static ApplicationConfig getInstance() {
        if (instance ==null) {
            instance = new ApplicationConfig();
        }
        return instance;
    }

    public ApplicationConfig initiateServer() {
        app = Javalin.create(config -> {
            javalinConfig = config;
            config.http.defaultContentType = "application/json";
            config.router.contextPath = "/api";
            config.bundledPlugins.enableRouteOverview("/routes");
            config.bundledPlugins.enableDevLogging();
        });
        return instance;
    }

    public ApplicationConfig setRoute(EndpointGroup route) {
        javalinConfig.router.apiBuilder( () -> {
            path("/", route);
        });
        return instance;
    }

    // Adding below methods to ApplicationConfig, means that EVERY ROUTE will be checked for security roles. So open routes must have a role of ANYONE
    public ApplicationConfig checkSecurityRoles() {
        app.beforeMatched(securityController.authenticate()); // check if there is a valid token in the header
        app.beforeMatched(securityController.authorize()); // check if the user has the required role
        return instance;
    }

    public ApplicationConfig startServer(int port) {
        app.start(port);
        return instance;
    }

    public ApplicationConfig handleExceptions() {
        app.exception(Exception.class, (e,ctx) -> {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("msg", e.getMessage());
            ctx.status(500).json(node);
        });
        return instance;
    }

    public static void stopServer() {
        app.stop();
        app = null;
    }
}