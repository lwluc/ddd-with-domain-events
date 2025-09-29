package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.isNotBlank;

public class Hausnummer extends ValueObject<String> {

    public Hausnummer(String value) {
        super(value, isNotBlank());
    }

}