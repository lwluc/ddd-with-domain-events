package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.erzeugung;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Kundennummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.Adresse;

public class BestellungsaufgabeMitAdresseEvent extends BestellungsaufgabeEvent {

    public BestellungsaufgabeMitAdresseEvent(Kundennummer kundennummer, Adresse adresse) {
        super(kundennummer, adresse);
    }
}
