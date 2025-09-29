package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.service;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellnummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.AbholortReferenz;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.Adresse;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.out.AbholortQuery;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.out.AdressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AdresseService {

    private final AdressRepository adressRepository;
    private final AbholortQuery abholortQuery;

    public Adresse getAdresse(Bestellnummer bestellnummer) {
        return adressRepository.find(bestellnummer);
    }

    public Adresse getAbholort(AbholortReferenz abholortReferenz) {
        return abholortQuery.find(abholortReferenz);
    }
}
