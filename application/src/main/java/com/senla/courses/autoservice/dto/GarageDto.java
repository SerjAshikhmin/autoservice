package com.senla.courses.autoservice.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Setter
@Getter
public class GarageDto {

    private int id;
    private String address;
    @JsonManagedReference
    private List<GaragePlaceDto> garagePlaces;

    public GarageDto(int id, String address, List<GaragePlaceDto> garagePlaces) {
        this.id = id;
        this.address = address;
        this.garagePlaces = garagePlaces;
    }

    @Override
    public String toString() {
        String result = String.format("Garage № %d, address: %s, garagePlaces: ", id, address);
        return result + garagePlaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GarageDto garageDto = (GarageDto) o;
        return id == garageDto.id &&
                address.equals(garageDto.address) &&
                Objects.equals(garagePlaces, garageDto.garagePlaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, garagePlaces);
    }
}
