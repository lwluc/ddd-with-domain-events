package de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception;

public class AdresseNotFoundException extends RuntimeException {
    public AdresseNotFoundException(String message) {
        super(message);
    }
}
