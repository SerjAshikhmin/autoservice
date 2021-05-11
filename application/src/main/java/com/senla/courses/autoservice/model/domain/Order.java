package com.senla.courses.autoservice.model.domain;

import com.senla.courses.autoservice.model.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Orders")
public class Order implements Serializable {

    private static final long serialVersionUID = -4862926644813433704L;
    @Id
    private int id;
    private LocalDateTime submissionDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String kindOfWork;
    private int cost;
    @OneToOne
    @PrimaryKeyJoinColumn
    private GaragePlace garagePlace;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<Master> masters;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(int id, LocalDateTime submissionDate, LocalDateTime startDate, LocalDateTime endDate, String kindOfWork, int cost,
                 GaragePlace garagePlace, List<Master> masters, OrderStatus status) {
        this.id = id;
        this.submissionDate = submissionDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.kindOfWork = kindOfWork;
        this.cost = cost;
        this.garagePlace = garagePlace;
        this.masters = masters;
        this.status = status;
        this.garagePlace.setBusy(true);
        for (Master master : this.masters) {
            if (master != null) {
                master.setOrder(this);
                master.setBusy(true);
            }
        }
    }

    @Override
    public String toString() {
        String result = String.format("Order № %d, submission date: %s; start date: %s; end date: %s; kind of work: %s," +
                " cost: %d, status: %s, garage place: %s; masters: %s", id, submissionDate, startDate, endDate,
                kindOfWork, cost, status.toString(), garagePlace, masters);

        return result;
    }
}
