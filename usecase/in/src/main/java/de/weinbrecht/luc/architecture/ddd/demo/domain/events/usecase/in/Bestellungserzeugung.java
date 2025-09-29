package de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.in;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellung;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.BestellungsaufgabeEvent;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception.BestellungException;

public interface Bestellungserzeugung {

    Bestellung create(BestellungsaufgabeEvent bestellungsaufgabeEvent) throws BestellungException;
}
