package com.javaproj.pilatesproject.dao;

import com.javaproj.pilatesproject.entities.Pass;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class PassDAO {
    private final static String UNIT_NAME = "pilatesPU";

    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

   public void create(Pass pass) {
    try {
        em.persist(pass);
        em.flush();  // Wymusza zapis w bazie
    } catch (Exception e) {
        e.printStackTrace(); // Logowanie błędów
    }
}

    public Pass findById(Long id) {
        return em.find(Pass.class, id);
    }
    
    public List<Pass> findAll() {
    Query query = em.createQuery("SELECT p FROM Pass p");
    return query.getResultList();
    }
    
    public void update(Pass pass) {
        em.merge(pass);  // Zaktualizowanie użytkownika w bazie danych
    
}

    public void delete(Long id) {
        Pass pass = findById(id);
        if (pass != null) {
            em.remove(pass);
        }
    }
}