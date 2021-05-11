package com.senla.courses.autoservice.exceptions.garageexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GarageAddingException extends RuntimeException {

    public GarageAddingException(String message) {
        super(message);
    }
}