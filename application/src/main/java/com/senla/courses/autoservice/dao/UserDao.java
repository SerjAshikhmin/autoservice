package com.senla.courses.autoservice.dao;

import com.senla.courses.autoservice.dao.interfaces.IUserDao;
import com.senla.courses.autoservice.dao.jpadao.AbstractJpaDao;
import com.senla.courses.autoservice.dao.jpadao.DbJpaConnector;
import com.senla.courses.autoservice.model.security.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Repository
public class UserDao extends AbstractJpaDao<User> implements IUserDao {

    @Autowired
    DbJpaConnector dbJpaConnector;
    private EntityManager entityManager;

    @Override
    public User findByUserName(String userName) throws PersistenceException {
        User user;
        entityManager = dbJpaConnector.openSession();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> objCriteria = criteriaBuilder.createQuery(User.class);
        Root<User> objRoot = objCriteria.from(User.class);
        objCriteria.select(objRoot);
        objCriteria.where(criteriaBuilder.equal(objRoot.get("userName"), userName));
        user = entityManager.createQuery(objCriteria).getSingleResult();
        Hibernate.initialize(user.getAuthorities());
        if (!entityManager.getTransaction().isActive()) {
            dbJpaConnector.closeSession();
        }

        return user;
    }

    @Override
    public void addUser(User user) throws PersistenceException {
        insert(user);
    }
}
