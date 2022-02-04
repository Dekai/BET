package com.dk.rr.sample;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class TestSample {

    @Test
    @DisplayName("Test Sample")
    void testSample() {
        String greeting = "Dekai, Good luck 2022";
        assertEquals(greeting, "Dekai, Good luck 2022");
    }

    @RepeatedTest(value = 6)
    @DisplayName("test Repeat test")
    void testRepeatTest(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        repetitionInfo.getCurrentRepetition();

        log.info(String.format("About to execute repetition %d of %d for %s", //
                repetitionInfo.getCurrentRepetition(), repetitionInfo.getTotalRepetitions(), testInfo.getTestMethod().get().getName()));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"ZHANG DEKAI", "ZHANG NA", "ZHANG XINYAN", "ZHANG XINYI", "ZHANG SAN"})
    @DisplayName("test parameterized")
    void testParameterized(String name) {
        assertTrue( ()-> name.contains("ZHANG"));
    }

    @ParameterizedTest
    @MethodSource("getNameSource")
    @DisplayName("testMethodSource")
    void testMethodSource(String name) {
        assertTrue(()-> name.contains("ZHANG"));
    }

    static Stream<String> getNameSource() {
        return Stream.of("ZHANG DEKAI", "ZHANG NA", "ZHANG XINYAN", "ZHANG XINYI", "ZHANG SAN", "DEKAI ZHANG");
    }

    @ParameterizedTest
    @MethodSource("getNameSource")
    @DisplayName("testHamcrest")
    void testHamcrest(String name) {
        Pattern zhangFamily = Pattern.compile("^Zhang\\s.*|.*ZHANG$", Pattern.CASE_INSENSITIVE);
        assertThat(name, new MatchesPattern(zhangFamily));
    }

}
