package com.eventmate.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ExceptionResponse> AppExceptionHandler(HttpServletRequest request, AppException ex){
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(ex.getMessage());
        response.setError(ex.getError());
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setPath(request.getServletPath());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> EntityNotFoundExceptionHandler(HttpServletRequest request, EntityNotFoundException ex){
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(ex.getMessage());
        response.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setPath(request.getServletPath());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
