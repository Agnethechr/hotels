package app.routes;

import app.config.HibernateConfig;
import app.controllers.HotelController;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static HotelController hotelController = new HotelController();

    public static EndpointGroup getRoutes() {
        return () -> {
            post("/", hotelController::create);
            get("/", hotelController::readAll);
            get("/{id}", hotelController::read);
            put("/{id}", hotelController::update);
            delete("/{id}", hotelController::delete);
        };
    }
}