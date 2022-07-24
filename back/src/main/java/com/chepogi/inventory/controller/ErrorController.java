package com.chepogi.inventory.controller;

import com.chepogi.inventory.dto.HttpErrorResponse;
import com.chepogi.inventory.exceptions.DepartmentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalTime;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler( DepartmentException.class)
    public ResponseEntity<HttpErrorResponse> handleDepartmentException(final DepartmentException ex){
        HttpErrorResponse errorResponse = HttpErrorResponse.builder().status(HttpStatus.BAD_REQUEST)
                .timeStamp(LocalTime.now().toString()).message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
