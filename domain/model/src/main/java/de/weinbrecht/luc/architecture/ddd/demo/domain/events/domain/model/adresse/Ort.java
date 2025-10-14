package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.isNotBlank;

public class Ort extends ValueObject<String> {

    public Ort(String value) {
        super(value, isNotBlank());
    }

}