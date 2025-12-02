
package com.github.muhsenerdev.commons.jpa;

import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.core.SingleValueObject;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BaseEmbeddableId<T> extends SingleValueObject<T> {

    @Column(name = "id")
    private T value;

    protected BaseEmbeddableId() {
    }

    protected BaseEmbeddableId(T value) {
        this.value = DomainUtils.notNull(value, "ID value cannot be null for " + this.getClass().getName());
    }

}
