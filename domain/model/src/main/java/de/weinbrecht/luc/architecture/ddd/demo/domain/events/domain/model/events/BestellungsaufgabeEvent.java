package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Kundennummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.AbholortReferenz;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.Adresse;

public class BestellungsaufgabeEvent extends BestellungBoundaryEvent {

    protected BestellungsaufgabeEvent(Kundennummer kundennummer, Adresse adresse) {
        super(kundennummer, adresse);
    }

    protected BestellungsaufgabeEvent(Kundennummer kundennummer, AbholortReferenz abholortReferenz) {
        super(kundennummer, abholortReferenz);
    }
}
