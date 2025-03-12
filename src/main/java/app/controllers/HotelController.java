package app.controllers;

import app.config.HibernateConfig;
import app.daos.HotelDAO;
import app.dtos.HotelDTO;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HotelController {
    private HotelDAO hotelDAO;

    private List<HotelDTO> hotels = new ArrayList<>();

    public HotelController() {
        this.hotelDAO = HotelDAO.getInstance(HibernateConfig.getEntityManagerFactory());
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            this.hotels = objectMapper.readValue(new File("hotels.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, HotelDAO.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<HotelDTO> getAllHotels(){return hotels;}

    public void createHotel(HotelDTO hotelDTO){hotelDAO.save(hotelDTO);}

    public HotelDTO createNewHotel(HotelDTO hotelDTO){
        hotelDAO.save(hotelDTO);
        return hotelDTO;
    }
}
