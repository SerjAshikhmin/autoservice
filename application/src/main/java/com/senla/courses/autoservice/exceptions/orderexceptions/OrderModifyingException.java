package com.senla.courses.autoservice.exceptions.orderexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class OrderModifyingException extends RuntimeException {

    public OrderModifyingException(String message) {
        super(message);
    }
}