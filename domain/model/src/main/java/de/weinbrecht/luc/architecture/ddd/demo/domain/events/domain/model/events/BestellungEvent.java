package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Kundennummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.AbholortReferenz;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.Adresse;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.type.DomainEvent;
import io.github.domainprimitives.object.DomainObject;
import lombok.Getter;

@Getter
public abstract class BestellungEvent extends DomainObject implements DomainEvent  {

    private final Kundennummer kundennummer;
    private final Adresse adresse;
    private final AbholortReferenz abholortReferenz;

    protected BestellungEvent(Kundennummer kundennummer, Adresse adresse) {
        this.kundennummer = kundennummer;
        this.adresse = adresse;
        this.abholortReferenz = null;

        validateNotNull(adresse, "Adresse");

        validate();
    }

    protected BestellungEvent(Kundennummer kundennummer, AbholortReferenz abholortReferenz) {
        this.kundennummer = kundennummer;
        this.adresse = null;
        this.abholortReferenz = abholortReferenz;

        validateNotNull(abholortReferenz, "Abholort");

        validate();
    }

    @Override
    protected void validate() {
        validateNotNull(kundennummer, "Kundennummer");

        evaluateValidations();
    }
}
