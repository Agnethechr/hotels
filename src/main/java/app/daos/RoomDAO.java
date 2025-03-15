package app.daos;

import app.dtos.HotelDTO;
import app.dtos.RoomDTO;
import app.entities.Hotel;
import app.entities.Room;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

public class RoomDAO {
    private static EntityManagerFactory emf;
    private static RoomDAO instance;

    private RoomDAO() {
    }

    public static RoomDAO getInstance(EntityManagerFactory _emf) {
        if (emf == null) {
            emf = _emf;
            instance = new RoomDAO();
        }
        return instance;
    }

    public Room create(Room room) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(room);
            em.getTransaction().commit();
            return room;
        } finally {
            em.close();
        }
    }

    public List<Room> readAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Room r", Room.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Room read(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Room.class, id);
        } finally {
            em.close();
        }
    }

    public Room update(int id, Room updatedRoom) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Room room = em.find(Room.class, id);
            if (room != null) {
                room.setNumber(updatedRoom.getNumber());
                room.setPrice(updatedRoom.getPrice());
                em.merge(room);
                em.getTransaction().commit();
            }
            return room;
        } finally {
            em.close();
        }
    }

    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Room room = em.find(Room.class, id);
            if (room != null) {
                em.remove(room);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}

