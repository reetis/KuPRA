package eu.komanda30.kupra.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@SequenceGenerator(
        name = "fridgeItemIdSequence",
        sequenceName = "fridge_item_seq",
        allocationSize = 1)
public class FridgeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fridgeItemIdSequence")
    private int id;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private BigDecimal amount = BigDecimal.ZERO;

    //For hibernate
    protected FridgeItem() {
    }

    public FridgeItem(Product product, BigDecimal amount) {
        this.product = product;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void increaseAmount(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public Product getProduct() {
        return product;
    }

    public void consumeAmount(BigDecimal amount){
        this.amount = this.amount.subtract(amount);
    }
}
