package com.senla.courses.autoservice.dao;

import com.senla.courses.autoservice.dao.interfaces.IOrderDao;
import com.senla.courses.autoservice.exceptions.OrderNotFoundException;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.model.Order;
import com.senla.courses.autoservice.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class OrderDao implements IOrderDao {

    private List<Order> orders = new ArrayList<>();

    @Override
    public boolean addOrder(Order order) {
        return orders.add(order);
    }

    @Override
    public boolean removeOrder(Order order) {
        if (order == null) {
            return false;
        }
        order.getGaragePlace().setBusy(false);
        order.getMasters().stream()
                .forEach(master -> master.setBusy(false));
        return orders.remove(order);
    }

    @Override
    public Order getOrderById(int id) {
        try {
            return orders.stream()
                    .filter(order -> order.getId() == id)
                    .findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders() {
        return orders;
    }

    @Override
    public void setAllOrders(List<Order> allOrders) {
        this.orders = allOrders;
    }

    @Override
    public Order updateOrder(Order order) {
        Order daoOrder = getOrderById(order.getId());
        return updateOrderFields(order, daoOrder);
    }

    @Override
    public void cancelOrder(Order order) {
        Order daoOrder = getOrderById(order.getId());
        daoOrder.getGaragePlace().setBusy(false);
        order.getMasters().stream()
                .forEach(master -> master.setBusy(false));
        daoOrder.setStatus(OrderStatus.CANCELED);
    }

    @Override
    public void closeOrder(Order order) {
        Order daoOrder = getOrderById(order.getId());
        daoOrder.getGaragePlace().setBusy(false);
        order.getMasters().stream()
                .forEach(master -> master.setBusy(false));
        daoOrder.setEndDate(LocalDateTime.now());
        daoOrder.setStatus(OrderStatus.COMPLETED);
    }

    public void updateOrderTime(Order order, LocalDateTime newStartTime, LocalDateTime newEndTime) {
        Order daoOrder = getOrderById(order.getId());
        daoOrder.setStartDate(newStartTime);
        daoOrder.setEndDate(newEndTime);
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
        this.orders = orders;
    }

    @Override
    public List<Order> getAllOrdersInProgress(Comparator orderComparator) {
        List<Order> completedOrders = getAllOrders().stream()
                .filter(order -> order.getStatus() == OrderStatus.IN_WORK)
                .collect(Collectors.toList());

        if (orderComparator != null) {
            completedOrders.sort(orderComparator);
        }
        return completedOrders;
    }

    private Order updateOrderFields(Order order, Order daoOrder) {
        daoOrder.setSubmissionDate(order.getSubmissionDate());
        daoOrder.setStartDate(order.getStartDate());
        daoOrder.setEndDate(order.getEndDate());
        daoOrder.setKindOfWork(order.getKindOfWork());
        daoOrder.setCost(order.getCost());
        daoOrder.setGaragePlace(order.getGaragePlace());
        daoOrder.setMasters(order.getMasters());
        daoOrder.setStatus(order.getStatus());
        return daoOrder;
    }

}