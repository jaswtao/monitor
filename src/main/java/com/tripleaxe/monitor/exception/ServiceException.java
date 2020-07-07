package com.tripleaxe.monitor.exception;

public class ServiceException extends RuntimeException {
    private final String message;

    public ServiceException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
