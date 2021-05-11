package com.senla.courses.autoservice.dao.jpadao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;


@Repository
public class DbJpaConnector {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public EntityManager openSession() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    public void closeSession() {
        entityManager.close();
    }

    public EntityTransaction getTransaction() {
        return openSession().getTransaction();
    }

}
