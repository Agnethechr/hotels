package app.security.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private static Logger logger = LoggerFactory.getLogger(Routes.class);
    private static ISecurityController securityController = new SecurityController();
    private static ObjectMapper objectMapper = new ObjectMapper();


    public static EndpointGroup getRoutes() {
        return () ->
        {
            path("auth", () -> {
                post("register", securityController.register());
                post("login", securityController.login());
            });
            path("secured", () -> {
                before(ctx->securityController.authenticate());
                before(ctx->securityController.authorize());
                get("demo", (ctx) -> ctx.json(objectMapper.createObjectNode().put("demo","hello")),Role.USER);
            });
        };
    }
    public enum Role implements RouteRole { ANYONE, USER, ADMIN }
}
