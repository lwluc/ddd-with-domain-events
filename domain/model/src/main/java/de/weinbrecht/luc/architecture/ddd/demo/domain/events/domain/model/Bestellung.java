package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.AbholortReferenz;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.Adresse;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.BestellungBoundaryEvent;
import io.github.domainprimitives.object.Aggregate;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.UUID;
import java.util.function.Function;

@Getter
public class Bestellung extends Aggregate {

    private final Bestellnummer bestellnummer;
    private final Kundennummer kundennummer;

    @Getter(AccessLevel.NONE)
    private final AbholortReferenz abholortReferenz;

    @Getter(AccessLevel.NONE)
    private final Function<Bestellnummer, Adresse> adresseSupplier;

    @Getter(AccessLevel.NONE)
    private final Function<AbholortReferenz, Adresse> abholortSupplier;

    public Bestellung(
            BestellungBoundaryEvent bestellungBoundaryEvent,
            Function<Bestellnummer, Adresse> adresseSupplier,
            Function<AbholortReferenz, Adresse>  abholortSupplier
    ) {
       this(
               new Bestellnummer(UUID.randomUUID().toString()),
               bestellungBoundaryEvent.getKundennummer(),
               bestellungBoundaryEvent.getAbholortReferenz(),
               adresseSupplier,
               abholortSupplier
       );
    }

    public Bestellung(
            Bestellnummer bestellnummer,
            Kundennummer kundennummer,
            AbholortReferenz abholortReferenz,
            Function<Bestellnummer, Adresse> adresseSupplier,
            Function<AbholortReferenz, Adresse> abholortSupplier
    ) {
        this.bestellnummer = bestellnummer;
        this.kundennummer = kundennummer;
        this.abholortReferenz = abholortReferenz;
        this.adresseSupplier = adresseSupplier;
        this.abholortSupplier = abholortSupplier;

        this.validate();
    }

    @Override
    protected void validate() {
        validateNotNull(bestellnummer, "ID");
        validateNotNull(kundennummer, "Kundennummer");
        validateNotNull(adresseSupplier, "Adresse Supplier");
        validateNotNull(abholortSupplier, "Abholort Supplier");

        evaluateValidations();
    }

    public Adresse getAdresse() {
        if (abholortReferenz != null) {
            return abholortSupplier.apply(abholortReferenz);
        }
        return adresseSupplier.apply(bestellnummer);
    }
}