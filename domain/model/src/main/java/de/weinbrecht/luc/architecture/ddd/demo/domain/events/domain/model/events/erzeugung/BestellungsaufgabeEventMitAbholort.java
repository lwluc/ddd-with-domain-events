package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.erzeugung;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Kundennummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.AbholortReferenz;

public class BestellungsaufgabeEventMitAbholort extends BestellungsaufgabeEvent {

    public BestellungsaufgabeEventMitAbholort(Kundennummer kundennummer, AbholortReferenz abholortReferenz) {
        super(kundennummer, abholortReferenz);
    }
}
