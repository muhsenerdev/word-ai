package com.github.muhsenerdev.wordai.users.domain;

import java.io.Serializable;
import java.util.UUID;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.jpa.BaseEmbeddableId;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class RoleId extends BaseEmbeddableId<UUID> implements Serializable {

    private RoleId(UUID value) {
        super(value);
    }

    public static RoleId of(UUID value) throws InvalidDomainObjectException {
        return new RoleId(value);
    }

    public static RoleId random() {
        return new RoleId(UUID.randomUUID());
    }

}
