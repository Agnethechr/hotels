package app.controllers;

import app.config.HibernateConfig;
import app.daos.HotelDAO;
import app.daos.RoomDAO;
import app.dtos.HotelDTO;
import app.dtos.RoomDTO;
import app.entities.Hotel;
import app.entities.Room;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotelController {
    private HotelDAO hotelDAO;
    private RoomDAO roomDAO;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static List<HotelDTO> hotels = new ArrayList<>();

    public HotelController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.hotelDAO = HotelDAO.getInstance(emf);
    }

    public void readFromFile() throws IOException {
        JsonNode node = objectMapper.readTree(new File("src/main/hotels.json"));
        List<HotelDTO> hotelsDTO = objectMapper.convertValue(node, new TypeReference<List<HotelDTO>>() {});

        for (HotelDTO hotelDTO : hotelsDTO) {
            Hotel createdHotel = new Hotel(hotelDAO.create(hotelDTO));


            for (RoomDTO roomDTO : hotelDTO.getRooms()) {

                Room room = new Room();
                room.setNumber(roomDTO.getNumber());
                room.setPrice(roomDTO.getPrice());
                room.setHotel(createdHotel);
            }
        }
    }


    public void create(Context ctx) {
        HotelDTO hotelDTO = ctx.bodyAsClass(HotelDTO.class);
        HotelDTO createdHotel = hotelDAO.create(hotelDTO);
        ctx.json(createdHotel);
    }

    public void readAll(Context ctx) {
        ctx.json(hotelDAO.readAll());
    }

    public void read(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(hotelDAO.read(id));
    }

    public void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Hotel updatedHotel = ctx.bodyAsClass(Hotel.class);
        ctx.json(hotelDAO.updateById(id, updatedHotel));
    }

    public void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        hotelDAO.deleteById(id);
        ctx.status(204);
    }
}
