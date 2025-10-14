package de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.in;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellnummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.erzeugung.BestellungsaufgabeEventMitAbholort;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.erzeugung.BestellungsaufgabeMitAdresseEvent;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception.BestellungException;

public interface Bestellungserzeugung {

    Bestellnummer create(BestellungsaufgabeEventMitAbholort bestellungsaufgabeEventMitAbholort) throws BestellungException;

    Bestellnummer create(BestellungsaufgabeMitAdresseEvent bestellungsaufgabeMitAdresseEvent) throws BestellungException;
}
