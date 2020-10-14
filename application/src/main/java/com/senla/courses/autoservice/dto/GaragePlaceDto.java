package com.senla.courses.autoservice.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GaragePlaceDto that = (GaragePlaceDto) o;
        return id == that.id &&
                area == that.area &&
                busy == that.busy &&
                Objects.equals(garage, that.garage) &&
                type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, garage, type, area, busy);
    }
}
