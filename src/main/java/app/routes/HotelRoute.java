package app.routes;

import app.controllers.HotelController;
import app.dtos.HotelDTO;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class HotelRoute {
    private final HotelController hotelController = new HotelController();

    /*Javalin.create(config -> {
        config.router.contextPath = "/api";
        config.router.apiBuilder(() -> {
            path("hotel", () -> {
                post("/", (ctx) -> {
                    HotelDTO hotelDTO = ctx.bodyAsClass(HotelDTO.class);
                    hotelController.createHotel(hotelDTO);
                    ctx.status(201).json(hotelDTO);
                });
                get("/", (ctx) -> {
                    ctx.json(hotelController.getAllHotels());
                });
                post("/", (ctx) -> {
                    HotelDTO newHotel = ctx.bodyAsClass(HotelDTO.class);
                    HotelDTO returnedHotel = hotelDAO.save(newHotel);
                    ctx.json(returnedHotel);
                });
                put("/", (ctx) -> {
                    try {
                        HotelDTO hotel = ctx.bodyAsClass(HotelDTO.class);
                        HotelDTO updatedId = hotelDAO.updateById(Integer.parseInt(ctx.pathParam("id")), hotel);
                        ctx.json(updatedId);
                    } catch (Exception e) {
                        ctx.status(404).result("Hotel not found");
                    }
                });
                delete("/:id", (ctx) -> {
                    int id = Integer.parseInt(ctx.pathParam("id"));
                    boolean deleted = hotelDAO.deleteById(id);
                    if (deleted) {
                        ctx.status(204);
                    } else {
                        ctx.status(404).result("Hotel not found");
                    }
                });
            });*/
}
