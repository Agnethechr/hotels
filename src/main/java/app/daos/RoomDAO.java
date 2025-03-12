package app.daos;

import app.dtos.RoomDTO;
import app.entities.Hotel;
import app.entities.Room;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

public class RoomDAO {
    private static EntityManagerFactory emf;
    private static RoomDAO instance;

    private RoomDAO() {}

    public static RoomDAO getInstance(EntityManagerFactory _emf) {
        if (emf == null) {
            emf = _emf;
            instance = new RoomDAO();
        }
        return instance;
    }

    public RoomDTO save(RoomDTO roomDTO) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Hotel hotel = em.find(Hotel.class, roomDTO.getHotelId());
            if (hotel == null) {
                throw new IllegalArgumentException("Hotel not found");
            }

            Room room = new Room();
            room.setId(roomDTO.getId());
            room.setHotel(hotel);
            room.setNumber(roomDTO.getNumber());
            room.setPrice(roomDTO.getPrice());

            em.persist(room);
            transaction.commit();
            return new RoomDTO(room);
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        } finally {
            em.close();
        }
    }

    public RoomDTO getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Room room = em.find(Room.class, id);
            if (room == null) {
                return null;
            }
            return new RoomDTO(room);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public RoomDTO updateById(int id, RoomDTO roomDTO) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Room room = em.find(Room.class, id);
            if (room == null) {
                return null;
            }

            room.setNumber(roomDTO.getNumber());
            room.setPrice(roomDTO.getPrice());

            em.merge(room);
            transaction.commit();
            return new RoomDTO(room);
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        } finally {
            em.close();
        }
    }

    public boolean deleteById(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Room room = em.find(Room.class, id);
            if (room != null) {
                em.remove(room);
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

    public List<RoomDTO> getRoomsForHotel(int hotelId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Room> query = em.createQuery("SELECT r FROM Room r WHERE r.hotel.id = :hotelId", Room.class);
            query.setParameter("hotelId", hotelId);
            return query.getResultList().stream().map(RoomDTO::new).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
}
