package com.pierre.dsvendas.resources.exceptions;

import com.pierre.dsvendas.entities.services.exception.DatabaseException;
import com.pierre.dsvendas.entities.services.exception.ResourceFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceFoundException.class)
    public ResponseEntity<StandarError> entityNotFound(ResourceFoundException e, HttpServletRequest request) {
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
        err.setError("Resource not found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
