package com.senla.courses.autoservice.exceptions.garageexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class GarageNotFoundException extends RuntimeException {

    public GarageNotFoundException(String message) {
        super(message);
    }
}