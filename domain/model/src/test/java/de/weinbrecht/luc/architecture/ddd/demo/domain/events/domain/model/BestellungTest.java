package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model;

import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse.*;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.abfrage.BestellungsabfrageEvent;
import de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.events.erzeugung.BestellungsaufgabeEvent;
import io.github.domainprimitives.validation.InvariantException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class BestellungTest {

    private static final Bestellnummer BESTELLNUMMER = new Bestellnummer("77");
    private static final Kundennummer KUNDENNUMMER = new Kundennummer("99");
    private static final AbholortReferenz ABHOLORT_REFERENZ = new AbholortReferenz("12");

    private static final Adresse ADRESSE = new Adresse(
            new Strasse("Testweg"),
            new Hausnummer("12a"),
            new Postleitzahl("12345"),
            new Ort("Testung")
    );

    private static final BestellungsabfrageEvent BESTELLUNGSABFRAGE_EVENT_ABHOLORT = new BestellungsabfrageEvent(
            BESTELLNUMMER,
            KUNDENNUMMER,
            ABHOLORT_REFERENZ
    );

    private static final BestellungsabfrageEvent BESTELLUNGSABFRAGE_EVENT_ADRESSE = new BestellungsabfrageEvent(
            BESTELLNUMMER,
            KUNDENNUMMER,
            ADRESSE
    );

    private static final Function<Bestellnummer, Adresse> BESTELLNUMMER_ADRESSE_FUNCTION = (bestellnummer) -> null;
    private static final Function<AbholortReferenz, Adresse> ABHOLORT_REFERENZ_ADRESSE_FUNCTION = abholortReferenz -> null;

    @Test
    void should_create_valid_object_abholort() {
        assertDoesNotThrow(() ->
                new Bestellung(
                        BESTELLUNGSABFRAGE_EVENT_ABHOLORT,
                        BESTELLNUMMER_ADRESSE_FUNCTION,
                        ABHOLORT_REFERENZ_ADRESSE_FUNCTION
                )
        );
    }

    @Test
    void should_create_valid_object_adresse() {
        assertDoesNotThrow(() ->
                new Bestellung(
                        BESTELLUNGSABFRAGE_EVENT_ADRESSE,
                        BESTELLNUMMER_ADRESSE_FUNCTION,
                        ABHOLORT_REFERENZ_ADRESSE_FUNCTION
                )
        );
    }

    @Nested
    class TestCaching {

        @Nested
        class TestAbholortCallback {

            @Test
            void should_query_only_once() {
                final Function<Bestellnummer, Adresse> adresseCallbackMock = mock();
                final Function<AbholortReferenz, Adresse> abholortCallbackMock = mock();
                when(abholortCallbackMock.apply(ABHOLORT_REFERENZ)).thenReturn(ADRESSE);
                final Bestellung bestellung = new Bestellung(
                        BESTELLUNGSABFRAGE_EVENT_ABHOLORT,
                        adresseCallbackMock,
                        abholortCallbackMock
                );

                final Adresse adresseFirstCall = bestellung.getAdresse();
                final Adresse adresseSecondCall = bestellung.getAdresse();

                assertThat(adresseFirstCall).isEqualTo(adresseSecondCall);
                verify(abholortCallbackMock, times(1)).apply(ABHOLORT_REFERENZ);
                verifyNoInteractions(adresseCallbackMock);
            }

            @Test
            void should_query_twice_if_forced() {
                final Function<Bestellnummer, Adresse> adresseCallbackMock = mock();
                final Function<AbholortReferenz, Adresse> abholortCallbackMock = mock();
                when(abholortCallbackMock.apply(ABHOLORT_REFERENZ)).thenReturn(ADRESSE);
                Bestellung bestellung = new Bestellung(
                        BESTELLUNGSABFRAGE_EVENT_ABHOLORT,
                        adresseCallbackMock,
                        abholortCallbackMock
                );

                Adresse adresseFirstCall = bestellung.getAdresse();
                Adresse adresseSecondCall = bestellung.getAdresse(true);

                SoftAssertions softAssertions = new SoftAssertions();
                softAssertions.assertThat(adresseFirstCall).isEqualTo(ADRESSE);
                softAssertions.assertThat(adresseSecondCall).isEqualTo(ADRESSE);
                softAssertions.assertAll();
                verify(abholortCallbackMock, times(2)).apply(ABHOLORT_REFERENZ);
                verifyNoInteractions(adresseCallbackMock);
            }
        }

        @Nested
        class TestAdresseCallback {

            @Test
            void should_query_only_once() {
                final Function<Bestellnummer, Adresse> adresseCallbackMock = mock();
                final Function<AbholortReferenz, Adresse> abholortCallbackMock = mock();
                when(adresseCallbackMock.apply(BESTELLNUMMER)).thenReturn(ADRESSE);
                final Bestellung bestellung = new Bestellung(
                        BESTELLUNGSABFRAGE_EVENT_ADRESSE,
                        adresseCallbackMock,
                        abholortCallbackMock
                );

                final Adresse adresseFirstCall = bestellung.getAdresse();
                final Adresse adresseSecondCall = bestellung.getAdresse();

                assertThat(adresseFirstCall).isEqualTo(adresseSecondCall);
                verify(adresseCallbackMock, times(1)).apply(BESTELLNUMMER);
                verifyNoInteractions(abholortCallbackMock);
            }

            @Test
            void should_query_twice_if_forced() {
                final Function<Bestellnummer, Adresse> adresseCallbackMock = mock();
                final Function<AbholortReferenz, Adresse> abholortCallbackMock = mock();
                when(adresseCallbackMock.apply(BESTELLNUMMER)).thenReturn(ADRESSE);
                Bestellung bestellung = new Bestellung(
                        BESTELLUNGSABFRAGE_EVENT_ADRESSE,
                        adresseCallbackMock,
                        abholortCallbackMock
                );

                Adresse adresseFirstCall = bestellung.getAdresse();
                Adresse adresseSecondCall = bestellung.getAdresse(true);

                SoftAssertions softAssertions = new SoftAssertions();
                softAssertions.assertThat(adresseFirstCall).isEqualTo(ADRESSE);
                softAssertions.assertThat(adresseSecondCall).isEqualTo(ADRESSE);
                softAssertions.assertAll();
                verify(adresseCallbackMock, times(2)).apply(BESTELLNUMMER);
                verifyNoInteractions(abholortCallbackMock);
            }
        }
    }



    @Nested
    class TestInvariants {

        @Test
        void should_throw_if_address_supplier_is_null() {
            assertThatThrownBy(() ->
                    new Bestellung(BESTELLUNGSABFRAGE_EVENT_ADRESSE, null, ABHOLORT_REFERENZ_ADRESSE_FUNCTION)
            )
                    .isInstanceOf(InvariantException.class)
                    .hasMessageContaining("Adresse Supplier");
        }

        @Test
        void should_throw_if_abholort_supplier_is_null() {
            assertThatThrownBy(() ->
                    new Bestellung(BESTELLUNGSABFRAGE_EVENT_ABHOLORT, BESTELLNUMMER_ADRESSE_FUNCTION, null)
            )
                    .isInstanceOf(InvariantException.class)
                    .hasMessageContaining("Abholort Supplier");
        }

        @Test
        void should_throw_if_bestellnummer_is_null() {
            final BestellungsabfrageEvent bestellungsabfrageEventMock = mock(BestellungsabfrageEvent.class);
            when(bestellungsabfrageEventMock.getBestellnummer()).thenReturn(null);
            when(bestellungsabfrageEventMock.getKundennummer()).thenReturn(KUNDENNUMMER);
            when(bestellungsabfrageEventMock.getAbholortReferenz()).thenReturn(ABHOLORT_REFERENZ);

            assertThatThrownBy(() ->
                    new Bestellung(bestellungsabfrageEventMock, BESTELLNUMMER_ADRESSE_FUNCTION, ABHOLORT_REFERENZ_ADRESSE_FUNCTION)
            )
                    .isInstanceOf(InvariantException.class)
                    .hasMessageContaining("Bestellnummer");
        }

        @Test
        void should_throw_if_kundennummer_is_null() {
            final BestellungsabfrageEvent bestellungsabfrageEventMock = mock(BestellungsabfrageEvent.class);
            when(bestellungsabfrageEventMock.getBestellnummer()).thenReturn(BESTELLNUMMER);
            when(bestellungsabfrageEventMock.getKundennummer()).thenReturn(null);
            when(bestellungsabfrageEventMock.getAbholortReferenz()).thenReturn(ABHOLORT_REFERENZ);

            assertThatThrownBy(() ->
                    new Bestellung(bestellungsabfrageEventMock, BESTELLNUMMER_ADRESSE_FUNCTION, ABHOLORT_REFERENZ_ADRESSE_FUNCTION)
            )
                    .isInstanceOf(InvariantException.class)
                    .hasMessageContaining("Kundennummer");
        }

        @Test
        void should_throw_if_adresse_is_null_on_validate() {
            final BestellungsaufgabeEvent bestellungsaufgabeEventMock = mock(BestellungsaufgabeEvent.class);
            when(bestellungsaufgabeEventMock.getKundennummer()).thenReturn(KUNDENNUMMER);
            when(bestellungsaufgabeEventMock.isBestellungsspezifischeAdresse()).thenReturn(true);
            when(bestellungsaufgabeEventMock.getAdresse()).thenReturn(null);

            assertThatThrownBy(() ->
                    Bestellung.validate(BESTELLNUMMER, bestellungsaufgabeEventMock)
            )
                    .isInstanceOf(InvariantException.class)
                    .hasMessageContaining("Eine Bestellung ohne Abholort oder Adresse ist nicht erlaubt.");
        }

        @Test
        void should_not_throw_if_adresse_is_not_null_on_validate() {
            final BestellungsaufgabeEvent bestellungsaufgabeEventMock = mock(BestellungsaufgabeEvent.class);
            when(bestellungsaufgabeEventMock.getKundennummer()).thenReturn(KUNDENNUMMER);
            when(bestellungsaufgabeEventMock.isBestellungsspezifischeAdresse()).thenReturn(true);
            when(bestellungsaufgabeEventMock.getAdresse()).thenReturn(mock(Adresse.class));

            assertDoesNotThrow(() -> Bestellung.validate(BESTELLNUMMER, bestellungsaufgabeEventMock));
        }
    }
}
