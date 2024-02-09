package com.example.githubcheck.Controller;

import com.example.githubcheck.Exceptions.UnknownErrorException;
import com.example.githubcheck.Exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsController {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(UnknownErrorException.class)
    public ResponseEntity<?> UnknownError() {
        return ResponseEntity.badRequest().build();
    }


}
