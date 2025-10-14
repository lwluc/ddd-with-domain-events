package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.abfrage;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellnummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Kundennummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.AbholortReferenz;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.Adresse;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.BestellungBoundaryEvent;
import lombok.Getter;

public class BestellungsabfrageEvent extends BestellungBoundaryEvent {

    @Getter
    private final Bestellnummer bestellnummer;

    public BestellungsabfrageEvent(
            Bestellnummer bestellnummer,
            Kundennummer kundennummer,
            Adresse adresse) {
        super(kundennummer, adresse);
        this.bestellnummer = bestellnummer;
    }

    public BestellungsabfrageEvent(
            Bestellnummer bestellnummer,
            Kundennummer kundennummer,
            AbholortReferenz abholortReferenz
    ) {
        super(kundennummer, abholortReferenz);
        this.bestellnummer = bestellnummer;
    }
}
