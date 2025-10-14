package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse;

import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AdresseTest {

    private static final Strasse STRASSE = new Strasse("Testweg");
    private static final Hausnummer HAUSNUMMER = new Hausnummer("12a");
    private static final Postleitzahl POSTLEITZAHL = new Postleitzahl("12345");
    private static final Ort ORT = new Ort("Testung");

    @Test
    void should_create_valid_object() {
        assertDoesNotThrow(() -> new Adresse(STRASSE, HAUSNUMMER, POSTLEITZAHL, ORT));
    }

    @Nested
    class TestInvariants {

        @Test
        void should_throw_if_strasse_null() {
            assertThatThrownBy(() -> new Adresse(null, HAUSNUMMER, POSTLEITZAHL, ORT))
                    .isInstanceOf(InvariantException.class)
                    .hasMessageContaining("Straße");
        }

        @Test
        void should_throw_if_hausnummer_null() {
            assertThatThrownBy(() -> new Adresse(STRASSE, null, POSTLEITZAHL, ORT))
                    .isInstanceOf(InvariantException.class)
                    .hasMessageContaining("Hausnummer");
        }

        @Test
        void should_throw_if_postleitzahl_null() {
            assertThatThrownBy(() -> new Adresse(STRASSE, HAUSNUMMER, null, ORT))
                    .isInstanceOf(InvariantException.class)
                    .hasMessageContaining("Postleitzahl");
        }

        @Test
        void should_throw_if_ort_null() {
            assertThatThrownBy(() -> new Adresse(STRASSE, HAUSNUMMER, POSTLEITZAHL, null))
                    .isInstanceOf(InvariantException.class)
                    .hasMessageContaining("Ort");
        }

        @Test
        void should_throw_if_all_null() {
            assertThatThrownBy(() -> new Adresse(null, null, null, null))
                    .isInstanceOf(InvariantException.class);
        }
    }
}