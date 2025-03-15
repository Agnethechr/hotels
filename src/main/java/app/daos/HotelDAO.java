package app.daos;

import app.dtos.HotelDTO;
import app.entities.Hotel;
import app.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class HotelDAO {
    private static EntityManagerFactory emf;
    private static HotelDAO instance;

    private HotelDAO() {
    }

    public static HotelDAO getInstance(EntityManagerFactory _emf) {
        if (emf == null) {
            emf = _emf;
            instance = new HotelDAO();
        }
        return instance;
    }

    public HotelDTO create(HotelDTO hotelDTO){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Hotel hotel = new Hotel(hotelDTO);
            em.persist(hotel);
            em.getTransaction().commit();
            return new HotelDTO(hotel);
        }
    }

    public List<Hotel> readAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT h FROM Hotel h", Hotel.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Hotel read(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Hotel.class, id);
        } finally {
            em.close();
        }
    }

    public HotelDTO getById(int id){
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Hotel> query = em.createQuery("SELECT h FROM Hotel h WHERE h.id = :id", Hotel.class);
            query.setParameter("id",id);
            Hotel hotel = query.getSingleResult();

            HotelDTO hotelDTO = new HotelDTO();
            hotelDTO.setId(hotel.getId());
            hotelDTO.setName(hotel.getName());
            hotelDTO.setAddress(hotel.getAddress());

            return hotelDTO;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Hotel updateById(int id, Hotel updatedHotel) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Hotel hotel = em.find(Hotel.class, id);
            if (hotel != null) {
                hotel.setName(updatedHotel.getName());
                hotel.setAddress(updatedHotel.getAddress());
                hotel.setRooms(updatedHotel.getRooms());
                em.merge(hotel);
                em.getTransaction().commit();
            }
            return hotel;
        } finally {
            em.close();
        }
    }

    public void deleteById(Integer id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Hotel hotel = em.find(Hotel.class, id);

            if (hotel != null) {
                em.remove(hotel);
            }
            em.getTransaction().commit();
        }
    }

    public boolean addRoom(Hotel hotel, Room room) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            hotel = em.find(Hotel.class, hotel.getId());
            room.setHotel(hotel);
            em.persist(room);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean removeRoom(Hotel hotel, Room room) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Room roomToDelete = em.find(Room.class, room.getId());
            if (roomToDelete != null) {
                em.remove(roomToDelete);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            em.close();
        }
    }
}
