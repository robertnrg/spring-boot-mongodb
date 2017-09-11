package com.robert.mongodb.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

@ControllerAdvice
public final class ExceptionHandlerController {

    /**
     * Logger
     **/
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler({RuntimeException.class, IOException.class, Exception.class})
    public ResponseEntity<String> handleRuntimeException(final Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>("Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleBadInput(final HttpMessageNotReadableException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>("Request failed", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoHandlerFoundException.class, ResourceNotFoundException.class})
    public ResponseEntity<String> handle(final NoHandlerFoundException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>("Resource not defined", HttpStatus.NOT_FOUND);
    }

}