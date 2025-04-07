package com.kata.kata.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class KataServiceTest {

    private final KataService service = new KataService();

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "3, FOOFOO",
            "5, BARBAR",
            "7, QUIX",
            "9, FOO",
            "15, FOOBARBAR",
            "33, FOOFOOFOO",
            "51, FOOBAR",
            "53, BARFOO"
    })
    void shouldTransformNumbersCorrectly(int input, String expected) {
        assertEquals(expected, service.transform(input));
    }



    @Test
    void shouldThrowExceptionForNumberBelowZero() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.transform(-1)
        );
        assertTrue(exception.getMessage().contains("Le nombre doit etre 0 et 100"));
    }

    @Test
    void shouldThrowExceptionForNumberAboveHundred() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.transform(101)
        );
        assertTrue(exception.getMessage().contains("Le nombre doit etre 0 et 100"));
    }
}
