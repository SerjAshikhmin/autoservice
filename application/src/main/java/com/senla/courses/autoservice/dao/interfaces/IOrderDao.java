package com.senla.courses.autoservice.dao.interfaces;

import com.senla.courses.autoservice.exceptions.orderexceptions.OrderNotFoundException;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.util.List;


@Repository
public interface IOrderDao {

    int addOrder(Order order) throws PersistenceException;
    int removeOrder(Order order) throws PersistenceException;
    Order getOrderById(int id) throws PersistenceException;
    List<Order> getAllOrders() throws PersistenceException;
    void setAllOrders(List<Order> allOrders);
    int updateOrder(Order order) throws PersistenceException;
    List<Master> getMastersByOrder(Order order) throws OrderNotFoundException;
    void updateAllOrders(List<Order> orders);

}
