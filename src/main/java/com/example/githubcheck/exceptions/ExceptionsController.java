package com.example.githubcheck.exceptions;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionsController {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public Error userNotFoundException(UserNotFoundException ex) {
        return new Error(NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public Error handleResponseStatusException(ResponseStatusException ex) {
        return new Error(ex.getStatusCode().value(), ex.getMessage());
    }
}

