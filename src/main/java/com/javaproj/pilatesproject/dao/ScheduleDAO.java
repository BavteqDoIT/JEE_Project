package com.javaproj.pilatesproject.dao;

import com.javaproj.pilatesproject.entities.Schedule;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class ScheduleDAO {

    private final static String UNIT_NAME = "pilatesPU";

    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(Schedule schedule) {
        try {
            em.persist(schedule);
            em.flush();  // Wymusza zapis w bazie
        } catch (Exception e) {
            e.printStackTrace(); // Logowanie błędów
        }
    }

    public Schedule findById(Long id) {
        return em.find(Schedule.class, id);
    }

    public List<Schedule> findAll() {
        Query query = em.createQuery("SELECT s FROM Schedule s");
        return query.getResultList();
    }

    public void update(Schedule schedule) {
        em.merge(schedule);  // Zaktualizowanie użytkownika w bazie danych

    }

    public void delete(Long id) {
        Schedule schedule = findById(id);
        if (schedule != null) {
            em.remove(schedule);
        }
    }

    public List<Schedule> findByDay(String day) {
        Query query = em.createQuery("SELECT s FROM Schedule s WHERE s.day = :day");
        query.setParameter("day", day);  // ustawienie parametru w zapytaniu
        return query.getResultList();
    }
}
