package pl.sda.crm.entity;

import javax.persistence.Column;
import java.util.Objects;

import static pl.sda.crm.util.ArgumentValidator.validate;

public class Pesel {

    @Column(name = "pesel")
    private String value;

    //for hibernate
    private Pesel(){

    }
    public Pesel(String value){
        validate(value != null && value.matches("\\d{11}"), "pesel is invalid: " + value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pesel pesel = (Pesel) o;
        return Objects.equals(value, pesel.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
