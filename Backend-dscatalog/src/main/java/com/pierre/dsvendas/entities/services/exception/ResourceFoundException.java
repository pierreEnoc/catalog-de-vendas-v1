package com.pierre.dsvendas.entities.services.exception;

public class ResourceFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceFoundException(String msg) {
        super(msg);
    }
}
