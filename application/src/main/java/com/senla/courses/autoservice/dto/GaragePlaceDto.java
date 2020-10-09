package com.senla.courses.autoservice.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class GaragePlaceDto {

    private int id;
    @JsonBackReference
    private GarageDto garage;
    private String type;
    private int area;
    private boolean busy;

    public GaragePlaceDto(int id, GarageDto garage, String type, int area) {
        this.id = id;
        this.garage = garage;
        this.type = type;
        this.area = area;
    }

    public GaragePlaceDto(int id, GarageDto garage, String type, int area, boolean busy) {
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
