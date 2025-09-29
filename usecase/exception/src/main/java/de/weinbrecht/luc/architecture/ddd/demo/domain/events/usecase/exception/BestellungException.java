package de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception;

public class BestellungException extends RuntimeException {
    public BestellungException(String message) {
        super(message);
    }

    public BestellungException(String message, Throwable cause) {
        super(message, cause);
    }
}
