package pl.sda.crm.entity;

import javax.persistence.*;
import java.time.LocalDate;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private PremiumStatus premiumStatus;

    public Customer() {
        this.id = UUID.randomUUID(); //czesto w systemach produkcyjnych - nigdy sie nie powiela - unikalne identyfikatory
        this.addresses = new ArrayList<>();
        this.premiumStatus = new PremiumStatus(TypePremiumStatus.BASIC);
    }

    public UUID getId() {
        return id;
    }

    public PremiumStatus getPremiumStatus(){
        return premiumStatus;
    }

    public void addAddresses(Address address){
        if(!this.addresses.contains(address)){
            this.addresses.add(address);
        }
    }

    public void addPremiumStatus(PremiumStatus premiumStatus){
        this.premiumStatus = premiumStatus;
        //this.premiumStatus.setExpireAt(premiumStatus.getExpireAt());
        //this.premiumStatus.setType(premiumStatus.getType());
    }

    public void updatePremiumStatus(TypePremiumStatus status){
        /*if( status == TypePremiumStatus.SILVER){
            this.premiumStatus.setExpireAt();
        }*/
        switch(status){
            case BASIC:
                premiumStatus.setType(TypePremiumStatus.BASIC);
                break;
            case SILVER:
                premiumStatus.setExpireAt(LocalDate.now().plusMonths(3));
                premiumStatus.setType(TypePremiumStatus.SILVER);
                break;
            case GOLD:
                premiumStatus.setType(TypePremiumStatus.GOLD);
                premiumStatus.setExpireAt(LocalDate.now().plusMonths(6));
                break;
            default:
                System.out.println("Invalid PremiumStatus.");
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
