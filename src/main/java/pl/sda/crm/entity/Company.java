package pl.sda.crm.entity;

import pl.sda.crm.service.RegisterCompanyForm;
import pl.sda.crm.service.RegisterPersonForm;

import javax.persistence.*;
import java.util.Objects;

import static pl.sda.crm.util.ArgumentValidator.validate;

@Entity
@DiscriminatorValue("COMPANY")
public class Company extends Customer {

    private String name;
    private String numberNip;

    private Company() {
    }

    public Company(String name, String numberNip) {
        validate(name != null || !name.isBlank(), "name is invalid" + name);
        validate(numberNip != null && numberNip.matches("\\d{13}"), "nip is invalid: " + numberNip);
        this.name = name;
        this.numberNip = numberNip;
    }

    public String getName() {
        return name;
    }

    public String getNumberNip() {
        return numberNip;
    }

    public static Company from(RegisterCompanyForm form){
        return new Company(form.getName(), form.getNumberNip());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Company company = (Company) o;
        return name.equals(company.name) && numberNip.equals(company.numberNip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, numberNip);
    }
}

