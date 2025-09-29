package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.service;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellnummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellung;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.BestellungsabfrageEvent;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.BestellungsaufgabeEvent;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception.BestellungException;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception.BestellungNotFoundException;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.in.Bestellungsabfrage;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.in.Bestellungserzeugung;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.out.BestellungRepository;
import io.github.domainprimitives.validation.InvariantException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BestellungDomainService implements Bestellungserzeugung, Bestellungsabfrage {

    private final AdresseService adresseService;
    private final BestellungRepository bestellungRepository;

    @Override
    public Bestellung create(BestellungsaufgabeEvent bestellungsaufgabeEvent) throws BestellungException {
        if (bestellungsaufgabeEvent == null) {
            throw new InvariantException(this.getClass().getName(), "Bestellung darf nicht leer sein.");
        }

        final Bestellung bestellung = new Bestellung(
                bestellungsaufgabeEvent,
                adresseService::getAdresse,
                adresseService::getAbholort
        );

        bestellungRepository.insert(bestellung);

        return bestellung;
    }

    @Override
    public Bestellung query(Bestellnummer bestellnummer) throws BestellungNotFoundException {
        if (bestellnummer == null) {
            throw new InvariantException(this.getClass().getName(), "Bestellung ohne Nummer kann nicht abgerufen werden.");
        }

        final BestellungsabfrageEvent bestellungsabfrageEvent = bestellungRepository.find(bestellnummer);

        return new Bestellung(
                bestellungsabfrageEvent,
                adresseService::getAdresse,
                adresseService::getAbholort
        );
    }
}
