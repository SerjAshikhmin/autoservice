package com.senla.courses.autoservice.dao.jpadao;

import com.senla.courses.autoservice.dao.interfaces.IJpaDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class AbstractJpaDao<T> implements IJpaDao<T> {

    @Autowired
    DbJpaConnector dbJpaConnector;
    private EntityManager entityManager;
    private Class<T> clazz;

    public AbstractJpaDao() {
        this.clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public int insert(T obj) throws PersistenceException {
        entityManager = dbJpaConnector.openSession();
        entityManager.persist(obj);
        if (!entityManager.getTransaction().isActive()) {
            dbJpaConnector.closeSession();
        }
        return 1;
    }

    @Override
    public int delete(T obj) throws PersistenceException {
        entityManager = dbJpaConnector.openSession();
        entityManager.remove(entityManager.merge(obj));
        if (!entityManager.getTransaction().isActive()) {
            dbJpaConnector.closeSession();
        }
        return 1;
    }

    @Override
    public int update(T obj) throws PersistenceException {
        entityManager = dbJpaConnector.openSession();
        entityManager.merge(obj);
        if (!entityManager.getTransaction().isActive()) {
            dbJpaConnector.closeSession();
        }
        return 1;
    }

    @Override
    public T findById(int id) throws PersistenceException {
        T obj;
        entityManager = dbJpaConnector.openSession();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> objCriteria = criteriaBuilder.createQuery(clazz);
        Root<T> objRoot = objCriteria.from(clazz);
        objCriteria.select(objRoot);
        objCriteria.where(criteriaBuilder.equal(objRoot.get("id"), id));
        obj = entityManager.createQuery(objCriteria).getSingleResult();
        if (!entityManager.getTransaction().isActive()) {
            dbJpaConnector.closeSession();
        }

        return obj;
    }

    @Override
    public List<T> findAll() throws PersistenceException {
        List<T> allObjects;
        entityManager = dbJpaConnector.openSession();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> objCriteria = criteriaBuilder.createQuery(clazz);
        Root<T> objRoot = objCriteria.from(clazz);
        objCriteria.select(objRoot);
        allObjects = entityManager.createQuery(objCriteria).getResultList();
        if (!entityManager.getTransaction().isActive()) {
            dbJpaConnector.closeSession();
        }

        return allObjects;
    }
}
