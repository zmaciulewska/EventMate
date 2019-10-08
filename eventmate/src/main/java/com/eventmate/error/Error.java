package com.eventmate.error;

public enum Error {
    EMAIL_EXIST("Email exists in database."),
    USERNAME_EXIST("Username exists in database.");

    private String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
