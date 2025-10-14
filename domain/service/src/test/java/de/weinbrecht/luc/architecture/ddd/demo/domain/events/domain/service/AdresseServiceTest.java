package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.service;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellnummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.AbholortReferenz;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.Adresse;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.out.AbholortQuery;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.out.AdressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AdresseServiceTest {

    private final AdressRepository adressRepositoryMock = mock(AdressRepository.class);
    private final AbholortQuery abholortQueryMock = mock(AbholortQuery.class);

    private AdresseService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new AdresseService(adressRepositoryMock, abholortQueryMock);
    }

    @Test
    void should_load_adresse_from_repo() {
        final Bestellnummer bestellnummer = mock(Bestellnummer.class);
        final Adresse expectedAdresse = mock(Adresse.class);
        when(adressRepositoryMock.find(bestellnummer)).thenReturn(expectedAdresse);

        Adresse result = classUnderTest.getAdresse(bestellnummer);

        assertThat(result).isEqualTo(expectedAdresse);
        verifyNoInteractions(abholortQueryMock);
    }

    @Test
    void should_query_abholhort() {
        final AbholortReferenz abholortReferenz = mock(AbholortReferenz.class);
        final Adresse expectedAdresse = mock(Adresse.class);
        when(abholortQueryMock.find(abholortReferenz)).thenReturn(expectedAdresse);

        Adresse result = classUnderTest.getAbholort(abholortReferenz);

        assertThat(result).isEqualTo(expectedAdresse);
        verifyNoInteractions(adressRepositoryMock);
    }
}