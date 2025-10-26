package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.erzeugung;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Kundennummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.AbholortReferenz;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.Adresse;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.BestellungEvent;

public abstract class BestellungsaufgabeEvent extends BestellungEvent {

    public BestellungsaufgabeEvent(
            Kundennummer kundennummer,
            Adresse adresse) {
        super(kundennummer, adresse);
    }

    public BestellungsaufgabeEvent(
            Kundennummer kundennummer,
            AbholortReferenz abholortReferenz
    ) {
        super(kundennummer, abholortReferenz);
    }

    public boolean isBestellungsspezifischeAdresse() {
        return getAdresse() != null;
    }
}
