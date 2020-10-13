package com.senla.courses.autoservice.exceptions.garageexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class GarageRemovingException extends RuntimeException {

    public GarageRemovingException(String message) {
        super(message);
    }
}