package com.senla.courses.autoservice.exceptions.masterexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class MasterModifyingException extends RuntimeException {

    public MasterModifyingException(String message) {
        super(message);
    }
}