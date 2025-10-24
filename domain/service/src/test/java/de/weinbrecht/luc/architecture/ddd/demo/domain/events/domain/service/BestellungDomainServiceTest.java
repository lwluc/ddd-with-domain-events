package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.service;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellnummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Bestellung;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.Kundennummer;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.*;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.abfrage.BestellungsabfrageEvent;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.erzeugung.BestellungsaufgabeEventMitAbholort;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.erzeugung.BestellungsaufgabeMitAdresseEvent;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.usecase.out.BestellungRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BestellungDomainServiceTest {

    private final AdresseService adresseServiceMock = mock(AdresseService.class);
    private final BestellungRepository bestellungRepositoryMock = mock(BestellungRepository.class);

    private BestellungDomainService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new BestellungDomainService(adresseServiceMock, bestellungRepositoryMock);
    }

    @Nested
    class CreationTest {

        private static final BestellungsaufgabeEventMitAbholort BESTELLUNGSAUFGABE_EVENT_MIT_ABHOLORT =
                new BestellungsaufgabeEventMitAbholort(
                    new Kundennummer("12"),
                    new AbholortReferenz("12")
                );

        private static final BestellungsaufgabeMitAdresseEvent BESTELLUNGSAUFGABE_MIT_ADRESSE_EVENT =
                new BestellungsaufgabeMitAdresseEvent(
                        new Kundennummer("12"),
                        new Adresse(
                                new Strasse("Testweg"),
                                new Hausnummer("12a"),
                                new Postleitzahl("12345"),
                            new Ort("Testung")
                        )
                );

        @Test
        void should_throw_if_creation_event_with_abholort_is_null() {
            assertThatThrownBy(() -> classUnderTest.create((BestellungsaufgabeEventMitAbholort) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Bestellung darf nicht leer sein");
        }

        @Test
        void should_throw_if_creation_event_with_adresse_is_null() {
            assertThatThrownBy(() -> classUnderTest.create((BestellungsaufgabeMitAdresseEvent) null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Bestellung darf nicht leer sein");
        }

        @Test
        void should_create_with_adresse() {
            Bestellnummer result = classUnderTest.create(BESTELLUNGSAUFGABE_MIT_ADRESSE_EVENT);

            assertThat(result).isNotNull();
            verify(bestellungRepositoryMock).insert(any(Bestellnummer.class), eq(BESTELLUNGSAUFGABE_MIT_ADRESSE_EVENT));
        }

        @Test
        void should_create_with_abholort_referenz() {
            Bestellnummer result = classUnderTest.create(BESTELLUNGSAUFGABE_EVENT_MIT_ABHOLORT);

            assertThat(result).isNotNull();
            verify(bestellungRepositoryMock).insert(any(Bestellnummer.class), eq(BESTELLUNGSAUFGABE_EVENT_MIT_ABHOLORT));
        }
    }

    @Nested
    class QueryTest {

        private static final Bestellnummer BESTELLNUMMER = new Bestellnummer("77");

        private static final BestellungsabfrageEvent BESTELLUNGSABFRAGE_EVENT_ABHOLORT = new BestellungsabfrageEvent(
                BESTELLNUMMER,
                new Kundennummer("12"),
                new AbholortReferenz("12")
        );

        private static final BestellungsabfrageEvent BESTELLUNGSABFRAGE_EVENT_ADRESSE = new BestellungsabfrageEvent(
                BESTELLNUMMER,
                new Kundennummer("12"),
                new Adresse(
                        new Strasse("Testweg"),
                        new Hausnummer("12a"),
                        new Postleitzahl("12345"),
                        new Ort("Testung")
                )
        );

        @Test
        void should_throw_if_query_with_null_bestellnummer() {
            assertThatThrownBy(() -> classUnderTest.query(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Bestellung ohne Nummer kann nicht abgerufen werden");
        }

        @Test
        void should_query_from_repo_with_abholort() {
            final Adresse expectedAdresse = mock(Adresse.class);
            when(bestellungRepositoryMock.find(BESTELLNUMMER)).thenReturn(BESTELLUNGSABFRAGE_EVENT_ABHOLORT);
            when(adresseServiceMock.getAbholort(BESTELLUNGSABFRAGE_EVENT_ABHOLORT.getAbholortReferenz()))
                    .thenReturn(expectedAdresse);

            Bestellung result = classUnderTest.query(BESTELLNUMMER);

            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(result.getBestellnummer()).isEqualTo(BESTELLNUMMER);
            softAssertions.assertThat(result.getKundennummer()).isEqualTo(BESTELLUNGSABFRAGE_EVENT_ABHOLORT.getKundennummer());
            softAssertions.assertThat(result.getAdresse()).isEqualTo(expectedAdresse);
            softAssertions.assertAll();
            verify(adresseServiceMock, never()).getAdresse(any());
        }

        @Test
        void should_query_from_repo_with_adresse() {
            final Adresse expectedAdresse = mock(Adresse.class);
            when(bestellungRepositoryMock.find(BESTELLNUMMER)).thenReturn(BESTELLUNGSABFRAGE_EVENT_ADRESSE);
            when(adresseServiceMock.getAdresse(BESTELLNUMMER)).thenReturn(expectedAdresse);

            Bestellung result = classUnderTest.query(BESTELLNUMMER);

            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(result.getBestellnummer()).isEqualTo(BESTELLNUMMER);
            softAssertions.assertThat(result.getKundennummer()).isEqualTo(BESTELLUNGSABFRAGE_EVENT_ABHOLORT.getKundennummer());
            softAssertions.assertThat(result.getAdresse()).isEqualTo(expectedAdresse);
            softAssertions.assertAll();
            verify(adresseServiceMock, never()).getAbholort(any());
        }
    }
}