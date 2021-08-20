package pl.sda.crm.entity;

import pl.sda.crm.service.RegisterPersonForm;
import pl.sda.crm.util.ArgumentValidator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static pl.sda.crm.util.ArgumentValidator.*;

@Entity
@DiscriminatorValue("PERSON")//ustalam wartosc w kolumnie customer_type
public class Person extends Customer{
    //pola wymagane - lepiej inicjalizowac w konstruktorze
    private String firstName;
    private String lastName;
    @Embedded//pola z klasy pesel beda zapisane wraz z polami Person w jednej tabeli
    private Pesel pesel;

    public Person(String firstName, String lastName, Pesel pesel) {
        //import statyczny do metod statycznych
        validate(firstName != null || !firstName.isBlank(), "firstname is invalid " +firstName);
        validate(lastName != null || !lastName.isBlank(), "lastname is invalid "+ lastName);

        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = requireNonNull(pesel, "pesel is null");
    }

    private Person(){}//tylko dla hibernate, nie do uzycia

    //meotda faktoryzujaca buduje na podstawie formularza
    public static Person from(RegisterPersonForm form) {
        return new Person(form.getFirstName(), form.getLastName(), new Pesel(form.getPesel()));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Pesel getPesel() {
        return pesel;
    }

    public void setPesel(Pesel pesel) {
        this.pesel = pesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return firstName.equals(person.firstName) && lastName.equals(person.lastName) && pesel.equals(person.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, pesel);
    }
}
