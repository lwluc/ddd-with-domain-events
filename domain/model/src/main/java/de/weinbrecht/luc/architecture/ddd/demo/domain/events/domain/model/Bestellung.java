package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.AbholortReferenz;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.Adresse;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.abfrage.BestellungsabfrageEvent;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.erzeugung.BestellungsaufgabeEvent;
import io.github.domainprimitives.object.Aggregate;
import io.github.domainprimitives.validation.InvariantException;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
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

    @Getter(AccessLevel.NONE)
    private final AtomicReference<Adresse> cachedAdresse = new AtomicReference<>();

    public Bestellung(
            BestellungsabfrageEvent bestellungsabfrageEvent,
            Function<Bestellnummer, Adresse> adresseSupplier,
            Function<AbholortReferenz, Adresse>  abholortSupplier
    ) {
        this(
                bestellungsabfrageEvent.getBestellnummer(),
                bestellungsabfrageEvent.getKundennummer(),
                bestellungsabfrageEvent.getAbholortReferenz(),
                adresseSupplier,
                abholortSupplier
        );
    }

    private Bestellung(
            Bestellnummer bestellnummer,
            Kundennummer kundennummer,
            AbholortReferenz abholortReferenz
    ) {
        this.bestellnummer = bestellnummer;
        this.kundennummer = kundennummer;
        this.abholortReferenz = abholortReferenz;

        this.adresseSupplier = null;
        this.abholortSupplier = null;

        this.validate();
    }

    private Bestellung(
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

        validateNotNull(adresseSupplier, "Adresse Supplier");
        validateNotNull(abholortSupplier, "Abholort Supplier");

        this.validate();
    }

    @Override
    protected void validate() {
        validateNotNull(bestellnummer, "Bestellnummer");
        validateNotNull(kundennummer, "Kundennummer");

        evaluateValidations();
    }

    public Adresse getAdresse() {
        return getAdresse(false);
    }

    public Adresse getAdresse(boolean forceUpdate) {
        if (!forceUpdate) {
            return Optional.ofNullable(cachedAdresse.get())
                    .orElseGet(this::loadAdresse);
        }
        return loadAdresse();
    }

    private Adresse loadAdresse() {
        Adresse neueAdresse = (abholortReferenz != null)
                ? abholortSupplier.apply(abholortReferenz)
                : adresseSupplier.apply(bestellnummer);

        cachedAdresse.set(neueAdresse);
        return neueAdresse;
    }

    public static void validate(Bestellnummer bestellnummer, BestellungsaufgabeEvent bestellungsaufgabeEvent) {
        new Bestellung(
                bestellnummer,
                bestellungsaufgabeEvent.getKundennummer(),
                bestellungsaufgabeEvent.getAbholortReferenz()
        );

        if (bestellungsaufgabeEvent.isBestellungsspezifischeAdresse() && bestellungsaufgabeEvent.getAdresse() == null) {
            throw new InvariantException("Bestellung", "Eine Bestellung ohne Abholort oder Adresse ist nicht erlaubt.");
        }
    }
}