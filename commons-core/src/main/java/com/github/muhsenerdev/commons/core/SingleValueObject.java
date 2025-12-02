package com.github.muhsenerdev.commons.core;

public abstract class SingleValueObject<T> {

    public abstract T getValue();

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SingleValueObject<?> that = (SingleValueObject<?>) o;
        return getValue().equals(that.getValue());
    }

    public int hashCode() {
        return getValue().hashCode();
    }

    public String toString() {
        return getValue().toString();
    }

}
