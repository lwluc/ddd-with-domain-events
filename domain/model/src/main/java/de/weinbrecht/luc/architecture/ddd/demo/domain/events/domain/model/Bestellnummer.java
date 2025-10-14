package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.isNotBlank;

public class Bestellnummer extends ValueObject<String> {

    public Bestellnummer(String value) {
        super(value, isNotBlank());
    }

}
