package com.eventmate.error;

public enum Error {
    EMAIL_EXIST("Email exists in database."),
    USERNAME_EXIST("Username exists in database."),
    USER_NOT_ALLOWED("User is not allowed to perform this operation."),
    EVENT_ALREADY_CONFIRMED("This event already has administrator responsible for."),
    CANNOT_CONFIRM_PIVATE_EVENT("Private event cannot be confirmed."),
    EVENT_REMOVED("This event has been removed."),
    PREFFERED_DATE_DOES_NOT_MATCH("Given preffered date is beyond event's dates."),
    USER_NOT_EXISTS("User with given id doesn't exist."),
    USER_WITHOUT_SHOWCASE("Użytkownik nieposiadający wizytówki nie może wykonać tej operacji."),
    EVENT_OFFER_ALREADY_EXISTS("Nie można stworzyć poraz kolejny ofery do tego wydarzenia"),
    IDENTICAL_USER_CONTACT("Cannot create contact between one user.");

    private String message;
    //private Long id;

    Error(String message) {
        this.message = message;
    }

    /*Error(String message, Long id) {
        this.message = message;
        this.id = id;
    }*/

    public String getMessage() {
        return message;
    }

}
