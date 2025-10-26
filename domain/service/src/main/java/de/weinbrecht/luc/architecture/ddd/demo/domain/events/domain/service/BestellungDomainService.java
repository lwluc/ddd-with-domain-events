package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.service;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellnummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellung;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.abfrage.BestellungsabfrageEvent;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.erzeugung.BestellungsaufgabeEvent;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.erzeugung.BestellungsaufgabeEventMitAbholort;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.erzeugung.BestellungsaufgabeEventMitAdresse;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception.BestellungException;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception.BestellungNotFoundException;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.in.Bestellungsabfrage;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.in.Bestellungserzeugung;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.out.BestellungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class BestellungDomainService implements Bestellungserzeugung, Bestellungsabfrage {

    private final AdresseService adresseService;
    private final BestellungRepository bestellungRepository;

    private Bestellnummer createFromEvent(BestellungsaufgabeEvent bestellungsaufgabeEvent) throws BestellungException {
        if (bestellungsaufgabeEvent == null) {
            throw new IllegalArgumentException("Bestellung darf nicht leer sein");
        }

        final Bestellnummer bestellnummer = new Bestellnummer(UUID.randomUUID().toString());
        Bestellung.validate(bestellnummer, bestellungsaufgabeEvent);

        bestellungRepository.insert(bestellnummer, bestellungsaufgabeEvent);

        return bestellnummer;
    }

    @Override
    public Bestellnummer create(BestellungsaufgabeEventMitAbholort bestellungsaufgabeEventMitAbholort) throws BestellungException {
        return createFromEvent(bestellungsaufgabeEventMitAbholort);
    }

    @Override
    public Bestellnummer create(BestellungsaufgabeEventMitAdresse bestellungsaufgabeEventMitAdresse) throws BestellungException {
        return createFromEvent(bestellungsaufgabeEventMitAdresse);
    }

    @Override
    public Bestellung query(Bestellnummer bestellnummer) throws BestellungNotFoundException {
        if (bestellnummer == null) {
            throw new IllegalArgumentException("Bestellung ohne Nummer kann nicht abgerufen werden");
        }

        final BestellungsabfrageEvent bestellungsabfrageEvent = bestellungRepository.find(bestellnummer);

        return new Bestellung(
                bestellungsabfrageEvent,
                adresseService::getAdresse,
                adresseService::getAbholort
        );
    }
}
