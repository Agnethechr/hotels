package app.routes;

import app.config.HibernateConfig;
import app.controllers.HotelController;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final HotelRoute hotelRoute = new HotelRoute();
    private final RoomRoute roomRoute = new RoomRoute();
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
   // private static HotelController hotelController = new HotelController(emf);

    public EndpointGroup getRoutes() {
        return () -> {
//            path("/hotels", hotelRoute.getRoutes());
//            path("/rooms", roomRoute.getRoutes());
            path("/hotel",() ->{
               // get("/", hotelController::getAllHotels);
            });
        };
    }
}