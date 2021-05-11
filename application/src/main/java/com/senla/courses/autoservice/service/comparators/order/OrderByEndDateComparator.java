package com.senla.courses.autoservice.service.comparators.order;

import com.senla.courses.autoservice.model.domain.Order;

import java.util.Comparator;

public class OrderByEndDateComparator implements Comparator<Order> {

    private static OrderByEndDateComparator instance;

    private OrderByEndDateComparator() {}

    public static OrderByEndDateComparator getInstance() {
        if (instance == null) {
            instance = new OrderByEndDateComparator();
        }
        return instance;
    }

    @Override
    public int compare(Order order, Order t1) {
        return order.getEndDate().compareTo(t1.getEndDate());
    }
}
