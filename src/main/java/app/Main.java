package app;

import app.config.HibernateConfig;
import app.controllers.HotelController;
import app.routes.ApplicationConfig;
import app.routes.Routes;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;

public class Main {

    final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    final static HotelController hotelController = new HotelController();

    public static void main(String[] args) throws IOException {

        //Udkommenter denne linje efter programmet er kørt første gang for at tilføje fra json til databasen.
        //hotelController.readFromFile();


        ApplicationConfig.getInstance()
                .initiateServer()
                .setRoute(Routes.getRoutes())
                .handleExceptions()
                .startServer(7070);
    }
}
