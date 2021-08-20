package pl.sda.crm.service;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class RegisterPersonForm {
    private final String firstName;
    private final String lastName;
    private final String pesel;


    public RegisterPersonForm(String firstName, String lastName, String pesel) {
        this.firstName = requireNonNull(firstName);
        this.lastName = requireNonNull(lastName);
        this.pesel = requireNonNull(pesel);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterPersonForm that = (RegisterPersonForm) o;
        return firstName.equals(that.firstName) && lastName.equals(that.lastName) && pesel.equals(that.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, pesel);
    }

    @Override
    public String toString() {
        return "RegisterPersonForm{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                '}';
    }
}
