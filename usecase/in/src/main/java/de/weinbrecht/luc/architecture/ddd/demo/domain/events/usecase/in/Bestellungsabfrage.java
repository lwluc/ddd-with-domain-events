package de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.in;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellnummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellung;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception.BestellungNotFoundException;

public interface Bestellungsabfrage {

    Bestellung query(Bestellnummer bestellnummer) throws BestellungNotFoundException;
}
