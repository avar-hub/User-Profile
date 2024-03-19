package com.socials.UserProfile.advice;

import com.socials.UserProfile.exception.UserNotSavedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotSavedException.class)
    public ResponseEntity<String> handleUserNotSavedException(UserNotSavedException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
