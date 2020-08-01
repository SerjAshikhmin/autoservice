package com.senla.courses.autoservice.controller;

import com.lib.dicontainer.annotations.InjectByType;
import com.lib.dicontainer.annotations.Singleton;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.model.Order;
import com.senla.courses.autoservice.model.enums.OrderStatus;
import com.senla.courses.autoservice.service.interfaces.IOrderService;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class OrderController {

    @InjectByType
    private IOrderService orderService;

    public boolean addOrder(int id, LocalDateTime submissionDate, LocalDateTime startDate, LocalDateTime endDate,
                            String kindOfWork, int cost, int garageId, int garagePlaceId, String masterName, OrderStatus orderStatus) {
        return orderService.addOrder(id, submissionDate, startDate, endDate, kindOfWork, cost, garageId, garagePlaceId, masterName, orderStatus);
    }

    public boolean removeOrder(int id) {
        return orderService.removeOrder(id);
    }

    public void cancelOrder(int id) {
        orderService.cancelOrder(id);
    }

    public void closeOrder(int id) {
        orderService.closeOrder(id);
    }

    public void shiftEndTimeOrders(int hours, int minutes) {
        orderService.shiftEndTimeOrders(hours, minutes);
    }

    public List<Order> getAllOrdersSorted(String sortBy) {
        return orderService.getAllOrdersSorted(sortBy);
    }

    public List<Order> getAllOrdersInProgress(String sortBy) {
        return orderService.getAllOrdersInProgress(sortBy);
    }

    public List<Master> getMastersByOrder (int id) {
        return orderService.getMastersByOrder(id);
    }

    public List<Order> getOrdersByPeriod (LocalDateTime startPeriod, LocalDateTime endPeriod, String sortBy) {
        return orderService.getOrdersByPeriod(startPeriod, endPeriod, sortBy);
    }

    public LocalDateTime getNearestFreeDate() {
        return orderService.getNearestFreeDate();
    }

    public boolean importOrder(String fileName) {
        return orderService.importOrder(fileName);
    }

    public boolean exportOrder(int id, String fileName) {
        return orderService.exportOrder(id, fileName);
    }

    public void saveState() {
        orderService.saveState();
    }

    public void loadState() {
        orderService.loadState();
    }
}