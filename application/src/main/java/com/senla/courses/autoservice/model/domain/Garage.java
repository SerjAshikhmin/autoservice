package com.senla.courses.autoservice.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Garage")
public class Garage implements Serializable {

    private static final long serialVersionUID = -4862926644813433702L;
    @Id
    private int id;
    private String address;
    @OneToMany(mappedBy = "garage", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<GaragePlace> garagePlaces;

    public Garage(int id, String address, List<GaragePlace> garagePlaces) {
        this.id = id;
        this.address = address;
        this.garagePlaces = garagePlaces;
    }

    @Override
    public String toString() {
        String result = String.format("Garage № %d, address: %s, garagePlaces: ", id, address);
        return result + garagePlaces;
    }
}
