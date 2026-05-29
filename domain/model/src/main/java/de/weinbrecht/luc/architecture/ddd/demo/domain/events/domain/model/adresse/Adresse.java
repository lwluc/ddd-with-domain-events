package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse;

import io.github.domainprimitives.object.ValueObject;
import lombok.Getter;

import java.util.List;

@Getter
public class Adresse extends ValueObject {

    private final Strasse strasse;
    private final Hausnummer hausnummer;
    private final Postleitzahl postleitzahl;
    private final Ort ort;

    public Adresse(Strasse strasse, Hausnummer hausnummer, Postleitzahl postleitzahl, Ort ort) {
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.postleitzahl = postleitzahl;
        this.ort = ort;

        validate();
    }

    @Override
    protected void validate() {
        validateNotNull(strasse, "Straße");
        validateNotNull(hausnummer, "Hausnummer");
        validateNotNull(postleitzahl, "Postleitzahl");
        validateNotNull(ort, "Ort");

        evaluateValidations();
    }

    @Override
    protected List<Object> getEqualityComponents() {
        return List.of(strasse, hausnummer, postleitzahl, ort);
    }
}
