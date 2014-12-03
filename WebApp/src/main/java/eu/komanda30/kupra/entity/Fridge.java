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

    private UserId userId;

    private int productId;

    private double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
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
}
