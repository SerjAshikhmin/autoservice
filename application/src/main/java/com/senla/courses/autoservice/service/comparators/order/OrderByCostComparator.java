package com.senla.courses.autoservice.service.comparators.order;

import com.senla.courses.autoservice.model.domain.Order;

import java.util.Comparator;

public class OrderByCostComparator implements Comparator<Order> {

    private static OrderByCostComparator instance;

    private OrderByCostComparator() {}

    public static OrderByCostComparator getInstance() {
        if (instance == null) {
            instance = new OrderByCostComparator();
        }
        return instance;
    }

    @Override
    public int compare(Order order, Order t1) {
        if(order.getCost() > t1.getCost())
            return 1;
        else if(order.getCost()< t1.getCost())
            return -1;
        else
            return 0;
    }
}
