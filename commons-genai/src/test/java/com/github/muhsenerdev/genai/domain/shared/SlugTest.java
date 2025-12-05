package com.github.muhsenerdev.genai.domain.shared;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.jpa.Slug;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SlugTest {

    static Stream<String> provideInvalidSlugs() {
        return Stream.of("-invalid-slug", "invalid-slug-", "invalid--slug", "iNvalidSlug", "türkçe-slug");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSlugs")
    void invalidSlugs_shouldThrowException(String value) {
        assertThrows(InvalidDomainObjectException.class, () -> Slug.of(value));

    }
}