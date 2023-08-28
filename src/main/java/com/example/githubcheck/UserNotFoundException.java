package com.example.githubcheck;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = (" User with that username was not found"))
public class UserNotFoundException extends RuntimeException {
}
