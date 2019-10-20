package com.eventmate.error;

public enum Error {
    EMAIL_EXIST("Email exists in database."),
    USERNAME_EXIST("Username exists in database."),
    USER_NOT_ALLOWED("User is not allowed to perform this operation."),
    EVENT_ALREADY_CONFIRMED("This event already has administrator responsible for."),
    CANNOT_CONFIRM_PIVATE_EVENT("Private event cannot be confirmed."),
    EVENT_REMOVED("This event has been removed."),
    PREFFERED_DATE_DOES_NOT_MATCH("Given preffered date is beyond event's dates.");

    private String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
