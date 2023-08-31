package com.example.githubcheck.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE,reason = ("Not Acceptable"))
public class NotAcceptableException extends RuntimeException{
}
