package com.pierre.dsvendas.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.pierre.dsvendas.entities.services.exception.DatabaseException;
import com.pierre.dsvendas.entities.services.exception.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandarError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
       HttpStatus status = HttpStatus.NOT_FOUND;
        StandarError err = new StandarError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Database exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
    
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandarError> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandarError err = new StandarError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Database exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Validation exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        
        for (FieldError f : e.getBindingResult().getFieldErrors()) {
        	err.addError(f.getField(), f.getDefaultMessage());
        }
        
        return ResponseEntity.status(status).body(err);
    }
    
    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<StandarError> amazonService(AmazonServiceException e, HttpServletRequest request) {
       HttpStatus status = HttpStatus.BAD_REQUEST;
        StandarError err = new StandarError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("AWS Exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
    
    
    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandarError> amazonClient(AmazonClientException e, HttpServletRequest request) {
       HttpStatus status = HttpStatus.BAD_REQUEST;
        StandarError err = new StandarError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("AWS Exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandarError> illegalArgumenet(IllegalArgumentException e, HttpServletRequest request) {
       HttpStatus status = HttpStatus.BAD_REQUEST;
        StandarError err = new StandarError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Bad request");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
