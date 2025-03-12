package app.controllers;

import app.daos.RoomDAO;
import app.dtos.HotelDTO;
import app.dtos.RoomDTO;
import app.config.HibernateConfig;
import app.entities.Room;
import io.javalin.http.Context;

import java.util.List;

public class RoomController {
    private final RoomDAO roomDAO = RoomDAO.getInstance(HibernateConfig.getEntityManagerFactory());

    public void createRoom(Context ctx) {
        RoomDTO roomDTO = ctx.bodyAsClass(RoomDTO.class);
        RoomDTO createdRoom = roomDAO.save(roomDTO);
        if (createdRoom != null) {
            ctx.status(201).json(createdRoom);
        } else {
            ctx.status(400).result("Failed to create room");
        }
    }
}