package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Kundennummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.AbholortReferenz;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.Adresse;

public class BestellungsabfrageEvent extends BestellungBoundaryEvent {
    protected BestellungsabfrageEvent(Kundennummer kundennummer, Adresse adresse) {
        super(kundennummer, adresse);
    }

    protected BestellungsabfrageEvent(Kundennummer kundennummer, AbholortReferenz abholortReferenz) {
        super(kundennummer, abholortReferenz);
    }
}
