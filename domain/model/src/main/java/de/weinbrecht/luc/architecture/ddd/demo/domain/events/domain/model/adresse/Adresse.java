package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse;

import io.github.domainprimitives.object.ComposedValueObject;
import lombok.Getter;

@Getter
public class Adresse extends ComposedValueObject {

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
}
