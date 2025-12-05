package com.github.muhsenerdev.commons.jpa;

import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.core.SingleValueObject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Slug extends SingleValueObject<String> {

    private static final Pattern PATTERN = Pattern.compile("^[a-z0-9]+(?:-[a-z0-9]+)*$");

    @Column(name = "value")
    private String value;

    private Slug(String value) {
        DomainUtils.hasText(value, "Slug value cannot be null or blank", "slug.blank");
        String trimmed = value.trim();

        if (!PATTERN.matcher(trimmed).matches()) {
            throw new InvalidDomainObjectException("Invalid slug format: " + value, "slug.invalid");

        }

        this.value = value;

    }

    public static Slug of(String value) {
        return new Slug(value);
    }

    @Override
    public String getValue() {
        return this.value;
    }

}
