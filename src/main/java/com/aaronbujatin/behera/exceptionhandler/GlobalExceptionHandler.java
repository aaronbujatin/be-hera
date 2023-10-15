package com.aaronbujatin.behera.exceptionhandler;

import com.aaronbujatin.behera.exception.InvalidArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidArgumentException.class})
    public ResponseEntity<Object> handleUsernameAlreadyExists(InvalidArgumentException invalidArgumentException){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(invalidArgumentException.getMessage());
    }

}
