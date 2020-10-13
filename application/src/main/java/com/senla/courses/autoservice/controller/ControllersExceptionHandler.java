package com.senla.courses.autoservice.controller;

import com.senla.courses.autoservice.exceptions.orderexceptions.OrderAddingException;
import com.senla.courses.autoservice.exceptions.orderexceptions.OrderModifyingException;
import com.senla.courses.autoservice.exceptions.orderexceptions.OrderNotFoundException;
import com.senla.courses.autoservice.exceptions.garageexceptions.GarageAddingException;
import com.senla.courses.autoservice.exceptions.garageexceptions.GarageNotFoundException;
import com.senla.courses.autoservice.exceptions.garageexceptions.GarageRemovingException;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterAddingException;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterModifyingException;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ControllersExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MasterAddingException.class)
    protected ResponseEntity<Exception> handleMasterAddingException() {
        return new ResponseEntity<>(new Exception("Error adding the master"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MasterNotFoundException.class)
    protected ResponseEntity<Exception> handleMasterNotFoundException() {
        return new ResponseEntity<>(new Exception("Master not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MasterModifyingException.class)
    protected ResponseEntity<Exception> handleMasterModificationException() {
        return new ResponseEntity<>(new Exception("Error removing/modifying the master"), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(GarageNotFoundException.class)
    protected ResponseEntity<Exception> handleGarageNotFoundException() {
        return new ResponseEntity<>(new Exception("Garage not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GarageAddingException.class)
    protected ResponseEntity<Exception> handleGarageAddingException() {
        return new ResponseEntity<>(new Exception("Error adding the garage"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GarageRemovingException.class)
    protected ResponseEntity<Exception> handleGarageRemovingException() {
        return new ResponseEntity<>(new Exception("Error removing the garage"), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    protected ResponseEntity<Exception> handleOrderNotFoundException() {
        return new ResponseEntity<>(new Exception("Order not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderAddingException.class)
    protected ResponseEntity<Exception> handleOrderAddingException() {
        return new ResponseEntity<>(new Exception("Error adding the order"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderModifyingException.class)
    protected ResponseEntity<Exception> handleOrderModifyingException() {
        return new ResponseEntity<>(new Exception("Error removing/modifying the order"), HttpStatus.NOT_MODIFIED);
    }
}