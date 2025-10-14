package de.weinbrecht.luc.architecture.ddd.demo.domain.events.domain.model.adresse;

import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PostleitzahlTest {

    @Test
    void should_create_valid_object() {
        assertDoesNotThrow(() -> new Postleitzahl("12345"));
    }

    @Nested
    class TestInvariants {

        @ParameterizedTest
        @NullAndEmptySource
        void should_throw_if_invalid(String value) {
            assertThatThrownBy(() -> new Postleitzahl(value)).isInstanceOf(InvariantException.class);
        }

        @Test
        void should_throw_if_invalid_value() {
            assertThatThrownBy(() -> new Postleitzahl("affe")).isInstanceOf(InvariantException.class);
        }
    }
}