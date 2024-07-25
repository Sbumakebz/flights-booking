package com.flight.booking;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@EnableWebMvc
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({NoHandlerFoundException.class })
    public ResponseEntity<Object> handleEntityNotFoundException (
            NoHandlerFoundException ex) {
        return new ResponseEntity<Object>(
               "Endpoint not found.", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFoundException (
            EntityNotFoundException ex) {
        return new ResponseEntity<Object>(
                ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
