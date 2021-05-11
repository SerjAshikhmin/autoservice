package com.senla.courses.autoservice.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Master")
public class Master implements Serializable {

    private static final long serialVersionUID = -4862926644813433703L;
    @Id
    private int id;
    private String name;
    private int category;
    private boolean busy;
    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Order order;

    public Master(int id, String name, int category) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.busy = false;
    }

    public Master(int id, String name, int category, boolean busy, Order order) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.busy = busy;
        this.order = order;
    }

    public int getOrderId() {
        return order.getId();
    }

    @Override
    public String toString() {
        String result = String.format("Master № %d, name: %s, category: %d, is %s busy", id, name, category, busy ? "" : "not");
        return result;
    }
}
