package com.senla.courses.autoservice.dao;

import com.senla.courses.autoservice.dao.interfaces.IOrderDao;
import com.senla.courses.autoservice.dao.jpadao.AbstractJpaDao;
import com.senla.courses.autoservice.dao.jpadao.DbJpaConnector;
import com.senla.courses.autoservice.exceptions.OrderNotFoundException;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.model.Order;
import com.senla.courses.autoservice.model.enums.OrderStatus;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class OrderDao extends AbstractJpaDao<Order> implements IOrderDao {

    @PersistenceContext
    private EntityManager entityManager;

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
        entityManager = DbJpaConnector.openSession();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> objCriteria = criteriaBuilder.createQuery(Order.class);
        Root<Order> objRoot = objCriteria.from(Order.class);
        objCriteria.select(objRoot);
        objCriteria.where(criteriaBuilder.equal(objRoot.get("id"), id));
        order = entityManager.createQuery(objCriteria).getSingleResult();
        Hibernate.initialize(order.getMasters());
        if (!entityManager.getTransaction().isActive()) {
            DbJpaConnector.closeSession();
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
        entityManager = DbJpaConnector.openSession();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> objCriteria = criteriaBuilder.createQuery(Order.class);
        Root<Order> objRoot = objCriteria.from(Order.class);
        objCriteria.select(objRoot);
        allOrders = entityManager.createQuery(objCriteria).getResultList();
        for (Order order : allOrders) {
            Hibernate.initialize(order.getMasters());
        }
        if (!entityManager.getTransaction().isActive()) {
            DbJpaConnector.closeSession();
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

    @Override
    public void cancelOrder(Order order) throws PersistenceException {
        Order daoOrder = findById(order.getId());
        daoOrder.getGaragePlace().setBusy(false);
        order.getMasters().stream()
                .forEach(master -> master.setBusy(false));
        daoOrder.setStatus(OrderStatus.CANCELED);
        updateOrder(daoOrder);
    }

    @Override
    public void closeOrder(Order order) throws PersistenceException {
        Order daoOrder = findById(order.getId());
        daoOrder.getGaragePlace().setBusy(false);
        order.getMasters().stream()
                .forEach(master -> master.setBusy(false));
        daoOrder.setEndDate(LocalDateTime.now());
        daoOrder.setStatus(OrderStatus.COMPLETED);
        updateOrder(daoOrder);
    }

    public void updateOrderTime(Order order, LocalDateTime newStartTime, LocalDateTime newEndTime) throws PersistenceException {
        Order daoOrder = findById(order.getId());
        daoOrder.setStartDate(newStartTime);
        daoOrder.setEndDate(newEndTime);
        updateOrder(daoOrder);
    }

    public List<Master> getMastersByOrder (Order order) throws OrderNotFoundException {
        if (order != null) {
            return order.getMasters();
        } else {
            throw new OrderNotFoundException();
        }
    }

    @Override
    public void updateAllOrders(List<Order> orders) {
        //this.orders = orders;
    }

    @Override
    public List<Order> getAllOrdersInProgress(Comparator orderComparator) throws PersistenceException {
        List<Order> completedOrders = getAllOrders().stream()
                .filter(order -> order.getStatus() == OrderStatus.IN_WORK)
                .collect(Collectors.toList());

        if (orderComparator != null) {
            completedOrders.sort(orderComparator);
        }
        return completedOrders;
    }

}
