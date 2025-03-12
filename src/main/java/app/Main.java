package app;

import app.config.ApplicationConfig;

public class Main {
    public static void main(String[] args) {
        ApplicationConfig
                .getInstance()
                .initiateServer()
                //.setRoute(Routes.getRoutes())
                .handleException()
                .startServer(7070);
    }
}
