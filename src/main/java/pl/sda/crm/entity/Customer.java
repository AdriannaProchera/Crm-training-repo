package pl.sda.crm.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//standarodwa strategia dziedziczenia
@DiscriminatorColumn(name = "customer_type") //ustala nazwe kolumny okreslajaca typ obiektu

public abstract class Customer {
    @Id
    private UUID id; //mozna wygenerowac w konstruktorze

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Address> addresses;

    public Customer() {
        this.id = UUID.randomUUID(); //czesto w systemach produkcyjnych - nigdy sie nie powiela - unikalne identyfikatory
        this.addresses = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void addAddresses(Address address){
        if(!this.addresses.contains(address)){
            this.addresses.add(address);
        }
    }
    //musimy przekazywac kopie
    public List<Address> getAddresses(){
        return new ArrayList<>(addresses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id);
    }
//tu uzywamy tych pol ktore sa niezmienne
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
