package com.senla.courses.autoservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


@NoArgsConstructor
@Setter
@Getter
public class MasterDto {

    private int id;
    private String name;
    private int category;
    private boolean busy;

    public MasterDto(int id, String name, int category) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.busy = false;
    }

    @Override
    public String toString() {
        String result = String.format("Master № %d, name: %s, category: %d, is %s busy", id, name, category, busy ? "" : "not");
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MasterDto masterDto = (MasterDto) o;
        return id == masterDto.id &&
                category == masterDto.category &&
                busy == masterDto.busy &&
                name.equals(masterDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, busy);
    }
}
