package de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.out;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellnummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.Adresse;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception.AdresseNotFoundException;

public interface AdressRepository {

    Adresse find(Bestellnummer bestellnummer) throws AdresseNotFoundException;
}
