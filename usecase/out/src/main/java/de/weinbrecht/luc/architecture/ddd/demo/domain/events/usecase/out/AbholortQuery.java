package de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.out;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.AbholortReferenz;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.Adresse;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.exception.AdresseNotFoundException;

public interface AbholortQuery {

    Adresse find(AbholortReferenz abholortReferenz) throws AdresseNotFoundException;
}
