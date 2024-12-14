package com.javaproj.pilatesproject.dao;

import com.javaproj.pilatesproject.entities.Class;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class ClassDAO {
    private final static String UNIT_NAME = "pilatesPU";

    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

   public void create(Class classes) {
    try {
        em.persist(classes);
        em.flush();  // Wymusza zapis w bazie
    } catch (Exception e) {
        e.printStackTrace(); // Logowanie błędów
    }
}

    public Class findById(Long id) {
        return em.find(Class.class, id);
    }
    
    public List<Class> findAll() {
    Query query = em.createQuery("SELECT c FROM Class c");
    return query.getResultList();
    }
    
    public void update(Class classes) {
        em.merge(classes);  // Zaktualizowanie użytkownika w bazie danych
    
}

    public void delete(Long id) {
        Class classes = findById(id);
        if (classes != null) {
            em.remove(classes);
        }
    }
}