package pl.sda.crm.service;

import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class RegisteredCustomerId {
    private final UUID id;

    public RegisteredCustomerId(UUID id) {
        this.id = requireNonNull(id);
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisteredCustomerId that = (RegisteredCustomerId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
