package com.senla.courses.autoservice.exceptions.masterexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MasterAddingException extends RuntimeException {

    public MasterAddingException(String message) {
        super(message);
    }
}