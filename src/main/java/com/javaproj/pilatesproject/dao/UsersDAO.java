package com.javaproj.pilatesproject.dao;

import com.javaproj.pilatesproject.entities.Users;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class UsersDAO {
    private final static String UNIT_NAME = "pilatesPU";

    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

   public void create(Users user) {
    try {
        em.persist(user);
        em.flush();  // Wymusza zapis w bazie
    } catch (Exception e) {
        e.printStackTrace(); // Logowanie błędów
    }
}

    public Users findById(Long id) {
        return em.find(Users.class, id);
    }
    
    public List<Users> findAll() {
    Query query = em.createQuery("SELECT u FROM Users u");
    return query.getResultList();
    }
    
    public void update(Users user) {
        em.merge(user);  // Zaktualizowanie użytkownika w bazie danych
    
}

    public void delete(Long id) {
        Users user = findById(id);
        if (user != null) {
            em.remove(user);
        }
    }
}