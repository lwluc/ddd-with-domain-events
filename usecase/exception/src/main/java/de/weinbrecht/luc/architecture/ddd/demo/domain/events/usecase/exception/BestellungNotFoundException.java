package de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception;

public class BestellungNotFoundException extends RuntimeException {
    public BestellungNotFoundException(String message) {
        super(message);
    }
}
