package com.solu_web.gestor_citas_medicas.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundExceptions.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
        ResourceNotFoundExceptions ex,
        WebRequest request
    ){
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            ex.getMessage(),
            request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGeneralException(
            Exception ex,
            WebRequest request
        ){
            ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
