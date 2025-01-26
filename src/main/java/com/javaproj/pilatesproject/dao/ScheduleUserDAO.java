package com.javaproj.pilatesproject.dao;

import com.javaproj.pilatesproject.entities.Schedule;
import com.javaproj.pilatesproject.entities.ScheduleUser;
import com.javaproj.pilatesproject.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class ScheduleUserDAO {

    private final static String UNIT_NAME = "pilatesPU";

    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(ScheduleUser scheduleUser) {
        try {
            em.persist(scheduleUser);
            em.flush();  // Wymusza zapis w bazie
        } catch (Exception e) {
            e.printStackTrace(); // Logowanie błędów
        }
    }

    public ScheduleUser findById(int id) {
        return em.find(ScheduleUser.class, id);
    }

    public List<ScheduleUser> findAll() {
        Query query = em.createQuery("SELECT s FROM ScheduleUser s");
        return query.getResultList();
    }

    public void update(ScheduleUser scheduleUser) {
        em.merge(scheduleUser);  // Zaktualizowanie użytkownika w bazie danych

    }

    public void delete(int id) {
        ScheduleUser scheduleUser = findById(id);
        if (scheduleUser != null) {
            em.remove(scheduleUser);
        }
    }

    public List<ScheduleUser> findByDay(String day) {
        Query query = em.createQuery("SELECT s FROM ScheduleUser s WHERE s.day = :day");
        query.setParameter("day", day);  // ustawienie parametru w zapytaniu
        return query.getResultList();
    }
    
    public int countUsers(Schedule scheduleId) {
        Query query = em.createQuery("SELECT COUNT(s) FROM ScheduleUser s WHERE s.scheduleId = :scheduleId");
        query.setParameter("scheduleId", scheduleId);
        return ((Long) query.getSingleResult()).intValue();
    }
    
    public ScheduleUser findByScheduleAndUser(Schedule schedule, User user) {
    try {
        // Tworzymy zapytanie JPQL, które sprawdza, czy istnieje ScheduleUser z danym schedule i user
        String jpql = "SELECT su FROM ScheduleUser su WHERE su.scheduleId = :schedule AND su.userId = :user";
        return em.createQuery(jpql, ScheduleUser.class)
                            .setParameter("schedule", schedule)
                            .setParameter("user", user)
                            .getResultList()
                            .stream()
                            .findFirst()
                            .orElse(null); // Zwraca pierwszy znaleziony wynik lub null, jeśli nie znaleziono
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


}
