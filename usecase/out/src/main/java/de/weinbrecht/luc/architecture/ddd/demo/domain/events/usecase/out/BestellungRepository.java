package de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.out;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellnummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.abfrage.BestellungsabfrageEvent;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.erzeugung.BestellungsaufgabeEvent;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception.BestellungNotFoundException;

public interface BestellungRepository {

    void insert(Bestellnummer bestellnummer, BestellungsaufgabeEvent bestellungsaufgabeEvent);

    BestellungsabfrageEvent find(Bestellnummer bestellnummer) throws BestellungNotFoundException;
}
