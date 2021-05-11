package com.senla.courses.autoservice.service.comparators.order;

import com.senla.courses.autoservice.model.domain.Order;

import java.util.Comparator;

public class OrderByStartDateComparator implements Comparator<Order> {

    private static OrderByStartDateComparator instance;

    private OrderByStartDateComparator() {}

    public static OrderByStartDateComparator getInstance() {
        if (instance == null) {
            instance = new OrderByStartDateComparator();
        }
        return instance;
    }

    @Override
    public int compare(Order order, Order t1) {
        return order.getStartDate().compareTo(t1.getStartDate());
    }
}
