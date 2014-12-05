package eu.komanda30.kupra.entity;

import javax.persistence.*;

/**
 * Created by Lukas on 2014.12.02.
 */


@Table(name="fridge")
@Entity
@SequenceGenerator(
        name="fridgeIdSequence",
        sequenceName="fridge_seq",
        allocationSize=1)
public class Fridge {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="fridgeIdSequence")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private KupraUser user;

    private int productId;

    private double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public KupraUser getUser() {
        return user;
    }

    public void setUser(KupraUser user) {
        this.user = user;
    }
}
