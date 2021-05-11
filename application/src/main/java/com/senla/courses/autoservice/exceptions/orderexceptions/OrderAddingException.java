package com.senla.courses.autoservice.exceptions.orderexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderAddingException extends RuntimeException {

    public OrderAddingException(String message) {
        super(message);
    }
}