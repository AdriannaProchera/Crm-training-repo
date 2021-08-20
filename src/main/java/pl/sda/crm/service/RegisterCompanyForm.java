package pl.sda.crm.service;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class RegisterCompanyForm {
    private final String name;
    private final String numberNip;

    public RegisterCompanyForm(String name, String numberNip) {
        this.name = requireNonNull(name);
        this.numberNip = requireNonNull(numberNip);
    }

    public String getName() {
        return name;
    }

    public String getNumberNip() {
        return numberNip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterCompanyForm that = (RegisterCompanyForm) o;
        return name.equals(that.name) && numberNip.equals(that.numberNip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, numberNip);
    }

    @Override
    public String toString() {
        return "RegisterCompanyForm{" +
                "name='" + name + '\'' +
                ", numberNip='" + numberNip + '\'' +
                '}';
    }
}
