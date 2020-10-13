package com.senla.courses.autoservice.dao;

import com.senla.courses.autoservice.dao.interfaces.IOrderDao;
import com.senla.courses.autoservice.dao.jpadao.AbstractJpaDao;
import com.senla.courses.autoservice.dao.jpadao.DbJpaConnector;
import com.senla.courses.autoservice.exceptions.orderexceptions.OrderNotFoundException;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.model.Order;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository
public class OrderDao extends AbstractJpaDao<Order> implements IOrderDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    DbJpaConnector dbJpaConnector;

    @Override
    public int addOrder(Order order) throws PersistenceException {
        return insert(order);
    }

    @Override
    public int removeOrder(Order order) throws PersistenceException {
        return delete(order);
    }

    @Override
    public Order getOrderById(int id) throws PersistenceException {
        Order order;
        entityManager = dbJpaConnector.openSession();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> objCriteria = criteriaBuilder.createQuery(Order.class);
        Root<Order> objRoot = objCriteria.from(Order.class);
        objCriteria.select(objRoot);
        objCriteria.where(criteriaBuilder.equal(objRoot.get("id"), id));
        order = entityManager.createQuery(objCriteria).getSingleResult();
        Hibernate.initialize(order.getMasters());
        if (!entityManager.getTransaction().isActive()) {
            dbJpaConnector.closeSession();
        }

        return order;
    }

    @Override
    public List<Order> getAllOrders() throws PersistenceException {
        return findAll();
    }

    @Override
    public List<Order> findAll() throws PersistenceException {
        List<Order> allOrders;
        entityManager = dbJpaConnector.openSession();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> objCriteria = criteriaBuilder.createQuery(Order.class);
        Root<Order> objRoot = objCriteria.from(Order.class);
        objCriteria.select(objRoot);
        allOrders = entityManager.createQuery(objCriteria).getResultList();
        for (Order order : allOrders) {
            Hibernate.initialize(order.getMasters());
        }
        if (!entityManager.getTransaction().isActive()) {
            dbJpaConnector.closeSession();
        }

        return allOrders;
    }

    @Override
    public void setAllOrders(List<Order> allOrders) {
        //this.orders = allOrders;
    }

    @Override
    public int updateOrder(Order order) throws PersistenceException {
        return update(order);
    }

    public List<Master> getMastersByOrder (Order order) throws OrderNotFoundException {
        if (order != null) {
            return order.getMasters();
        } else {
            throw new OrderNotFoundException("Order not found");
        }
    }

    @Override
    public void updateAllOrders(List<Order> orders) {
        //this.orders = orders;
    }

}
