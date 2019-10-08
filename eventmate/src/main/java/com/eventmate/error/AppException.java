package com.eventmate.error;

public class AppException extends RuntimeException  {

    private String error;

    public AppException(Error message) {
        super(message.getMessage());
        this.error = message.name();
    }

    public String getError() {
        return error;
    }
}