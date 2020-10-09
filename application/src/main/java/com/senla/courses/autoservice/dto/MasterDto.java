package com.senla.courses.autoservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
}
