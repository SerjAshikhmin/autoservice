package com.senla.courses.autoservice.service.comparators.order;

import com.senla.courses.autoservice.model.domain.Order;

import java.util.Comparator;

public class OrderBySubmissionDateComparator implements Comparator<Order> {

    private static OrderBySubmissionDateComparator instance;

    private OrderBySubmissionDateComparator() {}

    public static OrderBySubmissionDateComparator getInstance() {
        if (instance == null) {
            instance = new OrderBySubmissionDateComparator();
        }
        return instance;
    }

    @Override
    public int compare(Order order, Order t1) {
        return order.getSubmissionDate().compareTo(t1.getSubmissionDate());
    }
}
