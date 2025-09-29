package de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.out;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellnummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellung;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.BestellungsabfrageEvent;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception.BestellungNotFoundException;

public interface BestellungRepository {

    void insert(Bestellung bestellung);

    BestellungsabfrageEvent find(Bestellnummer bestellnummer) throws BestellungNotFoundException;
}
