package com.javaproj.pilatesproject.dao;

import com.javaproj.pilatesproject.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class UserDAO {
    private final static String UNIT_NAME = "pilatesPU";

    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

   public void create(User user) {
    try {
        em.persist(user);
        em.flush();  // Wymusza zapis w bazie
    } catch (Exception e) {
        e.printStackTrace(); // Logowanie błędów
    }
}

    public User findById(Long id) {
        return em.find(User.class, id);
    }
    
    public List<User> findAll() {
    Query query = em.createQuery("SELECT u FROM User u");
    return query.getResultList();
    }
    
    public void update(User user) {
        em.merge(user);  // Zaktualizowanie użytkownika w bazie danych
    
}

    public void delete(Long id) {
        User user = findById(id);
        if (user != null) {
            em.remove(user);
        }
    }
}