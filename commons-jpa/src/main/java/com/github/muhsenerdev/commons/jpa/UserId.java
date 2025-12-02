package com.github.muhsenerdev.commons.jpa;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserId extends BaseEmbeddableId<UUID> implements Serializable {

    private UserId(UUID value) {
        super(value);
    }

    public static UserId of(UUID value) {
        return new UserId(value);
    }

    public static UserId random() {
        return new UserId(UUID.randomUUID());
    }
}
