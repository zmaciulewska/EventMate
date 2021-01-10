package com.eventmate.error;

public enum Error {
    EMAIL_EXIST("Email jest wykorzystywany przez innego użytkownika."),
    USERNAME_EXIST("Login jest wykorzystywany przez innego użytkownika."),
    USER_NOT_ALLOWED("Użytkownik nie może wykonać tej operacji."),
    EVENT_ALREADY_CONFIRMED("Te wydarzenie zostało już zatwierdzone."),
    CANNOT_CONFIRM_PIVATE_EVENT("Wydarzenie prywatne nie wymaga zatwierdzania."),
    EVENT_REMOVED("Te wydarzenie zostało usunięte."),
    CONTACT_REMOVED("Ten kontakt został usunięty."),
    EVENT_OFFER_REMOVED("Ta oferta została usunięta."),
    WRONG_DATE_ORDER("Data rozpoczęcia nie może być po dacie zakończenia."),
    PREFFERED_DATE_DOES_NOT_MATCH("Preferowana data jest poza zakresem wydarzenia."),
    USER_NOT_EXISTS("Użytkownik nie istnieje."),
    USER_WITHOUT_SHOWCASE("Użytkownik nieposiadający wizytówki nie może wykonać tej operacji."),
    EVENT_OFFER_ALREADY_EXISTS("Nie można stworzyć kolejnej oferty do tego wydarzenia."),
    IDENTICAL_USER_CONTACT("Nie można nawiązać kontaktu między jednym użytkownikiem.");

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
