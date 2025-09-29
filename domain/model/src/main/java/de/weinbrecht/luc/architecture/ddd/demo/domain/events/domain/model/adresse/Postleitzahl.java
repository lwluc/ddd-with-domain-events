package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.isPattern;

public class Postleitzahl extends ValueObject<String> {

    public Postleitzahl(String value) {
        super(value, isPattern("\\d{5})"));
    }

}