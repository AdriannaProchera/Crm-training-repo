package pl.sda.crm.entity;

import pl.sda.crm.util.ArgumentValidator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static pl.sda.crm.util.ArgumentValidator.validate;

@Entity
@Table(name = "premiumStatus")
public class PremiumStatus {
    //private boolean active;
    @Id
    private UUID id;
    private LocalDate expireAt;
    private TypePremiumStatus type;


    public PremiumStatus(LocalDate expireAt, TypePremiumStatus type) {
        this.id = UUID.randomUUID();
        this.expireAt = expireAt;
        this.type = type;
    }

    public PremiumStatus(TypePremiumStatus type) {
        this.id = UUID.randomUUID();
        this.type = type;
    }

    private PremiumStatus(){

    }

//    public void setActive(boolean active) {
//        this.active = active;
//    }

    public void setExpireAt(LocalDate expireAt) {
        this.expireAt = expireAt;
    }

    public void setType(TypePremiumStatus type) {
        this.type = type;
    }

//    public boolean isActive() {
//        return active;
//    }
    public UUID getId() {
    return id;
}

    public LocalDate getExpireAt() {
        return expireAt;
    }

    public TypePremiumStatus getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PremiumStatus that = (PremiumStatus) o;
        return expireAt.equals(that.expireAt) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash( expireAt, type);
    }


    @Override
    public String toString() {
        return "PremiumStatus{" +
                "expireAt=" + expireAt +
                ", type=" + type +
                '}';
    }
}
