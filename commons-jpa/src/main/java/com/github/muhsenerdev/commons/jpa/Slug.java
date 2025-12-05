package com.github.muhsenerdev.commons.jpa;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Slug {

    private static final Pattern PATTERN = Pattern.compile("^[a-z0-9]+(?:-[a-z0-9]+)*$");

    private String value;

    public static Slug of(String value) {
        if (value == null || value.isBlank())
            return null;

        String trimmed = value.trim();

        if (!PATTERN.matcher(trimmed).matches()) {
            throw new InvalidDomainObjectException("Invalid slug format: " + value, "slug.invalid");

        }

        return new Slug(trimmed);

    }

    @Override
    public String toString() {
        return value;
    }
}
