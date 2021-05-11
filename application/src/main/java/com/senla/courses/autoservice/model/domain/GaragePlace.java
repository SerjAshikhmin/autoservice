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
@Table(name = "GaragePlace")
public class GaragePlace implements Serializable {

    private static final long serialVersionUID = -4862926644813433701L;
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "garage_id")
    private Garage garage;
    private String type;
    private int area;
    private boolean busy;
    @OneToOne(mappedBy = "garagePlace")
    private Order order;

    public GaragePlace(int id, Garage garage, String type, int area) {
        this.id = id;
        this.garage = garage;
        this.type = type;
        this.area = area;
    }

    public GaragePlace(int id, Garage garage, String type, int area, boolean busy) {
        this.id = id;
        this.garage = garage;
        this.type = type;
        this.area = area;
        this.busy = busy;
    }

    public int getGarageId() {
        return this.garage.getId();
    }

    @Override
    public String toString() {
        String result = String.format("GaragePlace № %d, type: %s, area: %s, is %s busy", id, type, area, busy ? "" : "not");
        return result;
    }
}
